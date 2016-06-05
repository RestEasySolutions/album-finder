package com.resteasy.ksubaka.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resteasy.ksubaka.domain.Album;

/**
 * The Spotify datbase implementation of an Album
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyAlbum implements Album {

	/*
	 * The spotify API doesn't call its fields title and year, so we need
	 * to retrieve them and then use the Album methods to return them through
	 * the interface
	 */
	private String name;
	private String release_date;

	/**
	 * Getter for name
	 *
	 * @return the name of the album
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 *
	 * @param name - the name of the album
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the release date of the album
	 *
	 * @return the release date of the album
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * Setter for the release date of the album
	 *
	 * @param release_date - the release date of the album
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	/**
	 * Getter for title (sourced from the name)
	 *
	 * @return the title of the album
	 */
	@Override
	public String getTitle() {
		return name;
	}

	/**
	 * Getter for year (sourced from the year, month and date that
	 * the album was released, so we only need the forst for characters)
	 *
	 * @return the year that the album was released
	 */
	@Override
	public String getYear() {
		if (release_date != null) {
			return release_date.substring(0, 4);
		} else {
			return null;
		}
	}
}
