package org.corp.xyz.movie_database.model;

import org.corp.xyz.movie_database.service.MovieService;

public class Movie {
	private long id;
	private String name;
	private String description;
	
	public Movie(long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Movie(MovieService movieService, String name, String description) {
		this.id = MovieService.nextId;
		MovieService.nextId++;
		this.name = name;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
