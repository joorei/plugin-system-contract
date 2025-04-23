package org.codeturnery.plugin.stage;

import org.codeturnery.plugin.PluginRelated;
import org.codeturnery.plugin.RegistryRelated;

abstract class AbstractStageChangeException extends RuntimeException implements PluginRelated, RegistryRelated {

	private static final long serialVersionUID = 8260087300166657979L;

	AbstractStageChangeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	AbstractStageChangeException(final String message) {
		super(message);
	}

	AbstractStageChangeException(final Throwable cause) {
		super(cause);
	}
}
