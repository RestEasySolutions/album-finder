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
public class SpotifyResponseTest {

	SpotifyResponse spotifyResponse;

	@Before
	public void setUp() {
		spotifyResponse = new SpotifyResponse();
	}

	/**
	 * Test the accessors for spotify album response
	 *
	 * @throws Exception
	 */
	@Test
	public void testResultsAccessors() throws Exception {
		SpotifyAlbumsResponse expected = new SpotifyAlbumsResponse();
		assertNull(spotifyResponse.getAlbums());
		spotifyResponse.setAlbums(expected);
		assertEquals(expected, spotifyResponse.getAlbums());
	}
}
