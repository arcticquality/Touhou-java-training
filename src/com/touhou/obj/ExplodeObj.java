package com.touhou.obj;

import com.touhou.utils.GameUtils;

import javax.swing.*;
import java.awt.*;

public class ExplodeObj extends  GameObj
{
    int explodeCount=0;
    int enemyColor=1;

    static Image[] picEnemy1=new Image[16];
    static
    {
        for(int i=0;i<picEnemy1.length;i++)
        {
            picEnemy1[i]=new ImageIcon("imgs/enemyExplosion/enemyBlue/enemy_"+(i+1)+".png").getImage();
        }
    }
    static Image[] picEnemy2=new Image[16];
    static
    {
        for(int i=0;i<picEnemy2.length;i++)
        {
            picEnemy2[i]=new ImageIcon("imgs/enemyExplosion/enemyRed/enemy_"+(i+1)+".png").getImage();
        }
    }

    public ExplodeObj(int x, int y, int enemyColor) {
        super(x, y);
        this.enemyColor = enemyColor;
    }

    @Override
    public void paintSelf(Graphics graphics) {
        if(explodeCount<16&&enemyColor==1)
        {
            explodeCount++;
            img=picEnemy1[explodeCount-1];
        }
        else if(explodeCount<16&&enemyColor==2)
        {
            explodeCount++;
            img=picEnemy2[explodeCount-1];
        }
        else
            GameUtils.removeObjList.add(this);
        super.paintSelf(graphics);
    }
}