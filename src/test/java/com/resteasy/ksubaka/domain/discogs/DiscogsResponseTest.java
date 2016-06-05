package com.resteasy.ksubaka.domain.discogs;

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
public class DiscogsResponseTest {

	DiscogsResponse discogsResponse;

	@Before
	public void setUp() {
		discogsResponse = new DiscogsResponse();
	}

	/**
	 * Test the accessors for results
	 *
	 * @throws Exception
	 */
	@Test
	public void testResultsAccessors() throws Exception {
		List<DiscogsAlbum> expected = new ArrayList<>();
		assertNull(discogsResponse.getResults());
		discogsResponse.setResults(expected);
		assertEquals(expected, discogsResponse.getResults());
	}
}
