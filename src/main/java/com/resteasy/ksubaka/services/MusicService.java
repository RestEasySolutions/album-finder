package com.resteasy.ksubaka.services;

import com.resteasy.ksubaka.domain.Album;

import java.util.List;

/**
 * Defines the expected methods required by any implentation
 * Created by Ant Brown on 05/06/2016.
 */
public interface MusicService {
	List<Album> getAlbums(String artistTitle);
}
