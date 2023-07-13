package com.touhou;

import com.touhou.obj.*;
import com.touhou.utils.GameBgm;
import com.touhou.utils.GameRecord;
import com.touhou.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameWin extends JFrame implements Runnable {
    //0.未开始 1.游戏中 2.暂停 3.失败 4.成功
    public static int state=0;
    private Image offScreenImage=null;
    private BgObj bgObj=new BgObj(GameUtils.getBgImg(),0,-2400,2);
    private MarisaObj marisaObj=new MarisaObj(GameUtils.getMarisaImg(),275,550,50,50,0,this);
    private BossObj bossObj=new BossObj(GameUtils.getBossImg(),265,0,70,70,8,this);


    private int countFPS=1;
    private static int flagEnemyChange=1;
    private int countEnemyChange=0;
    private int hasBoss=0;
    private static int score=0;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameWin.score = score;
    }

    public static int getFlagEnemyChange()
    {
        return flagEnemyChange;
    }

    public void launch()
    {
        this.setVisible(true);
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setTitle("Touhou");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        GameUtils.gameObjList.add(bgObj);
        GameUtils.gameObjList.add(marisaObj);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getX()>310&&e.getX()<490&&e.getY()>510&&e.getY()<570)
                {
                    if(e.getButton()==1&&state==0)
                    {
                        state=1;
                        repaint();
                    }
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if(state==1)
                {
                    state=2;
                    repaint();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(state==2)
                {
                    state=1;
                }
            }
        });
        while(true)
        {
            if(state==1)
            {
                createObj();
                repaint();
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
    @Override
    public void paint(Graphics g)
    {
        if(offScreenImage==null)
        {
            offScreenImage=createImage(800,600);
        }
        Graphics graphics=offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);
        if(state==0)
        {

            graphics.drawImage(GameUtils.getCoverImg(),0,0,null);

            graphics.setColor(new Color(239,207,192));
            graphics.fillRoundRect(310,510,180,60,50,50);

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("仿宋", Font.BOLD,40));
            graphics.drawString("开始游戏",320,555);
        }
        if(state==1)
        {
            for(int i=0;i<GameUtils.gameObjList.size();i++)
            {
                GameUtils.gameObjList.get(i).paintSelf(graphics);
            }
            GameUtils.gameObjList.removeAll(GameUtils.removeObjList);

            graphics.drawImage(GameUtils.getUIBgImg(),600,0,null);
            graphics.drawImage(GameUtils.getIconImg(),600,400,null);
            graphics.drawImage(GameUtils.getIcon2Img(),600,200,null);
            graphics.setColor(Color.WHITE);
            graphics.drawRect(600,0,200,600);
            //g.drawLine(600,200,800,200);
            graphics.drawLine(600,400,800,400);

            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("黑体",Font.BOLD+ Font.ITALIC,20));
            if(Integer.valueOf(GameRecord.highestScore)>=score)
            {
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.drawString("最高得分:",610,60);
                graphics.drawString(""+GameRecord.highestScore,630,85);
            }
            else
            {
                graphics.setColor(Color.WHITE);
                graphics.drawString("最高得分:",610,60);
                graphics.drawString(""+score,630,85);
            }
            graphics.setColor(Color.WHITE);
            graphics.drawString("得分:",610,110);
            graphics.drawString(""+score,630,135);
            graphics.drawString("残机:",610,160);
            graphics.drawString(""+marisaObj.getLife()+"/15",630,185);
            //graphics.drawString("Bomb:",610,160);
            if(hasBoss==1)
            {

                graphics.drawString("Boss:",20,65);
                //graphics.drawString(""+bossObj.getLife(),70,110);
                if(bossObj.getLife()>400)
                {
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(75,50,bossObj.getLife(),10);
                }
                else if(bossObj.getLife()>200)
                {
                    graphics.setColor(Color.YELLOW);
                    graphics.fillRect(75,50,bossObj.getLife(),10);
                }
                else if(bossObj.getLife()<=200)
                {
                    graphics.setColor(Color.RED);
                    graphics.fillRect(75,50,bossObj.getLife(),10);
                }

            }

        }
        if(state==2)
        {
            graphics.drawImage(GameUtils.getGamePauseImg(),0,0,null);

            graphics.drawImage(GameUtils.getUIBgImg(),600,0,null);
            graphics.drawImage(GameUtils.getIconImg(),600,400,null);
            graphics.drawImage(GameUtils.getIcon2Img(),600,200,null);
            graphics.setColor(Color.WHITE);
            graphics.drawRect(600,0,200,600);
            //g.drawLine(600,200,800,200);
            graphics.drawLine(600,400,800,400);

            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("黑体",Font.BOLD+ Font.ITALIC,20));
            graphics.drawString("最高得分:",610,60);
            if(Integer.valueOf(GameRecord.highestScore)>=score)
            {
                graphics.drawString(""+GameRecord.highestScore,630,85);
            }
            else
            {
                graphics.drawString(""+score,630,85);
            }
            graphics.drawString("得分:",610,110);
            graphics.drawString(""+score,630,135);
            graphics.drawString("残机:",610,160);
            graphics.drawString(""+marisaObj.getLife()+"/15",630,185);
        }
        if(state==3)
        {

            GameUtils.gameObjList.removeAll(GameUtils.removeObjList);
            graphics.drawImage(GameUtils.getGameLoseImg(),0,0,null);

            graphics.drawImage(GameUtils.getUIBgImg(),600,0,null);
            graphics.drawImage(GameUtils.getIconImg(),600,400,null);
            graphics.drawImage(GameUtils.getIcon2Img(),600,200,null);
            graphics.setColor(Color.WHITE);
            graphics.drawRect(600,0,200,600);
            //g.drawLine(600,200,800,200);
            graphics.drawLine(600,400,800,400);

            graphics.setColor(Color.WHITE);
            graphics.drawString("最高得分:",610,60);
            if(Integer.valueOf(GameRecord.highestScore)>=score)
            {
                graphics.drawString(""+GameRecord.highestScore,630,85);
            }
            else
            {
                graphics.drawString(""+score,630,85);
            }
            graphics.drawString("得分:",610,110);
            graphics.drawString(""+score,630,135);
            graphics.drawString("残机:",610,160);
            graphics.drawString(""+marisaObj.getLife()+"/15",630,185);
            GameRecord.recordHighestScore();

        }
        if(state==4)
        {

            GameUtils.gameObjList.removeAll(GameUtils.removeObjList);

            graphics.drawImage(GameUtils.getGameWinImg(),0,0,null);

            graphics.drawImage(GameUtils.getUIBgImg(),600,0,null);
            graphics.drawImage(GameUtils.getIconImg(),600,400,null);
            graphics.drawImage(GameUtils.getIcon2Img(),600,200,null);
            graphics.setColor(Color.WHITE);
            graphics.drawRect(600,0,200,600);
            //g.drawLine(600,200,800,200);
            graphics.drawLine(600,400,800,400);

            graphics.setColor(Color.WHITE);
            graphics.drawString("最高得分:",610,60);
            if(Integer.valueOf(GameRecord.highestScore)>=score)
            {
                graphics.drawString(""+GameRecord.highestScore,630,85);
            }
            else
            {
                graphics.drawString(""+score,630,85);
            }
            graphics.drawString("得分:",610,110);
            graphics.drawString(""+score,630,135);
            graphics.drawString("残机:",610,160);
            graphics.drawString(""+marisaObj.getLife()+"/15",630,185);
            GameRecord.recordHighestScore();

        }
        g.drawImage(offScreenImage,0,0,null);
        countFPS++;
    }
    void createObj()
    {

        if(countEnemyChange>30)
            flagEnemyChange=2;
        if(countEnemyChange>60)
            flagEnemyChange=0;
        //阶段一弹幕
        if(countFPS%10==0&&flagEnemyChange==1)
        {
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmukuImg(),marisaObj.getX()+20,marisaObj.getY()-15,10,20,10,this));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
        }
        //阶段二弹幕
        if(countFPS%5==0&&(flagEnemyChange==2||flagEnemyChange==3))
        {
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmukuImg(),marisaObj.getX()+10,marisaObj.getY()-15,10,20,10,this));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmukuImg(),marisaObj.getX()+30,marisaObj.getY()-15,10,20,10,this));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
        }
        //阶段三弹幕
        if(countFPS%3==0&&flagEnemyChange==0)
        {
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmukuImg(),marisaObj.getX()+10,marisaObj.getY()-15,10,20,20,this));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmukuImg(),marisaObj.getX()+30,marisaObj.getY()-15,10,20,20,this));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmuku2Img(),marisaObj.getX()+0,marisaObj.getY()-15,10,20,10,this,1,0));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
            GameUtils.danmukuObjList.add(new DanmukuObj(GameUtils.getSelfDanmuku2Img(),marisaObj.getX()+40,marisaObj.getY()-15,10,20,10,this,1,1));
            GameUtils.gameObjList.add(GameUtils.danmukuObjList.get(GameUtils.danmukuObjList.size()-1));
        }

        //阶段一 敌人
        if(countFPS%30==0&&flagEnemyChange==1)
        {
            //敌机
            GameUtils.enemyObjArrayList.add(new EnemyObj(GameUtils.getEnemy1Img(),(int)(Math.random()*12)*50,0,50,50,5,this,1));
            GameUtils.gameObjList.add(GameUtils.enemyObjArrayList.get(GameUtils.enemyObjArrayList.size()-1));
            countEnemyChange++;
            //敌机弹幕
            for(EnemyObj enemyObj:GameUtils.enemyObjArrayList)
            {
                if(enemyObj.getColorState()==1&&enemyObj.getState()==1)
                {
                    GameUtils.normalEnemyDanmukuObjArrayList.add(new NormalEnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),enemyObj.getX()+17,enemyObj.getY()+17,15,15,10,this));
                    GameUtils.gameObjList.add(GameUtils.normalEnemyDanmukuObjArrayList.get(GameUtils.normalEnemyDanmukuObjArrayList.size()-1));
                }
            }
        }
