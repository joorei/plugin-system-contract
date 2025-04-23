package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.Plugin;
import org.codeturnery.plugin.Registry;

/**
 * Implies that a plug-in instance could not be started for some reason, e.g.
 * because it is already started.
 */
public class StartException extends AbstractStageChangeException {

	private static final long serialVersionUID = -8750543591009554940L;

	private final Registry registry;
	private final Plugin plugin;

	public StartException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.registry = registry;
		this.plugin = plugin;
	}

	public StartException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public StartException(final String message, final Throwable cause, final Plugin plugin, final Registry registry) {
		super(message, cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public static StartException notInstalled(final Plugin plugin, final Registry registry) {
		return new StartException("The plug-in must be installed to be started.", plugin, registry);
	}

	public static StartException alreadyStarted(final Plugin plugin, final Registry registry) {
		return new StartException("The plug-in is already started.", plugin, registry);
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
