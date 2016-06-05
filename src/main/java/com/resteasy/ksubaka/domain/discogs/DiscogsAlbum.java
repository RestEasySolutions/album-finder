package com.resteasy.ksubaka.domain.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.resteasy.ksubaka.domain.Album;

/**
 * The Discogs database implementation of an Album
 * Created by Ant Brown on 05/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsAlbum implements Album {
	private String title;
	private String year;

	/**
	 * Getter for title
	 *
	 * @return the title of the album
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 *
	 * @param title - the title of the album
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for year
	 *
	 * @return the year that the album was released
	 */
	@Override
	public String getYear() {
		return year;
	}

	/**
	 * Setter for year
	 *
	 * @param year - the year that the album was released
	 */
	public void setYear(String year) {
		this.year = year;
	}
}
