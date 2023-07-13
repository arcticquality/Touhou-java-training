package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import javax.swing.*;
import java.awt.*;

public class BossExplodeObj extends  GameObj
{
    int explodeCount=0;

    static Image[] picBoss=new Image[16];
    static
    {
        for(int i=0;i<picBoss.length;i++)
        {
            picBoss[i]=new ImageIcon("imgs/enemyExplosion/boss/boss_"+(i+1)+".png").getImage();
        }
    }

    public BossExplodeObj(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintSelf(Graphics graphics) {
        if(explodeCount<16)
        {
            explodeCount++;
            img=picBoss[explodeCount-1];
        }
        else
        {
            GameUtils.removeObjList.add(this);
            GameWin.state=4;
        }

        super.paintSelf(graphics);
    }
}