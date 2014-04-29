package net.coderodde.pzzlsrch.solvers.support;

import java.util.List;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import net.coderodde.pzzlsrch.solvers.Solver;
import static net.coderodde.pzzlsrch.Utils.checkNotNull;

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
    
    @Override
    public Solver<T> setHeuristicFunction(final HeuristicFunction<T> hf) {
        this.hf = hf;
        return this;
    }

    @Override
    public List<T> search(final T source, final T target) {
        checkNotNull(hf, "Heuristic function is null.");
        checkNotNull(source, "Source is null.");
        checkNotNull(target, "Target is null.");
        
        return null;
    }   
}
