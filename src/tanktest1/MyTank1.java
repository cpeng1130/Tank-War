package tanktest1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MyTank1 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	MyStratPanel msp = null;
	MyPanel my = null;

	JMenuBar jmb = null;
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	JMenuItem jmi2 = null;
	JMenuItem jmi3 = null;
	JMenuItem jmi4 = null;

	public static void main(String[] args) throws IOException {
		new MyTank1();
	}

	public MyTank1() throws IOException {

		// my = new MyPanel();
		// this.add(my);
		// Thread t1 = new Thread(my);
		// t1.start();

		// this.addKeyListener(my);
		msp = new MyStratPanel();
		this.add(msp);
		Thread tmsp = new Thread(msp);
		tmsp.start();

		jmb = new JMenuBar();
		jm1 = new JMenu("Game (G)");
		jm1.setMnemonic('G');

		jmi1 = new JMenuItem("Start New Game");
		jmi1.addActionListener(this);
		jmi1.setActionCommand("new game");

		jmi2 = new JMenuItem("Esc game (e)");
		jmi2.setMnemonic('e');

		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");

		jmi3 = new JMenuItem("Save and Exit (s)");
		jmi3.setMnemonic('s');
		jmi3.addActionListener(this);
		jmi3.setActionCommand("save");

		jmi4 = new JMenuItem("Continue(c)");
		jmi4.setMnemonic('c');
		jmi4.addActionListener(this);
		jmi4.setActionCommand("continue");
		//

		jmb.add(jm1);
		jm1.add(jmi1);
		jm1.add(jmi4);
		jm1.add(jmi3);
		jm1.add(jmi2);

		this.setJMenuBar(jmb);

		this.setSize(1200, 900);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// star a new game
		if (e.getActionCommand().equals("new game")) {
			try {

				my = new MyPanel("newgame");

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			Thread t1 = new Thread(my);
			t1.start();
			this.remove(msp);
			this.add(my);
			this.addKeyListener(my);
			this.setVisible(true);

		} // user click the esc to out of system
		else if (e.getActionCommand().equals("exit")) {

			try {
				Recorder.keepRecord();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			System.exit(0);
		} // save data and exit
		else if (e.getActionCommand().equals("save")) {
			System.out.println("====================");
			Recorder.setEts(my.ets);
			Recorder.keepRecordEnemyTankLocation();
			System.exit(0);
		} // continue last game
		else if (e.getActionCommand().equals("continue")) {
			try {

				my = new MyPanel("continue");
				//my.flag = "continue";
				

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			Thread t1 = new Thread(my);
			t1.start();
			this.remove(msp);
			this.add(my);
			this.addKeyListener(my);
			this.setVisible(true);
		}

	}
}

class MyStratPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	int times = 0;

	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 800, 600);

		if (times % 2 == 0) {
			g.setColor(Color.yellow);
			// set font style
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g.drawString("LEVEL ---1  ", 310, 270);
		}
		times++;
	}

	public void run() {

		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			this.repaint();
		}
	}
}

// my panel
class MyPanel extends JPanel implements KeyListener, Runnable {
	// recover data
	private static final long serialVersionUID = 1L;
	// uplaod pic
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	Image image4 = null;
	BufferedImage mypic1 = null;
	BufferedImage mypic2 = null;
	BufferedImage mypic3 = null;
	BufferedImage mypic4 = null;

	// define a startpanel

	// enemyTank
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	// enemyTank size
	int etsize = 10;

	// restore location of enemytank
	Vector<Node> nodes = new Vector<Node>();

	Vector<Bomb> bombs = new Vector<Bomb>();

	// define a Hero
	Hero hero = null;

	String flag = "newgame";

