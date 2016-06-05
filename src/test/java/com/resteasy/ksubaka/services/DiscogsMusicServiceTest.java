package com.resteasy.ksubaka.services;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.discogs.DiscogsAlbum;
import com.resteasy.ksubaka.domain.discogs.DiscogsResponse;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by Ant Brown on 05/06/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({RestTemplate.class, ResponseEntity.class, DiscogsMusicService.class})
public class DiscogsMusicServiceTest {
	private final static String ARTIST = "An Artist";
	private DiscogsResponse discogsResponse;

	@Mock
	RestTemplate restTemplate;

	@Mock
	ResponseEntity<DiscogsResponse> responseEntity;

	@InjectMocks
	private DiscogsMusicService discogsMusicService;

	@Captor
	ArgumentCaptor argumentCaptor;

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		discogsResponse = new DiscogsResponse();
		discogsResponse.setResults(new ArrayList<>());
		whenNew(RestTemplate.class).withNoArguments().thenReturn(restTemplate);
		when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(ResponseEntity.class), (Class<?>) any(Class.class))).thenReturn((ResponseEntity) responseEntity);
		when(responseEntity.getBody()).thenReturn(discogsResponse);
	}

	@Test
	public void testGetAlbums() throws Exception {
		discogsMusicService.getAlbums(ARTIST);
	}

	@Test
	public void testGetAlbums_correctUriPassedToRestTemplate() throws Exception {
		discogsMusicService.getAlbums(ARTIST);

		// Check that the URI being passed into the rest template has been correctly formed
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host("api.discogs.com").path("/database/search").queryParam("artist", ARTIST).queryParam("format", "cd").build();
		URI expectedUri = uriComponents.toUri();
		verify(restTemplate).exchange((URI) argumentCaptor.capture(), any(HttpMethod.class), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(expectedUri, argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctHttpMethodPassedToRestTemplate() throws Exception {
		discogsMusicService.getAlbums(ARTIST);

		// Check that the HTTP Method being passed into the rest template is GET
		verify(restTemplate).exchange(any(URI.class), (HttpMethod) argumentCaptor.capture(), any(HttpEntity.class), (Class<?>) any(Class.class));
		assertEquals(HttpMethod.GET, argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctHeadersPassedToRestTemplate() throws Exception {
		discogsMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate).exchange(any(URI.class), any(HttpMethod.class), (HttpEntity) argumentCaptor.capture(), (Class<?>) any(Class.class));
		assertTrue("User-Agent", ((HttpEntity) argumentCaptor.getValue()).getHeaders().containsKey("User-Agent"));
		assertEquals("RestEasySolutions", ((HttpEntity) argumentCaptor.getValue()).getHeaders().get("User-Agent").get(0));
		assertTrue("Authorization", ((HttpEntity) argumentCaptor.getValue()).getHeaders().containsKey("Authorization"));
		assertEquals("Discogs token=WKvKNNNMPYCbgegrZYYqMCmsZEVZVyCLhKVUHJNj", ((HttpEntity) argumentCaptor.getValue()).getHeaders().get("Authorization").get(0));
		// There should only be the two that we have added
		assertEquals(2, ((HttpEntity) argumentCaptor.getValue()).getHeaders().size());
	}

	@Test
	public void testGetAlbums_correctResponseTypePassedToRestTemplate() throws Exception {
		discogsMusicService.getAlbums(ARTIST);

		// Check that the http headers mandated by the API are passed in correctly
		verify(restTemplate).exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), (Class) argumentCaptor.capture());
		assertEquals(DiscogsResponse.class, (Class) argumentCaptor.getValue());
	}

	@Test
	public void testGetAlbums_correctResponseReturned() throws Exception {
		DiscogsAlbum album1 = new DiscogsAlbum();
		album1.setTitle("The Doors");
		album1.setYear("1967");
		discogsResponse.getResults().add(album1);
		DiscogsAlbum album2 = new DiscogsAlbum();
		album2.setTitle("Strange Days");
		album2.setYear("1967");
		discogsResponse.getResults().add(album2);
		DiscogsAlbum album3 = new DiscogsAlbum();
		album3.setTitle("Waiting For The Sun");
		album3.setYear("1968");
		discogsResponse.getResults().add(album3);

		List<Album> actualResponse = discogsMusicService.getAlbums(ARTIST);

		// Check that the response only contains 3 albums and that they are the correct ones
		// i.e. as returned from the Rest Template
		assertEquals(3, actualResponse.size());
		assertTrue(actualResponse.contains(album1));
		assertTrue(actualResponse.contains(album2));
		assertTrue(actualResponse.contains(album3));
	}

}
