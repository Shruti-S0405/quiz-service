package com.zephyra.quiz_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.zephyra.quiz_service.DTO.QuestionWrapper;
import com.zephyra.quiz_service.Model.Question;
import com.zephyra.quiz_service.Model.Quiz;
import com.zephyra.quiz_service.Model.Response;
import com.zephyra.quiz_service.Repository.QuizRepository;
import java.util.*;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        return new ResponseEntity<>("Successs", HttpStatus.CREATED);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer Id) {
        Optional<Quiz> quiz = quizRepository.findById(Id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionText(), q.getOption1(), q.getOption2(), q.getOption3());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses){
        Quiz quiz = quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response :responses){
            if(response.getResponse().equals(questions.get(i).getCorrectAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
