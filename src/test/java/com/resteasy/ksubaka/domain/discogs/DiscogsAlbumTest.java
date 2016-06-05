package com.resteasy.ksubaka.domain.discogs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Simple bean tester
 * Just tests the accessors to ensure that no-one adds any unexpected behaviour
 * Created by Ant Brown on 05/06/2016.
 */
public class DiscogsAlbumTest {

	private DiscogsAlbum discogsAlbum;

	@Before
	public void setUp() {
		discogsAlbum = new DiscogsAlbum();
	}

	/**
	 * Test the accessors for title
	 *
	 * @throws Exception
	 */
	@Test
	public void testTitleAccessors() throws Exception {
		String expected = "First And Last And Always";
		assertNull(discogsAlbum.getTitle());
		discogsAlbum.setTitle(expected);
		assertEquals(expected, discogsAlbum.getTitle());
	}

	/**
	 * Test the accessors for year
	 *
	 * @throws Exception
	 */
	@Test
	public void testYearAccessors() throws Exception {
		String expected = "1967";
		assertNull(discogsAlbum.getYear());
		discogsAlbum.setYear(expected);
		assertEquals(expected, discogsAlbum.getYear());
	}

}
