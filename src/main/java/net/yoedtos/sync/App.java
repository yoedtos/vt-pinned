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
		if(args.length < 3 || !args[0].contains("-t")) {
			throw new IllegalArgumentException("Invalid command");
		}
		var threads = Integer.valueOf(args[1]);

		System.out.println("\n\tReproduce 'Virtual Thread Pinning' issue\n");
		System.out.println("\tNumber of cores: " + Runtime.getRuntime().availableProcessors());
		System.out.println("\tJDK version: " + System.getProperty("java.version"));
		System.out.println("\tStarted with " + threads + " threads");
		System.out.println("\t----------------------------------------");
		try {
			var lockType = args[2].toUpperCase();
			LOGGER.debug("Lock type: {}", args[2]);
			new NoVirtual(threads).execute(Type.valueOf(lockType));
			new Virtual(threads).execute(Type.valueOf(lockType));
		} catch (Exception e) {
			LOGGER.error("Error: ", e.toString());
			throw e;
		}
		System.out.println("\t----------------------------------------");
	}
}
