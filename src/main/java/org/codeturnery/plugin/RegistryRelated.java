package org.codeturnery.plugin;

/**
 * Implementing exceptions occur in the context of a specific registry instance.
 */
public interface RegistryRelated {
	/**
	 * @return The registry in which context the exception occurred.
	 */
	public Registry getRegistry();
}
