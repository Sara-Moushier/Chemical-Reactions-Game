package Game;

class Test {
	public static void main(String[] args) {
		Timer timer = new Timer(10000); // sets timer for 10 seconds
		timer.start();
		while (!timer.finished())
			System.out.println(timer.getRunTime()); // prints time elapsed in the program in nano seconds
		System.out.println("Successful");
	}
}

public class Timer extends Thread {
	private final long minTime;
	private final long startTime;

	public Timer(long minTime) {
		this.minTime = minTime;
		this.startTime = System.nanoTime();
	}

	public long getRunTime() {
		return System.nanoTime() - startTime;
	}

	public boolean finished() {
		return !isAlive();
	}

	public void run() {
		try {
			sleep(minTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}