package net.coderodde.pzzlsrch.ds;

/**
 *
 * @param <T> the element type.
 * @author Rodion Efremov
 * @version 1.0
 */
public interface IntegerPriorityQueue<T> {
    
    /**
     * Adds a new element to this queue.
     * 
     * @param element the element to add.
     * @param priority the priority of the element being added.
     */
    public void add(final T element, final int priority);
    
    /**
     * Returns but does not remove the minimum element in the queue.
     * 
     * @return the element with the least priority.
     */
    public T min();
    
    /**
     * Removes and returns the element with the least priority.
     * 
     * @return the element with the least priority.
     */
    public T extractMinimum();
    
    /**
     * Updates the priority of an element.
     * 
     * @param element the element whose priority to update.
     * @param priority the new priority.
     */
    public void decreasePriority(final T element, final int priority);
    
    /**
     * Returns the amount of elements in the queue.
     * 
     * @return the amount of elements in the queue.
     */
    public int size();
    
    public void clear();
    
    public int getMinimumPriority();
    
    public IntegerPriorityQueue<T> newInstance();
}
