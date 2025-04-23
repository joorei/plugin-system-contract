package org.codeturnery.plugin;

/**
 * Implies a problem handling the JAR file from which a plug-in was to be
 * loaded.
 */
public class JarFileException extends RuntimeException {

	private static final long serialVersionUID = -9184641602930204126L;

	public JarFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public JarFileException(String message) {
		super(message);
	}

	public JarFileException(Throwable cause) {
		super(cause);
	}
}
