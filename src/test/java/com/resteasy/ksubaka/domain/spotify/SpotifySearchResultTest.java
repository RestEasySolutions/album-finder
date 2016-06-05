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
public class SpotifySearchResultTest {

	SpotifySearchResult spotifySearchResult;

	@Before
	public void setUp() {
		spotifySearchResult = new SpotifySearchResult();
	}

	/**
	 * Test the accessors for id
	 *
	 * @throws Exception
	 */
	@Test
	public void testIdAccessors() throws Exception {
		String expected = "123-ABC";
		assertNull(spotifySearchResult.getId());
		spotifySearchResult.setId(expected);
		assertEquals(expected, spotifySearchResult.getId());
	}
}
