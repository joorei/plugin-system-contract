package org.codeturnery.plugin.stage;

import java.io.File;
import org.codeturnery.plugin.RegisteredPlugin;
import org.codeturnery.plugin.Registry;

/**
 * Implies that registering a new plug-in instance for a given JAR file failed,
 * because a corresponding {@link RegisteredPlugin} instance already exists.
 */
public class AlreadyRegisteredException extends RegistrationException {
	private static final long serialVersionUID = 1364159929173263027L;
	private final RegisteredPlugin registeredPlugin;

	public AlreadyRegisteredException(final File jarFile, final RegisteredPlugin plugin, final Registry registry) {
		super("A plug-in was already registered using the given path: " + jarFile.getAbsolutePath(), jarFile, registry);
		this.registeredPlugin = plugin;
	}

	public RegisteredPlugin getRegisteredPlugin() {
		return this.registeredPlugin;
	}
}
