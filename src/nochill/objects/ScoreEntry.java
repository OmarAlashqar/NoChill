package nochill.objects;

import java.io.Serializable;

/**
 * This class holds information about a single score entry for the leaderboard
 */
public class ScoreEntry implements Comparable<ScoreEntry>, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int score;

	public ScoreEntry(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(ScoreEntry entry) {
		if (score < entry.getScore())
			return 1;
		else if (score > entry.getScore())
			return -1;
		else
			return 0;
	}

}
