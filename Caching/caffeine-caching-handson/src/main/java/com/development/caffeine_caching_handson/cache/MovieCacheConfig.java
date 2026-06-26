package com.development.caffeine_caching_handson.cache;

import java.time.Duration;

import com.development.caffeine_caching_handson.repository.DataDao;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.RequiredArgsConstructor;

import org.jspecify.annotations.Nullable;
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
				.removalListener(new RemovalListener<String, String>() { // asynchronous in nature
					@Override
					public void onRemoval(@Nullable String key, @Nullable String value, RemovalCause cause) {
						System.out.println("Key = " + key + ", value = " + value + " was removed. Reason = " + cause.toString());
					}
				})
				.evictionListener(new RemovalListener<String, String>() { // synchronous in nature
					@Override
					public void onRemoval(@Nullable String key, @Nullable String value, RemovalCause cause) {
						System.out.println("Key = " + key + ", value = " + value + " was evicted. Reason = " + cause.toString());
					}
				})
				.build(dataDao::movieByDirector); // this movieByDirector is our loader for the Cache miss
	}

	// for automated time based caching purpose
	// if the Entry has not been accessed in the given seconds, then evicted
//	@Bean
//	public LoadingCache<String, String> automatedMovieCache() {
//		return Caffeine.newBuilder()
//				.expireAfterAccess(Duration.ofSeconds(5))
//				.build(dataDao::movieByDirector); // this movieByDirector is our loader for the Cache miss
//	}

	// usage of refreshAfterWrite method, after the time has lapsed
	// the entry merely becomes eligible for refresh, the next access
	// triggers the refresh, until refresh finishes the old cached value is returned
//	@Bean
//	public LoadingCache<String, String> automatedMovieCache() {
//		return Caffeine.newBuilder()
//				.refreshAfterWrite(Duration.ofSeconds(10))
//				.build(dataDao::movieByDirector); // this movieByDirector is our loader for the Cache miss
//	}
}
