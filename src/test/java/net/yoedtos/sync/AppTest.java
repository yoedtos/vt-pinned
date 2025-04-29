package net.yoedtos.sync;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

	@Test
	void testAppCommandArguments() {
		assertThatThrownBy(() -> App.main(new String[]{"-e", "2", "implicit"}))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Invalid command");

		assertThatThrownBy(() -> App.main(new String[]{"-t", "2"}))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Invalid command");
	}
}
