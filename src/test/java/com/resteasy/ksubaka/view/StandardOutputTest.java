package com.resteasy.ksubaka.view;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.discogs.DiscogsAlbum;
import com.resteasy.ksubaka.domain.spotify.SpotifyAlbum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests that the display sent to the standard output (System.out) is correct
 * Created by Ant Brown on 05/06/2016.
 */
@RunWith(PowerMockRunner.class)
public class StandardOutputTest {

	private OutputStream output;

	@Mock
	Exception unexpectedException;

	@Before
	public void setUp() {
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
	}

	@After
	public void tearDown(){
		// Clear up the system properties
		System.clearProperty("api");
		System.clearProperty("artist");
	}

	@Test
	public void testDisplayAlbums_withNullAlbums() throws Exception {
		System.setProperty("api", "Spotify");
		System.setProperty("artist", "An Artist");
		String expected = "=============================================================\r\n" +
				"Albums by An Artist, sourced from Spotify\r\n" +
				"-------------------------------------------------------------\r\n" +
				"No albums by this artist\r\n" +
				"=============================================================\r\n";

		// Method under test
		StandardOuput.displayAlbums(null);

		assertEquals(expected, output.toString());
	}

	@Test
	public void testDisplayAlbums_withEmptyListOfAlbums() throws Exception {
		System.setProperty("api", "Spotify");
		System.setProperty("artist", "An Artist");
		String expected = "=============================================================\r\n" +
				"Albums by An Artist, sourced from Spotify\r\n" +
				"-------------------------------------------------------------\r\n" +
				"No albums by this artist\r\n" +
				"=============================================================\r\n";
		List<Album> albumList = new ArrayList<>();

		// Method under test
		StandardOuput.displayAlbums(albumList);

		assertEquals(expected, output.toString());
	}

	@Test
	public void testDisplayAlbums_withListOfAlbums() throws Exception {
		System.setProperty("api", "Spotify");
		System.setProperty("artist", "The Doors");
		String expectedContents1 = "=============================================================\r\n" +
				"Albums by The Doors, sourced from Spotify\r\n" +
				"-------------------------------------------------------------\r\n";
		String expectedContents2 = "The Doors : 1967\r\n";
		String expectedContents3 = "Strange Days : 1967\r\n";
		String expectedContents4 = "Waiting For The Sun : 1968\r\n";
		String expectedContents5 = "=============================================================\r\n";
		List<Album> albumList = new ArrayList<>();
		DiscogsAlbum album1 = new DiscogsAlbum();
		album1.setTitle("The Doors");
		album1.setYear("1967");
		albumList.add(album1);
		DiscogsAlbum album2 = new DiscogsAlbum();
		album2.setTitle("Strange Days");
		album2.setYear("1967");
		albumList.add(album2);
		DiscogsAlbum album3 = new DiscogsAlbum();
		album3.setTitle("Waiting For The Sun");
		album3.setYear("1968");
		albumList.add(album3);

		// Method under test
		StandardOuput.displayAlbums(albumList);

		assertTrue(output.toString().startsWith(expectedContents1));
		// Can't test exact String in it's entirety as the order isn't guaranteed
		assertTrue(output.toString().contains(expectedContents2));
		assertTrue(output.toString().contains(expectedContents3));
		assertTrue(output.toString().contains(expectedContents4));
		assertTrue(output.toString().endsWith(expectedContents5));
	}

	@Test
	public void testDisplayAlbums_withListOfAlbumsContainingDuplicates() throws Exception {
		System.setProperty("api", "Spotify");
		System.setProperty("artist", "The Doors");
		String expectedContents1 = "=============================================================\r\n" +
				"Albums by The Doors, sourced from Spotify\r\n" +
				"-------------------------------------------------------------\r\n";
		String expectedContents2 = "The Doors : 1967\r\n";
		String expectedContents3 = "Strange Days : 1967\r\n";
		String expectedContents4 = "Waiting For The Sun : 1968\r\n";
		String expectedContents5 = "=============================================================\r\n";
		List<Album> albumList = new ArrayList<>();
		DiscogsAlbum album1 = new DiscogsAlbum();
		album1.setTitle("The Doors");
		album1.setYear("1967");
		albumList.add(album1);
		// Adding album twice
		albumList.add(album1);
		DiscogsAlbum album2 = new DiscogsAlbum();
		album2.setTitle("Strange Days");
		album2.setYear("1967");
		albumList.add(album2);
		DiscogsAlbum album3 = new DiscogsAlbum();
		album3.setTitle("Waiting For The Sun");
		album3.setYear("1968");
		albumList.add(album3);
		// Adding album twice
		albumList.add(album3);

		// Method under test
		StandardOuput.displayAlbums(albumList);

		assertTrue(output.toString().startsWith(expectedContents1));
		// Can't test exact String in it's entirety as the order isn't guaranteed
		assertTrue(output.toString().contains(expectedContents2));
		assertTrue(output.toString().contains(expectedContents3));
		assertTrue(output.toString().contains(expectedContents4));
		assertTrue(output.toString().endsWith(expectedContents5));
	}

	@Test
	public void testDisplayUnexpectedException() throws Exception {
		String errorMessage = "this error";
		when(unexpectedException.getMessage()).thenReturn(errorMessage);
		String expectedContents1 = "=============================================================\r\n" +
				"Failed due to " + errorMessage + "\r\n" +
				"-------------------------------------------------------------\r\n";
		String expectedContents2 = "=============================================================\r\n";

		// Method under test
		StandardOuput.displayUnexpectedException(unexpectedException);

		assertTrue(output.toString().startsWith(expectedContents1));
		// Can't test exact String as stackTrace isn't guaranteed
		assertTrue(output.toString().endsWith(expectedContents2));
		verify(unexpectedException, times(1)).getMessage();
	}

	@Test
	public void testDisplayUserError() throws Exception {
		String errorMessage = "this error";
		String expectedContents = "=============================================================\r\n" +
				"Failed due to missing " + errorMessage + "\r\n" +
				"=============================================================\r\n";

		// Method under test
		StandardOuput.displayUserError(errorMessage);

		assertEquals(expectedContents, output.toString());
	}
}
