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
     * The matrix of tiles in this node..
     */
    private byte[][] m;
    
    /**
     * The x-coordinate of the zero tile.
     */
    private byte x;
    
    /**
     * The y-coordinate of the zero tile.
     */
    private byte y;
    
    /**
     * Creates an initial puzzle node.
     * 
     * @param n the dimension of this puzzle node.
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
    
    /**
     * Copy-constructs a new node from <code>node</code>.
     * 
     * @param node the node to copy.
     */
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
    
    @Override
    public Iterator<PuzzleNode> iterator() {
        return new PuzzleNodeIterator();
    }
    
    @Override
    public boolean equals(Object o) {
        if ((o instanceof PuzzleNode) == false) {
            return false;
        }
        
        final PuzzleNode other = (PuzzleNode) o;
        
        if (other.getDimension() != this.getDimension()) {
            return false;
        }
        
        final int n = this.getDimension();
        
        for (int y = 0; y != n; ++y) {
            for (int x = 0; x != n; ++x) {
                if (this.m[y][x] != other.m[y][x]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        final int n = m.length;
        int i = 1;
        int hash = 0;
        
        for (int y = 0; y != n; ++y) {
            for (int x = 0; x != n; ++x, ++i) {
                hash += m[y][x] * i;
            }
        }
        
        return hash;
    }
    
    public byte get(final int x, final int y) {
        return m[y][x];
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
        final int fieldLength = 
                Math.max(5, (int)(Math.floor(Math.log10(maxNumber))) + 1);
        
        final StringBuilder sb = new StringBuilder();
        final StringBuilder ALL = new StringBuilder(8192);
        
        String smallBar = "+-";
        String filler = "| ";
        
        for (int i = 0; i != fieldLength + 1; ++i) {
            smallBar += '-';
            filler += ' ';
        }
        
        for (int i = 0; i != m.length; ++i) {
            sb.append(smallBar);
        }
        
        sb.append('+')
          .append('\n');
        
        final String horizontalBar = sb.toString();
        
        for (int yy = 0; yy != m.length; ++yy) {
            ALL.append(horizontalBar);;
            
            for (int xx = 0; xx != m.length; ++xx) {
                ALL.append(filler);
            }
            
            ALL.append("|\n");
            
            for (int xx = 0; xx != m.length; ++xx) {
                int fl;
                
                if (m[yy][xx] == 0) {
                    fl = 1;
                } else {
                    fl = (int)(Math.floor(Math.log10(m[yy][xx]))) + 1; 
                }
                
                int tmp = fieldLength - fl;
                int after = tmp / 2;
                int before = tmp - after;
                String skip = "";
                String skip2 = "";
                
                for (int i = 0; i != before; ++i) {
                    skip += ' ';
                }
                
                for (int i = 0; i != after; ++i) {
                    skip2 += ' ';
                }
                
                ALL.append("| ")
                   .append(String.format(skip + "%d" + skip2 + " ", m[yy][xx]));
            }
            
            ALL.append("|\n");
            
            for (int xx = 0; xx != m.length; ++xx) {
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
        
        PuzzleNodeIterator() {
            final PuzzleNode node = PuzzleNode.this;
            
            neighbourList = new ArrayList<PuzzleNode>(4);
            
            PuzzleNode tmp = node.moveUp();
            
            if (tmp != null) {
                neighbourList.add(tmp);
            }
            
            tmp = node.moveRight();
            
            if (tmp != null) {
                neighbourList.add(tmp);
            }
            
            tmp = node.moveDown();
            
            if (tmp != null) {
                neighbourList.add(tmp);
            }
            
            tmp = node.moveLeft();
            
            if (tmp != null) {
                neighbourList.add(tmp);
            }
            
            iterator = neighbourList.iterator();
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
