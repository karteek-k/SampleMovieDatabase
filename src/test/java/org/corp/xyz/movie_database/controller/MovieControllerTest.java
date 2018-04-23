package org.corp.xyz.movie_database.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.corp.xyz.movie_database.controllers.MovieController;
import org.corp.xyz.movie_database.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
public class MovieControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MovieService movieService;
	
	String sampleMovieJsonPrefix = "{\"id\":";
	String sampleMovieJson = "\"name\":\"Avengers\",\"description\":\"Age of Ultron\"}";
	
	@Test
	public void createNewMovieIfDoesnotExists() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/movie").accept(MediaType.APPLICATION_JSON)
				.param("movieName", "Avengers").param("movieDescription", "Age of").contentType(MediaType.TEXT_HTML);
		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertFalse(response.getContentAsString().equals(""));
			assertFalse(response.getContentAsString().equalsIgnoreCase("null"));
			Pattern p = Pattern.compile(".*(\\d+).*");
			Matcher m = p.matcher(response.getContentAsString().trim());
			if (m.matches()) {
				sampleMovieJson = sampleMovieJsonPrefix + m.group(0) + "," + sampleMovieJson;
			} else {
				fail("String doesn't match the regex pattern");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void searchForMovie() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie").accept(MediaType.APPLICATION_JSON).param("name", "all").contentType(MediaType.TEXT_PLAIN);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expectedPattern = ".*venger.*";
		Pattern p = Pattern.compile(expectedPattern);
		Matcher m = p.matcher(result.getResponse().getContentAsString().trim());
		if (!m.matches()) {
			fail("No movies found!");
		}
	}
	
	@Test
	public void updateExistingMovie() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/movie").accept(MediaType.TEXT_HTML)
				.content(sampleMovieJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			System.out.println(response.getContentAsString().trim());
//			assertEquals(response.getContentAsString().trim(), Boolean.TRUE.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void removeExistingMovie() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/movie").accept(MediaType.TEXT_HTML)
				.content(sampleMovieJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			System.out.println(response.getContentAsString().trim());
//			assertEquals(response.getContentAsString().trim(), Boolean.TRUE.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
