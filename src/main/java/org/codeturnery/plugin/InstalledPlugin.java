package org.codeturnery.plugin;

import java.time.Instant;

import org.codeturnery.plugin.stage.StartException;
import org.codeturnery.plugin.stage.UninstallationException;

public interface InstalledPlugin extends RegisteredPlugin {
	public Instant getInstallationTime();

	/**
	 * @return A new instance to be used for all further actions on this plug-in.
	 *         The current instance will be set as expired and must not be used
	 *         anymore.
	 *         
	 * @throws UninstallationException if a problem occurred while uninstalling the plug-in
	 */
	public RegisteredPlugin uninstall() throws UninstallationException;

	/**
	 * @return A new instance to be used for all further actions on this plug-in.
	 *         The current instance will be set as expired and must not be used
	 *         anymore.
	 * 
	 * @throws StartException if a problem occurred while starting the plug-in
	 */
	public StartedPlugin start() throws StartException;
}
