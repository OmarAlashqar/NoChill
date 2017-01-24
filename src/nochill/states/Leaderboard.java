package nochill.states;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nochill.Resources;
import nochill.gui.Button;
import nochill.gui.Button.ActionListener;
import nochill.objects.ScoreEntry;

/**
 * This state is for the leaderboard
 */
public class Leaderboard extends BasicGameState {

	private static int id = 2;

	private final TrueTypeFont nameFont = Resources.getNameFont();
	private final TrueTypeFont scoreFont = Resources.getScoreFont();

	private Image bg;
	private ArrayList<ScoreEntry> highScores;
	private Button btnBack;

	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		readHighScores();

//		// Reset leaderboard
//		highScores = new ArrayList<ScoreEntry>();
//		highScores.add(new ScoreEntry("AAA", 6));
//		highScores.add(new ScoreEntry("BBB", 5));
//		highScores.add(new ScoreEntry("CCC", 4));
//		highScores.add(new ScoreEntry("DDD", 3));
//		highScores.add(new ScoreEntry("EEE", 2));

		initGui(gc, game);
	}

	public void append(String name, int score) {
		highScores.remove(highScores.size() - 1);
		highScores.add(0, new ScoreEntry(name, score));
		Collections.sort(highScores);
	}

	public int getLowestScore() {
		int lowest = highScores.get(0).getScore();

		for (ScoreEntry entry : highScores)
			if (entry.getScore() < lowest)
				lowest = entry.getScore();

		return lowest;
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		btnBack.update(gc.getInput());
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		
		bg.draw(0,0);
		
		btnBack.render(g);
		
		int pos = 0;
		for (ScoreEntry entry : highScores) {
			
			g.setColor(new Color(173, 231, 167));
			g.fillRoundRect(100, 150 + pos * 50 + 4, 512 - 200, 40, 20);
			
			g.setColor(Color.darkGray);
			g.setFont(nameFont);
			g.drawString(entry.getName(), 150, 150 + pos * 50);
			g.setFont(scoreFont);
			g.drawString(String.valueOf(entry.getScore()), 320, 150 + pos * 50);
			pos++;
		}
	}

	@SuppressWarnings("unchecked")
	private void readHighScores() {
		// Reading and storing the original file
		try {
			FileInputStream fileIn = new FileInputStream("res/leaderboard.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			highScores = (ArrayList<ScoreEntry>) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeHighScores() {
		// Outputting to file
		try {
			FileOutputStream fileOut = new FileOutputStream("res/leaderboard.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(highScores);
			out.close();
			fileOut.close();
			System.out.printf("HighScores were saved successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGui(final GameContainer gc, final StateBasedGame game) {

		bg = Resources.getLeaderboardBg();
		
		// Initializing the buttons
		btnBack = new Button("back", 4);

		// Adding ActionListeners to the buttons
		btnBack.addActionListener(new ActionListener() {
			public void actionEvent() {
				gc.getInput().clearKeyPressedRecord();
				game.enterState(0);
			}
		});
	}

	public int getID() {
		return id;
	}

}
