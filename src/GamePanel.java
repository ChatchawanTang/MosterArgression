import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
/*import java.audio.AudioPlayer;
import java.audio.AudioStream;*/


public class GamePanel extends JLayeredPane implements MouseMotionListener {

    Image bgImage;
    Image guns;
    Image freezeGunsImage;
    Image OilpumpImage;
    Image BulletImage;
    Image freezeBulletImage;
    //monster part
    Image monster1Image;
    Image monster2Image;
    Collider[] colliders;
    
    ArrayList<ArrayList<Monster>> laneMonster;
    ArrayList<ArrayList<Bullet>> laneGuns;
    ArrayList<Oil> activeOil;

    Timer redrawTimer;
    Timer advancerTimer;
    Timer oilProducer;
    Timer monsterProducer;
    JLabel oilScoreboard;

    GameWindow.PlantType activePlantingBrush = GameWindow.PlantType.None;

    int mouseX , mouseY;

    private int oilScore;

    public int getOilScore() {
        return oilScore;
    }

    public void setOilScore(int oilScore) {
        this.oilScore = oilScore;
        oilScoreboard.setText(String.valueOf(oilScore));
    }

    public GamePanel(JLabel oilScoreboard){
        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);
        this.oilScoreboard = oilScoreboard;
        setOilScore(50);  //pool avalie

        //insert image
        bgImage  = new ImageIcon(this.getClass().getResource("images/mainBG2.png")).getImage();
        guns = new ImageIcon(this.getClass().getResource("images/guns/gun.png")).getImage();
        freezeGunsImage = new ImageIcon(this.getClass().getResource("images/guns/Freezegun.png")).getImage();
        OilpumpImage = new ImageIcon(this.getClass().getResource("images/guns/oilpump.gif")).getImage();
        BulletImage = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        freezeBulletImage = new ImageIcon(this.getClass().getResource("images/freezepea.png")).getImage();
        
        //insert monster image
        monster1Image = new ImageIcon(this.getClass().getResource("images/monster/monster1.gif")).getImage(); //change monster pic
        monster2Image = new ImageIcon(this.getClass().getResource("images/monster/monster2.gif")).getImage();

        //lane
        laneMonster = new ArrayList<>();
        laneMonster.add(new ArrayList<>()); //line 1
        laneMonster.add(new ArrayList<>()); //line 2
        laneMonster.add(new ArrayList<>()); //line 3
        laneMonster.add(new ArrayList<>()); //line 4
        laneMonster.add(new ArrayList<>()); //line 5

        laneGuns = new ArrayList<>();
        laneGuns.add(new ArrayList<>()); //line 1
        laneGuns.add(new ArrayList<>()); //line 2
        laneGuns.add(new ArrayList<>()); //line 3
        laneGuns.add(new ArrayList<>()); //line 4
        laneGuns.add(new ArrayList<>()); //line 5

        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i%9)*100,109 + (i/9)*120);
            a.setAction(new PlantActionListener((i%9),(i/9)));
            colliders[i] = a;
            add(a,new Integer(0));
        }
        activeOil = new ArrayList<>();

        redrawTimer = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60,(ActionEvent e) -> advance());
        advancerTimer.start();

        oilProducer = new Timer(5000,(ActionEvent e) -> {
            Random rnd = new Random();
            Oil sta = new Oil(this,rnd.nextInt(800)+100,0,rnd.nextInt(300)+200);
            activeOil.add(sta);
            add(sta,new Integer(1));
        });
        oilProducer.start();

        monsterProducer = new Timer(7000,(ActionEvent e) -> {
            Random rnd = new Random();
            LevelData lvl = new LevelData();
            String [] Level = lvl.Level[Integer.parseInt(lvl.Lvl)-1];
            int [][] LevelValue = lvl.LevelValue[Integer.parseInt(lvl.Lvl)-1];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Monster m = null;
            for(int i = 0;i<LevelValue.length;i++) {
                if(t>=LevelValue[i][0]&&t<=LevelValue[i][1]) {
                    m = Monster.getMonster(Level[i],GamePanel.this,l);
                }
            }
            laneMonster.get(l).add(m);
        });
        monsterProducer.start();

    }

    private void advance(){
        for (int i = 0; i < 5 ; i++) {
            for(Monster z : laneMonster.get(i)){
                z.advance();
            }

            for (int j = 0; j < laneGuns.get(i).size(); j++) {
                Bullet b = laneGuns.get(i).get(j);
                b.advance();
            }

        }

        for (int i = 0; i < activeOil.size() ; i++) {
            activeOil.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null);

        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if(c.assignedPlant != null){
                Plant p = c.assignedPlant;
                if(p instanceof Guns){
                    g.drawImage(guns,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof FreezeGuns){
                    g.drawImage(freezeGunsImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Oilpump){
                    g.drawImage(OilpumpImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
            }
        }

        for (int i = 0; i < 5 ; i++) {
            for(Monster z : laneMonster.get(i)){
                if(z instanceof Monster1){
                    g.drawImage(monster1Image,z.posX,109+(i*120),null);
                }else if(z instanceof Monster2){
                    g.drawImage(monster2Image,z.posX,109+(i*120),null);
                }
            }

            for (int j = 0; j < laneGuns.get(i).size(); j++) {
                Bullet b = laneGuns.get(i).get(j);
                if(b instanceof FreezeBullet){
                    g.drawImage(freezeBulletImage, b.posX, 130 + (i * 120), null);
                }else {
                    g.drawImage(BulletImage, b.posX, 130 + (i * 120), null);
                }
            }

        }

}

    class PlantActionListener implements ActionListener {

        int x,y;

        public PlantActionListener(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(activePlantingBrush == GameWindow.PlantType.Oilpump){
                if(getOilScore()>=50) {
                    colliders[x + y * 9].setPlant(new Oilpump(GamePanel.this, x, y));
                    setOilScore(getOilScore()-50);
                }
            }
            if(activePlantingBrush == GameWindow.PlantType.Guns){
                if(getOilScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Guns(GamePanel.this, x, y));
                    setOilScore(getOilScore()-100);
                }
            }

            if(activePlantingBrush == GameWindow.PlantType.FreezeGuns){
                if(getOilScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezeGuns(GamePanel.this, x, y));
                    setOilScore(getOilScore()-175);
                }
            }
            activePlantingBrush = GameWindow.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    static int progress = 0;
    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if(progress>=200) //score to win if u kill 1 monster u will got 10 point so u needto kill 20 to win
        {
           if("1".equals(LevelData.Lvl)) {
            JOptionPane.showMessageDialog(null,"Level Completed !!" + '\n' + "Goes to next Level");
            GameWindow.gw.dispose();
            LevelData.write("2");
            GameWindow.gw = new GameWindow();
            }  else {
               JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "Next Levels will coming soon !!!" + '\n' + "Resetting games");
               LevelData.write("1");
               System.exit(0);
           }
           progress = 0;
        }
    }
    /*public static void playmusic(String filepath)
    {
        InputStream music;
        try
        {
            music = new FileInputStream(new File(filepath));
            AudioStream audios=new AudioStream(music);
            AudioPlayer.player.start(audios);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error");
        }
    }*/
}
