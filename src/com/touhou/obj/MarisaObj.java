package com.touhou.obj;

import com.touhou.GameWin;
import com.touhou.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.touhou.utils.GameUtils.*;

public class MarisaObj extends GameObj
{
    private int life=3;
    private int arrIn=0;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public MarisaObj() {
        super();
    }

    public MarisaObj(Image img, int x, int y, int width, int height, double speed, GameWin frame) {
        super(img, x, y, width, height, speed, frame);
        this.frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MarisaObj.super.x=e.getX()-25;
                MarisaObj.super.y=e.getY()-25;
            }
        });
    }

    @Override
    public void paintSelf(Graphics graphics) {
        super.paintSelf(graphics);
        if(arrIn==0)
        {
            arrIn=1;
            marisaObjArrayList.add(this);
        }//添加进ArrList用于调用
        //石头回复
        for(RecoverStone recoverStone: recoverStoneArrayList)
        {
            if(this.getRecForStone().intersects(recoverStone.getRec())&&life<15)
            {
                this.life++;
                recoverStone.setX(1000);
                recoverStone.setY(1000);
                GameUtils.removeObjList.add(recoverStone);
            }
            if(this.getRecForStone().intersects(recoverStone.getRec())&&life>=15)
            {
                recoverStone.setX(1000);
                recoverStone.setY(1000);
                GameUtils.removeObjList.add(recoverStone);
            }
        }
        //小兵碰撞伤害
        for(EnemyObj enemyObj:enemyObjArrayList)
        {
            if(this.getRec().intersects(enemyObj.getRec())&&enemyObj.state==1)
            {

                if(GameWin.getFlagEnemyChange()==1)
                {
                    ExplodeObj explodeObj=new ExplodeObj(enemyObj.getX(),enemyObj.getY(),enemyObj.getColorState());
                    GameUtils.gameObjList.add(explodeObj);
                }
                if(GameWin.getFlagEnemyChange()==2)
                {
                    ExplodeObj explodeObj=new ExplodeObj(enemyObj.getX(),enemyObj.getY(),enemyObj.getColorState());
                    GameUtils.gameObjList.add(explodeObj);
                }
                enemyObj.setX(1000);
                enemyObj.setY(1000);
                GameUtils.removeObjList.add(enemyObj);
                this.life--;
            }
            if(this.life==0)
            {
                this.setX(-1000);
                this.setY(-1000);
                GameUtils.removeObjList.add(this);
                GameWin.state=3;
            }

        }
        //小兵弹幕伤害
        for(NormalEnemyDanmukuObj normalEnemyDanmukuObj: normalEnemyDanmukuObjArrayList)
        {
            if(this.getRec().intersects(normalEnemyDanmukuObj.getRec()))
            {
                life--;
                for(NormalEnemyDanmukuObj ned: normalEnemyDanmukuObjArrayList)
                {
                    ned.setX(1000);
                    ned.setY(1000);
                    removeObjList.add(ned);
                }
            }
            if(this.life==0)
            {
                this.setX(-1000);
                this.setY(-1000);
                removeObjList.add(this);
                GameWin.state=3;
            }
        }
        //boss碰撞伤害
        for(BossObj bossObj:bossObjArrayList)
        {
            if(this.getRec().intersects(bossObj.getRec()))
            {
                life--;
            }
            if(this.life==0)
            {
                this.setX(-1000);
                this.setY(-1000);
                GameUtils.removeObjList.add(this);
                GameWin.state=3;
            }
        }
        //Boss弹幕伤害
        for(EnemyDanmukuObj enemyDanmukuObj:enemyDanmukuObjArrayList)
        {
            if(this.getRec().intersects(enemyDanmukuObj.getRec()))
            {
                life--;
                enemyDanmukuObj.setX(1000);
                enemyDanmukuObj.setY(1000);
                removeObjList.add(enemyDanmukuObj);
//                for(EnemyDanmukuObj ed: enemyDanmukuObjArrayList)
//                {
//                    removeObjList.add(ed);
//                }
            }
            if(this.life==0)
            {
                this.setX(-1000);
                this.setY(-1000);
                removeObjList.add(this);
                GameWin.state=3;
            }
        }

    }

    @Override
    public Rectangle getRec() {
        return new Rectangle(x-15,y+15,width-30,height-30);
    }

    public Rectangle getRecForStone() {
        return new Rectangle(x,y,width,height);
    }

}
