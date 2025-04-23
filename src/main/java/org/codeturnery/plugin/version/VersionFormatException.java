package org.codeturnery.plugin.version;

/**
 * Implies that an attempt to parse a semantic version string did fail because
 * it was not in the expected format.
 * 
 * @see <a href="https://semver.org/spec/v2.0.0.html">Semantic Versioning
 *      2.0.0</a>
 */
public class VersionFormatException extends Exception {
	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 9082214673330600377L;
	private final int actualPartsCount;
	private final String versionString;

	public VersionFormatException(final String message, final int actualPartsCount, final String versionString) {
		super(message);
		this.actualPartsCount = actualPartsCount;
		this.versionString = versionString;
	}

	public static VersionFormatException invalidPartCount(final int length, final String versionString) {
		return new VersionFormatException("The given version string did not contain exactly three parts.", length,
				versionString);
	}

	public static VersionFormatException nullPart(final int length, final String versionString) {
		return new VersionFormatException("At least one of the version string parts was interpreted as null.", length,
				versionString);
	}

	public int getActualPartsCount() {
		return this.actualPartsCount;
	}

	public String getVersionString() {
		return this.versionString;
	}
}
