package thread;

public class RunnableTest1 {

	public static void main(String[] args) {
		Cat1 c= new Cat1();
		Thread t= new Thread(c);
		t.start();
	}

}

class Cat1 implements Runnable {
	int x=0;
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			x++;
			System.out.println("hello world "+x);
			if(x==10){
				break;
			}
		}
	}
}