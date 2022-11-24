import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {

    enum PlantType{
        None,
        Oilpump,
        Guns,
        FreezeGuns
    }

    public GameWindow(){
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        // sun score top left
        JLabel oil = new JLabel("OIL");
        oil.setLocation(40,85);
        oil.setSize(60,20);
        
        GamePanel gp = new GamePanel(oil);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,new Integer(0));
        //oil pump card
        GunsCard oilpump = new GunsCard(new ImageIcon(this.getClass().getResource("images/cards/oilpump.png")).getImage());// changed card
        oilpump.setLocation(110,8);
        oilpump.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Oilpump;
        });
        getLayeredPane().add(oilpump,new Integer(3));
        //gun card
        GunsCard guns = new GunsCard(new ImageIcon(this.getClass().getResource("images/cards/gun card.png")).getImage());
        guns.setLocation(200,8);
        guns.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Guns;
        });
        getLayeredPane().add(guns,new Integer(3));
        //freeze gun card
        GunsCard freezeguns = new GunsCard(new ImageIcon(this.getClass().getResource("images/cards/Freezegun card.png")).getImage());
        freezeguns.setLocation(290,8);
        freezeguns.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.FreezeGuns;
        });
        getLayeredPane().add(freezeguns,new Integer(3));



        getLayeredPane().add(oil,new Integer(2));
        setResizable(false);
        setVisible(true);
    }
    public GameWindow(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0,0);
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu,new Integer(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }
    static GameWindow gw;
    public static void begin() {
        gw.dispose();
       gw = new GameWindow();
    }
    public static void main(String[] args) {
          gw = new GameWindow(true);
    }

}




