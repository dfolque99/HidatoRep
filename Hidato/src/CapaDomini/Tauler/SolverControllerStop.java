package CapaDomini.Tauler;

/**
 *
 * @author felix.axel.gimeno
 */
public class SolverControllerStop {
    boolean stop = false;
    SolverControllerStop ref = new SolverControllerStop(); 
    private SolverControllerStop(){}
    public boolean isStopped(){return stop;}
    public void allow(){stop = false;}
    public void stop(){stop = true;}
}
