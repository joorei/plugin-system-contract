package org.codeturnery.plugin;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @param <T> The type of service wrapped by this instance.
 */
public interface ServiceWrapper<T> {
	/**
	 * Use the wrapped service instance in the provided function. The service may be
	 * automatically unloaded after this call, hence you must not use the service
	 * instance outside of the given function, where it is provided. Keeping a
	 * reference to it after the method returns may lead to problems like memory
	 * leaks.
	 * 
	 * @param <R>      the type returned by the given function
	 * @param function the function that is called with the actual service instance
	 * @return the result of the given function
	 * @throws CallServiceException
	 */
	public <R> R callService(final Function<T, R> function) throws CallServiceException;

	/**
	 * Use the wrapped service instance in the provided consumer. The service may be
	 * automatically unloaded after this call, hence you must not use the service
	 * instance outside of the given consumer, where it is provided. Keeping a
	 * reference to it after the method returns may lead to problems like memory
	 * leaks.
	 * 
	 * @param consumer the consumer that is called with the actual service instance
	 * @throws CallServiceException
	 */
	public void callService(final Consumer<T> consumer) throws CallServiceException;
}
