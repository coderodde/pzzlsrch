package net.coderodde.pzzlsrch.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class models a node of a puzzle game.
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
    
    /**
     * Returns the iterator over this node's neighbor nodes.
     * 
     * @return the iterator over this node's neighbor nodes. 
     */
    @Override
    public Iterator<PuzzleNode> iterator() {
        return new PuzzleNodeIterator();
    }
    
    /**
     * Checks whether <code>o</code> represents the same node as this.
     * 
     * @param o the object to test.
     * 
     * @return <code>true</code> if <code>o</code> is a puzzle node and decodes
     * the same state as this.
     */
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
    
    /**
     * Computes the hash of this puzzle node.
     * 
     * @return the hash of this puzzle node.
     */
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
    
    /**
     * Reads a particular tile.
     * 
     * @param x the x-coordinate of the tile.
     * @param y the y-coordinate of the tile.
     * 
     * @return the tile at position <tt>(x, y)</tt>. 
     */
    public byte get(final int x, final int y) {
        return m[y][x];
    }
    
    /**
     * Returns a puzzle node generated by sliding the empty tile upwards.
     * 
     * @return a puzzle node or <code>null</code> if the empty tile slides away
     * from the matrix.
     */
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
    
    /**
     * Returns a puzzle node generated by sliding the empty tile downwards.
     * 
     * @return a puzzle node or <code>null</code> if the empty tile slides away
     * from the matrix.
     */
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
    
    /**
     * Returns a puzzle node generated by sliding the empty tile to the left.
     * 
     * @return a puzzle node or <code>null</code> if the empty tile slides away
     * from the matrix.
     */
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
    
    /**
     * Returns a puzzle node generated by sliding the empty tile to the right.
     * 
     * @return a puzzle node or <code>null</code> if the empty tile slides away
     * from the matrix.
     */
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
    
    /**
     * Returns the dimension of this puzzle node.
     * 
     * @return the dimension of this puzzle node.
     */
    public final int getDimension() {
        return m.length;
    }
    
    /**
     * Produces printable String representing this puzzle node.
     * 
     * @return a text-UI representation of this puzzle node.
     */
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
    
    /**
     * This inner class implements the puzzle node neighbor iterator.
     */
    private class PuzzleNodeIterator implements Iterator<PuzzleNode> {

        /**
         * The list of neighbor nodes.
         */
        private List<PuzzleNode> neighbourList;
        
        /**
         * The iterator of the neighbor list.
         */
        private Iterator<PuzzleNode> iterator;
        
        /**
         * Constructs an iterator over the enclosing nodes neighbors.
         */
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
        
        /**
         * Returns <code>true</code> if this iterator has more nodes to iterate,
         * <code>false</code> otherwise.
         * 
         * @return <code>true</code> if this iterator has more nodes to iterate,
         * <code>false</code> otherwise.
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next neighbor puzzle node, or throws 
         * <code>NoSuchElementException</code> if there is no more left.
         * 
         * @return the next puzzle node.
         * 
         * @throws NoSuchElementException if there is no more nodes to iterate.
         */
        public PuzzleNode next() {
            return iterator.next();
        }

        /**
         * Not implemented.
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "Removal on a node from the iterator makes no sense.");
        }
    }
    
    /**
     * Checks the dimension.
     * 
     * @param n the dimension to check.
     * 
     * @throws IllegalArgumentException if dimension is too small or too large.
     */
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
