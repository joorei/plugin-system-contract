package org.codeturnery.plugin;

/**
 * A problem occurred when loading services for a specific class or interface.
 */
public class CallServiceException extends RuntimeException implements RegistryRelated {

	private static final long serialVersionUID = 4886623970702082407L;
	private final Class<?> clazz;
	private final Registry registry;

	public CallServiceException(final Throwable cause, final Class<?> clazz, final Registry registry) {
		super(cause);
		this.clazz = clazz;
		this.registry = registry;
	}

	public CallServiceException(final String message, final Throwable cause, final Class<?> clazz,
			final Registry registry) {
		super(message, cause);
		this.clazz = clazz;
		this.registry = registry;
	}

	public CallServiceException(final String message, final Class<?> clazz, final Registry registry) {
		super(message);
		this.clazz = clazz;
		this.registry = registry;
	}

	public Class<?> getClazz() {
		return this.clazz;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}
}
