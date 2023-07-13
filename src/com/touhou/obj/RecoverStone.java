package com.touhou.obj;


import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;

public class RecoverStone extends GameObj{
    public RecoverStone() {
        super();
    }
    int lengthCount=0;

    public RecoverStone(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    public RecoverStone(Image img, int x, int y, int width, int height, double speed) {
        super(img, x, y, width, height, speed);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        if(lengthCount<20)
        {
            y-=1;
            lengthCount++;
        }
        else
        {
            y+=3;
            if(y>600)
            {
                this.setY(-1000);
                this.setX(1000);
                GameUtils.removeObjList.add(this);

            }
        }

    }

    @Override
    public Rectangle getRec() {
        return super.getRec();
    }
}