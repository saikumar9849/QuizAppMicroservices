package com.quizapp.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quizapp.question_service.dao.QuestionDao;
import com.quizapp.question_service.entities.Question;
import com.quizapp.question_service.model.QuestionWrapper;
import com.quizapp.question_service.model.QuizResponse;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<List<Question>>(questionDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		try {
			questionDao.save(question);
			return new ResponseEntity<>("Question added Sucessfully!", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Failed to add Question", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Integer>> generateQuestionsForQuiz(String categoryName, Integer numQuestions) {
		List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<List<Integer>>(questionIds, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		for (Integer id : questionIds) {
			Question question = questionDao.findById(id).get();
			wrappers.add(new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(),
					question.getOption2(), question.getOption3(), question.getOption4()));
		}
		return new ResponseEntity<List<QuestionWrapper>>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<QuizResponse> responses) {
		int right = 0;
		for (QuizResponse response : responses) {
			Question question = questionDao.findById(response.getId()).get();
			if (question.getRightAnswer().equals(response.getResponse())) {
				right++;
			}
		}
		return new ResponseEntity<Integer>(right, HttpStatus.OK);
	}
}
