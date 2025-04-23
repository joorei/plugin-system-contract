package org.codeturnery.plugin;

import java.time.Instant;

import org.codeturnery.plugin.stage.Stage;
import org.eclipse.jdt.annotation.Checks;

/**
 * Implies that the corresponding plug-in instance has expired. Provides the
 * information which stage switch did expire the corresponding instance.
 */
public class Expiration {
	private final Stage previousStage;
	private final Stage newStage;
	private final Instant switchTime;

	public Expiration(final Stage previousStage, final Stage newStage) {
		this.previousStage = previousStage;
		this.newStage = newStage;
		this.switchTime = Checks.requireNonNull(Instant.now());
	}

	public Stage getPreviousStage() {
		return this.previousStage;
	}

	public Stage getNewStage() {
		return this.newStage;
	}

	public Instant getSwitchTime() {
		return this.switchTime;
	}
}
