package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import javax.swing.*;
import java.awt.*;

import static com.touhou.utils.GameUtils.danmukuObjList;
import static com.touhou.utils.GameUtils.removeObjList;

public class EnemyObj extends GameObj{
    int state=1;
    private int colorState;

    public int getColorState() {
        return colorState;
    }

    public int getState() {
        return state;
    }

    public EnemyObj() {
        super();
    }

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public EnemyObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, int colorState) {
        super(img, x, y, width, height, speed, frame);
        this.colorState = colorState;
    }

    @Override
    public void paintSelf(Graphics graphics) {

        super.paintSelf(graphics);
        //移动
        if(colorState==2&&state==1)
        {
            y+=speed;
            if(x>GameUtils.marisaObjArrayList.get(0).getX()+5)
            {
                x-=1;
            }
            else if(x<GameUtils.marisaObjArrayList.get(0).getX()-5)
            {
                x+=1;
            }
        }
        else if(colorState==1&&state==1)
            y+=speed;
        //伤害判定
        for(DanmukuObj danmukuObj:danmukuObjList)
        {
            if(danmukuObj.getRec().intersects(this.getRec())&&state==1)
            {
                state=0;
                if(GameWin.getFlagEnemyChange()==1)
                {
                    ExplodeObj explodeObj=new ExplodeObj(x,y,colorState);
                    GameUtils.gameObjList.add(explodeObj);
                }
                if(GameWin.getFlagEnemyChange()==2)
                {
                    ExplodeObj explodeObj=new ExplodeObj(x,y,colorState);
                    GameUtils.gameObjList.add(explodeObj);
                }
                RecoverStone recoverStone=new RecoverStone(GameUtils.getRecoverStoneImg(),x,y,25,25,5);
                GameUtils.gameObjList.add(recoverStone);
                GameUtils.recoverStoneArrayList.add(recoverStone);
                GameUtils.gameObjList.add(GameUtils.recoverStoneArrayList.get(GameUtils.recoverStoneArrayList.size()-1));


                GameUtils.removeObjList.add(this);
                danmukuObj.setX(-1000);
                danmukuObj.setY(1000);
                GameUtils.removeObjList.add(danmukuObj);
                if(GameWin.getFlagEnemyChange()==1)
                {
                    GameWin.setScore(GameWin.getScore()+10);
                }
                else if(GameWin.getFlagEnemyChange()==2)
                {
                    GameWin.setScore(GameWin.getScore()+20);
                }

            }
        }
        if(y>600)
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
