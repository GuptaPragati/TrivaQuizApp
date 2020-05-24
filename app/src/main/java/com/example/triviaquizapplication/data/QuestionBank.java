package com.example.triviaquizapplication.data;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaquizapplication.controller.AppController;
import com.example.triviaquizapplication.model.Question;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> questionArrayList=new ArrayList<>();
    private String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    public List<Question> getQuestions(final HandleQuestionBankAsysnCall callBack)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++) {
                    try {
                        Question question=new Question();
                        question.setQuestion(response.getJSONArray(i).getString(0));
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                        questionArrayList.add(question);
                       /* JSONArray eachArray= (JSONArray) response.get(i);*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (callBack!=null){callBack.processfinished(questionArrayList);}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }
}
