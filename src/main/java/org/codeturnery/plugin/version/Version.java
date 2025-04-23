package org.codeturnery.plugin.version;

/**
 * Represents a simplified variation of semver versioning to denote the version of a plug-in.
 * <p>
 * Currently supports versions in the format <code>x.y.z</code> only.
 * 
 * @see <a href="https://semver.org/spec/v2.0.0.html">Semantic Versioning 2.0.0</a>
 */
public interface Version {

	public String getMajor();

	public String getMinor();

	public String getPatch();
}
