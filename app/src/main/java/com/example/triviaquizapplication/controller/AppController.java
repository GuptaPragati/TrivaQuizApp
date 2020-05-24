package com.example.triviaquizapplication.controller;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    private static final String TAG= AppController.class.getSimpleName();
    private static AppController mIntance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance()
    { /*if (mIntance==null)
        {
            mIntance=new AppController();
        }*/
        return mIntance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mIntance=this;
    }

    private RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequestQueue(Request<T> request,String tag)
    {
        request.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(request);
    }
    public void addRequestQueue(Request request)
    {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
    public void cancelPendingRequest(Object tag)
    {
        if (requestQueue!=null)
        {
            requestQueue.cancelAll(tag);
        }
    }
}
