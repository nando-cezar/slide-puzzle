package model;

public class Player {

    private Long playerId;
    private String playerUsername;
    private String playerEmail;
    private String playerPassword;
    private String playerUrlImage;
    
    public Player() {
    }
    
    public Player(String playerUsername, String playerPassword) {
        this.playerUsername = playerUsername;
        this.playerPassword = playerPassword;
    }
    
    public Player(String playerUsername, String playerEmail, String playerPassword) {
        this.playerUsername = playerUsername;
        this.playerEmail = playerEmail;
        this.playerPassword = playerPassword;
    }

    public Player(Long playerId, String playerUsername, String playerEmail, String playerPassword) {
    	this.playerId = playerId;
        this.playerUsername = playerUsername;
        this.playerEmail = playerEmail;
        this.playerPassword = playerPassword;
    }

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}

	public String getPlayerEmail() {
		return playerEmail;
	}

	public void setPlayerEmail(String playerEmail) {
		this.playerEmail = playerEmail;
	}

	public String getPlayerPassword() {
		return playerPassword;
	}

	public void setPlayerPassword(String playerPassword) {
		this.playerPassword = playerPassword;
	}

	public String getPlayerUrlImage() {
		return playerUrlImage;
	}

	public void setPlayerUrlImage(String playerUrlImAge) {
		this.playerUrlImage = playerUrlImAge;
	}

}
