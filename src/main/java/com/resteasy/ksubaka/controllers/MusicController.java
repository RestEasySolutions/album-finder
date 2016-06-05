package com.resteasy.ksubaka.controllers;

import com.resteasy.ksubaka.domain.Album;
import com.resteasy.ksubaka.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Container for the calls to the services
 * Created by Ant Brown on 05/06/2016.
 */
@Controller
public class MusicController {

	@Autowired
	MusicService musicService;

	/**
	 * Gets the albums for a supplied artist from the appropriate source
	 *
	 * @param artist
	 * @return a list of albums by the supplied artist
	 * @throws UnsupportedEncodingException
	 */
	public List<Album> getAlbums(String artist) {
		return musicService.getAlbums(artist);
	}

}
