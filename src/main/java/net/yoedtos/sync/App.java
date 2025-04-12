package net.yoedtos.sync;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.sync.api.Type;
import net.yoedtos.sync.core.NoVirtual;
import net.yoedtos.sync.core.Virtual;

public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		LOGGER.debug("App start: {}", Arrays.toString(args));
		var threads = Integer.valueOf(args[1]);

		System.out.println("\n\tReproduce 'Virtual Thread Pinning' issue\n");
		System.out.println("\tNumber of cores: " + Runtime.getRuntime().availableProcessors());
		System.out.println("\tJDK version: " + System.getProperty("java.version"));
		System.out.println("\tStarted with " + threads + " threads");
		System.out.println("\t----------------------------------------");
		try {
			new NoVirtual(threads).execute(Type.EXPLICIT);
			new Virtual(threads).execute(Type.EXPLICIT);
		} catch (Exception e) {
			LOGGER.error("Error: ", e.toString());
			throw e;
		}
		System.out.println("\t----------------------------------------");
	}
}