	public MyPanel(String flag) throws IOException {
		// use flag to check this game is new or continue

		// recover data
		Recorder.getRecord();

		mypic1 = ImageIO.read(new File("D:/JavaCode/MyTankGameTest1/boom1.png"));
		mypic2 = ImageIO.read(new File("D:/JavaCode/MyTankGameTest1/boom2.png"));
		mypic3 = ImageIO.read(new File("D:/JavaCode/MyTankGameTest1/boom3.png"));
		mypic4 = ImageIO.read(new File("D:/JavaCode/MyTankGameTest1/boom4.png"));

		// load mystartpanel

		// image1 =
		// Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/src/boom1.png"));
		// image2 =
		// Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/src/boom2.png"));
		// image3 =
		// Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/src/boom3.png"));
		// image4 =
		// Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/src/boom4.png"));
		hero = new Hero(550, 100);
		hero.setDirect(0);

		if (flag.equals("newgame")) {
			// draw new game enemyTank location
			for (int i = 0; i < etsize; i++) {
				EnemyTank et = new EnemyTank((i + 1) * 40, 0);

				et.setColor(0);
				et.setDirect(1);
				et.setEts(ets);
				ets.add(et);

				Thread t = new Thread(et);
				t.start();

				// add a bullet to EnemyTank
				Shot s = new Shot(et.x + 10, et.y + 30, 1);
				// add bullet
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();

			}

		} else if (flag.equals("continue")) {
			nodes = Recorder.readEnemyNewLoction();
			for (int i = 0; i < nodes.size(); i++) {
				Node note = nodes.get(i);
				EnemyTank et = new EnemyTank(note.x, note.y);

				et.setColor(0);
				et.setDirect(note.direct);
				et.setEts(ets);
				ets.add(et);

				Thread t = new Thread(et);
				t.start();

				// add a bullet to EnemyTank
				Shot s = new Shot(et.x + 10, et.y + 30, 1);
				// add bullet
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();

			}

		}

		StartSound ss= new StartSound("D:\\JavaCode\\TankInfor\\TankWar.wav");
		Thread ts= new Thread(ss);
		ts.start();
		
		//start sound file
		
	}

	public void paint(Graphics g) {
		super.paint(g);

		// draw a bomb
		for (int i = 0; i < bombs.size(); i++) {
			Bomb b = bombs.get(i);
			if (b.lifetime > 6) {
				g.drawImage(mypic1, b.x, b.y, 30, 30, this);
			} else if (b.lifetime > 4) {
				g.drawImage(mypic2, b.x, b.y, 30, 30, this);
			} else if (b.lifetime > 2) {
				g.drawImage(mypic3, b.x, b.y, 30, 30, this);
			} else {
				g.drawImage(mypic4, b.x, b.y, 30, 30, this);
			}
			// let bomb is life reduce
			b.lifeDown();
			if (b.lifetime == 0) {
				b.islived = false;
				bombs.remove(b);
			}
		}

		// set background color
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);

