package listener;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseListenerDemo1 extends JFrame{
	private static final long serialVersionUID = 1L;
	MyPanel_2 my=null;
	public static void main(String[] args) {
		new MouseListenerDemo1();
	}
	public MouseListenerDemo1() {
		my= new MyPanel_2();
		this.add(my);
		//this.addMouseListener(my);
		//this.addKeyListener(my);
		//this.addMouseMotionListener(my);
		this.addWindowListener(my);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
class MyPanel_2 extends JPanel implements MouseListener, KeyListener,MouseMotionListener,WindowListener{
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g){
		super.paint(g);
	}
	//mouse clicked
	public void mouseClicked(MouseEvent e) {
	//	System.out.println("clicked  "+ e.getX() +"  "+ e.getY()  );
	}
	//mouse move in the area
	public void mouseEntered(MouseEvent e) {
	}
	// mouse move out the area
	public void mouseExited(MouseEvent e) {
	}
	// mouse press down
	public void mousePressed(MouseEvent e) {
	}
	// mouse release 
	public void mouseReleased(MouseEvent e) {
		
	}
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
	}
	public void keyReleased(KeyEvent arg0) {
		
	}
	public void keyTyped(KeyEvent arg0) {
		
	}
	public void mouseDragged(MouseEvent e) {
		System.out.println("mouse drageed" +e.getX()+"  "+e.getY());
	}
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouse moved"+e.getX()+"  "+e.getY());
	}
	public void windowActivated(WindowEvent arg0) {
		System.out.println("windowActivated");
	}
	public void windowClosed(WindowEvent arg0) {
		System.out.println("windowClosed");
	}
	public void windowClosing(WindowEvent arg0) {
		System.out.println("windowClosing");
	}
	// mininse the window's size
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println("windowDeactivated");
	}
	public void windowDeiconified(WindowEvent arg0) {
	}
	public void windowIconified(WindowEvent arg0) {
	}
	public void windowOpened(WindowEvent arg0) {
		System.out.println("windowOpened");

	}
}