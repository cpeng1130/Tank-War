package listener;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MovingSmallBallDemo1 extends JFrame {
	private static final long serialVersionUID = 1L;
	MyPanel mp = null;

	public static void main(String[] args) {
		new MovingSmallBallDemo1();
	}

	public MovingSmallBallDemo1() {
		mp = new MyPanel();
		this.add(mp);
		this.addKeyListener(mp);

		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

class MyPanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	int x = 10, y = 10;

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		g.fillOval(x, y, 15, 15);
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println("key down "+arg0.getKeyCode()+"
		// "+arg0.getKeyChar());
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			y = y + 2;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			y = y - 2;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			x = x - 2;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x = x + 2;
		}
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// System.out.println("key up "+arg0.getKeyCode()+"
		// "+arg0.getKeyChar());
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
