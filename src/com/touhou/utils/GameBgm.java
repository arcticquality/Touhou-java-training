package com.touhou.utils;

import com.touhou.GameWin;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class GameBgm implements Runnable
{
    public static int bgmOn=0;
    public static Boolean bgmSwitch=false;
    public static Boolean bossBgmSwitch=false;
    public static AudioClip aau;
    public static void bgmPlay(){
        URL cb;
        File fileTh06_1=new File("music/th06_01.wav");
        File fileTh06_4=new File("music/th06_04.wav");
        File fileTh06_5=new File("music/th06_05.wav");

        try {
            if(GameWin.state==0&&bgmOn==0)
            {
                cb=fileTh06_1.toURL();
                aau = Applet.newAudioClip(cb);//加载音频
                aau.play(); //播放封面音频
                aau.loop();
                bgmOn=1;
                bgmSwitch=false;
            }
            else if(GameWin.state==1&&(GameWin.getFlagEnemyChange()==1||GameWin.getFlagEnemyChange()==2))
            {
                if(bgmOn==1)
                {
                    aau.stop();
                    bgmSwitch=false;
                    bgmOn=2;
                }
                cb=fileTh06_4.toURL();
                aau = Applet.newAudioClip(cb);//加载音频
                aau.play(); //播放道中音频
                aau.loop();
                bgmSwitch=true;
            }
            else if (GameWin.state==1&&GameWin.getFlagEnemyChange()==0 )
            {
                if(bgmOn==2)
                {
                    aau.stop();
                    bgmSwitch=false;
                    bgmOn=3;
                }
                cb=fileTh06_5.toURL();
                aau = Applet.newAudioClip(cb);//加载音频
                aau.play(); //播放boss音频
                aau.loop();
                bossBgmSwitch=true;
                bgmSwitch=true;
            }
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run()
    {
        while (true)
        {
            if(GameWin.state==0)
            {
                if(!bgmSwitch)
                    bgmPlay();
            }
            if(GameWin.state==1)
            {
                if(!bgmSwitch&&(GameWin.getFlagEnemyChange()==1||GameWin.getFlagEnemyChange()==2))
                    bgmPlay();
                else if(!bossBgmSwitch&&GameWin.getFlagEnemyChange()==0)
                    bgmPlay();
            }
            if(GameWin.state==2)
            {
            }
            if(GameWin.state==3)
            {
                aau.stop();
            }
            if(GameWin.state==4)
            {
                aau.stop();
            }
        }

    }
}
