package com.touhou.utils;

import com.touhou.obj.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GameUtils
{
    public static Image coverImg=new ImageIcon("imgs/cover.jpg").getImage();
    public static Image bgImg=new ImageIcon("imgs/background.png").getImage();
    public static Image UIBgImg=new ImageIcon("imgs/UIbackground.jpg").getImage();
    public static Image iconImg=new ImageIcon("imgs/icon.jpg").getImage();
    public static Image icon2Img=new ImageIcon("imgs/Marisa.png").getImage();
    public static Image gameLoseImg=new ImageIcon("imgs/gameLose.png").getImage();
    public static Image gameWinImg=new ImageIcon("imgs/gameWin.png").getImage();
    public static Image gamePauseImg=new ImageIcon("imgs/gamepause.png").getImage();
    public static ArrayList<GameObj> removeObjList=new ArrayList<>();
    public static ArrayList<GameObj> gameObjList=new ArrayList<>();
    public static String highestScore;

    static {
        try {
            highestScore = new BufferedReader(new FileReader("file/highestScore.txt")).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //********************************************************************************************
    public static Image MarisaImg=new ImageIcon("imgs/self/SelfMid.png").getImage();

    public static Image selfDanmukuImg=new ImageIcon("imgs/danmuku/selfdanmuku.png").getImage();
    public static Image selfDanmuku2Img=new ImageIcon("imgs/danmuku/selfdanmuku2.png").getImage();


    public static Image recoverStoneImg=new ImageIcon("imgs/recoverStone.png").getImage();

    public static ArrayList<DanmukuObj> danmukuObjList=new ArrayList<>();//我方弹幕集合
    public static ArrayList<MarisaObj> marisaObjArrayList=new ArrayList<>();
    public static ArrayList<RecoverStone> recoverStoneArrayList=new ArrayList<>();

    //********************************************************************************************
    public static Image bossImg=new ImageIcon("imgs/enemy/boss.png").getImage();
    public static Image enemy1Img=new ImageIcon("imgs/enemy/enemy1.png").getImage();
    public static Image enemy2Img=new ImageIcon("imgs/enemy/enemy2.png").getImage();

    public static Image enemyDanmukuImg=new ImageIcon("imgs/danmuku/enemydanmuku.png").getImage();
    public static Image enemyDanmuku2Img=new ImageIcon("imgs/danmuku/enemydanmuku2.png").getImage();
    public static ArrayList<EnemyObj> enemyObjArrayList=new ArrayList<>();
    public static ArrayList<BossExplodeObj> bossExplodeObjArrayList=new ArrayList<>();
    public static ArrayList<BossObj> bossObjArrayList=new ArrayList<>();
    public static ArrayList<EnemyDanmukuObj> enemyDanmukuObjArrayList=new ArrayList<>();
    public static ArrayList<NormalEnemyDanmukuObj> normalEnemyDanmukuObjArrayList=new ArrayList<>();

    //********************************************************************************************
    public static Image getMarisaImg() {
        return MarisaImg;
    }

    public static Image getSelfDanmukuImg() {
        return selfDanmukuImg;
    }

    public static Image getSelfDanmuku2Img() {
        return selfDanmuku2Img;
    }

    public static Image getRecoverStoneImg() {
        return recoverStoneImg;
    }
    //********************************************************************************************

    public static Image getBossImg() {
        return bossImg;
    }

    public static Image getEnemyDanmukuImg() {
        return enemyDanmukuImg;
    }

    public static Image getEnemyDanmuku2Img() {
        return enemyDanmuku2Img;
    }


    public static Image getEnemy1Img() {
        return enemy1Img;
    }

    public static Image getEnemy2Img() {
        return enemy2Img;
    }

    //********************************************************************************************

    public static Image getIcon2Img() {
        return icon2Img;
    }

    public static Image getIconImg() {
        return iconImg;
    }

    public static Image getUIBgImg() {
        return UIBgImg;
    }

    public static Image getCoverImg() {
        return coverImg;
    }

    public static Image getBgImg() {
        return bgImg;
    }

    public static Image getGameLoseImg() {
        return gameLoseImg;
    }

    public static Image getGameWinImg() {
        return gameWinImg;
    }

    public static Image getGamePauseImg() {
        return gamePauseImg;
    }

    public static String getHighestScore() {
        return highestScore;
    }
}
