package com.development.caffeine_caching_handson.cache;

import com.development.caffeine_caching_handson.repository.DataDao;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MovieCacheConfig {

	private final DataDao dataDao;

	// for manual caching purpose
	@Bean
	public Cache<String, String> manualMovieCache() {
		return Caffeine.newBuilder().build();
	}

	// for automated caching purpose
	@Bean
	public LoadingCache<String, String> automatedMovieCache() {
		return Caffeine.newBuilder().build(dataDao::movieByDirector);
	}
}
