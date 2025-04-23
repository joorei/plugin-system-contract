package org.codeturnery.plugin;

import java.util.Optional;

import org.codeturnery.plugin.version.Version;

/**
 * A set of Java classes and functionality that can be installed and uninstalled
 * during runtime.
 * <p>
 * A plug-in can be known in the application in different stages
 * ({@link RegisteredPlugin registered}, {@link InstalledPlugin installed} and
 * {@link StartedPlugin started}.
 * 
 * <h2>Conflict potential</h2>
 * <p>
 * {@link InstalledPlugin}s can potentially conflict with each other if they
 * define classes with equal fully qualified names but different
 * implementations. To reduce the likelihood of such conflicts, before allowing
 * the installation of a plug-in its JAR file is scanned for FQCNs that are
 * already defined by another plug-in. The affected plug-in will be marked as
 * conflicting. Installing conflicting plug-ins anyway will result in undefined
 * behavior.
 * <p>
 * However, scanning the JAR file is not bulletproof and even if unlikely two
 * plug-ins can still conflict with each other, e.g. due to conflicting
 * dependencies. In such case they may cause problems at runtime.
 * <p>
 * A more specific conflict is the case of two plug-ins that use the same
 * symbolic name. When combined with the corresponding plug-in version, symbolic
 * names are supposed to be unique between different plug-ins. Hence, when two
 * plug-ins use the same symbolic name (not to be confused with their file name)
 * it can be assumed that they will conflict with each other and they are marked
 * accordingly. A likely cause of such case is the attempted usage of basically
 * the same plug-in in two different versions.
 * <p>
 * Loading the same JAR path for different plug-ins into the application is not
 * allowed.
 */
public interface Plugin {
	/**
	 * @return The symbolic name of this plug-in. Technically multiple plug-ins with
	 *         the same symbolic name can be installed (e.g. in different versions),
	 *         but this carries the risk of conflicts.
	 */
	public String getSymbolicName() throws ExpiredException;

	/**
	 * @return The self-reported version of this plug-in.
	 */
	public Version getVersion() throws ExpiredException;

	public boolean isExpired();

	public Optional<Expiration> getExpiration();

	public void assertNotExpired() throws ExpiredException;

	public boolean isRegistered() throws ExpiredException;

	public boolean isInstalled() throws ExpiredException;

	public boolean isStarted() throws ExpiredException;

	public RegisteredPlugin assertRegistered() throws ExpiredException, ClassCastException;

	public InstalledPlugin assertInstalled() throws ExpiredException, ClassCastException;

	public StartedPlugin assertStarted() throws ExpiredException, ClassCastException;

	public Optional<RegisteredPlugin> asRegistered() throws ExpiredException;

	public Optional<InstalledPlugin> asInstalled() throws ExpiredException;

	public Optional<StartedPlugin> asStarted() throws ExpiredException;
}
