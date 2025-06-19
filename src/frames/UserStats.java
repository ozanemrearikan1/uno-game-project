package frames;
/**
 * @author ozanemrearikan
 */
public class UserStats {
	// To use comparator easily, I have created this class!
	
	private String username;
    private int totalScore;

    UserStats(String username, int totalScore) {
        this.username = username;
        this.totalScore = totalScore;
    }

	public String getUsername() {
		return username;
	}

	public int getTotalScore() {
		return totalScore;
	}
	
	
    
    

}
