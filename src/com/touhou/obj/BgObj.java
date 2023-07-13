package com.touhou.obj;

import java.awt.*;

public class BgObj extends GameObj
{
    public BgObj() {
        super();
    }

    public BgObj(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        y+=speed;
        if(y>=0)
        {
            y=-2400;
        }
    }
}
