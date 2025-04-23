package org.codeturnery.plugin;

/**
 * Thrown when a plug-in instance is accessed that has been expired.
 * <p>
 * Plug-in instances expire when their stage is changed. E.g. when using
 * {@link InstalledPlugin#uninstall()}, a {@link RegisteredPlugin} instance will
 * be returned. The {@link InstalledPlugin} instance must then no longer be used
 * in any way, the only exception being {@link Plugin#isExpired} and
 * {@link Plugin#getExpiration}. Instead, the returned {@link RegisteredPlugin}
 * instance must be used.
 * <p>
 * As accessing an expired plug-in instance is considered a programming error,
 * this exception is unchecked.
 */
public class ExpiredException extends RuntimeException implements PluginRelated {

	private static final long serialVersionUID = -4561578981151228764L;
	private final Expiration expiration;
	private final Plugin plugin;

	public ExpiredException(final String message, final Expiration expiration, final Plugin plugin) {
		super(message);
		this.plugin = plugin;
		this.expiration = expiration;
	}

	public ExpiredException(final Throwable cause, final Expiration expiration, final Plugin plugin) {
		super(cause);
		this.plugin = plugin;
		this.expiration = expiration;
	}

	public ExpiredException(final String message, final Throwable cause, final Expiration expiration,
			final Plugin plugin) {
		super(message, cause);
		this.plugin = plugin;
		this.expiration = expiration;
	}

	public static ExpiredException isExpired(final Expiration expiration, final Plugin plugin) {
		return new ExpiredException("The used plugin instance has expired and must not be used anymore.", expiration,
				plugin);
	}

	public Expiration getExpiration() {
		return this.expiration;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}
}
