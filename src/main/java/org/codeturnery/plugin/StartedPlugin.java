package org.codeturnery.plugin;

import java.time.Instant;

import org.codeturnery.plugin.stage.StopException;

public interface StartedPlugin extends InstalledPlugin {
	public Instant getStartTime();

	/**
	 * @return A new instance to be used for all further actions on this plug-in.
	 *         The current instance will be set as expired and must not be used
	 *         anymore.
	 * 
	 * @throws StopException if a problem occurred while stopping the plug-in
	 */
	public InstalledPlugin stop() throws StopException;
}
