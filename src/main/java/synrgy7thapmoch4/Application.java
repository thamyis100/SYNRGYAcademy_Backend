package synrgy7thapmoch4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("info logging level");
		logger.error("error logging level");
		logger.warn("warning logging level");
		logger.debug("debug logging level");
		logger.trace("trace logging level");
		logger.error("Error processing request");
	}
}
