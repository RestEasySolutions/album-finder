package com.resteasy.ksubaka;

import com.resteasy.ksubaka.controllers.MusicController;
import com.resteasy.ksubaka.view.StandardOuput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the QueryApplication, ensuring that the main method behaves as expected
 * Created by Ant Brown on 05/06/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({SpringApplication.class, StandardOuput.class})
public class QueryApplicationTests {
	private static final String[] ARGS = new String[0];
	private static final String API = "Spotify";

	@Mock
	ConfigurableApplicationContext applicationContext;

	@Mock
	MusicController musicController;

	@Captor
	ArgumentCaptor argumentCaptor;

	@Before
	public void setUp() {
		// Add the system properties that would be included in the execution command
		System.setProperty("api", API);
		System.setProperty("artist", "An Artist");
		// Mock out Spring
		PowerMockito.mockStatic(SpringApplication.class);
		when(SpringApplication.run(any(), ARGS)).thenReturn(applicationContext);
		when(applicationContext.getBean("musicController")).thenReturn(musicController);
		// Mock out StandardOutput
		PowerMockito.mockStatic(StandardOuput.class);
	}

	@After
	public void tearDown() {
		// Clear up the system properties
		System.clearProperty("api");
		System.clearProperty("artist");
	}

	/**
	 * Test the standard flow
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain() throws Exception {
		when(musicController.getAlbums(anyString())).thenReturn(null);
		QueryApplication.main(ARGS);
		// Ensure that display method is called
		verifyStatic(times(1));
		StandardOuput.displayAlbums(any());
		// No other messages should be displayed
		verifyStatic(times(0));
		StandardOuput.displayUserError(any());
		verifyStatic(times(0));
		StandardOuput.displayUnexpectedException(any());
		// Context should be closed
		verifyStatic(times(1));
		SpringApplication.exit(any());
	}

	/**
	 * Test that the active profile is set onto the system properties
	 * from the api property
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain_setsActiveProfile() throws Exception {
		when(musicController.getAlbums(anyString())).thenReturn(null);
		QueryApplication.main(ARGS);
		assertEquals(API, System.getProperty("spring.profiles.active"));
		verifyStatic(times(1));
		StandardOuput.displayAlbums(any());
		// Context should be closed
		verifyStatic(times(1));
		SpringApplication.exit(any());
	}

	/**
	 * Test that the validateSystemProperties method throws
	 * an exception if the api property isn't present
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain_invalidApi() throws Exception {
		System.clearProperty("api");
		QueryApplication.main(ARGS);
		//Check that the error is displayed with the correct message
		verifyStatic(times(1));
		StandardOuput.displayUserError((String) argumentCaptor.capture());
		assertEquals("api", argumentCaptor.getValue());
		// No other messages should be displayed
		verifyStatic(times(0));
		StandardOuput.displayAlbums(any());
		verifyStatic(times(0));
		StandardOuput.displayUnexpectedException(any());
		// At this point we won't have created a context, so
		// it won't be closed
		verifyStatic(times(0));
		SpringApplication.exit(any());
	}

	/**
	 * Test that the validateSystemProperties method throws
	 * an exception if the artist property isn't present
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain_invalidArtist() throws Exception {
		System.clearProperty("artist");
		QueryApplication.main(ARGS);
		//Check that the error is displayed with the correct message
		verifyStatic(times(1));
		StandardOuput.displayUserError((String) argumentCaptor.capture());
		assertEquals("artist", argumentCaptor.getValue());
		// No other messages should be displayed
		verifyStatic(times(0));
		StandardOuput.displayAlbums(any());
		verifyStatic(times(0));
		StandardOuput.displayUnexpectedException(any());
		// At this point we won't have created a context, so
		// it won't be closed
		verifyStatic(times(0));
		SpringApplication.exit(any());
	}

	/**
	 * Ensure that any unexpected exceptions are handled and displayed correctly
	 *
	 * @throws Exception
	 */
	@Test
	public void testMain_unexpectedException() throws Exception {
		Exception illegalArgumentException = new IllegalArgumentException();
		when(musicController.getAlbums(anyString())).thenThrow(illegalArgumentException);
		QueryApplication.main(ARGS);
		//Check that the exception is displayed with the correct message
		verifyStatic(times(1));
		StandardOuput.displayUnexpectedException((Exception) argumentCaptor.capture());
		assertEquals(illegalArgumentException, argumentCaptor.getValue());
		// No other messages should be displayed
		verifyStatic(times(0));
		StandardOuput.displayAlbums(any());
		verifyStatic(times(0));
		StandardOuput.displayUserError(any());
		// Context should be closed
		verifyStatic(times(1));
		SpringApplication.exit(any());
	}
}
