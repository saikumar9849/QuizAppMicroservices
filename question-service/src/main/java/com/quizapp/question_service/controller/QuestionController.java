package com.quizapp.question_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quizapp.question_service.entities.Question;
import com.quizapp.question_service.model.QuestionWrapper;
import com.quizapp.question_service.model.QuizResponse;
import com.quizapp.question_service.service.QuestionService;


@RestController
@RequestMapping(path="question")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@GetMapping(path="/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions(){
		return questionService.getAllQuestions();
	}
	
	@GetMapping("/category/{cat}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cat")String category){
		return questionService.getQuestionsByCategory(category);
	}
	
	@PostMapping("/addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}
	
	//helper functions to provide required data to the quiz-service microservice
	
	//generate: to generate the questions for the Quiz
	@GetMapping("/generate")
	public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
		return questionService.generateQuestionsForQuiz(categoryName,numQuestions);
	}
	
	//getQuestions (questionid): to send the questions info(id, que, and options) to quiz-service.
	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsfromId(@RequestBody List<Integer> questionIds){
		return questionService.getQuestionsFromId(questionIds);
	}
	
	//getScore: to calculate the score for quiz using the responses and return score
	@PostMapping("/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses){
		return questionService.getScore(responses);
	}
}
