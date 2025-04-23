package org.codeturnery.plugin.stage;

import java.io.File;
import org.codeturnery.plugin.Registry;
import org.codeturnery.plugin.RegistryRelated;

/**
 * Implies that registering a new plug-in instance for a given JAR file failed,
 * e.g. because the JAR file could not be read.
 */
public class RegistrationException extends RuntimeException implements RegistryRelated {

	private static final long serialVersionUID = -5310702196640516548L;

	private final File jarFile;
	private final Registry registry;

	public RegistrationException(final String message, final File jarFile, final Registry registry) {
		super(message);
		this.jarFile = jarFile;
		this.registry = registry;
	}

	public RegistrationException(final Throwable cause, final File jarFile, final Registry registry) {
		super(cause);
		this.jarFile = jarFile;
		this.registry = registry;
	}

	public RegistrationException(final String message, final Throwable cause, final File jarFile,
			final Registry registry) {
		super(message, cause);
		this.jarFile = jarFile;
		this.registry = registry;
	}

	public static RegistrationException jarReadFail(final File jarFile, final Registry registry) {
		return new RegistrationException("Registering plug-in failed. Can't read file in path: " + jarFile.getAbsolutePath(), jarFile, registry);
	}

	public File getJarFile() {
		return this.jarFile;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}
}
