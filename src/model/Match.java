package model;

public class Match {
	
	private Long id;
	private Puzzle puzzle;

	public Match(Puzzle puzzle) {
		super();
		this.puzzle = puzzle;
	}
	
	public Match(Long id, Puzzle puzzle) {
 		this.id = id;
		this.puzzle = puzzle;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	
}
