package util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences sharedPreferences;
    public Prefs(Activity activity)
    {
        this.sharedPreferences=activity.getSharedPreferences(String.valueOf(activity), Context.MODE_PRIVATE);
    }
    public void saveHighestScore(int score)
    {
        int currentScore=score;
        int lastScore= sharedPreferences.getInt("highScore",0);
        if (currentScore>lastScore)
        {
            sharedPreferences.edit().putInt("highScore",currentScore).apply();
        }
    }

    public int getHighScore()
    {
        return sharedPreferences.getInt("highScore",0);
    }

    public void setCurrentQuesState(int index)
    {
        sharedPreferences.edit().putInt("quesState",index).apply();

    }

    public int getQuesState()
    {
        return sharedPreferences.getInt("quesState",0);
    }
}
