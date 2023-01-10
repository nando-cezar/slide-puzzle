package interfaces;

public interface StopwatchListener {	
	void pause();
	void stop();
	boolean isRunning();
	Long getDuration();
	long setMilliSeconds();
}
