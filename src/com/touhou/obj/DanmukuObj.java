package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;

public class DanmukuObj extends GameObj{
    int danmukutype=0;
    int danmukuSide;
    int lengthCount=0;
    public DanmukuObj() {
        super();
    }

    public DanmukuObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public DanmukuObj(Image img, int x, int y, int width, int height, double speed, GameWin frame, int danmukutype, int danmukuSide) {
        super(img, x, y, width, height, speed, frame);
        this.danmukutype = danmukutype;
        this.danmukuSide = danmukuSide;
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        if(GameWin.getFlagEnemyChange()==0&&danmukutype==1)
        {
            if(lengthCount<=3&&danmukuSide==0)
            {
                y-=speed;
                x-=speed*2;
                lengthCount++;
            }
            else if(lengthCount<=3&&danmukuSide==1)
            {
                y-=speed;
                x+=speed*2;
                lengthCount++;
            }
            else
            {
                if(x>GameUtils.bossObjArrayList.get(0).getX())
                {
                    y-=speed;
                    x-=speed;
                }
                else if(x<GameUtils.bossObjArrayList.get(0).getX())
                {
                    y-=speed;
                    x+=speed;
                }
                else
                    y-=speed;
            }

        }
        else
            y-=speed;
        if(y<0)
        {
            this.setY(-1000);
            this.setX(1000);
            GameUtils.removeObjList.add(this);

        }
    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}
