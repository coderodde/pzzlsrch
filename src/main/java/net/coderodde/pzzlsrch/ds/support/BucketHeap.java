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

    static class ListNode<T> {
        T element;
        int priority;
        ListNode<T> prev;
        ListNode<T> next;
        
        ListNode(final T element, final int priority) {
            this.element = element;
            this.priority = priority;
        }
    }
    
    private Map<T, ListNode<T>> map;
    private ListNode<T>[] storage;
    private int size;
    private int minimumPriority = Integer.MAX_VALUE;
    
    public BucketHeap(final int maxPriority) {
        this.storage = new ListNode[maxPriority + 1];
        this.map = new HashMap<T, ListNode<T>>();
    }
    
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

    public T min() {
        checkNotEmpty();
        return storage[minimumPriority].element;
    }

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

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        map.clear();
        storage = new ListNode[storage.length];
    }

    public int getMinimumPriority() {
        return minimumPriority;
    }

    public IntegerPriorityQueue<T> newInstance() {
        return new BucketHeap<T>(storage.length);
    }
   
    private final void checkNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Reading from an empty heap.");
        }
    }
}
