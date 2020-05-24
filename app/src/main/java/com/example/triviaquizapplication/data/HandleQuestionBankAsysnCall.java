package com.example.triviaquizapplication.data;

import com.example.triviaquizapplication.model.Question;

import java.util.ArrayList;

public interface HandleQuestionBankAsysnCall {
    void processfinished(ArrayList<Question> questionArrayList);
}
