package com.resteasy.ksubaka.controllers;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.spotify.SpotifyAlbum;
import com.resteasy.ksubaka.services.MusicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Ant Brown on 05/06/2016.
 */
public class MusicControllerTest {

	@Mock
	MusicService musicService;

	@InjectMocks
	private MusicController musicController;

	@Before
	public void setUp() {
		initMocks(this);
	}

	/**
	 * Test that the list of albums returned from the music service
	 * is passed back correctly
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetAlbums() throws Exception {
		String suppliedString = "First And Last And Always";
		List<Album> albums = new ArrayList<>();
		albums.add(new SpotifyAlbum());
		when(musicService.getAlbums(anyString())).thenReturn(albums);
		// the method being tested
		List<Album> response = musicController.getAlbums(suppliedString);

		assertNotNull(response);
		assertEquals(1, response.size());
		verify(musicService).getAlbums(anyString());
	}
}
