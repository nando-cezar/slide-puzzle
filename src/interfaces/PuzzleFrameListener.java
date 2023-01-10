package interfaces;

import model.Match;
import model.Puzzle;
import util.enums.TypeGame;

public interface PuzzleFrameListener {
	void onClick(Puzzle puzzle, Match match, long currentTime, TypeGame typeGame);
}
