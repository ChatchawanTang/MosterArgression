import javax.swing.*;
import java.awt.event.*;

public class Monster {

    public int health = 1800;
    public int speed = 1;

    private GamePanel gp;

    public int posX = 1000;
    public int myLane;
    public boolean isMoving = true;

    public Monster(GamePanel parent,int lane){
        this.gp = parent;
        myLane = lane;
    }

    public void advance(){
        if(isMoving) {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.colliders[i].assignedPlant != null && gp.colliders[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.colliders[i];
                }
            }
            if (!isCollides) {
                if(slowInt>0){
                    if(slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                }else {
                    posX -= 1;
                }
            } else {
                collided.assignedPlant.health -= 10;
                if (collided.assignedPlant.health < 0) {
                    collided.removePlant();
                }
            }
            if (posX < 0) {
                isMoving = false;
                JOptionPane.showMessageDialog(gp,"Monster Attack the CITY!!!!" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
                GameWindow.gw = new GameWindow();
            }
        }
    }

    int slowInt = 0;
    public void slow(){
        slowInt = 1000;
    }
    public static Monster getMonster(String type,GamePanel parent, int lane) {
        Monster z = new Monster(parent,lane);
       switch(type) {
           case "Monster1" : z = new Monster1(parent,lane);
                                 break;
           case "Monster2" : z = new Monster2(parent,lane);
                                 break;
    }
       return z;
    }

}
