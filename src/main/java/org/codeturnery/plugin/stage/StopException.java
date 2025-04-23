package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.Plugin;
import org.codeturnery.plugin.Registry;

/**
 * Implies that stopping a plug-in instance failed for some reason, e.g. because
 * the JAR file could not be read.
 */
public class StopException extends AbstractStageChangeException {

	private static final long serialVersionUID = -5237856821150142130L;

	private final Plugin plugin;
	private final Registry registry;

	public StopException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.plugin = plugin;
		this.registry = registry;
	}

	public StopException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public StopException(final String message, final Throwable cause, final Plugin plugin, final Registry registry) {
		super(message, cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public static StopException notStarted(final Plugin plugin, final Registry registry) {
		return new StopException("The plug-in must be started to be stopped.", plugin, registry);
	}

	public static StopException alreadyStopped(final Plugin plugin, final Registry registry) {
		return new StopException("The plug-in is already stopped.", plugin, registry);
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
