package org.codeturnery.plugin;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

public class Conflict {
	private final RegisteredPlugin conflictingPlugin;
	private final Set<String> conflictingClasses;
	private final boolean conflictingSymbolicName;

	public Conflict(final RegisteredPlugin conflictingPlugin, final Set<String> conflictingClasses,
			final boolean symbolicNameConflict) {
		this.conflictingPlugin = conflictingPlugin;
		this.conflictingSymbolicName = symbolicNameConflict;
		this.conflictingClasses = conflictingClasses;
	}

	public Set<String> getConflictingClasses() {
		return this.conflictingClasses;
	}

	public RegisteredPlugin getConflictingPlugin() {
		return this.conflictingPlugin;
	}

	public boolean isConflictingSymbolicName() {
		return this.conflictingSymbolicName;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null || !(obj instanceof Conflict)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		final Conflict other = (Conflict) obj;

		return this.conflictingSymbolicName == other.conflictingSymbolicName
				&& this.conflictingClasses.equals(other.conflictingClasses)
				&& this.conflictingPlugin.equals(other.conflictingPlugin);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new int[] { this.conflictingClasses.hashCode(), this.conflictingPlugin.hashCode(),
				(this.conflictingSymbolicName ? 1 : 0) });
	}
}
