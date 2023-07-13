package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;

public class BossObj extends GameObj
{
    int life=500;
    private int move1=0;
    private int move2=0;
    private int type=1;
    private int alive=1;
    private int finalStateMove=1;
    private int finalStateCount=0;
    int getScore=0;

    public BossObj() {
        super();
    }

    public BossObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getFinalStateMove() {
        return finalStateMove;
    }

    public int getFinalStateCount() {
        return finalStateCount;
    }

    public void setFinalStateCount(int finalStateCount) {
        this.finalStateCount = finalStateCount;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        //移动方式
        if(life>400)
        {
            if(y<=120&&type==1)
            {
                y+=speed;
            }
            else
            {
                type=2;
                //x轴
                if(move1==0&&x!=0)
                {
                    x-=5;
                    if(x<10)
                    {
                        move1=1;
                    }

                }
                else if(move1==1&&x+width!=600)
                {
                    x+=5;
                    if(x+width>590)
                    {
                        move1=0;
                    }
                }
                //y轴
                if(move2==0&&y!=0)
                {
                    y-=5;
                    if(y<10)
                    {
                        move2=1;
                    }

                }
                else if(move2==1&&y+height!=200)
                {
                    y+=5;
                    if(y+height>200)
                    {
                        move2=0;
                    }

                }
            }
        }
        else if(life>=200)
        {
            //x轴
            if(x+35>300)
            {
                x-=1;

            }
            else if(x+35<300)
            {
                x+=1;
            }
            //y轴
            if(y+35>200)
            {
                y-=1;

            }
            else if(y+35<200)
            {
                y+=1;

            }
            if(x+35==300&&y+35==200)
            {
            }
        }
        else if(life<200)
        {
            //x轴
            if(x+35>300)
            {
                x-=5;

            }
            else if(x+35<300)
            {
                x+=5;
            }
            //y轴
            if(y+35>200)
            {
                y-=5;

            }
            else if(y+35<200)
            {
                y+=5;

            }
            if(x+35==300&&y+35==200)
            {
                finalStateMove=0;
            }
        }

        //伤害判定
        for(DanmukuObj danmukuObj:GameUtils.danmukuObjList)
        {
            if(this.getRec().intersects(danmukuObj.getRec()))
            {
                if(life<200&&finalStateMove==1)
                {

                }
                else
                {
                    danmukuObj.setX(1000);
                    danmukuObj.setY(1000);
                    GameUtils.removeObjList.add(danmukuObj);
                    life--;
                    GameWin.setScore(GameWin.getScore()+1);
                }
            }
            if(life==0)
            {
                BossExplodeObj bossExplodeObj=new BossExplodeObj(x,y);
                GameUtils.gameObjList.add(bossExplodeObj);
                GameUtils.bossExplodeObjArrayList.add(bossExplodeObj);
                GameUtils.removeObjList.add(this);
                for(EnemyDanmukuObj enemyDanmukuObj:GameUtils.enemyDanmukuObjArrayList)
                {
                    GameUtils.removeObjList.add(enemyDanmukuObj);
                }
                alive=0;
                if(getScore==0)
                {
                    getScore=1;

                    GameWin.setScore(GameWin.getScore()+500);
                }
            }
        }

    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }


}
