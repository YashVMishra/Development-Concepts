package com.development.caffeine_caching_handson.controller;

import com.development.caffeine_caching_handson.repository.DataDao;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

	private final DataDao dataDao;

	// used for manually populating the cache
	private final Cache<String, String> manualMovieCache;

	// used for automated populating the cache
	private final LoadingCache<String, String> automatedMovieCacheWithSizeBasedEviction;

	@GetMapping("/movie/{director}")
	public String getMovieByDirectorManualCache(@PathVariable String director) {

		// manually populating the cache, when we ourselves gives the logic on
		// how to populate the cache when it misses, then it is Manual Population
		// of cache.
		return manualMovieCache.get(director, dataDao::movieByDirector);
	}

	@GetMapping("/movie")
	public String getMovieByDirectorAutomatedCache(@RequestParam String director) {

		// automated populating the cache, we just pass the key value, and
		// if misses then the cache automatically handles the miss and populates
		// the cache itself.
		return automatedMovieCacheWithSizeBasedEviction.get(director);
	}
}
