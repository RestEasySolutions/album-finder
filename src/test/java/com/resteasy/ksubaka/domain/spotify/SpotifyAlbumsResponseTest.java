package com.resteasy.ksubaka.domain.spotify;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Simple bean tester
 * Just tests the accessors to ensure that no-one adds any unexpected behaviour
 * Created by Ant Brown on 05/06/2016.
 */
public class SpotifyAlbumsResponseTest {

	SpotifyAlbumsResponse spotifyAlbumsResponse;

	@Before
	public void setUp() {
		spotifyAlbumsResponse = new SpotifyAlbumsResponse();
	}

	/**
	 * Test the accessors for items
	 *
	 * @throws Exception
	 */
	@Test
	public void testResultsAccessors() throws Exception {
		List<SpotifySearchResult> expected = new ArrayList<>();
		assertNull(spotifyAlbumsResponse.getItems());
		spotifyAlbumsResponse.setItems(expected);
		assertEquals(expected, spotifyAlbumsResponse.getItems());
	}
}
