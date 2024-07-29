package com.quizapp.question_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponse {
	//Question Id and Answer(selected Option)
	private Integer id;
	private String response;
}
