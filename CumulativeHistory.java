import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CumulativeHistory implements Iterable<CumulativeScore> {
  // list of score at the end of each previous round
  private List<CumulativeScore> previousRoundScores = new ArrayList<CumulativeScore>(15);

  public CumulativeScore getCurrentScore() {
    if (previousRoundScores.size() == 0) {
      return new CumulativeScore(0, 0, 0, 0);
    }
    return previousRoundScores.get(previousRoundScores.size() - 1);
  }

  public int getRoundNumber() {
    return previousRoundScores.size() + 1;
  }

  public int getMaxScore() {
    CumulativeScore currentScore = getCurrentScore();
    return Math.max(Math.max(currentScore.getScore(0), currentScore.getScore(1)),
        Math.max(currentScore.getScore(2), currentScore.getScore(3)));
  }

  // TODO: improve spacing/formatting
  public void printScoreHistory() {
    for (CumulativeScore scores : previousRoundScores) {
      for (int score : scores) {
        System.out.print(score + "  ");
      }
      System.out.println();
    }
  }

  public void addRoundScore(RoundScore scores) {
    if (scores.whoShotTheMoon() >= 0) {
      // if somebody shot the moon, then ...
      if (wouldShootingLoseGame(scores.whoShotTheMoon())) {
        // if giving everyone else 26 without ending the game would cause that player to
        // use, then subtract 26 from that player
        for (int player = 0; player < 4; player++) {
          if (player == scores.whoShotTheMoon()) {
            scores.setScore(player, -26);
          } else {
            scores.setScore(player, 0);
          }
        }
      } else {
        // else give all other players 26
        for (int player = 0; player < 4; player++) {
          if (player == scores.whoShotTheMoon()) {
            scores.setScore(player, 0);
          } else {
            scores.setScore(player, 26);
          }
        }
      }
    } // if nobody shot the moon, then just add scores on
      // if somebody shot the moon, then we have adjusted the round scores accordingly
    CumulativeScore currentScore = getCurrentScore();
    previousRoundScores.add(new CumulativeScore(currentScore.getScore(0) + scores.getScore(0),
        currentScore.getScore(1) + scores.getScore(1), currentScore.getScore(2) + scores.getScore(2),
        currentScore.getScore(3) + scores.getScore(3)));
  }

  private boolean wouldShootingLoseGame(int player) {
    // TODO: easy enough, but want a nice clean way
    return false;
  }

  @Override
  public Iterator<CumulativeScore> iterator() {
    return previousRoundScores.iterator();
  }

}