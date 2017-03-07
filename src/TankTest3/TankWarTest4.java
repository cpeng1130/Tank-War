package TankTest3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankWarTest4 extends JFrame {
	private static final long serialVersionUID = 1L;
	MyPanel3 mp = null;

	public static void main(String[] args) {
		new TankWarTest4();
	}

	public TankWarTest4() {
		mp = new MyPanel3();
		Thread tmp=new Thread(mp);
		tmp.start();
		this.addKeyListener(mp);
		this.add(mp);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class MyPanel3 extends JPanel implements KeyListener ,Runnable {

	private static final long serialVersionUID = 1L;
	DarkTank3 darktank = null;
	HeroTank3 herotank = null;
	Vector <DarkTank3> dts= new Vector<DarkTank3>();	
	int amountofdarktank=6;
	public MyPanel3() {
		herotank = new HeroTank3(100, 500, 0, 0);
		for (int x = 0; x < amountofdarktank; x++) {
			darktank = new DarkTank3((x + 1) * 40, 0, 1, 1);
			dts.add(darktank);
		}
	}

	public void paint(Graphics g) {
		// set background;
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);

		// draw 3 dark tank
		for (int x = 0; x <dts.size(); x++) {
			
			DarkTank3 dt= dts.get(x);
			drawTank(dt.getX(),dt.getY(),dt.getDirection(),g,0);
			Thread t= new Thread(dt);
			t.start();
		}
		//System.out.println("========================"+dts.size());
		// draw a hero tank

		drawTank(herotank.getX(),herotank.getY(),herotank.getDirection(), g,1);

		// add a keyListener
		this.addKeyListener(this);
		
		//draw a bullet
		if(herotank.s!=null){
			g.fillRect(herotank.shotEnemy().x, herotank.shotEnemy().y, 2, 2);
		}
	}

	/***
	 * draw a Tank
	 * 
	 * @param ts
	 * @param g
	 */
	public void drawTank(int x, int y, int direction,Graphics g,int type) {
		if (type == 0) {//dark tank
			g.setColor(Color.green);
		} else if (type == 1) {// hero tank
			g.setColor(Color.cyan);
		}
		switch (direction) {
		case 0: // up
			// lift wheel

			g.fill3DRect(x, y, 5, 30, false);

			// right wheel
			g.fill3DRect(x + 15, y, 5, 30, false);

			// center part
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			//
			g.fill3DRect(x + 9, y - 10, 2, 20, false);
			break;
		case 1:// down

			g.fill3DRect(x, y, 5, 30, false);

			// right wheel
			g.fill3DRect(x + 15, y, 5, 30, false);

			// center part
			g.fill3DRect(x + 5, y + 5, 10, 20, false);

			g.fill3DRect(x + 9, y + 20, 2, 20, false);

			break;
		case 2:// lift

			g.fill3DRect(x, y, 30, 5, false);

			g.fill3DRect(x, y + 15, 30, 5, false);

			g.fill3DRect(x + 5, y + 5, 20, 10, false);

			g.fill3DRect(x - 10, y + 9, 20, 2, false);

			break;
		case 3:// right

			g.fill3DRect(x, y, 30, 5, false);

			g.fill3DRect(x, y + 15, 30, 5, false);

			g.fill3DRect(x + 5, y + 5, 20, 10, false);

			g.fill3DRect(x + 20, y + 9, 20, 2, false);

			break;
		}

	}

	/****
		 *  control hero tank 
		 * 
		 */
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int tempspeed = 0;
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			herotank.setDirection(0);
			tempspeed = herotank.y - herotank.speed;
			herotank.setY(tempspeed);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			herotank.setDirection(1);
			tempspeed = herotank.y + herotank.speed;
			herotank.setY(tempspeed);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			herotank.setDirection(2);
			tempspeed = herotank.x - herotank.speed;
			herotank.setX(tempspeed);

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			herotank.setDirection(3);
			tempspeed = herotank.x + herotank.speed;
			herotank.setX(tempspeed);
		}
		 if(e.getKeyCode()==KeyEvent.VK_J){
			herotank.shotEnemy();
		}
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void run() {
		while(true){
		this.repaint();
		}
	}
}