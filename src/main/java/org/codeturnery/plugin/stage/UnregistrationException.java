package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.Plugin;
import org.codeturnery.plugin.RegisteredPlugin;
import org.codeturnery.plugin.Registry;

/**
 * Indicates that a plug-in instance could not be unregistrered for some reason.
 */
public class UnregistrationException extends AbstractStageChangeException {

	private static final long serialVersionUID = 2243546366688194656L;
	private final Plugin plugin;
	private final Registry registry;

	public UnregistrationException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.plugin = plugin;
		this.registry = registry;
	}

	public UnregistrationException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public UnregistrationException(final String message, final Throwable cause, final Plugin plugin,
			final Registry registry) {
		super(message, cause);
		this.plugin = plugin;
		this.registry = registry;
	}

	public static UnregistrationException notStopped(final Plugin plugin, final Registry registry) {
		return new UnregistrationException(
				"The plug-in must be stopped (and uninstalled) before it can be unregistered.", plugin, registry);
	}

	public static UnregistrationException notUninstalled(final Plugin plugin, final Registry registry) {
		return new UnregistrationException("The plug-in must be uninstalled before it can be unregistered.", plugin,
				registry);
	}

	public static UnregistrationException notInRegistry(final RegisteredPlugin plugin, final Registry registry) {
		return new UnregistrationException("The plug-in can not be removed from registry as it is not present in it.",
				plugin, registry);
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
