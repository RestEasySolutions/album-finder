package com.resteasy.ksubaka;

import com.resteasy.ksubaka.controllers.MusicController;
import com.resteasy.ksubaka.exceptions.UserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static com.resteasy.ksubaka.view.StandardOuput.displayAlbums;
import static com.resteasy.ksubaka.view.StandardOuput.displayUnexpectedException;
import static com.resteasy.ksubaka.view.StandardOuput.displayUserError;

/**
 * Entry point to the application
 * The expected command line entry is :
 * java -jar -Dapi={preferred api} -Dartist={artist} query.jar
 * {preferred api} can be spotify or discogs
 * if {artist} is more than one word, then it must be in quotes
 * i.e. -Dartist="The Seers"
 * Created by Ant Brown on 05/06/2016.
 */
@SpringBootApplication
public class QueryApplication {

	private static final String KEY_ARTIST = "artist";
	private static final String KEY_API = "api";
	private static final String KEY_ACTIVE_PROFILE = "spring.profiles.active";

	public static void main(String[] args) {
		ApplicationContext ctx = null;
		try {
			validateSystemProperties();

			// Map the supplied api system property into an active profile property
			System.setProperty(KEY_ACTIVE_PROFILE, System.getProperty(KEY_API));

			ctx = SpringApplication.run(QueryApplication.class, args);
			MusicController controller = (MusicController) ctx.getBean("musicController");

			// Load into Set to remove any duplicates
			displayAlbums(controller.getAlbums(System.getProperty(KEY_ARTIST)));
		} catch (UserException ue) {
			displayUserError(ue.getMessage());
		} catch (Exception e) {
			displayUnexpectedException(e);
		} finally {
			// close the application
			if (ctx != null) {
				SpringApplication.exit(ctx);
			}
		}
	}

	/**
	 * Verify that the expected system properties are present and
	 * throw a user exception if any aren't
	 *
	 * @throws UserException is any of the required system properties are missing
	 */
	private static void validateSystemProperties() throws UserException {
		if (System.getProperty(KEY_ARTIST) == null) {
			throw new UserException(KEY_ARTIST);
		}
		if (System.getProperty(KEY_API) == null) {
			throw new UserException(KEY_API);
		}
	}

}
