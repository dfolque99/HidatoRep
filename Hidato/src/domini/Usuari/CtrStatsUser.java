package domini.Usuari;

import java.time.Duration;

/**
 * @since 2015-11-14
 * @author felix.axel.gimeno
 * @see HidatoUser
 * @see User
 */
public final class CtrStatsUser {

    private final HidatoUser user;

    @SuppressWarnings("unused")
	private CtrStatsUser() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param user instance of User to show stats of
     */
    public CtrStatsUser(HidatoUser user) {
        this.user = user;
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
    public Duration getAverageTimePerSolve() {
        return user.getAverageTimePerSolve();
    }

    /**
     *
     * @return total created boards, from class User
     */
    public int getTotalCreatedBoards() {
        return user.getTotalCreatedBoards();
    }
}