		// draw hero tank
		if (hero.islived) {
			this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);

		}

		// draw enemytank
		for (int i = 0; i < ets.size(); i++) {
			EnemyTank et = ets.get(i);
			// System.out.println("----------------------");
			if (et.islived) {
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);

				// draw bullet
				for (int j = 0; j < et.ss.size(); j++) {
					Shot enemyshot = et.ss.get(j);
					if (enemyshot.islived) {
						g.fillOval(enemyshot.x - 4, enemyshot.y + 15, 6, 6);
					} else {
						et.ss.remove(enemyshot);
					}
				}
			}
		}

		// draw RecordInfor
		this.showRecordInfo(g);

		// this.drawTank(80, 620, g, 0, 0);
		// g.setColor(Color.black);
		// g.setFont(new Font("TimesRoman", Font.BOLD, 26));
		// g.drawString(Recorder.getEnnum()+"", 110, 640);
		//
		// this.drawTank(150, 620, g, 0, 1);
		// g.setColor(Color.black);
		// g.setFont(new Font("TimesRoman", Font.BOLD, 26));
		// g.drawString(Recorder.getEnnum()+"", 180, 640);

		// draw Gamer's Record
		// g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		//
		// g.drawString("Record of Tank War", 830, 80);
		// this.drawTank(850, 120, g, 0, 0);
		// g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		// g.setColor(Color.black);
		// g.drawString(Recorder.getEnCount() + "", 900, 150);
		//
		// this.drawTank(850, 190, g, 0, 1);
		// g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		// g.setColor(Color.black);
		// g.drawString(Recorder.getMyCount() + "", 900, 217);

		// draw every bullet from ss
		for (int i = 0; i < this.hero.ss.size(); i++) {
			Shot bullet = hero.ss.get(i);
			// dram one bullet
			if (bullet != null && bullet.islived == true) {
				g.setColor(Color.RED);
				g.fillOval(bullet.x, bullet.y, 6, 6);
			}
			if (bullet.islived == false) {
				hero.ss.remove(bullet);
			}
		}
		g.setColor(Color.cyan);
		// draw a Tank left wheel
		// g.fill3DRect(hero.getX()-5, hero.getY()+5, 30, 5, false);
		// g.fill3DRect(hero.getX()-5, hero.getY()+20,30, 5, false);
		// g.fill3DRect(hero.getX() , hero.getY() + 10, 20, 10, false);
		// g.fillOval(hero.getX() + 3, hero.getY() +9, 10, 10);
		// g.drawLine(hero.getX() + 3, hero.getY()+14, hero.getX()
		// +35,hero.getY()+14);
		// g.fill3DRect(hero.getX(), hero.getY(), 5, 30, false);
		// draw a Tank right wheel
		// g.fill3DRect(hero.getX() + 15, hero.getY(), 5, 30, false);
		// // draw a tank center part
		// // draw a tank of cycle
		// // draw a line
		// //g.setColor(Color.cyan);

		// System.out.println("------------");

	}

	// draw recordinfor
	public void showRecordInfo(Graphics g) {

		this.drawTank(80, 620, g, 0, 0);
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 26));
		g.drawString(Recorder.getEnnum() + "", 110, 640);

		this.drawTank(150, 620, g, 0, 1);
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 26));
		g.drawString(Recorder.getMylife() + "", 180, 640);
		g.setFont(new Font("TimesRoman", Font.BOLD, 35));

		g.drawString("Record of Tank War", 830, 80);
		this.drawTank(850, 120, g, 0, 0);
		g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g.setColor(Color.black);
		g.drawString(Recorder.getEnCount() + "", 900, 150);

		this.drawTank(850, 190, g, 0, 1);
		g.setFont(new Font("TimesRoman", Font.BOLD, 35));
		g.setColor(Color.black);
		g.drawString(Recorder.getMyCount() + "", 900, 217);
	}

	// enemy's bullet is hit me

	public void hitMe() {
		// get all the bullet from all enemy tank
		for (int i = 0; i < this.ets.size(); i++) {
			// get enemy's tank
			EnemyTank et = ets.get(i);
			// get every bullet's location
			for (int j = 0; j < et.ss.size(); j++) {
				//
				Shot enemyshot = et.ss.get(j);
				if (hero.islived) {
					this.hitTank(enemyshot, hero);
				}
			}
		}
	}

	// check my bullet hit enemy
	public void hitEnemyTank() {
		for (int i = 0; i < hero.ss.size(); i++) {
			Shot myshot = hero.ss.get(i);
			// bullet is effective
			if (myshot.islived) {
				// get enemyTank
				for (int j = 0; j < ets.size(); j++) {
					EnemyTank et = ets.get(j);
					if (et.islived) {
						this.hitTank(myshot, et);

					}
				}
			}
		}
	}

	public void hitTank(Shot s, Tank et) {
		// get the direction of tank
		switch (et.direct) {
		// up down
		case 0:
		case 1:
			if (s.x >= et.x && s.x <= et.x + 20 && s.y >= et.y && s.y <= et.y + 30) {
				// bullet is dead
				s.islived = false;
				// enemy tank is dead;
				et.islived = false;
				if (et.getType() == 0) {
					Recorder.reduceNum();
				} else if (et.getType() == 1) {
					Recorder.reduceMyLife();
				}
				// create a bomb
				Bomb b = new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		// lift right
		case 2:
		case 3:
			if (s.x >= et.x && s.x <= et.x + 30 && s.y >= et.y && s.y <= et.y + 20) {
				s.islived = false;
				et.islived = false;
				if (et.getType() == 0) {
					Recorder.reduceNum();
				} else if (et.getType() == 1) {
					Recorder.reduceMyLife();
				}

				// create a bomb
				Bomb b = new Bomb(et.x, et.y);
				bombs.add(b);
			}
			break;
		}
	}

	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		switch (type) {
		case 0:// enemytank
			g.setColor(Color.cyan);
			break;
		case 1:// hero
			g.setColor(Color.yellow);
			break;
		}
		switch (direct) {
		// 0 up
		case 0:// draw a Tank left wheel
			g.fill3DRect(x, y, 5, 30, false);
			// draw a Tank right wheel
			g.fill3DRect(x + 15, y, 5, 30, false);
			// draw a tank center part
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// draw a tank of cycle
			g.fillOval(x + 4, y + 10, 10, 10);
			// draw a line
			g.drawLine(x + 9, y - 10, x + 9, y + 10);
			break;
		case 1:
			g.fill3DRect(x, y, 5, 30, false);
			// draw a Tank right wheel
			g.fill3DRect(x + 15, y, 5, 30, false);
			// draw a tank center part
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// draw a tank of cycle
			g.fillOval(x + 4, y + 10, 10, 10);
			// draw a line
			g.drawLine(x + 9, y + 40, x + 9, y + 5);
			break;
		case 2:
			g.fill3DRect(x - 5, y + 5, 30, 5, false);
			g.fill3DRect(x - 5, y + 20, 30, 5, false);
			g.fill3DRect(x, y + 10, 20, 10, false);
			g.fillOval(x + 3, y + 9, 10, 10);
			g.drawLine(x + 3, y + 14, x - 15, y + 14);
			break;
		case 3:
			g.fill3DRect(x - 5, y + 5, 30, 5, false);
			g.fill3DRect(x - 5, y + 20, 30, 5, false);
			g.fill3DRect(x, y + 10, 20, 10, false);
			g.fillOval(x + 3, y + 9, 10, 10);
			g.drawLine(x + 3, y + 14, x + 35, y + 14);
			break;
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// hero.setDirect() = 0;
			hero.setDirect(0);
			int y2 = hero.getY();
			y2 = y2 - 3;
			hero.setY(y2);
			if (y2 < 0) {
				hero.setY(0);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			hero.setDirect(1);
			// tankdirect = 1;
			int y2 = hero.getY();
			y2 = y2 + 3;
			hero.setY(y2);
			if (y2 > 560) {
				hero.setY(560);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			hero.setDirect(2);
			// tankdirect = 2;
			int x2 = hero.getX();
			x2 = x2 - 3;
			hero.setX(x2);
			if (x2 <= 0) {
				hero.setX(0);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			hero.setDirect(3);
			// tankdirect = 3;
			int x2 = hero.getX();
			x2 = x2 + 3;
			hero.setX(x2);
			if (x2 > 750) {
				hero.setX(750);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_J) {
			if (this.hero.ss.size() <= 7) {
				this.hero.shotEnemy();
			}

		}
		this.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			// check hero's bullet hit the enemy's tank
			this.hitEnemyTank();

			// enemy's tank hit me
			this.hitMe();

			// check enemy's bullet hit hero

			this.repaint();
		}
	}
}

class Tank {
	// location of tank
	int x, y, direct, color, speed, type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	boolean islived = true;

	public int getDirect() {
		return direct;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	Shot s = null;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class EnemyTank extends Tank implements Runnable {
	public int getType() {
		return 0;
	}

	int speed = 2;
	// define a vector that can visit the MyPanel's enemyTank
	Vector<EnemyTank> ets = new Vector<EnemyTank>();

	// get the amount of enemy tank from MyPanel
	public void setEts(Vector<EnemyTank> vv) {
		this.ets = vv;
	}

	// define a vector to restore the bullet of enemy tank
	Vector<Shot> ss = new Vector<Shot>();

	// when the tank created the bullet should be in it, and when the bullet out
	// of bound , add a bullet

	EnemyTank(int x, int y) {
		super(x, y);
	}

	// function is for check tanks is cover
	public boolean isTouchedOhterEnemy() {
		boolean b = false;

		switch (this.direct) {
		// up:
		case 0:
			for (int i = 0; i < ets.size(); i++) {
				EnemyTank et = ets.get(i);
				// if not himself
				if (et != this) {
					// if the direct is up or down
					if (et.direct == 0 || et.direct == 1) {
						if ((this.x >= et.x) && (this.x <= (et.x + 20)) && (this.y >= et.y)
								&& (this.y <= (et.y + 30))) {
							return true;
						}
						if ((this.x + 20) >= (et.x + 20) && (this.x + 20) <= (et.x + 20) && (this.y >= et.y)
								&& (this.y <= et.y + 30)) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if ((this.x >= et.x) && (this.x <= (et.x + 30)) && (this.y >= et.y)
								&& (this.y <= (et.y + 20))) {
							return true;
						}
						if ((this.x + 20) >= (et.x) && (this.x + 20) <= (et.x + 30) && (this.y >= et.y)
								&& (this.y <= et.y + 20)) {
							return true;
						}
					}
				}
			}
			break;

		// down
		case 1:
			for (int i = 0; i < ets.size(); i++) {
				EnemyTank et = ets.get(i);
				// if not himself
				if (et != this) {
					// if the direct is up or down
					if (et.direct == 0 || et.direct == 1) {
						if ((this.x >= et.x) && (this.x <= (et.x + 20)) && (this.y + 30 >= et.y)
								&& (this.y + 30 <= (et.y + 30))) {
							return true;
						}
						if ((this.x + 20) >= (et.x) && (this.x + 20) <= (et.x + 20) && (this.y + 30 >= et.y)
								&& (this.y + 30 <= et.y + 30)) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if ((this.x >= et.x) && (this.x <= (et.x + 30)) && (this.y + 30 >= et.y)
								&& (this.y + 30 <= (et.y + 20))) {
							return true;
						}
						if ((this.x + 20) >= (et.x) && (this.x + 20) <= (et.x + 30) && (this.y + 30 >= et.y)
								&& (this.y + 30 <= et.y + 20)) {
							return true;
						}
					}
				}
			}

			break;

		// left
		case 2:
			for (int i = 0; i < ets.size(); i++) {
				EnemyTank et = ets.get(i);
				// if not himself
				if (et != this) {
					// if the direct is up or down
					if (et.direct == 0 || et.direct == 1) {
						if ((this.x >= et.x) && (this.x <= (et.x + 20)) && (this.y >= et.y)
								&& (this.y <= (et.y + 30))) {
							return true;
						}
						if ((this.x) >= (et.x + 20) && (this.x) <= (et.x + 20) && (this.y + 20 >= et.y)
								&& (this.y + 20 <= et.y + 30)) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if ((this.x >= et.x) && (this.x <= (et.x + 30)) && (this.y >= et.y)
								&& (this.y <= (et.y + 20))) {
							return true;
						}
						if ((this.x) >= (et.x) && (this.x) <= (et.x + 30) && (this.y + 20 >= et.y)
								&& (this.y + 20 <= et.y + 20)) {
							return true;
						}
					}
				}
			}
			break;

		// right
		case 3:
			for (int i = 0; i < ets.size(); i++) {
				EnemyTank et = ets.get(i);
				// if not himself
				if (et != this) {
					// if the direct is up or down
					if (et.direct == 0 || et.direct == 1) {
						if ((this.x + 30 >= et.x) && (this.x + 30 <= (et.x + 20)) && (this.y >= et.y)
								&& (this.y <= (et.y + 30))) {
							return true;
						}
						if ((this.x + 30) >= (et.x + 30) && (this.x + 30) <= (et.x + 20) && (this.y >= et.y)
								&& (this.y <= et.y + 30)) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if ((this.x + 30 >= et.x) && (this.x + 30 <= (et.x + 30)) && (this.y + 20 >= et.y)
								&& (this.y + 20 <= (et.y + 20))) {
							return true;
						}
						if ((this.x + 30) >= (et.x) && (this.x + 30) <= (et.x + 30) && (this.y + 20 >= et.y)
								&& (this.y + 20 <= et.y + 20)) {
							return true;
						}
					}
				}
			}
			break;
		}

		return b;
	}

	public void run() {
		int time = 0;
		while (true) {

			switch (this.direct) {
			// up moving
			case 0:
				for (int i = 0; i < 30; i++) {
					if (y > 0 && !this.isTouchedOhterEnemy()) {
						y -= speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}

				break;
			case 1:
				for (int i = 0; i < 30; i++) {
					if (y < 550 && !this.isTouchedOhterEnemy()) {
						y += speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
				break;
			case 2:
				for (int i = 0; i < 30; i++) {
					if (x > 0 && !this.isTouchedOhterEnemy()) {
						x -= speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
				break;
			case 3:
				for (int i = 0; i < 30; i++) {
					if (x < 650 && !this.isTouchedOhterEnemy()) {
						x += speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
				break;
			}
			System.out.println(Thread.currentThread().getName());
			this.direct = (int) (Math.random() * 4);

			if (this.islived == false) {
				// deadtank out of thread
				break;
			}

			// check the amount of bullet
			time++;
			if (time % 2 == 0) {
				if (islived) {
					if (ss.size() < 5) {
						Shot s = null;
						switch (direct) {

						case 0:
							s = new Shot(x + 9, y - 12, 0);
							ss.add(s);
							break;
						case 1:
							s = new Shot(x + 9, y + 40, 1);
							ss.add(s);
							break;
						case 2:
							s = new Shot(x - 19, y - 3, 2);
							ss.add(s);
							break;
						case 3:
							s = new Shot(x + 36, y - 3, 3);
							ss.add(s);
							break;
						}
						// start up the bullet thread
						Thread t = new Thread(s);
						t.start();
					}
				}
			}
		}
	}

}

// my Tank
class Hero extends Tank {
	public int getType() {
		return 1;
	}

	Vector<Shot> ss = new Vector<Shot>();
	Shot s = null;

	Hero(int x, int y) {
		super(x, y);
	}

	public void shotEnemy() {

		switch (this.direct) {
		case 0:
			s = new Shot(x + 7, y - 12, 0);
			ss.add(s);
			break;
		case 1:
			s = new Shot(x + 7, y + 40, 1);
			ss.add(s);
			break;
		case 2:
			s = new Shot(x - 19, y + 12, 2);
			ss.add(s);
			break;
		case 3:
			s = new Shot(x + 36, y + 12, 3);
			ss.add(s);
			break;
		}
		Thread t1 = new Thread(s);
		t1.start();

	}
}

class Shot implements Runnable {
	int x, y, direct, speed;
	boolean islived = true;

	Shot(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	public void run() {
		int speed = 6;
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			switch (direct) {
			case 0:
				y -= speed;
				break;

			case 1:
				y += speed;
				break;
			case 2:
				x -= speed;
				break;
			case 3:
				x += speed;
				break;
			}
			// System.out.println("s x=" + x + " y=" + y);
			if (x < 0 || y < 0 || x > 800 || y > 585) {
				islived = false;
				break;
			}
		}
	}
}

class Node {
	int x, y, direct;

	public Node(int x, int y, int direct) {
		super();
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

}

class Recorder {
	private static int encount = 0;
	private static int mycount = 0;
	private static int ennum = 20;
	private static int mylife = 3;
	static BufferedWriter bw = null;
	static BufferedReader br = null;
	// read every enemytank location
	static Vector<Node> nodes = new Vector<Node>();

	public static Vector<Node> readEnemyNewLoction() {
		try {
			br = new BufferedReader(new FileReader("D:\\JavaCode\\TankInfor\\1.txt"));
			// read first line
			String x = br.readLine();
			encount = Integer.parseInt(x);
			while ((x = br.readLine()) != null) {
				String[] xyz = x.split(" ");

				Node node = new Node(Integer.parseInt(xyz[0]), Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
				nodes.add(node);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return nodes;
	}

	// record the amount of enemy tank and their direction
	static Vector<EnemyTank> ets = new Vector<EnemyTank>();

	public Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recorder.ets = ets;
	}

	public static void keepRecordEnemyTankLocation() {

		try {
			bw = new BufferedWriter(new FileWriter("D:\\JavaCode\\TankInfor\\1.txt"));
			bw.write(encount + "\r\n");
			for (int i = 0; i < ets.size(); i++) {
				EnemyTank et = ets.get(i);
				if (et.islived) {
					String locationanddir = et.x + " " + et.y + " " + et.direct + " ";
					bw.write(locationanddir + "\r\n");

				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	// use to record how many enemy tank I hit
	public static void keepRecord() throws IOException {

		try {
			bw = new BufferedWriter(new FileWriter("D:\\JavaCode\\TankInfor\\1.txt"));
			bw.write(encount + "\r\n");

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			bw.close();
		}
	}

	// use to get how many enemy tank I hit
	public static void getRecord() {
		try {
			br = new BufferedReader(new FileReader("D:\\JavaCode\\TankInfor\\1.txt"));
			String x = br.readLine();
			encount = Integer.parseInt(x);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	public static int getEnnum() {
		return ennum;
	}

	public static void setEnnum(int ennum) {
		Recorder.ennum = ennum;
	}

	public static int getMylife() {
		return mylife;
	}

	public static void setMylife(int mylife) {
		Recorder.mylife = mylife;
	}

	public static void reduceNum() {
		ennum--;
		encount++;
	}

	public static void reduceMyLife() {
		mylife--;
		mycount++;
	}

	public static int getEnCount() {
		return encount;
	}

	public static int getMyCount() {
		return mycount;
	}

}

class Bomb {
	int x, y;

	public Bomb(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	boolean islived = true;
	int lifetime = 8;

	public void lifeDown() {
		this.lifetime--;
	}
}
class StartSound implements Runnable{
	private String filename;
	public StartSound(String filename){
		this.filename=filename;
	}
	public void run() {
		File soundfile= new File(filename);
		AudioInputStream audioinputstream=null;
		try{
			audioinputstream=AudioSystem.getAudioInputStream(soundfile);
			}
		catch(Exception e){
			e.printStackTrace();
			return;
		}
		AudioFormat format= audioinputstream.getFormat();
		SourceDataLine auline=null;
		DataLine.Info info= new DataLine.Info(SourceDataLine.class, format);
		try{auline =(SourceDataLine )AudioSystem.getLine(info);
		auline.open(format);
		}
		
		
		catch(Exception e){
			e.printStackTrace();
			return;
		}
		auline.start();
		int nbytesread=0;
		
		byte[] abdata= new byte[1024];
		try{
			while(nbytesread!=-1){
				nbytesread=audioinputstream.read(abdata,0,abdata.length);
				if(nbytesread>=0){
					auline.write(abdata, 0, nbytesread);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		finally{
			auline.drain();
			auline.close();
		}
		
	}
	
}