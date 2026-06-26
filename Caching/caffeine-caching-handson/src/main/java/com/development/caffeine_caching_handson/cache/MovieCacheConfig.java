package com.development.caffeine_caching_handson.cache;

import java.time.Duration;

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

	// for automated size based caching purpose
	@Bean
	public LoadingCache<String, String> automatedMovieCache() {
		return Caffeine.newBuilder()
				.maximumSize(3) // uses LRU Policy to Evict the items from Cache
				.build(dataDao::movieByDirector); // this movieByDirector is our loader for the Cache miss
	}

//	for automated time based caching purpose
//	if the Entry has not been accessed in the given seconds, then evicted
//	@Bean
//	public LoadingCache<String, String> automatedMovieCache() {
//		return Caffeine.newBuilder()
//				.expireAfterAccess(Duration.ofSeconds(5))
//				.build(dataDao::movieByDirector); // this movieByDirector is our loader for the Cache miss
//	}
}
