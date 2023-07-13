package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;

public class NormalEnemyDanmukuObj extends GameObj{
    int move=0;
    public NormalEnemyDanmukuObj() {
        super();
    }

    public NormalEnemyDanmukuObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
            y+=speed;
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
