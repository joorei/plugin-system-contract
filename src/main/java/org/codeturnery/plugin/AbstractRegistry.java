package org.codeturnery.plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.codeturnery.plugin.stage.StopException;
import org.codeturnery.plugin.stage.UninstallationException;
import org.codeturnery.plugin.stage.UnregistrationException;

abstract public class AbstractRegistry implements Registry {
	/**
	 * All known plug-ins of this registry, weather started, installed or just
	 * registered.
	 */
	private final Set<Plugin> plugins;

	protected void addPlugin(final Plugin plugin) throws RegistryException {
		plugin.assertNotExpired();
		final boolean pluginWasNotPresent = this.plugins.add(plugin);
		if (!pluginWasNotPresent) {
			throw RegistryException.alreadyRegistered(plugin, this);
		}
	}

	protected void removePlugin(final Plugin plugin) throws RegistryException {
		if (!plugin.isExpired()) {
			throw RegistryException.notExpired(plugin, this);
		}
		final boolean pluginWasPresent = this.plugins.remove(plugin);
		if (!pluginWasPresent) {
			throw RegistryException.unknown(plugin, this);
		}
	}

	protected AbstractRegistry() {
		this.plugins = new HashSet<>();
	}

	/**
	 * Iterates through all plug-ins in this registry and attempts to bring them all
	 * in an unregistered stage. If needed plug-ins will automatically be stopped
	 * and uninstalled.
	 * 
	 * @throws UnregistrationException
	 * @throws UninstallationException
	 * @throws StopException
	 */
	protected void forceUnregisterAllPlugins() throws UnregistrationException, UninstallationException, StopException {
		for (final Plugin plugin : getRegisteredPlugins()) {
			forceUnregisterPlugin(plugin);
		}
	}

	/**
	 * Attempts to bring the given plug-in in an unregistered stage. If needed it
	 * will automatically be stopped and uninstalled.
	 * 
	 * @param plugin
	 * @throws UnregistrationException
	 * @throws UninstallationException
	 * @throws StopException
	 */
	protected void forceUnregisterPlugin(final Plugin plugin)
			throws UnregistrationException, UninstallationException, StopException {
		final Plugin stoppedPlugin = plugin instanceof StartedPlugin ? ((StartedPlugin) plugin).stop() : plugin;

		final Plugin uninstalledPlugin = stoppedPlugin instanceof InstalledPlugin
				? ((InstalledPlugin) stoppedPlugin).uninstall()
				: stoppedPlugin;

		if (uninstalledPlugin instanceof RegisteredPlugin) {
			((RegisteredPlugin) uninstalledPlugin).unregister();
		}
	}

	/**
	 * Get the plug-in that was registered from the given instance.
	 * <p>
	 * Technically the first plug-in found in this registry, that was registered
	 * from the given JAR file is returned. But practically safety measures should
	 * be in place, so that it is not possible to register multiple plug-ins for the
	 * same JAR file in the same registry.
	 * 
	 * @param jarFile
	 * @return plug-in that was registered from the given JAR file.
	 */
	protected Optional<RegisteredPlugin> getCorrespondingPlugin(final File jarFile) {
		for (final RegisteredPlugin plugin : getRegisteredPlugins()) {
			if (plugin.isRegisteredFrom(jarFile)) {
				return Optional.of(plugin);
			}
		}

		return Optional.empty();
	}

	@SuppressWarnings("null")
	@Override
	public Set<Plugin> getPlugins() {
		return Collections.unmodifiableSet(this.plugins);
	}

	@Override
	public Set<InstalledPlugin> getInstalledPlugins() {
		return getFilteredPlugins(InstalledPlugin.class);
	}

	@Override
	public Set<RegisteredPlugin> getRegisteredPlugins() {
		return getFilteredPlugins(RegisteredPlugin.class);
	}

	@Override
	public Set<StartedPlugin> getStartedPlugin() {
		return getFilteredPlugins(StartedPlugin.class);
	}

	protected <T extends Plugin> Set<T> getFilteredPlugins(final Class<T> type) {
		return this.plugins.stream().filter(type::isInstance).map(type::cast).collect(Collectors.toSet());
	}

	/**
	 * @param presentPlugin
	 * @param replacement
	 * @throws ExpiredException
	 * @throws RegistryException
	 */
	protected void replacePluginInstances(final Plugin presentPlugin, final Plugin replacement)
			throws ExpiredException, RegistryException {
		removePlugin(presentPlugin);
		addPlugin(replacement);
	}

	@Override
	public boolean isInRegistry(final Plugin plugin) {
		return this.plugins.contains(plugin);
	}
}
