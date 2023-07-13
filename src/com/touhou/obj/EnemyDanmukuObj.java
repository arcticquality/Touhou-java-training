package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;

public class EnemyDanmukuObj extends GameObj{
    int move=0;
    int trackingBombMove=0;
    int danmukuType=0;
    int danmukuStage=0;
    int stateType=0;
    int lengthCount=0;
    int marisaX=GameUtils.marisaObjArrayList.get(0).getX()-25;
    int marisaY=GameUtils.marisaObjArrayList.get(0).getY()+25;
    int speedX=0;
    public EnemyDanmukuObj() {
        super();
    }

    public EnemyDanmukuObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, int danmukuType, int danmukuStage) {
        super(img, x, y, width, height, speed, frame);
        this.danmukuType = danmukuType;
        this.danmukuStage = danmukuStage;
    }

    public EnemyDanmukuObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, int danmukuType, int danmukuStage, int stateType) {
        super(img, x, y, width, height, speed, frame);
        this.danmukuType = danmukuType;
        this.danmukuStage = danmukuStage;
        this.stateType = stateType;
    }


    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        if(danmukuStage==3)
        {
            if(stateType==1&&GameUtils.bossObjArrayList.get(0).getFinalStateCount()>10)
            {
            }
            else if(stateType==1&&GameUtils.bossObjArrayList.get(0).getFinalStateCount()<=10)
            {

                if(danmukuType==1)
                {
                    x-=speed;
                }
                else if(danmukuType==2)
                {
                    x+=speed;
                }
                else if(danmukuType==3)
                {
                    x-=speed;
                    y-=speed;
                }
                else if(danmukuType==4)
                {
                    x+=speed;
                    y-=speed;
                }
                else if(danmukuType==5)
                {
                    x-=speed;
                    y+=speed;
                }
                else if(danmukuType==6)
                {
                    x+=speed;
                    y+=speed;
                }
                else if(danmukuType==7)
                {
                    y-=speed;
                }
                else if(danmukuType==8)
                {
                    y+=speed;
                }
            }
            if(stateType==2)
            {
                y+=20;
                if(y==marisaY&&trackingBombMove==0)
                {
                    trackingBombMove=1;
                }
                else if(y!=marisaY&&trackingBombMove==0)
                {
                    speedX=(marisaX-x)*20/Math.abs(y-marisaY);
                    trackingBombMove=1;
                }
                x+=speedX;
            }
        }
        else if(danmukuStage==2)
        {
            if(danmukuType==9)
            {
                y+=speed;
                if(y==marisaY&&trackingBombMove==0)
                {
                    trackingBombMove=1;
                }
                else if(y!=marisaY&&trackingBombMove==0)
                {
                    speedX=(marisaX-x)*10/Math.abs(y-marisaY);
                    trackingBombMove=1;
                }
                x+=speedX;
            }
            if(stateType==1)
            {
                if(danmukuType==1)
                {
                    x-=speed;
                    lengthCount++;
                }
                else if(danmukuType==2)
                {
                    x+=speed;
                    lengthCount++;
                }
                else if(danmukuType==3)
                {
                    x-=speed;
                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==4)
                {
                    x+=speed;
                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==5)
                {
                    x-=speed;
                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==6)
                {
                    x+=speed;
                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==7)
                {
                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==8)
                {
                    y+=speed;
                    lengthCount++;
                }
            }
            else if(stateType==2)
            {

                if(danmukuType==1)
                {
                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==2)
                {
                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==3)
                {

                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==4)
                {

                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==5)
                {

                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==6)
                {

                    y+=speed;
                    lengthCount++;
                }
                else if(danmukuType==7)
                {
                    y-=speed;
                    lengthCount++;
                }
                else if(danmukuType==8)
                {
                    y+=speed;
                    lengthCount++;
                }
            }
        }
        else if(danmukuStage==1)
        {
            if(danmukuType==1)
                y+=speed;
            else if (danmukuType==2)
            {
                y+=speed;
                if(y==marisaY&&trackingBombMove==0)
                {
                    trackingBombMove=1;
                }
                else if(y!=marisaY&&trackingBombMove==0)
                {
                    speedX=(marisaX-x)*10/Math.abs(y-marisaY);
                    trackingBombMove=1;
                }
                x+=speedX;
            }
        }


        if(y>600||y<0||x>600||x<0)
        {
            this.setY(1000);
            this.setX(-1000);
            GameUtils.removeObjList.add(this);

        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
