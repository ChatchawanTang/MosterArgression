import javax.swing.*;
import java.awt.event.ActionEvent;

public class Oilpump extends Plant {

    Timer oilProduceTimer;

    public Oilpump(GamePanel parent,int x,int y) {
        super(parent, x, y);
        oilProduceTimer = new Timer(15000,(ActionEvent e) -> {
            Oil sta = new Oil(gp,60 + x*100,110 + y*120,130 + y*120);
            gp.activeOil.add(sta);
            gp.add(sta,new Integer(1));
        });
        oilProduceTimer.start();
    }

}
