package CapaDomini.Tauler;

/**
 * Singleton class providing a value for signaling SolverController class when
 * the user wants it to stop
 *
 * @author felix.axel.gimeno
 */
public class SolverControllerStop {

    /**
     * value stop is false when the SolverController starts, and true if the
     * user selects "cancel" when solving a hidato
     * <p>
     * volatile means threads access the same value
     */
    private volatile static boolean stop = false;

    /**
     * true if user cancelled solving, false otherwise
     *
     * @return value of stop
     */
    public static boolean isStopped() {
        return SolverControllerStop.stop;
    }

    /**
     * make stop value false
     */
    public static void allow() {
        SolverControllerStop.stop = false;
    }

    /**
     * make stop value true
     */
    public static void stop() {
        SolverControllerStop.stop = true;
    }

    /**
     * AS this class is a singleton class, it cannot be instantiated
     */
    private SolverControllerStop() {
    }
}
