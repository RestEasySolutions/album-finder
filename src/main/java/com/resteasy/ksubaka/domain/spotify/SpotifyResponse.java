package com.resteasy.ksubaka.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The full response from the Spotify album search
 * We are only interested in the element named albums, which is a single
 * element (containing an array of albums)
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyResponse {
	private SpotifyAlbumsResponse albums;

	/**
	 * Getter for the albums container object
	 *
	 * @return the albums container object
	 */
	public SpotifyAlbumsResponse getAlbums() {
		return albums;
	}

	/**
	 * Setter for the albums container object
	 *
	 * @param albums - the albums container object
	 */
	public void setAlbums(SpotifyAlbumsResponse albums) {
		this.albums = albums;
	}

}
