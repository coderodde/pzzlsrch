package net.coderodde.pzzlsrch.ds.support;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;

/**
 *
 * @author rodionefremov
 */
public class DAryHeap<T> implements IntegerPriorityQueue<T> {

    private static class Node<T> {
        T element;
        int index;
        int priority;
    }
    
    /**
     * The minimum capacity of this heap.
     */
    private static final int MINIMUM_CAPACITY = 128;
    
    /**
     * The default capacity of this heap.
     */
    private static final int DEFAULT_CAPACITY = 1024;
    
    /**
     * The default degree of this heap.
     */
    private static final int DEFAULT_DEGREE = 2;
    
    /**
     * The degree of this heap.
     */
    private final int d;
    
    /**
     * Storage array.
     */
    private Object[] storage;
    
    /**
     * Used as to keep the decrease operation O(log N).
     */
    private Map<T, Node<T>> map;
    
    /**
     * Used to avoid creating the arrays every time children indices are 
     * computed.
     */
    private int[] indices;
    
    /**
     * The amount of elements in this heap.
     */
    private int size;
    
    public DAryHeap(final int d, final int capacity) {
        checkD(d);
        checkCapacity(capacity);
        this.d = d;
        this.storage = new Object[capacity];
        this.indices = new int[d];
        this.map = new HashMap<T, Node<T>>(capacity);
    }
    
    public DAryHeap(final int d) {
        this(d, DEFAULT_CAPACITY);
    }
    
    public DAryHeap() {
        this(DEFAULT_DEGREE, DEFAULT_CAPACITY);
    }
    
    public void add(T element, int priority) {
    }

    public T min() {
        checkNotEmpty();
        return ((Node<T>) storage[0]).element;
    }

    public T extractMinimum() {
        checkNotEmpty();
        T ret = ((Node<T>) storage[0]).element;
        map.remove(ret);
        Node<T> node = (Node<T>) storage[map.size()];
        storage[map.size()] = null; // For garbage collecting.
        storage[0] = node;
        siftDown(0);
        return ret;
    }

    public void decreasePriority(T element, int priority) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private final void checkAndExpand() {
        if (map.size() == storage.length) {
            Object[] arr = new Object[3 * storage.length / 2];
            System.arraycopy(storage, 0, arr, 0, storage.length);
            storage = arr;
        }
    }
    
    private final void checkD(final int d) {
        if (d < 2) {
            throw new IllegalArgumentException("Degree is less than 2.");
        }
    }
    
    private final void checkCapacity(final int capacity) {
        if (capacity < MINIMUM_CAPACITY) {
            throw new IllegalArgumentException(
                    "Capacity (" + capacity + ") is below the minimum (" +
                    MINIMUM_CAPACITY + ").");
        }
    }
    
    private final void checkNotEmpty() {
        if (map.size() == 0) {
            throw new NoSuchElementException("Reading from an empty heap.");
        }
    }
    
    private void computeChildrenIndices(final int parentIndex) {
        for (int i = 0; i != d; ++i) {
            indices[i] = d * i + 1;
            
            if (indices[i] >= map.)
        }
    }
    
    private boolean siftDown(final int index) {
        return false;
    }
}
