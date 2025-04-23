package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.InstalledPlugin;
import org.codeturnery.plugin.RegisteredPlugin;
import org.codeturnery.plugin.StartedPlugin;

/**
 * The stages a plug-in can be in.
 * <p>
 * This information is needed additionally to the type information of
 * {@link RegisteredPlugin}, {@link InstalledPlugin} and {@link StartedPlugin}.
 * For example a {@link StartedPlugin} is also of type {@link InstalledPlugin}
 * and {@link RegisteredPlugin}, but if it was not stopped its stage is
 * {@link Stage#STARTED}.
 */
public enum Stage {
	/**
	 * The plug-in was previously at least registered, but is now removed from the
	 * registry and can no longer be used.
	 */
	UNREGISTERED,
	/**
	 * The plug-in is registered but was not yet installed or has been uninstalled.
	 */
	REGISTERED,
	/**
	 * The plug-in is registered and has been installed, but was not yet started or
	 * has been stopped.
	 */
	INSTALLED,
	/**
	 * The plug-in is registered, installed and started.
	 */
	STARTED
}
