package net.coderodde.pzzlsrch.solvers.support;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import net.coderodde.pzzlsrch.solvers.Solver;
import static net.coderodde.pzzlsrch.Utils.checkNotNull;

/**
 * This class implements a solver based on bidirectional breadth-first search.
 * 
 * @author Rodion Efremov
 * @param <T> the node type.
 * @version 1.0
 */
public class BidirectionalBFSSolver<T extends Iterable<T>> extends Solver<T> {

    /**
     * The forward parent map of this solver.
     */
    private Map<T, T> parentMapA = new HashMap<T, T>();
    
    /**
     * The backward parent map of this solver.
     */
    private Map<T, T> parentMapB = new HashMap<T, T>();
    
    @Override
    public List<T> search(final T source, final T target) {
        checkNotNull(source, "Source is null.");
        checkNotNull(target, "Target is null.");
        parentMapA.clear();
        parentMapB.clear();
        
        parentMapA.put(source, null);
        parentMapB.put(target, null);
        
        Deque<T> queueA = new LinkedList<T>();
        Deque<T> queueB = new LinkedList<T>();
        
        queueA.addLast(source);
        queueB.addLast(target);
        
        while (queueA.size() != 0 && queueB.size() != 0) {
            T current = queueA.removeFirst();
            
            if (parentMapB.containsKey(current)) {
                return tracebackPathBidirectional(current, 
                                                  parentMapA, 
                                                  parentMapB);
            }
            
            for (final T neighbour : current) {
                if (parentMapA.containsKey(neighbour) == false) {
                    parentMapA.put(neighbour, current);
                    queueA.addLast(neighbour);
                }
            }
            
            current = queueB.removeFirst();
            
            if (parentMapA.containsKey(current)) {
                return tracebackPathBidirectional(current, 
                                                  parentMapA,
                                                  parentMapB);
            }
            
            for (final T neighbour : current) {
                if (parentMapB.containsKey(neighbour) == false) {
                    parentMapB.put(neighbour, current);
                    queueB.addLast(neighbour);
                }
            }
        }
        
        return Collections.<T>emptyList();
    }
}
