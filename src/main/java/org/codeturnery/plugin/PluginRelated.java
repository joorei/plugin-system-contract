package org.codeturnery.plugin;

/**
 * Implementing exceptions occur in the context of a specific plug-in instance.
 */
public interface PluginRelated {
	/**
	 * @return The plug-in in which context the exception occurred.
	 */
	public Plugin getPlugin();
}
