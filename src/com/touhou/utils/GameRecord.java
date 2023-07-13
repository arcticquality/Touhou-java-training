package com.touhou.utils;

import com.touhou.GameWin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameRecord
{
    public static String highestScore = GameUtils.getHighestScore();
    static int recordSwitch=0;


    public static void recordHighestScore()
    {
        if(Integer.valueOf(highestScore)<=GameWin.getScore())
        {
            if(recordSwitch==0)
            {
                recordSwitch=1;
                try {
                    BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("file/highestScore.txt"));
                    bufferedWriter.write(""+GameWin.getScore());
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
