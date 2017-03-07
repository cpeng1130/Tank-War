package thread;

public class RunnableTest2 {

	public static void main(String[] args) {
		Bird b= new Bird();
		Thread t= new Thread(b);
		t.start();
	}

}

class Bird implements Runnable {
	int times = 0;
	int sum = 55;
	boolean flag = true;

	public void run() {
		while(true){
		if (flag) {
			add();
		} else if (!flag) {
			show();
		}}
	}

	public void add() {
		int x = 0;
		while (true) {
			
			x++;
			if (x == sum) {
				flag=false;
				break;
			}
			System.out.println(x);
		}

	}

	public void show() {
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			System.out.println(times);
			times++;
		}
	}
}