package com.quizapp.quiz_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDto {
	private String categoryName;
	private int numberOfQues;
	private String title;
}
