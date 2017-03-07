package listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EventsTest1 extends JFrame implements 	ActionListener{
	MyPanel_1 my=null;
	JPanel jp=null;
	JButton jb1=null;
	JButton jb2=null;
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new EventsTest1();
	}

	public EventsTest1() {
		my= new MyPanel_1();
		jb1= new JButton("Black");
		jb2= new JButton("Red");
		
		// add Listener
		jb1.addActionListener(this);
		
		jb2.addActionListener(this);
		// set action order
		jb1.setActionCommand("aa");
		jb2.setActionCommand("bb");
		
		
		
		jp= new JPanel();
		jp.setBackground(Color.BLACK);
		this.add(jb1,BorderLayout.NORTH);
		this.add(jp);
		this.add(jb2,BorderLayout.SOUTH);
		
		
		
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// method to deal with the event
	public void actionPerformed(ActionEvent e) {
		//System.out.println("ok");
		// 
		if(e.getActionCommand().equals("aa")){
			System.out.println("black button");
			jp.setBackground(Color.black);
		}
		if(e.getActionCommand().equals("bb")){
			System.out.println("red button");
			jp.setBackground(Color.RED);
		}
	}
}

class MyPanel_1 extends JPanel {
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		super.paint(g);
		
	}
}