package net.coderodde.pzzlsrch.solvers.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;
import net.coderodde.pzzlsrch.ds.support.DAryHeap;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import net.coderodde.pzzlsrch.solvers.Solver;

/**
 * This class implements the bidirectional heuristic breadth-first search.
 * 
 * @author Rodion Efremov
 * @version 1.0
 * @param <T> the node type.
 */
public class BidirectionalHeuristicBFSSolver<T extends Iterable<T>> 
extends Solver<T> {
    
    /**
     * The heuristic function used in the forward search.
     */
    private HeuristicFunction<T> hf;
    
    /**
     * The heuristic function user in the backwards search.
     */
    private HeuristicFunction<T> hb;

    /**
     * The closed set of the forward search.
     */
    private Set<T> CLOSEDA = new HashSet<T>();
    
    /**
     * The closed set of the backwards search.
     */
    private Set<T> CLOSEDB = new HashSet<T>();
    
    /**
     * The priority queue of forwards search.
     */
    private IntegerPriorityQueue<T> OPENA = new DAryHeap<T>();
    
    /**
     * The priority queue of backwards search.
     */
    private IntegerPriorityQueue<T> OPENB = new DAryHeap<T>();
    
    /**
     * The map holding g-scores in forward search.
     */
    private Map<T, Integer> distanceMapA = new HashMap<T, Integer>();
    
    /**
     * The map holding g-scores in backwards search.
     */
    private Map<T, Integer> distanceMapB = new HashMap<T, Integer>();
    
    /**
     * The parent map in forward search.
     */
    private Map<T, T> parentMapA = new HashMap<T, T>();
    
    /**
     * The parent map in backwards search.
     */
    private Map<T, T> parentMapB = new HashMap<T, T>();
    
    /**
     * Sets the forwards search heuristic.
     * 
     * @param h the function to set.
     * 
     * @return itself for chaining. 
     */
    @Override
    public BidirectionalHeuristicBFSSolver<T> withHeuristicFunction
        (final HeuristicFunction<T> h) {
        this.hf = h;
        this.hb = h.newInstance();
        return this;
    }
    
    /**
     * Sets the priority queue implementation.
     * 
     * @param queue the queue to set.
     * 
     * @return itself for chaining.
     */
    @Override
    public BidirectionalHeuristicBFSSolver<T> 
            withPriorityQueue(final IntegerPriorityQueue<T> queue) {
        OPENA = queue.newInstance();
        OPENB = queue.newInstance();
        return this;
    }
        
    /**
     * @{InheritDoc}
     */
    @Override
    public List<T> search(final T source, final T target) {
        hf.withTarget(target);
        hb.withTarget(source);
        
        OPENA.clear();
        OPENB.clear();
        
        CLOSEDA.clear();
        CLOSEDB.clear();
        
        parentMapA.clear();
        parentMapB.clear();
        
        distanceMapA.clear();
        distanceMapB.clear();
        
        OPENA.add(source, hf.get(source));
        OPENB.add(target, hb.get(target));
        
        parentMapA.put(source, null);
        parentMapB.put(target, null);
        
        distanceMapA.put(source, 0);
        distanceMapB.put(target, 0);
        
        T touch = null;
        int m = Integer.MAX_VALUE;
        
        while (OPENA.size() * OPENB.size() != 0) {
            if (touch != null) {
                int m1 = distanceMapA.get(OPENA.min()) + hf.get(OPENA.min());
                int m2 = distanceMapB.get(OPENB.min()) + hb.get(OPENB.min());
                
                if (m <= Math.max(m1, m2)) {
                    return tracebackPathBidirectional(touch, 
                                                      parentMapA, 
                                                      parentMapB);
                }
            }
            
            T current = OPENA.extractMinimum();
            
            CLOSEDA.add(current);
            
            for (final T neighbour : current) {
                if (CLOSEDA.contains(neighbour)) {
                    continue;
                }
                
                int tmpg = distanceMapA.get(current) + 1;
                
                if (parentMapA.containsKey(neighbour) == false) {
                    OPENA.add(neighbour, tmpg + hf.get(neighbour));
                    parentMapA.put(neighbour, current);
                    distanceMapA.put(neighbour, tmpg);
                    
                    if (CLOSEDB.contains(neighbour)) {
                        if (m > tmpg + distanceMapB.get(neighbour)) {
                            m = tmpg + distanceMapB.get(neighbour);
                            touch = neighbour;
                        }
                    }
                } else if (tmpg < distanceMapA.get(neighbour)) {
                    OPENA.decreasePriority(neighbour, tmpg + hf.get(neighbour));
                    parentMapA.put(neighbour, current);
                    distanceMapA.put(neighbour, tmpg);
                    
                    if (CLOSEDB.contains(neighbour)) {
                        if (m > tmpg + distanceMapB.get(neighbour)) {
                            m = tmpg + distanceMapB.get(neighbour);
                            touch = neighbour;
                        }
                    }
                }
            }
            
            current = OPENB.extractMinimum();
            
            CLOSEDB.add(current);
            
            for (final T neighbour : current) {
                if (CLOSEDB.contains(neighbour)) {
                    continue;
                }
                
                int tmpg = distanceMapB.get(current) + 1;
                
                if (parentMapB.containsKey(neighbour) == false) {
                    OPENB.add(neighbour, tmpg + hb.get(neighbour));
                    parentMapB.put(neighbour, current);
                    distanceMapB.put(neighbour, tmpg);
                    
                    if (CLOSEDA.contains(neighbour)) {
                        if (m > tmpg + distanceMapA.get(neighbour)) {
                            m = tmpg + distanceMapA.get(neighbour);
                            touch = neighbour;
                        }
                    }
                } else if (tmpg < distanceMapB.get(neighbour)) {
                   OPENB.decreasePriority(neighbour, tmpg + hb.get(neighbour));
                   parentMapB.put(neighbour, current);
                   distanceMapB.put(neighbour, tmpg);
                   
                   if (CLOSEDA.contains(neighbour)) {
                       if (m > tmpg + distanceMapA.get(neighbour)) {
                           m = tmpg + distanceMapA.get(neighbour);
                           touch = neighbour;
                       }
                   }
                }
            }
        }
        
        if (touch != null) {
            System.out.println("FUNKKKKEEE");
            return tracebackPathBidirectional(touch, 
                                              parentMapA, 
                                              parentMapB);
        }
        
        return Collections.<T>emptyList();
    }
    
    @Override
    public HeuristicFunction<T> getHeuristicFunction() {
        return hf;
    }
}
