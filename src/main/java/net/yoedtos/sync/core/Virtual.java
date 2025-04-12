package net.yoedtos.sync.core;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.sync.api.FakeApi;
import net.yoedtos.sync.api.Type;

public class Virtual {
	private static final Logger LOGGER = LoggerFactory.getLogger(Virtual.class);

	private final int threads;
	private final CountDownLatch latch;

	public Virtual(int threads) {
		this.threads = threads;
		latch = new CountDownLatch(threads);
	}

	public void execute(Type type) {
		long start = System.nanoTime();
		LOGGER.debug("Init virtual");
		for (int i = 0; i < threads; i++) {
			Thread.ofVirtual().start(() -> {
				try {
					new FakeApi(type).call();
				} finally {
					latch.countDown();
				}
			});
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			LOGGER.error("Error: {}", e.toString());
			Thread.currentThread().interrupt();
		}
		long finish = System.nanoTime();
		double result = (finish - start) * 1.0E-6;
		LOGGER.debug("Result {} ms", result);
		LOGGER.debug("Done virtual");
		System.out.println("Virtual using: " + type);  
		System.out.println("Time: " + String.format("%15.4f", result) + " ms");
	}
}
