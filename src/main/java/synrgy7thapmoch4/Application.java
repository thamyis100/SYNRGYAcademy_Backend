package synrgy7thapmoch4;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import synrgy7thapmoch4.Controller.filehandler.FileStorageProperties;

@SpringBootApplication
@OpenAPIDefinition
@EnableConfigurationProperties({
		FileStorageProperties.class
})

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
