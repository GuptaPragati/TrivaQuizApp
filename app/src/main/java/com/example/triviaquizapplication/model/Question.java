package com.example.triviaquizapplication.model;

import lombok.*;

@Data
@NoArgsConstructor
public class Question {
    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }

    private String question;
private boolean answerTrue;

}