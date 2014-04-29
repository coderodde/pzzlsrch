package net.coderodde.pzzlsrch.solvers;

/**
 * This interface defines the API for a heuristic function;
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public interface HeuristicFunction<T> {
   
    public int get(final T t1, final T t2);
    
}
