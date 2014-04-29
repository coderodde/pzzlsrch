package net.coderodde.pzzlsrch.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class models a node of a puzzle.
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public class PuzzleNode implements Iterable<PuzzleNode> {
   
    private static final int MAX_DIMENSION = 11;
    
    /**
     * The matrix of this node.
     */
    private byte[][] m;
    
    private byte x;
    
    private byte y;
    
    /**
     * Creates an initial puzzle node.
     * 
     * @param n 
     */
    public PuzzleNode(final int n) {
        checkDimension(n);
        m = new byte[n][n];
        
        byte index = 1;
        for (int y = 0; y != n; ++y) {
            for (int x = 0; x != n; ++x, ++index) {
                if (index != n * n) {
                    m[y][x] = index;
                }
            }
        }
        
        x = (byte)(n - 1);
        y = (byte)(n - 1);
    }
    
    public PuzzleNode(final PuzzleNode node) {
        final int n = node.getDimension();
        m = new byte[n][n];
        
        for (int y = 0; y != n; ++y) {
            for (int x = 0; x != n; ++x) {
                m[y][x] = node.m[y][x];
            }
        }
        
        x = node.x;
        y = node.y;
    }
    
    public PuzzleNode moveUp() {
        if (y == 0) {
            return null;
        }
        
        final PuzzleNode node = new PuzzleNode(this);
        final byte tmp = node.m[y][x];
        node.m[y][x] = node.m[y - 1][x];
        node.m[y - 1][x] = tmp;
        node.y--;
        return node;
    }
    
    public PuzzleNode moveDown() {
        if (y == m.length - 1) {
            return null;
        }
        
        final PuzzleNode node = new PuzzleNode(this);
        final byte tmp = node.m[y][x];
        node.m[y][x] = node.m[y + 1][x];
        node.m[y + 1][x] = tmp;
        node.y++;
        return node;
    }
    
    public PuzzleNode moveLeft() {
        if (x == 0) {
            return null;
        }
        
        final PuzzleNode node = new PuzzleNode(this);
        final byte tmp = node.m[y][x];
        node.m[y][x] = node.m[y][x - 1];
        node.m[y][x - 1] = tmp;
        node.x--;
        return node;
    }
    
    public PuzzleNode moveRight() {
        if (x == m.length - 1) {
            return null;
        }
        
        final PuzzleNode node = new PuzzleNode(this);
        final byte tmp = node.m[y][x];
        node.m[y][x] = node.m[y][x + 1];
        node.m[y][x + 1] = tmp;
        node.x++;
        return node;
    }
    
    public final int getDimension() {
        return m.length;
    }
    
    @Override
    public String toString() {
        final int maxNumber = m.length * m.length - 1;
        final int fieldLength = ("" + maxNumber).length();
        final StringBuilder sb = new StringBuilder();
        final StringBuilder ALL = new StringBuilder(8192);
        
        String smallBar = "+-";
        String filler = "| ";
        
        for (int i = 0; i != fieldLength; ++i) {
            smallBar += '-';
            filler += ' ';
        }
        
        smallBar += '-';
        filler += ' ';
        
        for (int i = 0; i != m.length; ++i) {
            sb.append('+')
              .append(smallBar);
        }
        
        sb.append('+');
        
        final String horizontalBar = sb.toString();
        
        for (int y = 0; y != m.length; ++y) {
            ALL.append(horizontalBar)
               .append('\n');
            
            for (int x = 0; x != m.length; ++x) {
                ALL.append(filler);
            }
            
            ALL.append("|\n");
            
            for (int x = 0; x != m.length; ++x) {
                ALL.append("| ");
                ALL.append(String.format("% " + fieldLength + "d ", m[y][x]));
            }
            
            ALL.append("|\n");
            
            for (int x = 0; x != m.length; ++x) {
                ALL.append(filler);
            }
            
            ALL.append("|\n");
        }
        
        ALL.append(horizontalBar);
        return ALL.toString();
    }
    
    private class PuzzleNodeIterator implements Iterator<PuzzleNode> {

        private List<PuzzleNode> neighbourList;
        private Iterator<PuzzleNode> iterator;
        
        PuzzleNodeIterator(final PuzzleNode node) {
            neighbourList = new ArrayList<PuzzleNode>(4);
        }
        
        public boolean hasNext() {
            return iterator.hasNext();
        }

        public PuzzleNode next() {
            return iterator.next();
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "Removal on a node from the iterator makes no sense.");
        }
        
    }
    
    private final void checkDimension(final int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Dimension is less than 1.");
        }
        
        if (n > MAX_DIMENSION) {
            throw new IllegalArgumentException(
                "Dimension is larger than the maximum: " +
                n + " > " + MAX_DIMENSION + ".");
        }
    }
}
