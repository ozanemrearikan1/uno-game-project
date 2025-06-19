package frames;

import java.util.Comparator;
/**
 * @author ozanemrearikan
 * with using Comparator<>, java utility
 */
public class StatsComparator implements Comparator<UserStats> {
	// This comparator is added to arrange leaderboard on main frame.
	
	// Statistics data is taken and they are compared after conversion into integer.
	public int compare(UserStats u1, UserStats u2) {
        return Integer.compare(u2.getTotalScore(), u1.getTotalScore());
    }

}
