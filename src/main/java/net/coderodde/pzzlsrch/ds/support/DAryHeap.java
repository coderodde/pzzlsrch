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
        if (map.containsKey(element)) {
            return;
        }
        
        Node<T> node = new Node<T>();
        node.element = element;
        node.priority = priority;
        node.index = size;
        storage[size] = node;
        map.put(element, node);
        siftUp(size++);
    }

    public T min() {
        checkNotEmpty();
        return ((Node<T>) storage[0]).element;
    }

    public T extractMinimum() {
        checkNotEmpty();
        T ret = ((Node<T>) storage[0]).element;
        map.remove(ret);
        Node<T> node = (Node<T>) storage[--size];
        storage[size] = null; // For garbage collecting.
        
        if (size != 0) {
            storage[0] = node;
            node.index = 0;
            siftDown(0);
        }
        
        return ret;
    }

    public void decreasePriority(T element, int priority) {
        Node<T> node = map.get(element);
        
        if (node == null 
                || node.index == 0
                || node.priority <= priority) {
            return;
        }
        
        node.priority = priority;
        siftUp(node.index);
    }

    /**
     * Returns the amount of elements in this heap.
     * 
     * @return the amount of elements in this heap.
     */
    public int size() {
        return size;
    }
    
    public void clear() {
        map.clear();
        
        for (int i = 0; i != size; ++i) {
            storage[i] = null;
        }
        
        size = 0;
    }
    
    private final void checkAndExpand() {
        if (size == storage.length) {
            Object[] arr = new Object[3 * size / 2];
            System.arraycopy(storage, 0, arr, 0, size);
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
        if (size == 0) {
            throw new NoSuchElementException("Reading from an empty heap.");
        }
    }
    
    private void computeChildrenIndices(final int index) {
        for (int i = 0; i != d; ++i) {
            indices[i] = d * index + i + 1;
            
            if (indices[i] >= size) {
                indices[i] = -1;
                break;
            }
        }
    }
    
    private int getParentIndex(final int index) {
        return (index - 1) / d;
    }
    
    private void siftUp(int index) {
        if (index == 0) {
            return;
        }
        
        int parentIndex = getParentIndex(index);
        
        for (;;) {
            Node<T> current = (Node<T>) storage[index];
            Node<T> parent = (Node<T>) storage[parentIndex];
            
            if (parent.priority > current.priority) {
                storage[index] = parent;
                storage[parentIndex] = current;
                
                ((Node<T>) storage[index]).index = index;
                ((Node<T>) storage[parentIndex]).index = parentIndex;
                
                index = parentIndex;
                parentIndex = getParentIndex(index);
            } else {
                return;
            }
            
            if (index == 0) {
                return;
            }
        }
    }
    
    private void siftDown(int index) {
        final int priority = ((Node<T>) storage[index]).priority;
        
        for (;;) {
            int minChildPriority = priority;
            int minChildIndex = -1;
            computeChildrenIndices(index);

            for (int i : indices) {
                if (i == -1) {
                    break;
                }

                int tentative = ((Node<T>) storage[i]).priority;

                if (minChildPriority > tentative) {
                    minChildPriority = tentative;
                    minChildIndex = i;
                }
            }

            if (minChildIndex == -1) {
                return;
            }
            
            // Swap nodes at positions 'index' and 'minChildIndex'.
            Node<T> tmp = (Node<T>) storage[index];
            storage[index] = storage[minChildIndex];
            storage[minChildIndex] = tmp;
            
            // Update the indices.
            ((Node<T>) storage[index]).index = index;
            ((Node<T>) storage[minChildIndex]).index = minChildIndex;
            
            // Go for the next iteration.
            index = minChildIndex;
        }
    }
    
    static void printarr(int... a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    
    public static void main(String... args) {
        System.out.println((-1) / 3);
    }
}
