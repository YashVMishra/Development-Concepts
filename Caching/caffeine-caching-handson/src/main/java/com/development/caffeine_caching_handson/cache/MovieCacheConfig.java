package com.development.caffeine_caching_handson.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieCacheConfig {

	@Bean
	public Cache<String, String> movieCache() {
		return Caffeine.newBuilder().build();
	}
}
