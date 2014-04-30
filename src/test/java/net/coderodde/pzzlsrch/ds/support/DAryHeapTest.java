package net.coderodde.pzzlsrch.ds.support;

import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * This class test the d-ary heap.
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public class DAryHeapTest {
    
    private IntegerPriorityQueue<Integer> heap = new DAryHeap<Integer>(3);

    @Test
    public void testAdd() {
        heap.clear();
        
        for (int i = 100; i != 0; --i) {
            heap.add(i, i);
        }
        
        assertEquals((Integer) 1, heap.min());
        
        for (int i = 1; i != heap.size() + 1; ++i) {
            assertEquals((Integer) i, heap.extractMinimum());
        }
        
        System.out.println("testAdd");
    }

    @Test
    public void testMin() {
        heap.clear();
        
        heap.add(10, 10);
        
        assertEquals((Integer) 10, heap.min());
        
        heap.add(11, 11);
        
        assertEquals((Integer) 10, heap.min());
        
        heap.add(9, 9);
        
        assertEquals((Integer) 9, heap.min());
        
        heap.add(1000, 8);
        
        assertEquals((Integer) 1000, heap.min());
        System.out.println("testMin");
    }

    @Test
    public void testDecreasePriority() {
        heap.clear();
        
        for (int i = 0; i != 1000; ++i) {
            heap.add(i, i);
        }
        
        for (int i = 0; i != 1000; ++i) {
            heap.decreasePriority(i, -i);
        }
        
        for (int i = 999; i > -1; --i) {
            assertEquals((Integer) i, heap.extractMinimum());
        }
        
        assertEquals(0, heap.size());
        System.out.println("testDecreasePriority");
    }

    @Test
    public void testSize() {
        heap.clear();
        
        for (int i = 0; i != 1000; ++i) {
            assertEquals(i, heap.size());
            heap.add(i, i);
        }
        
        heap.clear();
        
        assertEquals(0, heap.size());
        
        heap.clear();
        
        assertEquals(0, heap.size());
        System.out.println("testSize");
    }
}