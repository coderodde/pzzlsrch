package net.coderodde.pzzlsrch.solvers;

/**
 * This interface defines the API for a heuristic function;
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public abstract class HeuristicFunction<T> {
   
    protected T target;
    
    public abstract int get(final T t1, final T t2);
    
    public abstract int get(final T t);
    
    public HeuristicFunction<T> withTarget(final T target) {
        this.target = target;
        return this;
    }
    
    public abstract HeuristicFunction<T> newInstance();
}
