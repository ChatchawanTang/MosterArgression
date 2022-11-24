import java.awt.event.ActionEvent;
import javax.swing.*;

public class FreezeGuns extends Plant {

    public Timer shootTimer;


    public FreezeGuns(GamePanel parent,int x,int y) {
        super(parent,x,y);
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            
            if(gp.laneMonster.get(y).size() > 0) {
                gp.laneGuns.get(y).add(new FreezeBullet(gp, y, 103 + this.x * 100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop(){
        shootTimer.stop();
    }

}