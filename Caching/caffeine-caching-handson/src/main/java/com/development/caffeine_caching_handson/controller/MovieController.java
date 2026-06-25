package com.development.caffeine_caching_handson.controller;

import com.development.caffeine_caching_handson.repository.DataDao;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

	private final DataDao dataDao;
	private final Cache<String, String> movieCache;

	@GetMapping("/movie/{director}")
	public String getMovieByDirector(@PathVariable String director) {
		return movieCache.get(director, dataDao::movieByDirector);
	}
}
