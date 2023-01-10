package model;

public class PlayerMatch {
	
	private Long id;
	private Player player;
	private Match match;
	private Long durationSeconds;
	private Long milliSecondsDuration;
	private boolean isCompleted;
	private double playerPoints;
	

	public PlayerMatch(Player player, Match match, Long milliSecondsDuration, boolean isCompleted) {
		super();
		this.player = player;
		this.match = match;
		this.milliSecondsDuration = milliSecondsDuration;
		this.durationSeconds = getSeconds();
		this.isCompleted = isCompleted;
		this.playerPoints = PlayerMatch.calculetePoints(durationSeconds);
	}
	
	public PlayerMatch(Long id, Player player, Match match, Long milliSecondsDuration, double playerPoints, boolean isCompleted) {
		super();
		this.id = id;
		this.player = player;
		this.match = match;
		this.milliSecondsDuration = milliSecondsDuration;
		this.durationSeconds = getSeconds();
		this.playerPoints = playerPoints;
		this.isCompleted = isCompleted;
	}

	private static float calculetePoints(Long durationSeconds) {
		int minute = 60;
		float points = 0;
		
		if (durationSeconds < minute) {
			points = 1_000;
		} else if (durationSeconds < 10 * minute) {
			points = 100;
		} else if (durationSeconds < 100 * minute) {
			points = 10;
		}
		return points;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Long getMilliSecondsDuration() {
		return milliSecondsDuration;
	}

	public void setMilliSecondsDuration(Long milliSecondsDuration) {
		this.milliSecondsDuration = milliSecondsDuration;
	}

	public double getPlayerPoints() {
		return playerPoints;
	}

	public void setPlayerPoints(double playerPoints) {
		this.playerPoints = playerPoints;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isComplete) {
		this.isCompleted = isComplete;
	}

	private long getSeconds() {
		return milliSecondsDuration / 1000 % 60;
	}

}
