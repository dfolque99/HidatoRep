package CapaDomini.Tauler;

/**
 *
 * @author felix.axel.gimeno
 */
public class SolverControllerStop {

    private static boolean stop = false;

    //final static SolverControllerStop ref = new SolverControllerStop();

    public static boolean isStopped() {
        return SolverControllerStop.stop;
    }
    public static void allow(){
        SolverControllerStop.stop = false;
    }

    public static void stop() {
        SolverControllerStop.stop = true;
    }

    private SolverControllerStop() {
    }

    /*
    public SolverControllerStop getInstance() {
        return ref;
    }
    */
}
