package org.corp.xyz.movie_database.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
	@RequestMapping("/")
	public @ResponseBody String hello() {
		return "Hello World!";
	}
}
