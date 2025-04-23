package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.Plugin;
import org.codeturnery.plugin.Registry;

/**
 * Implies that uninstalling a plug-in instance failed for some reason.
 */
public class UninstallationException extends AbstractStageChangeException {

	private static final long serialVersionUID = 8940482584990520498L;
	private final Plugin plugin;
	private final Registry registry;

	public UninstallationException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.plugin = plugin;
		this.registry = registry;
	}

	public UninstallationException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public UninstallationException(final String message, final Throwable cause, final Plugin plugin,
			final Registry registry) {
		super(message, cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public static UninstallationException stillStarted(final Plugin plugin, final Registry registry) {
		return new UninstallationException("Can't uninstall plug-in as it is still started.", plugin, registry);
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}
}
