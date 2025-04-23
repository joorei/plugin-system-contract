package org.codeturnery.plugin.version;

import java.util.Objects;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Checks;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Implementation of a {@link Version}.
 * <p>
 * TODO: add additional checks according to the specification for the individual parts of the version.
 */
public class StringVersion implements Version {

	private static final Pattern SPLIT_REGEX = Checks.requireNonNull(Pattern.compile("\\.")); //$NON-NLS-1$

	private final String major;
	private final String minor;
	private final String patch;

	/**
	 * @param versionString must be of format <code>x.y.z</code>
	 * @throws VersionFormatException
	 */
	public StringVersion(final String versionString) throws VersionFormatException {
		this(splitVersionString(versionString));
	}

	/**
	 * @param version Must contain three elements, the major, minor and patch
	 *                version in that order.
	 */
	protected StringVersion(final @NonNull String[] version) {
		this(version[0], version[1], version[2]);
	}

	protected StringVersion(final String major, final String minor, final String patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}

	/**
	 * @param versionString must be of format <code>x.y.z</code>
	 * @return Array with three non-null elements
	 * @throws VersionFormatException
	 */
	@SuppressWarnings("null")
	protected static @NonNull String[] splitVersionString(final String versionString) throws VersionFormatException {
		final String[] parts = SPLIT_REGEX.split(versionString);
		if (parts.length != 3) {
			throw VersionFormatException.invalidPartCount(parts.length, versionString);
		}
		if (Checks.isAnyNull(parts)) {
			throw VersionFormatException.nullPart(parts.length, versionString);
		}
		
		return parts;
	}

	@Override
	public String getMajor() {
		return this.major;
	}

	@Override
	public String getMinor() {
		return this.minor;
	}

	@Override
	public String getPatch() {
		return this.patch;
	}

	@Override
	public String toString() {
		return this.major + '.' + this.minor + '.' + this.patch;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null || !(obj instanceof StringVersion)) {
			return false;
		}

		final StringVersion other = (StringVersion) obj;

		return this.major.equals(other.major) && this.minor.equals(other.minor) && this.patch.equals(other.patch);

	}

	@Override
	public int hashCode() {
		return Objects.hash(this.major, this.minor, this.patch);
	}
}
