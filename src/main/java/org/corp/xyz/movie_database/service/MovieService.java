package org.corp.xyz.movie_database.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.corp.xyz.movie_database.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieService {
	
	public static long nextId = 1L;
	public static List<Movie> moviesList = new LinkedList<Movie>();
	
	static {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(""));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			int rows = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					long id = Long.parseLong(row.getCell(0).toString().trim());
					if (nextId <= id) {
						nextId = id + 1;
					}
					String name = row.getCell(1).toString();
					String description = row.getCell(2).toString();
					Movie movie = new Movie(id, name.trim(), description.trim());
					moviesList.add(movie);
				}
			}
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		Movie movie = new Movie(234890912, "Avengers", "Age of");
		MovieService.moviesList.add(movie);
	}
	
	public Movie movieFetch(String searchPhrase) {
		Pattern p = Pattern.compile(".*" + searchPhrase + ".*");
		for (Movie movie : moviesList) {
			Matcher m = p.matcher(movie.getName().toLowerCase());
			if (m.matches()) {
				return movie;
			}
		}
		return null;
	}
	
	public Movie findMovieById(long id) {
		for (Movie movie : moviesList) {
			if (movie.getId() == id) {
				return movie;
			}
		}
		return null;
	}
	
	public boolean addMovie(Movie movie) {
		return moviesList.add(movie);
	}
}
