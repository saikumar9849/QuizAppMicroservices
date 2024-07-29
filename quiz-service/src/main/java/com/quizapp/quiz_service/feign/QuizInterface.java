package com.quizapp.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.QuizResponse;

//LoadBalancing: FeignClient is also used as a load balancer.
//FeignClient: It is mainly used to call the calls in other microService. The "QUESTION-SERVICE" in the annotation indicates that we need to use this microService that is present in the eureka server to make the calls to that service

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
	
	//methode's that we need to call to get data from question service
	
	@GetMapping("/question/generate")
	public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);
	
	@PostMapping("/question/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsfromId(@RequestBody List<Integer> questionIds);
	
	@PostMapping("/question/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses);
}
