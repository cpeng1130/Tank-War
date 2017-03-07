package MyNotePad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Test1 extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea jta = null;
	JScrollPane jsp=null;
	JMenuBar jmb = null;
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	JMenuItem jmi2 = null;

	public static void main(String[] args) {
		new Test1();
	}

	public Test1() {
		jsp= new JScrollPane();
	
		jta = new JTextArea();
		jmb = new JMenuBar();
		jm1 = new JMenu("File");

		jm1.setMnemonic('F');
		jmi1 = new JMenuItem("Open", new ImageIcon("1.GIF"));
		jmi2 = new JMenuItem("Save", new ImageIcon("1.GIF"));

		jmi1.addActionListener(this);
		jmi1.setActionCommand("open");
		
		jmi2.addActionListener(this);
		jmi2.setActionCommand("save");
		
		jsp.add(jta);
		this.setJMenuBar(jmb);
		jmb.add(jm1);
		jm1.add(jmi1);
		jm1.add(jmi2);
		this.add(jta);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		// check the action
		
		if(e.getActionCommand().equals("save")){
			// show the dialog to save the file
			JFileChooser jfc= new JFileChooser();
			jfc.setDialogTitle("saving");
			jfc.showOpenDialog(null);
			jfc.setVisible(true);
			
			
			String filename= jfc.getSelectedFile().getAbsolutePath();
			BufferedWriter bw=null;
			try {
				bw= new BufferedWriter(new FileWriter(filename));
				bw.write(this.jta.getText());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			finally{
				try {
					bw.close();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			
		}
	
		else if (e.getActionCommand().equals("open")) {

			// JFileChooser
			JFileChooser jfc1 = new JFileChooser();
			jfc1.setDialogTitle("choose a file....");
			jfc1.showOpenDialog(null);// defalut mode
			jfc1.setVisible(true);

			String filepath = jfc1.getSelectedFile().getAbsolutePath();
			BufferedReader br = null;
			String s = "";
			String buf = "";
			try {
				br = new BufferedReader(new FileReader(filepath));
				while ((s = br.readLine()) != null) {
					buf = buf + s + "\r\n";
				}

				jta.setText(buf);
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			}

			// System.out.println(filepath);
			catch (IOException e1) {

				e1.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		}
	}
}
