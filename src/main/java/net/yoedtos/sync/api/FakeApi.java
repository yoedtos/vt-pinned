package net.yoedtos.sync.api;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(FakeApi.class);

	private Type type;

	public FakeApi(Type type) {
		this.type = type;
	}

	public void call() {
		switch (type) {
			case EXPLICIT -> runWithExplicit();
			case IMPLICIT -> runWithImplicit();
			default -> throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	private void runWithImplicit() {
		LOGGER.debug("Implicit: {}", Thread.currentThread().threadId());
		try {
			synchronized (this) {
				LOGGER.debug("{}", this);
				Thread.sleep(Duration.ofSeconds(4));
			}
		} catch (InterruptedException e) {
			LOGGER.error("Error: {}", e.toString());
			Thread.currentThread().interrupt();
		}
	}

	private void runWithExplicit() {
		LOGGER.debug("Explicit: {}", Thread.currentThread().threadId());
		var lock = new Object();

		try {
			synchronized (lock) {
				LOGGER.debug("{}", lock);
				Thread.sleep(Duration.ofSeconds(4));
			}
		} catch (InterruptedException e) {
			LOGGER.error("Error: {}", e.toString());
			Thread.currentThread().interrupt();
		}
	}
}
