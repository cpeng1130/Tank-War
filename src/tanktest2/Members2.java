package tanktest2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Members2 {

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



