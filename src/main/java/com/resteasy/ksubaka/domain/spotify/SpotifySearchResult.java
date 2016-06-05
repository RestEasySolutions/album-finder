package com.resteasy.ksubaka.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An element containing an album id
 * This is the end result of the album search, but we only need
 * the id, as we will use that to retrieve all of the album details
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySearchResult {
	private String id;

	/**
	 * Getter for the album id
	 *
	 * @return the album id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for the album id
	 *
	 * @param id - the album id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
