package com.quizapp.quiz_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.quiz_service.dao.QuizDao;
import com.quizapp.quiz_service.entities.Quiz;
import com.quizapp.quiz_service.feign.QuizInterface;
import com.quizapp.quiz_service.model.QuestionWrapper;
import com.quizapp.quiz_service.model.QuizResponse;


@Service
public class QuizService {

	@Autowired
	QuizDao quizdao;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Integer> questionIds = quizInterface.generateQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questionIds);
		quizdao.save(quiz);
		
		return new ResponseEntity<>("quiz created sucessfully", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer quizId) {
		Optional<Quiz> quiz = quizdao.findById(quizId);
		ResponseEntity<List<QuestionWrapper>> questions= quizInterface.getQuestionsfromId(quiz.get().getQuestionIds());
		return questions;
	}

	public ResponseEntity<Integer> calculateScore(Integer quizId, List<QuizResponse> responses) {
		return quizInterface.getScore(responses);
	}

}
