package net.yoedtos.sync.core;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.sync.api.FakeApi;
import net.yoedtos.sync.api.Type;

public class NoVirtual {
	private static final Logger LOGGER = LoggerFactory.getLogger(NoVirtual.class);

	private final int threads;

	public NoVirtual(int threads) {
		this.threads = threads;
	}

	public void execute(Type type) {
		long start = System.nanoTime();
		LOGGER.debug("Init platform");
		try (var executor = Executors.newThreadPerTaskExecutor(Thread.ofPlatform().factory())) {
			for (int i = 0; i < threads; i++) {
				executor.submit(() -> new FakeApi(type).call());
			}
		} catch (Exception e) {
			LOGGER.error("Error: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
		long finish = System.nanoTime();
		double result = (finish - start) * 1.0E-6;
		LOGGER.debug("Result {} ms", result);
		LOGGER.debug("Done platform");
		System.out.println("\tPlatform using: " + type);
		System.out.println("\tTime: " + String.format("%15.4f", result) + " ms");
	}
}
