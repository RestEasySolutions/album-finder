package com.resteasy.ksubaka.services;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.domain.discogs.DiscogsResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
 * All services that interact with the Discogs database
 * Created by Ant Brown on 05/06/2016.
 */
@Service
@Primary
@Profile("discogs")
public class DiscogsMusicService implements MusicService {
	private static final String SCHEME = "https";
	private static final String HOST = "api.discogs.com";
	private static final String RESOURCE_PATH = "/database/search";
	private static final String KEY_QUERY_PARAM_ARTIST = "artist";
	private static final String KEY_QUERY_PARAM_FORMAT = "format";
	private static final String QUERY_PARAM_FORMAT = "cd";

	/**
	 * Retrieves a list of albums from the Discogs database via its public API
	 * Creates a URI using the supplied parameter, calls the service and maps the response into domain objects
	 *
	 * @param artistTitle
	 * @return a list of albums
	 */
	@Override
	public List<Album> getAlbums(String artistTitle) {

		// Form the URI to access the retrieve albums service
		URI uri = createUri(artistTitle);

		// Create the http headers mandated by the API
		HttpEntity<String> request = createHttpHeaders();

		// Call the service and map the response into a domain object
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<DiscogsResponse> response = restTemplate.exchange(uri, HttpMethod.GET, request, DiscogsResponse.class);

		DiscogsResponse discogsResponse = response.getBody();
		return new ArrayList<>(discogsResponse.getResults());
	}

	/**
	 * Forms the URI to access the retrieve albums service, embedding the artists name
	 * @param artistTitle
	 * @return a fully formed URI for the get albums request
	 */
	private URI createUri(String artistTitle) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(SCHEME).host(HOST).path(RESOURCE_PATH).queryParam(KEY_QUERY_PARAM_ARTIST, artistTitle).queryParam(KEY_QUERY_PARAM_FORMAT, QUERY_PARAM_FORMAT).build();
		return uriComponents.toUri();
	}

	/**
	 * Create the http headers mandated by the API
	 * @return the fully formed http headers wrapped in a HttpEntity
	 */
	private HttpEntity<String> createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("User-Agent", "RestEasySolutions");
		headers.add("Authorization", "Discogs token=WKvKNNNMPYCbgegrZYYqMCmsZEVZVyCLhKVUHJNj");
		return new HttpEntity<String>(headers);

	}
}