package model;

public class RecordPlayerMatch {
	
	private int totalMatchNotCompleted;
	private int totalMatchCompleted;
	private long totalPoints;
	private int maxPoints;
	private int minPoints;
	private double avgPoints;
	private long totalDuration;
	private long maxDuration;
	private long minDuration;
	
	public int getTotalMatch() {
		return totalMatchNotCompleted + totalMatchCompleted ;
	}
	public int getTotalMatchNotCompleted() {
		return totalMatchNotCompleted;
	}
	public void setTotalMatchNotCompleted(int totalMatchNotCompleted) {
		this.totalMatchNotCompleted = totalMatchNotCompleted;
	}
	public int getTotalMatchCompleted() {
		return totalMatchCompleted;
	}
	public void setTotalMatchCompleted(int totalMatchCompleted) {
		this.totalMatchCompleted = totalMatchCompleted;
	}
	public long getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(long totalPoints) {
		this.totalPoints = totalPoints;
	}
	public int getMaxPoints() {
		return maxPoints;
	}
	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
	public int getMinPoints() {
		return minPoints;
	}
	public void setMinPoints(int minPoints) {
		this.minPoints = minPoints;
	}
	public double getAvgPoints() {
		return avgPoints;
	}
	public void setAvgPoints(double avgPoints) {
		this.avgPoints = avgPoints;
	}
	public long getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}
	public long getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(long maxDuration) {
		this.maxDuration = maxDuration;
	}
	public long getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(long minDuration) {
		this.minDuration = minDuration;
	}
	
}
