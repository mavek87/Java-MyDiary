package com.matteoveroni.mydiary;

import com.matteoveroni.mydiary.application.manager.ApplicationManager;
import com.matteoveroni.mydiary.application.builder.ApplicationBuilder;
import com.matteoveroni.mydiary.database.DAO;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class MyDiary extends Application {

	private static final String APPLICATION_NAME = "MyDiary";
	private static final String APPLICATION_VERSION = "0.0.16";
	private static final Logger LOG = LoggerFactory.getLogger(MyDiary.class);

	private ApplicationBuilder applicationBuilder;
	private ApplicationManager applicationManager;

	@Override
	public void start(Stage applicationStage) {
		LOG.debug(" --> Application started");
		applicationBuilder = new ApplicationBuilder(APPLICATION_NAME, APPLICATION_VERSION, applicationStage);
		LOG.debug(" --> ApplicationBuilder initialized");
		applicationManager = applicationBuilder.build();
		LOG.debug(" --> ApplicationManager builded");
	}

	@Override
	public void stop() {
		LOG.debug(" --> Application is going to be disposed");
		applicationManager.dispose();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
