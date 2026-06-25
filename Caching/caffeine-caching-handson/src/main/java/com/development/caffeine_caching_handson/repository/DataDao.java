package com.development.caffeine_caching_handson.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class DataDao {
	private static final Map<String, String> DATA = Map.of(
			"John","Die Hard","George","Mad Max: Fury Road",
			"Chad","John Wick",
			"James","Terminator 2: Judgment Day",
			"The Wachowskis","The Matrix","Richard","Lethal Weapon",
			"Christopher","Mission: Impossible - Fallout",
			"Quentin","Kill Bill: Volume 1","Ridley","Gladiator"
	);

	public Map<String, String> getAll() {
		System.out.println("DAO -> Calling getAll");
		return DATA;
	}

	public String movieByDirector(String director) {
		System.out.println("DAO -> Calling movieByDirector -> " + director);
		return DATA.get(director);
	}
}

