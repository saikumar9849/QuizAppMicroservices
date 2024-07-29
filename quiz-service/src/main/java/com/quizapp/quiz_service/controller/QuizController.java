package com.quizapp.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.QuizDto;
import com.quizapp.quiz_service.model.QuizResponse;
import com.quizapp.quiz_service.service.QuizService;


@RestController
@RequestMapping(path="quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizdto){
		return quizService.createQuiz(quizdto.getCategoryName(), quizdto.getNumberOfQues(), quizdto.getTitle());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable("id") Integer quizId){
		return quizService.getQuiz(quizId);
	}
	
	@PostMapping("/submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable("id") Integer quizId, @RequestBody List<QuizResponse> responses){
		return quizService.calculateScore(quizId,responses);
	}

}
