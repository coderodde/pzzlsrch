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
 * This class implements a solver based on breadth-first search.
 * 
 * @param <T> the node type.
 * @author Rodion Efremov
 * @version 1.0
 */
public class BFSSolver<T extends Iterable<T>> extends Solver<T> {

    /**
     * The parent map of this solver.
     */
    private Map<T, T> parentMap = new HashMap<T, T>();

    public Solver<T> setHeuristicFunction(final HeuristicFunction<T> h) {
        // Just ignore.
        return this;
    }

    public List<T> search(final T source, final T target) {
        checkNotNull(source, "Source is null.");
        checkNotNull(target, "Target is null.");
        
        parentMap.clear();
        parentMap.put(source, null);
     
        Deque<T> queue = new LinkedList<T>();
        queue.addLast(source);
        
        while (queue.size() > 0) {
            final T current = queue.removeFirst();
            
            if (current.equals(target)) {
                return tracebackPath(target, parentMap);
            }
            
            for (final T neighbour : current) {
                if (parentMap.containsKey(neighbour) == false) {
                    parentMap.put(neighbour, current);
                    queue.addLast(neighbour);
                }
            }
        }
        
        return Collections.<T>emptyList();
    }
}
