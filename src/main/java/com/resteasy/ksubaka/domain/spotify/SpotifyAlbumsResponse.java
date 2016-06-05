package com.resteasy.ksubaka.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * We are only interested in the element named items, which contains an
 * array of album identifiers
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbumsResponse {
	private List<SpotifySearchResult> items;

	/**
	 * Getter for the SpotifySearch Result objects
	 *
	 * @return a list of SpotifySearch Result objects
	 */
	public List<SpotifySearchResult> getItems() {
		return items;
	}

	/**
	 * Getter for the SpotifySearch Result objects
	 *
	 * @param items - a list of SpotifySearch Result objects
	 */
	public void setItems(List<SpotifySearchResult> items) {
		this.items = items;
	}

}
