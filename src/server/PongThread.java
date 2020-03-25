package server;

public class PongThread extends Thread {
	
	private PongMonitor mon;

	public PongThread(PongMonitor mon){
		this.mon = mon;
			
	}
	
	
	public void run(){
		 for (;;) {	
			   mon.step();

			 try {
				 Thread.sleep(1000/60);
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
	}

}
