package CapaDomini.Usuari;

import java.time.Duration;

/**
 * @since 2015-11-14
 * @author felix.axel.gimeno
 * @see HidatoUser
 * @see User
 */
public final class UserStatsController {

    private final HidatoUser user;

    @SuppressWarnings("unused")
	private UserStatsController() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param user instance of User to show stats of
     */
    public UserStatsController(HidatoUser user) {
        this.user = user;
    }

    
    /**
     *
     * @return userName of class User
     */
    public String getUsername() {
        return user.getUsername();
    }
    
    
    /**
     *
     * @return solvedGames of class User
     */
    public int getSolvedGames() {
        return user.getSolvedGames();
    }

    /**
     *
     * @return count of started games, from class User
     */
    public int getStartedGames() {
        return user.getStartedGames();
    }

    /**
     *
     * @return total time played, from class User
     */
    public Duration getTotalTimePlayed() {
        return user.getTotalTimePlayed();
    }

    /**
     *
     * @return score of user, from class HidatoUser 
     */
    public int getTotalScore() {
        return user.getTotalScore();
    }

    /**
     *
     * @return percentage of solved games to started, from class HidatoUser 
     */
    public double getSolvedPercentage() {
        return user.getSolvedPercentage();
    }

    /**
     *
     * @return percentage of score to started games, from class HidatoUser 
     */
    public int getAverageScore() {
        return user.getAverageScore();
    }

    /**
     *
     * @return percentage of time to solved games, from class HidatoUser 
     */
    public long getAverageTimePerSolve() {
        if (user.getAverageTimePerSolve() == null) return 0;
        return user.getAverageTimePerSolve().getSeconds();
    }

    /**
     *
     * @return total created boards, from class User
     */
    public int getTotalCreatedBoards() {
        return user.getTotalCreatedBoards();
    }
    
    public long getBestTime() {
        if (user.getBestTime() == null) return 0;
        return user.getBestTime().getSeconds();
    }
    
    public int getBestScore() {
        return user.getBestScore();
    }
}
