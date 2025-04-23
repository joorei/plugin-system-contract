package org.codeturnery.plugin;

public class RegistryException extends RuntimeException implements RegistryRelated, PluginRelated {

	private static final long serialVersionUID = -3173597119833955939L;
	private final Registry registry;
	private final Plugin plugin;
	
	public RegistryException(final String message, final Throwable cause, final Plugin plugin, final Registry registry) {
		super(message, cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public RegistryException(final String message, final Plugin plugin, final Registry registry) {
		super(message);
		this.registry = registry;
		this.plugin = plugin;
	}

	public RegistryException(final Throwable cause, final Plugin plugin, final Registry registry) {
		super(cause);
		this.registry = registry;
		this.plugin = plugin;
	}

	public static RegistryException unknown(final Plugin plugin, final Registry registry) {
		return new RegistryException("The plug-in to remove is not known in the registry.", plugin, registry);
	}

	public static RegistryException alreadyRegistered(final Plugin plugin, final Registry registry) {
		return new RegistryException("The plug-in to add is already present in the registry.", plugin, registry);
	}

	public static RegistryException notExpired(final Plugin plugin, final Registry registry) {
		// TODO Auto-generated method stub
		return new RegistryException("The plug-in to be removed from the registry has not been expirede yet.", plugin, registry);
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
