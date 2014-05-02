package net.coderodde.pzzlsrch.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;

/**
 * This abstract class defines the API for solvers and some methods shared by
 * most of them..
 * 
 * @author Rodion Efremov
 * @version 1.0
 * @param <T> the node type.
 */
public abstract class Solver<T extends Iterable<T>> {
    
    /**
     * Sets a heuristic function for this solver.
     * 
     * @param h the function to set.
     * 
     * @return this for chaining. 
     */
    public Solver<T> withHeuristicFunction(final HeuristicFunction<T> h) {
        // Do nothing by default.
        return this;
    }
    
    /**
     * Sets a priority queue for this solver.
     * 
     * @param queue the priority queue to set.
     * 
     * @return this for chaining.
     */
    public Solver<T> withPriorityQueue(final IntegerPriorityQueue<T> queue) {
        // Do nothing by default.
        return this;
    }
    
    /**
     * Returns the heuristic function used in this solver. if any.
     * 
     * @return the heuristic function of this solver or <code>null</code> if 
     * there is no such.
     */
    public HeuristicFunction<T> getHeuristicFunction() {
        return null;
    }
    
    /**
     * Searches for a shortest path between nodes <code>source</code> and
     * <code>target</code>.
     * 
     * @param source the source node.
     * @param target the target node.
     * 
     * @return the path leading from source to target, or an empty list
     * if target is not reachable.
     */
    public abstract List<T> search(final T source, final T target);
    
    /**
     * Builds a path in unidirectional solvers.
     * 
     * @param target the target node.
     * @param parentMap the parent map.
     * 
     * @return a path. 
     */
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
    
    /**
     * Builds a path in bidirectional solvers.
     * 
     * @param touch the touch node.
     * @param parentMapA the parent map of the forward search.
     * @param parentMapB the parent map of the backwards search.
     * 
     * @return a path. 
     */
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
