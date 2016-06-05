package com.resteasy.ksubaka.services;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.spotify.SpotifyAlbum;
import com.resteasy.ksubaka.domain.spotify.SpotifyAlbumsResponse;
import com.resteasy.ksubaka.domain.spotify.SpotifyResponse;
import com.resteasy.ksubaka.domain.spotify.SpotifySearchResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by Ant Brown on 05/06/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({RestTemplate.class, ResponseEntity.class, SpotifyMusicService.class})
public class SpotifyMusicServiceTest {
	private final static String ARTIST = "An Artist";
	private SpotifyResponse spotifyResponse;
	private SpotifyAlbumsResponse spotifyAlbumsResponse;

	@Mock
	RestTemplate restTemplate;

	@Mock
	ResponseEntity<SpotifyResponse> responseEntitySpotifyResponse;

	@Mock
	ResponseEntity<SpotifyAlbum> responseEntitySpotifyAlbum;

	@InjectMocks
	private SpotifyMusicService spotifyMusicService;

	@Captor
	ArgumentCaptor argumentCaptor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		spotifyAlbumsResponse = new SpotifyAlbumsResponse();
		spotifyAlbumsResponse.setItems(new ArrayList<>());
		spotifyResponse = new SpotifyResponse();
		spotifyResponse.setAlbums(spotifyAlbumsResponse);
		whenNew(RestTemplate.class).withNoArguments().thenReturn(restTemplate);
		when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), (Class<?>) any(Class.class))).thenReturn((ResponseEntity) responseEntitySpotifyResponse).thenReturn((ResponseEntity) responseEntitySpotifyAlbum);
		when(responseEntitySpotifyResponse.getBody()).thenReturn(spotifyResponse);
	}

	@Test
	public void testGetAlbums_correctUriPassedToRestTemplateForSearch() throws Exception {
		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the URI being passed into the rest template has been correctly formed
		verify(restTemplate).exchange((URI) argumentCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(createSearchUri(), argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctHttpMethodPassedToRestTemplateForSearch() throws Exception {
		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the HTTP Method being passed into the rest template is GET
		verify(restTemplate).exchange(any(URI.class), (HttpMethod) argumentCaptor.capture(), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(HttpMethod.GET, argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_ensureNoHeadersPassedToRestTemplateForSearch() throws Exception {
		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate).exchange(any(URI.class), any(HttpMethod.class), (HttpEntity) argumentCaptor.capture(), (Class<?>) any(Class.class));
		assertNull(argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctResponseTypePassedToRestTemplateForSearch() throws Exception {
		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate).exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), (Class) argumentCaptor.capture());
		assertEquals(SpotifyResponse.class, (Class) argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctUriPassedToRestTemplateForAlbum() throws Exception {
		// We need to get some ids back to ensure a call to the get album service
		String id = "1";
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult(id));
		when(responseEntitySpotifyAlbum.getBody()).thenReturn(createSpotifyAlbum(null, null));

		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the URI being passed into the rest template for the second call has been correctly formed
		verify(restTemplate, times(2)).exchange((URI) argumentCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(createAlbumUri(id), argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctHttpMethodPassedToRestTemplateForAlbum() throws Exception {
		// We need to get some ids back to ensure a call to the get album service
		String id = "1";
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult(id));
		when(responseEntitySpotifyAlbum.getBody()).thenReturn(createSpotifyAlbum(null, null));

		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the HTTP Method being passed into the rest template is GET
		verify(restTemplate, times(2)).exchange(any(URI.class), (HttpMethod) argumentCaptor.capture(), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(HttpMethod.GET, argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_ensureNoHeadersPassedToRestTemplateForAlbum() throws Exception {
		// We need to get some ids back to ensure a call to the get album service
		String id = "1";
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult(id));
		when(responseEntitySpotifyAlbum.getBody()).thenReturn(createSpotifyAlbum(null, null));

		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate, times(2)).exchange(any(URI.class), any(HttpMethod.class), (HttpEntity) argumentCaptor.capture(), (Class<?>) any(Class.class));
		assertNull(argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctResponseTypePassedToRestTemplateForAlbum() throws Exception {
		// We need to get some ids back to ensure a call to the get album service
		String id = "1";
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult(id));
		when(responseEntitySpotifyAlbum.getBody()).thenReturn(createSpotifyAlbum(null, null));

		// Method under test
		spotifyMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate, times(2)).exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), (Class) argumentCaptor.capture());
		assertEquals(SpotifyAlbum.class, (Class) argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctResponseReturned() throws Exception {
		// Add some ids for 3 albums
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult("1"));
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult("2"));
		spotifyAlbumsResponse.getItems().add(createSpotifySearchResult("3"));

		// Create 3 albums to be returned by the get album details service
		SpotifyAlbum album1 = createSpotifyAlbum("The Doors", "1967");
		SpotifyAlbum album2 = createSpotifyAlbum("Strange Days", "1967");
		SpotifyAlbum album3 = createSpotifyAlbum("Waiting For The Sun", "1968");
		when(responseEntitySpotifyAlbum.getBody()).thenReturn(album1).thenReturn(album2).thenReturn(album3);

		// Method under test
		List<Album> actualResponse = spotifyMusicService.getAlbums(ARTIST);

		// Check that the response only contains 3 albums and that they are the correct ones
		// i.e. as returned from the Rest Template
		assertEquals(3, actualResponse.size());
		assertTrue(actualResponse.contains(album1));
		assertTrue(actualResponse.contains(album2));
		assertTrue(actualResponse.contains(album3));
	}

	/**
	 * Create a standard URI for the search service using the standard test artist
	 *
	 * @return a fully formed URI for an album search
	 */
	private URI createSearchUri() {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.spotify.com").path("/v1/search").queryParam("q", "artist:" + ARTIST).queryParam("type", "album").build();
		return uriComponents.toUri();
	}

	/**
	 * Create a standard URI for the get album details service using a supplied album id
	 *
	 * @param albumId - the id to be added into the uri
	 * @return a fully formed URI for a retrieve album details
	 */
	private URI createAlbumUri(String albumId) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.spotify.com").path("/v1/albums/" + albumId).build();
		return uriComponents.toUri();
	}

	/**
	 * Create a SpotifySearchResult cionatining only the supplied album id
	 *
	 * @param albumId - the albumId to be added into the new object
	 * @return a fully formed SpotifySearchResult
	 */
	private SpotifySearchResult createSpotifySearchResult(String albumId) {
		SpotifySearchResult searchResult = new SpotifySearchResult();
		searchResult.setId(albumId);
		return searchResult;
	}

	/**
	 * Create a SpotifySearchResult cionatining only the supplied album id
	 *
	 * @param name-       the name of the album
	 * @param releaseDate - the date that the album was released
	 * @return a fully formed SpotifyAlbum
	 */
	private SpotifyAlbum createSpotifyAlbum(String name, String releaseDate) {
		SpotifyAlbum spotifyAlbum = new SpotifyAlbum();
		spotifyAlbum.setName(name);
		spotifyAlbum.setRelease_date(releaseDate);
		return spotifyAlbum;
	}
}
