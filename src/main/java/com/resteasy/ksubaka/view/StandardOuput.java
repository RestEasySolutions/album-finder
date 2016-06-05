package com.resteasy.ksubaka.view;

import com.resteasy.ksubaka.domain.Album;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages output to System.out
 * Created by Ant Brown on 05/06/2016.
 */
public class StandardOuput {
	/**
	 * Outputs the results to standard output
	 *
	 * @param albumList the albums to be displayed
	 */
	public static void displayAlbums(List<Album> albumList) {
		System.out.println("=============================================================");
		System.out.println("Albums by " + System.getProperty("artist") + ", sourced from " + System.getProperty("api"));
		System.out.println("-------------------------------------------------------------");
		if (albumList == null || albumList.isEmpty()) {
			System.out.println("No albums by this artist");
		} else {
			Set<Album> albums = new HashSet<>(albumList);
			for (Album album : albums) {
				System.out.println(album.getTitle() + " : " + album.getYear());
			}
		}
		System.out.println("=============================================================");
	}

	/**
	 * Displays an error message and stack trace
	 *
	 * @param e the exception to report
	 */
	public static void displayUnexpectedException(Exception e) {
		System.out.println("=============================================================");
		System.out.println("Failed due to " + e.getMessage());
		System.out.println("-------------------------------------------------------------");
		e.printStackTrace();
		System.out.println("=============================================================");
	}

	/**
	 * Reports a missing parameter
	 *
	 * @param missingValue
	 */
	public static void displayUserError(String missingValue) {
		System.out.println("=============================================================");
		System.out.println("Failed due to missing " + missingValue);
		System.out.println("=============================================================");
	}
}
