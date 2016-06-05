package com.resteasy.ksubaka.domain.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The full response from the Discogs album search
 * We are only interested in the element named results, which contains an
 * array of albums
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsResponse {
	private List<DiscogsAlbum> results;

	/**
	 * Getter for results
	 *
	 * @return the results in the response (a list of albums)
	 */
	public List<DiscogsAlbum> getResults() {
		return results;
	}

	/**
	 * Setter for results
	 *
	 * @param results - the results in the response (a list of albums)
	 */
	public void setResults(List<DiscogsAlbum> results) {
		this.results = results;
	}
}
