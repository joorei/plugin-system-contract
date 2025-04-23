package org.codeturnery.plugin;

import java.io.File;
import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;

import org.codeturnery.plugin.stage.InstallationException;
import org.codeturnery.plugin.stage.Stage;
import org.codeturnery.plugin.stage.UnregistrationException;

/**
 * If this instance hasn't been expired it represents a plug-in that is made
 * known to the application but <strong>may</strong> not be
 * {@link Stage#INSTALLED} (instance of {@link InstalledPlugin}) or
 * {@link Stage#STARTED} (instance of {@link StartedPlugin}).
 * <p>
 * Instances expire when they switch their stage, i.&nbsp;e. when they are
 * installed, started, stopped, uninstalled or unregistered. Corresponding
 * methods to switch stages will return a new instance with the new stage after
 * which the old instance must no longer be accessed.
 * <p>
 * A {@link RegisteredPlugin} provides information about the plug-in but if it
 * isn't an {@link InstalledPlugin} too then there is no potential for conflicts
 * with other plug-ins and even though it is visible in the application, its
 * functionality is neither available nor has it effect on the application.
 * <p>
 * Changing or deleting the JAR file backing this instance is
 * <strong>not</strong> allowed and will result in undefined behavior.
 */
public interface RegisteredPlugin extends Plugin {

	public URI getUri() throws ExpiredException;

	public Map<String, JarEntry> getClassEntries() throws ExpiredException;

	public boolean isRegisteredFrom(final File jarFile) throws ExpiredException;

	public Instant getRegistrationTime() throws ExpiredException;

	/**
	 * @return A new instance to be used for all further actions on this plug-in.
	 *         The current instance will be set as expired and must not be used
	 *         anymore.
	 * 
	 * @throws InstallationException if a problem occurred while installing the
	 *                               plug-in
	 */
	public InstalledPlugin install() throws InstallationException, ExpiredException;

	/**
	 * The current instance will be set as expired and must not be used anymore.
	 * 
	 * @throws UnregistrationException if a problem occurred while unregistering the
	 *                                 plug-in
	 */
	public void unregister() throws UnregistrationException, ExpiredException;

	/**
	 * Two possibilities how this plug-in may conflict with any other currently
	 * {@link Stage#REGISTERED} plug-in are checked:
	 * <ol>
	 * <li>if they both use the same symbolic name</li>
	 * <li>if they both define classes that match in their fully qualified class
	 * name but not in their CRC content checksum</li>
	 * </ol>
	 * 
	 * @return All (at the moment of invocation non-expired) plug-ins that have
	 *         potential to conflict with the current plug-in.
	 */
	public Set<Conflict> getConflicts() throws ExpiredException;

	public String getSymbolicNameWithVersion() throws ExpiredException;
}
