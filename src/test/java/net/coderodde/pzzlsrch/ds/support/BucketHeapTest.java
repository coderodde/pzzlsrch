package net.coderodde.pzzlsrch.ds.support;

import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the bucket heap.
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public class BucketHeapTest {
    
    private IntegerPriorityQueue<Integer> heap = new BucketHeap<Integer>(1000);

    @Before
    public void init() {
        heap.clear();
    }
    
    /**
     * This method tests the <code>add</code> method.
     */
    @Test
    public void testAdd() {
        for (int i = 100; i != 0; --i) {
            heap.add(i, i);
        }
        
        assertEquals((Integer) 1, heap.min());
        
        for (int i = 1; i != heap.size() + 1; ++i) {
            assertEquals((Integer) i, heap.extractMinimum());
        }
    }

    /**
     * This method tests the <code>min</code> method.
     */
    @Test
    public void testMin() {
        heap.add(10, 10);
        
        assertEquals((Integer) 10, heap.min());
        
        heap.add(11, 11);
        
        assertEquals((Integer) 10, heap.min());
        
        heap.add(9, 9);
        
        assertEquals((Integer) 9, heap.min());
        
        heap.add(1000, 8);
        
        assertEquals((Integer) 1000, heap.min());
    }

    /**
     * This method test the <code>decreasePriority</code>.
     */
    @Test
    public void testDecreasePriority() {
        for (int i = 0; i != 1000; ++i) {
            heap.add(i, 1000);
        }
        
        for (int i = 0; i != 1000; ++i) {
            heap.decreasePriority(i, i);
        }
        
        for (int i = 0; i != 1000; ++i) {
            assertEquals((Integer) i, heap.extractMinimum());
        }
        
        assertEquals(0, heap.size());
    }

    /**
     * This method tests the <code>size</code> method.
     */
    @Test
    public void testSize() {
        for (int i = 0; i != 1000; ++i) {
            assertEquals(i, heap.size());
            heap.add(i, i);
        }
        
        heap.clear();
        
        assertEquals(0, heap.size());
        
        heap.clear();
        
        assertEquals(0, heap.size());
    }
}