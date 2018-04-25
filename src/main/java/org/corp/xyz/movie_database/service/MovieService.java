package org.corp.xyz.movie_database.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.corp.xyz.movie_database.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieService {
	
	private static final String SEED_DATA_FILE_NAME = "/home/karteek/movies_list_sheet.xlsx";
	
	public static long nextId = 1L;
	public static List<Movie> moviesList = new LinkedList<Movie>();
	
	static {
		try {
			Workbook wb = new XSSFWorkbook(new FileInputStream(new File(SEED_DATA_FILE_NAME)));
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row != null) {
					long id = Long.parseLong(row.getCell(0).toString().trim());
					if (nextId <= id) {
						nextId = id + 1;
					}
					String name = row.getCell(1).toString();
					String description = row.getCell(2).toString();
					Movie movie = new Movie(id, name.trim(), description.trim());
					moviesList.add(movie);
				} else {
					break;
				}
            }
            wb.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
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
