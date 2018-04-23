package org.corp.xyz.movie_database.controllers;

import java.util.ArrayList;
import java.util.List;

import org.corp.xyz.movie_database.model.Movie;
import org.corp.xyz.movie_database.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody List<Movie> movieSearch(@RequestParam("name") String searchPhrase) {
		if (searchPhrase.equalsIgnoreCase("all")) {
			return MovieService.moviesList;
		}
		Movie movie;
		movie = movieService.movieFetch(searchPhrase);
		if (movie != null) {
			List <Movie> list = new ArrayList<Movie>();
			list.add(movie);
			return list;
		} else {
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public @ResponseBody long createMovie(@RequestParam("movieName") String name, @RequestParam("movieDescription") String description) {
		Movie movie = createNewMovieEntry(name, description);
		return movie.getId();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, produces = "text/html; charset=utf-8")
	public @ResponseBody String deleteMovie(@RequestParam("id") long id) {
		Movie movie = movieService.findMovieById(id);
		if (movie == null) {
			return Boolean.FALSE.toString();
		}
		List<Movie> moviesList = MovieService.moviesList;
		if (moviesList.remove(movie)) {
			return Boolean.TRUE.toString();
		} else {
			return Boolean.FALSE.toString();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html; charset=utf-8")
	public @ResponseBody String updateMovie(@RequestBody Movie movie) {
		Movie existingMovie = movieService.findMovieById(movie.getId());
		if (existingMovie == null) {
			return Boolean.FALSE.toString();
		}
		existingMovie.setName(movie.getName().trim());
		existingMovie.setDescription(movie.getDescription().trim());
		return Boolean.TRUE.toString();
	}
	
	public Movie createNewMovieEntry(String name, String description) {
		for (Movie movie : MovieService.moviesList) {
			if (movie.getName().toLowerCase().contentEquals(name.trim().toLowerCase()) && 
					movie.getDescription().toLowerCase().contentEquals(description.trim().toLowerCase())) {
				return movie;
			}
		}
		Movie movie = new Movie(movieService, name.trim(), description.trim());
		if (movieService.addMovie(movie)) {
			return movie;
		} else {
			return null;
		}
	}
}
