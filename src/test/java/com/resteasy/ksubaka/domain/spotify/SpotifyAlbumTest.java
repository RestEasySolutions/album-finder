package com.resteasy.ksubaka.domain.spotify;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Simple bean tester
 * Just tests the accessors to ensure that no-one adds any unexpected behaviour
 * Created by Ant Brown on 05/06/2016.
 */
public class SpotifyAlbumTest {

	SpotifyAlbum spotifyAlbum;

	@Before
	public void setUp(){
		spotifyAlbum = new SpotifyAlbum();
	}

	/**
	 * Test the accessors for name
	 * @throws Exception
	 */
	@Test
	public void testNameAccessors() throws Exception {
		String expected = "First And Last And Always";
		assertNull(spotifyAlbum.getName());
		spotifyAlbum.setName(expected);
		assertEquals(expected, spotifyAlbum.getName());
	}

	/**
	 * Test the accessors for title
	 * @throws Exception
	 */
	@Test
	public void testTitleAccessors() throws Exception {
		String expected = "First And Last And Always";
		assertNull(spotifyAlbum.getName());
		assertNull(spotifyAlbum.getTitle());
		spotifyAlbum.setName(expected);
		assertEquals(expected, spotifyAlbum.getTitle());
	}

	/**
	 * Test the accessors for release_date
	 * @throws Exception
	 */
	@Test
	public void testReleaseDateAccessors() throws Exception {
		String expected = "1967-08-15";
		String expectedYear = "1967";
		assertNull(spotifyAlbum.getRelease_date());
		spotifyAlbum.setRelease_date(expected);
		assertEquals(expected, spotifyAlbum.getRelease_date());
	}

	/**
	 * Test the accessors for year
	 * @throws Exception
	 */
	@Test
	public void testYearAccessors() throws Exception {
		String expected = "1967-08-15";
		String expectedYear = "1967";
		assertNull(spotifyAlbum.getRelease_date());
		assertNull(spotifyAlbum.getYear());
		spotifyAlbum.setRelease_date(expected);
		assertEquals(expectedYear, spotifyAlbum.getYear());
	}
}
