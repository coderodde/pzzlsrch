package net.coderodde.pzzlsrch.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This abstract class defines the API for solvers and some methods shared by
 * most of them..
 * 
 * @author Rodion Efremov
 * @version 1.0
 * @param <T> the node type.
 */
public abstract class Solver<T extends Iterable<T>> {
    
    public abstract 
        Solver<T> setHeuristicFunction(final HeuristicFunction<T> h);
    
    public abstract List<T> search(final T source, final T target);
    
    protected List<T> tracebackPath(final T target, final Map<T, T> parentMap) {
        List<T> path = new ArrayList<T>();
        
        if (target == null || parentMap == null) {
            // Return empty path.
            return path;
        }
        
        T node = target;
        
        while (node != null) {
            path.add(node);
            node = parentMap.get(node);
        }
        
        Collections.<T>reverse(path);
        return path;
    }
    
    protected List<T> tracebackPathBidirectional(final T touch,
                                                 final Map<T, T> parentMapA,
                                                 final Map<T, T> parentMapB) {
        List<T> path = new ArrayList<T>();
        
        T tmp = touch;
        
        while (tmp != null) {
            path.add(tmp);
            tmp = parentMapA.get(tmp);
        }
        
        Collections.<T>reverse(path);
        
        tmp = parentMapB.get(touch);
        
        while (tmp != null) {
            path.add(tmp);
            tmp = parentMapB.get(tmp);
        }
        
        return path;
    }
}
