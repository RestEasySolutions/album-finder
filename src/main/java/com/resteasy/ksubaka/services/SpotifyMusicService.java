package com.resteasy.ksubaka.services;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.spotify.SpotifyAlbum;
import com.resteasy.ksubaka.domain.spotify.SpotifyResponse;
import com.resteasy.ksubaka.domain.spotify.SpotifySearchResult;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * All services that interact with the Spotify database
 * Created by Ant Brown on 05/06/2016.
 */
@Service
@Profile("spotify")
public class SpotifyMusicService implements MusicService {

	private static final String SCHEME = "https";
	private static final String HOST = "api.spotify.com";
	private static final String SEARCH_RESOURCE_PATH = "/v1/search";
	private static final String ALBUM_RESOURCE_PATH = "/v1/albums/";
	private static final String KEY_QUERY_PARAM_ARTIST = "q";
	private static final String KEY_QUERY_PARAM_TYPE = "type";
	private static final String QUERY_PARAM_TYPE = "album";

	/**
	 * Retrieves a list of albums from the Spotify database via its public API
	 * The initial response contains a summary of the albums, but this doesn't
	 * include the year of release, so we need to get the album id from the
	 * summary element and retrieve the album details to obtain the year
	 *
	 * @param artistTitle
	 * @return a list of albums
	 */
	@Override
	public List<Album> getAlbums(String artistTitle) {

		//Form the URI to access the retrieve albums service
		URI uriForSearch = createUriForSearch(artistTitle);

		// Call the service and map the response into a domain object
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SpotifyResponse> response = restTemplate.exchange(uriForSearch, HttpMethod.GET, null, SpotifyResponse.class);

		List<Album> responseAlbums = new ArrayList<>();
		// Obtain the list of album summaries
		SpotifyResponse spotifyResponse = response.getBody();
		// Iterate over each summary using the ID to retrieve the full album details
		for (SpotifySearchResult searchResult : spotifyResponse.getAlbums().getItems()) {
			restTemplate = new RestTemplate();
			//Form the URI to access the retrieve album details service
			URI uriForAlbumDetails = createUriForAlbumDetails(searchResult.getId());

			// Call the service and map the response into a domain object
			ResponseEntity<SpotifyAlbum> album = restTemplate.exchange(uriForAlbumDetails, HttpMethod.GET, null, SpotifyAlbum.class);
			responseAlbums.add(album.getBody());
		}
		return responseAlbums;
	}

	/**
	 * Forms the URI to access the retrieve albums service, embedding the artists name
	 *
	 * @param artistTitle
	 * @return a fully formed URI for the get albums request
	 */
	private URI createUriForSearch(String artistTitle) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(SCHEME).host(HOST).path(SEARCH_RESOURCE_PATH).queryParam(KEY_QUERY_PARAM_ARTIST, "artist:" + artistTitle).queryParam(KEY_QUERY_PARAM_TYPE, QUERY_PARAM_TYPE).build();
		return uriComponents.toUri();
	}

	/**
	 * Forms the URI to access the retrieve an album's details, embedding the album id
	 *
	 * @param albumId
	 * @return a fully formed URI for the get album details request
	 */
	private URI createUriForAlbumDetails(String albumId) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(SCHEME).host(HOST).path(ALBUM_RESOURCE_PATH + albumId).build();
		return uriComponents.toUri();
	}
}