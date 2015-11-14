package domini;
import java.time.Duration;
/**
 *
 * @author felix.axel.gimeno
 */
public class CtrStatsUser {
    private final HidatoUser user;
    
    private CtrStatsUser(){throw new UnsupportedOperationException();}
    public CtrStatsUser(HidatoUser A){this.user = A;}
    
    public int getSolvedGames(){ return user.getSolvedGames(); }
    public int getStartedGames(){ return user.getStartedGames(); }
    public Duration getTotalTimePlayed(){ return user.getTotalTimePlayed(); }
}
