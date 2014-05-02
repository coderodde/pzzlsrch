package net.coderodde.pzzlsrch;

import java.util.Random;
import net.coderodde.pzzlsrch.model.PuzzleNode;

/**
 * This class contains some utilities.
 * 
 * @author Rodion Efremov
 * @version 1.0
 */
public class Utils {
    
    /**
     * Generates a puzzle node with distance from the solution node not more
     * than <code>steps</code> steps.
     * 
     * @param steps the amount of steps to generate.
     * @param dimension the dimension of the result node.
     * @param r the pseudo-random number generator.
     * 
     * @return a puzzle node.
     */
    public static final PuzzleNode 
        getRandomPuzzleNode(int steps,    
                            final int dimension, 
                            final Random r) {
        steps = Math.max(0, steps);
        PuzzleNode pn = new PuzzleNode(Math.max(2, dimension));
        
        for (int i = 0; i != steps; ++i) {
            pn = nextNotNull(pn, r);
        }
        
        return pn;
    } 
        
    /**
     * Prints a fancy title bar with text.
     * 
     * @param text the text to print.
     */
    public static final void title1(final String text) {
        bar(text, '*');
    }
    
    /**
     * Prints another fancy title bar with text.
     * 
     * @param text the text to print.
     */
    public static final void title2(final String text) {
        bar(text, '-');
    }
    
    /**
     * Checks whether <code>o</code> is <code>null</code>; if it is
     * an exception is thrown.
     * 
     * @param o the reference to check.
     * @param msg the message to pass in case an exception is thrown.
     * 
     * @throws NullPointerException if <code>o</code> is <code>null</code>.
     */
    public static final void checkNotNull(final Object o, final String msg) {
        if (o == null) {
            throw new NullPointerException(msg);
        }
    }
    
    /**
     * Checks whether the two puzzle nodes have same dimension.
     * 
     * @param pn1 the first puzzle node.
     * @param pn2 the second puzzle node.
     * 
     * @throws IllegalArgumentException in case the dimension do not equal.
     */
    public static final void checkDimensions
        (final PuzzleNode pn1, final PuzzleNode pn2) {
        if (pn1.getDimension() != pn2.getDimension()) {
            throw new IllegalArgumentException(
                    "Nodes' dimensions do not match.");
        }
    }
    
    /**
     * Returns a non-null neighbor node of <code>source</code>.
     * 
     * @param source the source node.
     * @param r the pseudo-random number generator.
     * 
     * @return a neighbor node of <code>source</code>. 
     */
    private static final PuzzleNode 
        nextNotNull(final PuzzleNode source, final Random r) {
        PuzzleNode next = null;
        
        do {
            next = next(source, r);
        } while (next == null);
        
        return next;
    }
        
    /**
     * Moves in the random direction an input node.
     * 
     * @param source the source node.
     * @param r the pseudo-random number generator.
     * 
     * @return the next node, might be <code>null</code>.
     */
    private static final PuzzleNode 
        next(final PuzzleNode source, final Random r) {
        switch (r.nextInt(4)) {
            case 0:
                return source.moveUp();
            case 1:
                return source.moveRight();
            case 2:
                return source.moveDown();
            case 3:
                return source.moveLeft();
            default:
                throw new IllegalStateException("This must not happen.");
        }       
    }
        
    /**
     * The implementation of <code>title1</code> and <code>title2</code>.
     * 
     * @param text the text to print.
     * @param c the character used to print the bar.
     */
    private static final void bar(final String text, char c) {
        final int bars = 80 - text.length() - 2;
        final int left = Math.max(0, bars / 2);
        final int right = Math.max(0, bars - left);
        final StringBuilder sb = new StringBuilder(80);
        
        for (int i = 0; i != left; ++i) {
            sb.append(c);
        }
        
        sb.append(' ')
          .append(text)
          .append(' ');
        
        for (int i = 0; i != right; ++i) {
            sb.append(c);
        }
        
        System.out.println(sb);
    }
}
