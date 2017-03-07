package thread;
/***
 * how to create a thread to develop a program
 * @author CP
 *
 */
public class ThreadTest1 {

	public static void main(String[] args) {
		Cat c= new Cat();
		c.start();
	}

}
class Cat extends Thread{
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		System.out.println("hello world");}
		
	}
}
