package org.codeturnery.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.codeturnery.plugin.stage.RegistrationException;
import org.codeturnery.plugin.stage.Stage;

public interface Registry {
	/**
	 * Returns the non-expired plug-ins in an unsorted manner.
	 * <p>
	 * While the returned instance is just a view of the registry's state and not
	 * modifiable, its entries are the actual instances.
	 * 
	 * @return The set of currently known (non-expired) plug-ins. Entries may not
	 *         just be {@link Stage#REGISTERED} but {@link Stage#INSTALLED} or
	 *         {@link Stage#STARTED} as well.
	 */
	public Set<Plugin> getPlugins();

	/**
	 * @return All plug-ins in this registry, that are <strong>at least</strong>
	 *         registered. I.e. returned plug-ins may also be installed or started.
	 */
	public Set<RegisteredPlugin> getRegisteredPlugins();

	/**
	 * @return All plug-ins in this registry, that are <strong>at least</strong>
	 *         installed. I.e. returned plug-ins may also be started.
	 */
	public Set<InstalledPlugin> getInstalledPlugins();

	/**
	 * @return All plug-ins in this registry, that are started.
	 */
	public Set<StartedPlugin> getStartedPlugin();

	/**
	 * {@link Stage#REGISTERED registers} a new plug-in from the given
	 * {@code jarFile}.
	 * <p>
	 * The given {@code jarFile} must not be currently used for a non-expired
	 * plug-in in this registry.
	 * <p>
	 * Note that altering or removing the backing JAR file while the file is known
	 * in any way in the application (even if it is just {@link RegisteredPlugin
	 * registered}) will result in undefined behavior.
	 *
	 * @param jarFile
	 * @return A {@link Stage#REGISTERED} plug-in, not {@link Stage#INSTALLED} or
	 *         {@link Stage#STARTED}.
	 * @throws RegistrationException
	 * @throws IOException           if an I/O error has occurred when accessing the
	 *                               JAR file
	 */
	public RegisteredPlugin registerPlugin(final File jarFile) throws RegistrationException, IOException;

	/**
	 * Creates a list of proxies for the services provided by all
	 * {@link Stage#STARTED} plug-ins, that were exposed via the given interface.
	 * <p>
	 * The services are not exposed directly because references to the actual
	 * services must be handled carefully for the garbage collector to be able to
	 * clean them up.
	 * 
	 * @param <T>  The type of the service.
	 * @param type The {@link Class} instance to denote the type of the service.
	 * @return A list of proxies that can be used to call the corresponding
	 *         services.
	 * @throws CallServiceException
	 */
	public <T> List<? extends ServiceWrapper<T>> loadServices(final Class<T> type) throws CallServiceException;

	public boolean isInRegistry(final Plugin plugin);
}
