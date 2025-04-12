package net.yoedtos.sync;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.sync.api.Type;
import net.yoedtos.sync.core.NoVirtual;
import net.yoedtos.sync.core.Virtual;

class AppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	@Test
	void testVirtualWithTwentThread() {
		LOGGER.info("Virtual Test");
		assertThatCode(() -> {
			new Virtual(20).execute(Type.EXPLICIT);
			new Virtual(20).execute(Type.IMPLICIT);
		})
		.doesNotThrowAnyException();
	}

	@Test
	void testNoVirtualWithTwentThread() {
		LOGGER.info("NoVirtual Test");
		assertThatCode(() -> {
			new NoVirtual(20).execute(Type.EXPLICIT);
			new NoVirtual(20).execute(Type.IMPLICIT);
		})
		.doesNotThrowAnyException();
	}
}
