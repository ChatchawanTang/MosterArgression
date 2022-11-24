import java.awt.*;

public class Bullet {

    public int posX;
    protected GamePanel gp;
    public int myLane;

    public Bullet(GamePanel parent,int lane,int startX){
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
    }

    public void advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gp.laneMonster.get(myLane).size(); i++) {
            Monster m = gp.laneMonster.get(myLane).get(i);
            Rectangle mRect = new Rectangle(m.posX,109 + myLane*120,400,120);
            if(pRect.intersects(mRect)){
                m.health -= 300;
                boolean exit = false;
                if(m.health < 0){
                    System.out.println("Monster Dead");
                    
                    gp.laneMonster.get(myLane).remove(i);
                    GamePanel.setProgress(10);
                    exit = true;
                }
                gp.laneGuns.get(myLane).remove(this);
                if(exit) break;
            }
        }
        //posX= speed out from gun
        posX += 15;
    }

}
