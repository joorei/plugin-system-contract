package org.codeturnery.plugin;

import java.util.Optional;

import org.codeturnery.plugin.stage.Stage;

public abstract class AbstractPlugin implements Plugin {

	private Optional<Expiration> expiration = Optional.empty();

	@Override
	public boolean isExpired() {
		return this.expiration.isPresent();
	}

	@Override
	public Optional<Expiration> getExpiration() {
		return this.expiration;
	}

	@Override
	public void assertNotExpired() throws ExpiredException {
		getExpiration().ifPresent(expiration -> {
			throw ExpiredException.isExpired(expiration, this);
		});
	}

	@SuppressWarnings("null")
	protected void expire(final Stage newStage) {
		final Stage currentStage = getStage();
		this.expiration = Optional.of(new Expiration(currentStage, newStage));
	}

	/**
	 * The stage this instance is currently in. It can still be expired independent
	 * from the current stage.
	 * <p>
	 * Also note that the stage {@link Stage#STARTED} implies that it is also
	 * installed and registered and the stage {@link Stage#INSTALLED} implies that
	 * is also registered.
	 * <p>
	 * This method will simply check the expiration state and the class of this
	 * instance.
	 * 
	 * @return The current stage.
	 * @throws ExpiredException 
	 */
	protected Stage getStage() throws ExpiredException {
		assertNotExpired();
		if (this instanceof StartedPlugin) {
			return Stage.STARTED;
		}
		if (this instanceof InstalledPlugin) {
			return Stage.INSTALLED;
		}

		return Stage.REGISTERED;
	}

	@Override
	public boolean isRegistered() throws ExpiredException {
		assertNotExpired();
		return this instanceof RegisteredPlugin;
	}

	@Override
	public boolean isInstalled() throws ExpiredException {
		assertNotExpired();
		return this instanceof InstalledPlugin;
	}

	@Override
	public boolean isStarted() throws ExpiredException {
		assertNotExpired();
		return this instanceof StartedPlugin;
	}

	@Override
	public RegisteredPlugin assertRegistered() throws ExpiredException, ClassCastException {
		return assertIs(RegisteredPlugin.class);
	}

	@Override
	public InstalledPlugin assertInstalled() {
		return assertIs(InstalledPlugin.class);
	}

	@Override
	public StartedPlugin assertStarted() throws ExpiredException, ClassCastException {
		return assertIs(StartedPlugin.class);
	}

	@Override
	public Optional<RegisteredPlugin> asRegistered() throws ExpiredException {
		return as(RegisteredPlugin.class);
	}

	@Override
	public Optional<InstalledPlugin> asInstalled() throws ExpiredException {
		return as(InstalledPlugin.class);
	}

	@Override
	public Optional<StartedPlugin> asStarted() throws ExpiredException {
		return as(StartedPlugin.class);
	}

	@SuppressWarnings("null")
	protected <T> Optional<T> as(final Class<T> type) throws ExpiredException {
		assertNotExpired();
		return type.isInstance(this) ? Optional.of(type.cast(this)) : Optional.empty();
	}

	protected <T> T assertIs(final Class<T> type) throws ExpiredException, ClassCastException {
		assertNotExpired();
		if (type.isInstance(this)) {
			return type.cast(this);
		}
		throw new ClassCastException(
				"Instance is not of type " + type.toString() + " but " + this.getClass().toString());
	}
}
