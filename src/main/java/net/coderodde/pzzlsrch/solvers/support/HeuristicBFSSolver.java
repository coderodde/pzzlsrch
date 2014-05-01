package net.coderodde.pzzlsrch.solvers.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static net.coderodde.pzzlsrch.Utils.checkNotNull;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;
import net.coderodde.pzzlsrch.ds.support.DAryHeap;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import net.coderodde.pzzlsrch.solvers.Solver;

/**
 * This class implements heuristic breadth-first search.
 * 
 * @author Rodion Efremov
 * @param <T> the node type.
 */
public class HeuristicBFSSolver<T extends Iterable<T>> extends Solver<T> {

    /**
     * The heuristic function implementation.
     */
    private HeuristicFunction<T> hf;
    
    private Map<T, T> parentMap = new HashMap<T, T>();
    
    private Map<T, Integer> distanceMap = new HashMap<T, Integer>();
    
    private IntegerPriorityQueue<T> OPEN = new DAryHeap<T>();
    
    private Set<T> CLOSED = new HashSet<T>();
    
    @Override
    public HeuristicFunction<T> getHeuristicFunction() {
        return hf;
    }
    
    @Override
    public Solver<T> withHeuristicFunction(final HeuristicFunction<T> hf) {
        this.hf = hf;
        return this;
    }
    
    @Override
    public Solver<T> withPriorityQueue(final IntegerPriorityQueue<T> queue) {
        OPEN = queue.newInstance();
        return this;
    }

    @Override
    public List<T> search(final T source, final T target) {
        checkNotNull(hf, "Heuristic function is null.");
        checkNotNull(OPEN, "Priority queue is null.");
        checkNotNull(source, "Source is null.");
        checkNotNull(target, "Target is null.");
        
        OPEN.clear();
        CLOSED.clear();
        parentMap.clear();
        distanceMap.clear();
        
        hf.withTarget(target);
        
        OPEN.add(source, hf.get(source));
        parentMap.put(source, null);
        distanceMap.put(source, 0);
        
        while (OPEN.size() > 0) {
            T current = OPEN.extractMinimum();
            
            if (current.equals(target)) {
                return tracebackPath(target, parentMap);
            }
            
            CLOSED.add(current);
            
            for (final T neighbour : current) {
                if (CLOSED.contains(neighbour)) {
                    continue;
                }
                
                int tmpg = distanceMap.get(current) + 1;
                
                if (parentMap.containsKey(neighbour) == false) {
                    parentMap.put(neighbour, current);
                    distanceMap.put(neighbour, distanceMap.get(current) + 1);
                    OPEN.add(neighbour, tmpg + hf.get(neighbour));
                } else if (tmpg < distanceMap.get(neighbour)) {
                    parentMap.put(neighbour, current);
                    distanceMap.put(neighbour, tmpg);
                    OPEN.decreasePriority(neighbour, tmpg + hf.get(neighbour));
                }
            }
        }
        
        return Collections.<T>emptyList();
    }   
}
