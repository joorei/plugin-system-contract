package org.codeturnery.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

import org.codeturnery.plugin.stage.RegistrationException;
import org.eclipse.jdt.annotation.Checks;
import org.eclipse.jdt.annotation.Nullable;

abstract public class AbstractRegisteredPlugin extends AbstractPlugin implements RegisteredPlugin {

	/**
	 * File URI (derived from the initial JAR path). Needed to install the plug-in.
	 */
	protected final URI uri;
	/**
	 * The manifest information parsed from the <code>META-INF/MANIFEST.MF</code> in
	 * the plug-ins JAR file.
	 */
	protected final Manifest manifest;
	/**
	 * The class names defined by the plug-ins JAR file.
	 */
	private final Map<String, JarEntry> classEntries;
	private final Instant registrationTime;

	/**
	 * 
	 * @param jarFile
	 * @param registry
	 * @throws RegistrationException if the given JAR file is not readable
	 * @throws IOException           if an I/O error has occurred when accessing the
	 *                               JAR file
	 */
	protected AbstractRegisteredPlugin(final File jarFile, final Registry registry)
			throws RegistrationException, IOException {
		// TODO: set up a listener watching the file for changes
		// TODO: file type
		if (!jarFile.canRead()) {
			throw RegistrationException.jarReadFail(jarFile, registry);
		}

		try (final JarFile jar = new JarFile(jarFile, true);) {
			this.manifest = Checks.requireNonNull(jar.getManifest());
			this.classEntries = determineClassEntries(jar);
		}

		this.uri = Checks.requireNonNull(jarFile.getAbsoluteFile().toURI());
		this.registrationTime = Instant.now();
	}

	/**
	 * Copies the properties of the given class into this new instance.
	 * 
	 * @param pluginToFlatCopy
	 */
	protected AbstractRegisteredPlugin(final AbstractRegisteredPlugin pluginToFlatCopy) {
		this.uri = pluginToFlatCopy.uri;
		this.manifest = pluginToFlatCopy.manifest;
		this.classEntries = pluginToFlatCopy.classEntries;
		this.registrationTime = pluginToFlatCopy.registrationTime;
	}

	@Override
	public Map<String, JarEntry> getClassEntries() throws ExpiredException {
		assertNotExpired();
		return this.classEntries;
	}

	@Override
	public URI getUri() throws ExpiredException {
		assertNotExpired();
		return this.uri;
	}

	@Override
	public boolean isRegisteredFrom(final File jarFile) throws ExpiredException {
		assertNotExpired();
		return this.uri.equals(jarFile.getAbsoluteFile().toURI());
	}

	@Override
	public Instant getRegistrationTime() throws ExpiredException {
		assertNotExpired();
		return this.registrationTime;
	}

	@Override
	public String getSymbolicNameWithVersion() {
		return getSymbolicName() + ':' + getVersion().toString();
	}

	@Override
	public Set<Conflict> getConflicts() {
		assertNotExpired();
		final var conflicts = new HashSet<Conflict>();
		for (final RegisteredPlugin comparisonPlugin : getRegistry().getRegisteredPlugins()) {
			if (this == comparisonPlugin) {
				continue;
			}
			Set<String> conflictingClasses = getConflictingClassPaths(comparisonPlugin);
			conflictingClasses = removeMatchingCrcContent(conflictingClasses, comparisonPlugin);
			final boolean symbolicNameConflict = getSymbolicName().equals(comparisonPlugin.getSymbolicName());
			if (!conflictingClasses.isEmpty() || symbolicNameConflict) {
				conflicts.add(new Conflict(comparisonPlugin, conflictingClasses, symbolicNameConflict));
			}
		}

		return conflicts;
	}

	@SuppressWarnings({ "null", "static-method" })
	protected Map<String, JarEntry> determineClassEntries(final JarFile jar) {
		final var foundClassNames = new HashMap<String, JarEntry>();
		final Enumeration<@Nullable JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			final JarEntry jarEntry = Checks.requireNonNull(entries.nextElement());
			final String className = jarEntry.getName();
			if (className.endsWith(".class")) { //$NON-NLS-1$
				final @Nullable JarEntry previousJarEntry = foundClassNames.put(className, jarEntry);
				if (previousJarEntry != null) {
					// TODO: the string should be considered non-validated input, which is relevant
					// when showing it in a webbrowser and such
					throw new JarFileException("Duplicated class found in the same JAR file: " + className);
				}
			}
		}
		return foundClassNames;
	}

	/**
	 * Determines and returns the class paths that are defined in both plug-ins (this
	 * instance and the given instance) and thus may cause conflicts.
	 * 
	 * @param otherPlugin The other plug-in instances.
	 *
	 * @return The conflicting class paths.
	 * @throws ExpiredException
	 */
	protected Set<String> getConflictingClassPaths(final RegisteredPlugin otherPlugin) throws ExpiredException {
		assertNotExpired();
		final var resultSet = new HashSet<>(this.getClassEntries().keySet());
		resultSet.retainAll(otherPlugin.getClassEntries().keySet());

		return resultSet;
	}

	/**
	 * Removes entries from the given {@code classPaths} which have the same content
	 * CRC checksum in both plug-ins according to the JAR file.
	 * 
	 * @param classPaths
	 * @param otherPlugin
	 * @return a new set not containing class paths to files with the same content
	 */
	protected Set<String> removeMatchingCrcContent(final Set<String> classPaths, final RegisteredPlugin otherPlugin) {
		return classPaths.stream().filter(classPath -> {
			final JarEntry entryA = getClassEntries().get(classPath);
			final JarEntry entryB = otherPlugin.getClassEntries().get(classPath);

			return entryA.getCrc() != entryB.getCrc();
		}).collect(Collectors.toSet());
	}

	abstract protected Registry getRegistry();
}
