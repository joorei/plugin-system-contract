package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.PluginRelated;
import org.codeturnery.plugin.RegistryRelated;

/**
 * Implementing exceptions resulted from the attempt to change the stage of a
 * plug-in instance. E.g. stopping a started plug-in, bringing it back into the
 * installed stage.
 */
public interface StageChangeException extends PluginRelated, RegistryRelated {
	// merges the two parent interfaces, currently no additional methods
}
