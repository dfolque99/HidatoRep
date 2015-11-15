package domini;

import java.time.Duration;

/**
 * @since 2015-11-14
 * @author felix.axel.gimeno
 */
public final class CtrStatsUser {

    private final HidatoUser user;

    private CtrStatsUser() {
        throw new UnsupportedOperationException();
    }

    public CtrStatsUser(HidatoUser user) {
        this.user = user;
    }

    public int getSolvedGames() {
        return user.getSolvedGames();
    }

    public int getStartedGames() {
        return user.getStartedGames();
    }

    public Duration getTotalTimePlayed() {
        return user.getTotalTimePlayed();
    }

    public int getTotalScore() {
        return user.getTotalScore();
    }

    public double getSolvedPercentage() {
        return user.getSolvedPercentage();
    }

    public int getAverageScore() {
        return user.getAverageScore();
    }

    public Duration getAverageTimePerSolve() {
        return user.getAverageTimePerSolve();
    }

    public int getTotalCreatedBoards() {
        return user.getTotalCreatedBoards();
    }
}
