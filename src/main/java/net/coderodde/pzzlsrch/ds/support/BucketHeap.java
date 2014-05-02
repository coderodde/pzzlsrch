package net.coderodde.pzzlsrch.ds.support;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;

/**
 * This class implements very easy integer priority queue.
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public class BucketHeap<T> implements IntegerPriorityQueue<T> {

    /**
     * The node class.
     * 
     * @param <T> the element type. 
     */
    static class ListNode<T> {
        /**
         * The element.
         */
        T element;
        
        /**
         * The priority of <code>element</code>.
         */
        int priority;
        
        /**
         * The predecessor <code>ListNode</code>.
         */
        ListNode<T> prev;
        
        /**
         * The successor <code>ListNode</code>.
         */
        ListNode<T> next;
        
        /**
         * Constructs new <code>ListNode</code> with specified element and 
         * priority.
         * 
         * @param element the element.
         * @param priority the priority associated with the element.
         */
        ListNode(final T element, final int priority) {
            this.element = element;
            this.priority = priority;
        }
    }
    
    /**
     * Maps elements to their respective list nodes. Used for efficiency.
     */
    private Map<T, ListNode<T>> map;
    
    /**
     * The storage/lookup table.
     */
    private ListNode<T>[] storage;
    
    /**
     * The cached size of this heap.
     */
    private int size;
    
    /**
     * The cached minimum priority of this queue.
     */
    private int minimumPriority = Integer.MAX_VALUE;
    
    /**
     * Constructs an empty heap.
     * 
     * @param maxPriority the maximum priority this heap allows. 
     */
    public BucketHeap(final int maxPriority) {
        this.storage = new ListNode[maxPriority + 1];
        this.map = new HashMap<T, ListNode<T>>();
    }
    
    /**
     * Adds an element with priority <code>priority</code> in this heap, if not
     * already present.
     * 
     * @param element the element to add.
     * @param priority the priority to associate with the element.
     */
    public void add(T element, int priority) {
        if (map.containsKey(element)) {
            return;
        }
        
        ListNode<T> node = new ListNode<T>(element, priority);
        map.put(element, node);
        ++size;
        
        if (storage[priority] != null) {
            storage[priority].prev = node;
            node.next = storage[priority];
            storage[priority] = node;
        } else {
            storage[priority] = node;
        }
        
        if (minimumPriority > priority) {
            minimumPriority = priority;
        }
    }

    /**
     * Returns but does not remove the element with the highest (lowest key)
     * priority.
     * 
     * @return the element with the least priority.
     * 
     * @throws NoSuchElementException if this heap is empty.
     */
    public T min() {
        checkNotEmpty();
        return storage[minimumPriority].element;
    }

    /**
     * Removes and returns the element with the highest priority.
     * 
     * @return the element with the highest priority.
     * 
     * @throws NoSuchElementException if this heap is empty.
     */
    public T extractMinimum() {
        checkNotEmpty();
        
        ListNode<T> node = storage[minimumPriority];
        map.remove(node.element);
        size--;
        
        if (node.next != null) {
            node.next.prev = null;
            storage[minimumPriority] = node.next;
        } else {
            storage[minimumPriority] = null;
            
            for (int i = minimumPriority + 1; i != storage.length; ++i) {
                if (storage[i] != null) {
                    minimumPriority = i;
                    return node.element;
                }
            }
            
            minimumPriority = Integer.MAX_VALUE;
        }
        
        return node.element;
    }

    /**
     * Decreases the key of an element.
     * 
     * @param element the element whose key to decrease.
     * @param priority the new priority of <code>element</code>.
     */
    public void decreasePriority(T element, int priority) {
        ListNode<T> node = map.get(element);
        
        if (node == null || node.priority <= priority) {
            return;
        }
        
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        
        if (storage[node.priority] == node) {
            storage[node.priority] = node.next;
        }
        
        node.priority = priority;
        
        if (storage[priority] != null) {
            storage[priority].prev = node;
            node.next = storage[priority];
        }
        
        storage[priority] = node;
        
        if (minimumPriority > priority) {
            minimumPriority = priority;
        }
    }

    /**
     * Returns the amount of elements stored in this heap.
     * 
     * @return the amount of elements stored in this heap.
     */
    public int size() {
        return size;
    }

    /**
     * Clears this heap.
     */
    public void clear() {
        size = 0;
        map.clear();
        storage = new ListNode[storage.length];
    }

    /**
     * Returns the minimum priority of this heap.
     * 
     * @return the minimum priority of this heap. 
     */
    public int getMinimumPriority() {
        return minimumPriority;
    }

    /**
     * Spawns a new empty <code>BucketHeap</code>.
     * 
     * @return a new heap with the same implementation.
     */
    public IntegerPriorityQueue<T> newInstance() {
        return new BucketHeap<T>(storage.length);
    }
   
    /**
     * Checks whether this heap is empty, and throws an exception if it is.
     * 
     * @throws NoSuchElementException if this heap is empty.
     */
    private final void checkNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Reading from an empty heap.");
        }
    }
}
