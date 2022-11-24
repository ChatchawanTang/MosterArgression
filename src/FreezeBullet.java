import java.awt.*;

public class FreezeBullet extends Bullet {

    public FreezeBullet(GamePanel parent,int lane,int startX){
        super(parent,lane,startX);
    }

    @Override
    public void advance(){
        Rectangle gRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gp.laneMonster.get(myLane).size(); i++) {
            Monster m = gp.laneMonster.get(myLane).get(i);
            Rectangle mRect = new Rectangle(m.posX,109 + myLane*120,400,120);
            //collision
            if(gRect.intersects(mRect)){
                m.health -= 300;
                m.slow();
                boolean exit = false;
                if(m.health < 0){
                    System.out.println("Monster Dead");
                    GamePanel.setProgress(10);
                    gp.laneMonster.get(myLane).remove(i);
                    exit = true;
                }
                gp.laneGuns.get(myLane).remove(this);
                if(exit) break;
            }
        }
        
        posX += 15;
    }

}
