package net.coderodde.pzzlsrch.solvers;

/**
 * This interface defines the API for a heuristic function;
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public abstract class HeuristicFunction<T> {
   
    /**
     * The target node.
     */
    protected T target;
    
    /**
     * Gets the heuristic estimate between nodes <code>t1</code> and
     * <code>t2</code>.
     * 
     * @param t1 the first node.
     * @param t2 the second node.
     * 
     * @return an (hopefully) optimistic estimate for the distance between the
     * two.
     */
    public abstract int get(final T t1, final T t2);
    
    /**
     * Gets the heuristic estimate between nodes <code>t</code> and
     * <code>target</code>.
     * 
     * @param t the first node.
     * 
     * @return an (hopefully) optimistic estimate for the distance between
     * <code>t</code> and <code>target</code>.
     */
    public abstract int get(final T t);
    
    /**
     * Sets the target node for this heuristic function.
     * 
     * @param target the target node to set.
     * 
     * @return this for chaining. 
     */
    public HeuristicFunction<T> withTarget(final T target) {
        this.target = target;
        return this;
    }
    
    /**
     * Spawns a new heuristic function with the same implementation.
     * 
     * @return a new heuristic function.
     */
    public abstract HeuristicFunction<T> newInstance();
}
