package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.InstalledPlugin;
import org.codeturnery.plugin.Plugin;
import org.codeturnery.plugin.Registry;

/**
 * Implies that a plug-in instance could not be installed for some reason, e.g.
 * because it was already installed (i.e. is an instance of
 * {@link InstalledPlugin}).
 */
public class InstallationException extends AbstractStageChangeException {

	private static final long serialVersionUID = -7948525914759589628L;
	private final Registry registry;
	private final Plugin plugin;

	public InstallationException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.registry = registry;
		this.plugin = plugin;
	}

	public InstallationException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public InstallationException(final String message, final Throwable cause, final Plugin plugin,
			final Registry registry) {
		super(message, cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public static InstallationException alreadyInstalled(final InstalledPlugin plugin, final Registry registry) {
		return new InstallationException("The plug-in is already installed.", plugin, registry);
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}
}
