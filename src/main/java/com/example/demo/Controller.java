package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	WordsHandler wordsHandler;
	
	@PostMapping("/add")
	public void addWord(@RequestBody String words) {
		wordsHandler.addWords(words);
		
	}
	
	@GetMapping("/stats")
	public String getStats() { 
		return wordsHandler.getStats();
	}
}