//
        //阶段二 敌人
        if(countFPS%20==0&&flagEnemyChange==2)
        {
            //敌机
            GameUtils.enemyObjArrayList.add(new EnemyObj(GameUtils.getEnemy2Img(),(int)(Math.random()*12)*50,0,50,50,5,this,2));
            GameUtils.gameObjList.add(GameUtils.enemyObjArrayList.get(GameUtils.enemyObjArrayList.size()-1));
            countEnemyChange++;
            //敌机弹幕
            for(EnemyObj enemyObj:GameUtils.enemyObjArrayList)
            {
                if(enemyObj.getColorState()==2&&enemyObj.getState()==1)
                {
                    GameUtils.normalEnemyDanmukuObjArrayList.add(new NormalEnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),enemyObj.getX()+17,enemyObj.getY()+17,15,15,10,this));
                    GameUtils.gameObjList.add(GameUtils.normalEnemyDanmukuObjArrayList.get(GameUtils.normalEnemyDanmukuObjArrayList.size()-1));
                }
            }
        }
        //boss
        if(flagEnemyChange==0&&hasBoss==0)
        {
            hasBoss=1;
            GameUtils.bossObjArrayList.add(bossObj);
            GameUtils.gameObjList.add(bossObj);
        }
        //boss出现后回复道具
        if(flagEnemyChange==0&&hasBoss==1)
        {
            if(countFPS%200==0)
            {
                RecoverStone recoverStone=new RecoverStone(GameUtils.getRecoverStoneImg(),(int)(Math.random()*24)*25+15,0,25,25,5);
                GameUtils.gameObjList.add(recoverStone);
                GameUtils.recoverStoneArrayList.add(recoverStone);
                GameUtils.gameObjList.add(GameUtils.recoverStoneArrayList.get(GameUtils.recoverStoneArrayList.size()-1));
            }
        }
        //boss弹幕

        if(bossObj.getLife()>400)
        {
            if(countFPS%10==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+17,bossObj.getY()+27,15,15,10,this,1,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+37,bossObj.getY()+27,15,15,10,this,1,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
            if(countFPS%10==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+7,bossObj.getY()+27,15,15,10,this,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
        }
        else if((bossObj.getLife()>=380&&bossObj.getLife()<400)||(bossObj.getLife()>=340&&bossObj.getLife()<360)||(bossObj.getLife()>=300&&bossObj.getLife()<320)||(bossObj.getLife()>=260&&bossObj.getLife()<280)||(bossObj.getLife()>=220&&bossObj.getLife()<240))
        {
            if(countFPS%10==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+7,bossObj.getY()+27,15,15,10,this,9,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
            if(countFPS%5==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+27,15,15,20,this,1,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+27,15,15,20,this,2,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+0,15,15,20,this,3,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+0,15,15,20,this,4,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+47,15,15,20,this,5,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+47,15,15,20,this,6,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+27,bossObj.getY()+7,15,15,20,this,7,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+27,bossObj.getY()+47,15,15,20,this,8,2,1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
        }
        else if((bossObj.getLife()>=360&&bossObj.getLife()<380)||(bossObj.getLife()>=320&&bossObj.getLife()<340)||(bossObj.getLife()>=280&&bossObj.getLife()<300)||(bossObj.getLife()>=240&&bossObj.getLife()<260)||(bossObj.getLife()>=200&&bossObj.getLife()<220))
        {
            if(countFPS%10==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+7,bossObj.getY()+27,15,15,10,this,9,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
            if(countFPS%5==0&&flagEnemyChange==0&&bossObj.getAlive()==1)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+27,15,15,20,this,1,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+27,15,15,20,this,2,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+0,15,15,20,this,3,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+0,15,15,20,this,4,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+7,bossObj.getY()+47,15,15,20,this,5,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+47,bossObj.getY()+47,15,15,20,this,6,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+27,bossObj.getY()+7,15,15,20,this,7,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(),bossObj.getX()+27,bossObj.getY()+47,15,15,20,this,8,2,2));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
            }
        }
        else if(bossObj.getLife()<200&&bossObj.getFinalStateMove()==0)
        {
            if(countFPS%10==0&&flagEnemyChange==0&&bossObj.getAlive()==1&&bossObj.getFinalStateCount() < 10)
            {
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 0, bossObj.getY() + 35, 15, 15, 10, this, 1, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 70, bossObj.getY() + 35, 15, 15, 10, this, 2, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 0, bossObj.getY() + 0, 15, 15, 10, this, 3, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 70, bossObj.getY() + 0, 15, 15, 10, this, 4, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 0, bossObj.getY() + 70, 15, 15, 10, this, 5, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 70, bossObj.getY() + 70, 15, 15, 10, this, 6, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 35, bossObj.getY() + 0, 15, 15, 10, this, 7, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmuku2Img(), bossObj.getX() + 35, bossObj.getY() + 70, 15, 15, 10, this, 8, 3, 1));
                GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size() - 1));
                bossObj.setFinalStateCount(bossObj.getFinalStateCount()+1);
            }

            if(countFPS%5==0&&flagEnemyChange==0&&bossObj.getAlive()==1&&bossObj.getFinalStateCount()<20)
            {

                if((bossObj.getFinalStateCount()-9)%5==1)
                {
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-150,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-200,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-250,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                }
                else if((bossObj.getFinalStateCount()-9)%5==2)
                {
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-50,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-100,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-150,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                }
                else if((bossObj.getFinalStateCount()-9)%5==3)
                {
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+50,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35-50,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                }
                else if((bossObj.getFinalStateCount()-9)%5==4)
                {
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+150,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+100,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+50,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                }
                else if((bossObj.getFinalStateCount()-9)%5==5)
                {
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+250,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+200,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                    GameUtils.enemyDanmukuObjArrayList.add(new EnemyDanmukuObj(GameUtils.getEnemyDanmukuImg(),bossObj.getX()+35+150,bossObj.getY()+70,15,15,20,this,0,3,2));
                    GameUtils.gameObjList.add(GameUtils.enemyDanmukuObjArrayList.get(GameUtils.enemyDanmukuObjArrayList.size()-1));
                }
                bossObj.setFinalStateCount(bossObj.getFinalStateCount()+1);
            }

            else if(bossObj.getFinalStateCount()==20)
            {
                bossObj.setFinalStateCount(0);
            }
        }
        if(countFPS%10==0)
        {
            score++;
        }



    }
    public static void main(String[] args)
    {
        GameWin windows=new com.touhou.GameWin();
        GameBgm gameBgm=new GameBgm();

        Thread threadA=new Thread(windows);
        Thread threadB=new Thread(gameBgm);

        threadA.start();
        threadB.start();
    }

    @Override
    public void run()
    {
        com.touhou.GameWin window=new com.touhou.GameWin();
        window.launch();
    }

}