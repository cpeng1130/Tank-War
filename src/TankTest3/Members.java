package TankTest3;

public class Members {
	// x,y location of Tank ;
	// type : 1 is hero tank , 0: is enemy tank;
	// life default is true;

}

class Shot3 implements Runnable {
	int x, y, direction;
	int speed = 3;
	boolean life = true;

	public Shot3(int x, int y, int direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void run() {
		while (true) {
			switch (direction) {
			case 0:
				y = y - 3;
				break;
			case 1:
				y = y + 3;
				break;
			case 2:
				x = x - 3;
				break;
			case 3:
				x = x + 3;
				break;
			}
		}
	}
}

class Tank3 {
	// x,y location of Tank ;
	// type : 1 is hero tank , 0: is enemy tank;
	// life default is true;
	int x, y, direction, type, speed;
	boolean life = true;

	Shot3 s = null;

	public Tank3(int x, int y, int direction, int type) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.type = type;
		speed = 3;
	}

	public Shot3 shotEnemy() {

		switch (direction) {
		case 0:
			s = new Shot3(x + 10, y - 10, 0);
			break;
		case 1:
			s = new Shot3(x + 10, y + 40, 1);
			break;
		case 2:
			s = new Shot3(x - 15, y + 10, 2);
			break;
		case 3:
			s = new Shot3(x + 40, y + 10, 3);
			break;

		}
		Thread t= new Thread(s);
		t.start();
		return s;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean getLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}

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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}

class HeroTank3 extends Tank3 {

	public HeroTank3(int x, int y, int direction, int type) {
		super(x, y, direction, type);

	}
}

class DarkTank3 extends Tank3 implements Runnable {

	public DarkTank3(int x, int y, int direction, int type) {
		super(x, y, direction, type);

	}

	public void run() {
		// int time=0;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			switch (this.direction) {
			case 0:
				this.y--;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				break;
			case 1:
				this.y++;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				break;
			case 2:
				this.x--;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				break;
			case 3:
				this.x++;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				break;
			}
			// System.out.println("==========
			// "+Thread.currentThread().getName());
			this.direction = (int) (Math.random() * 4);

		}
	}

}