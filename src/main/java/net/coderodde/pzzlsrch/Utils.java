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
        
    public static final void title1(final String text) {
        bar(text, '*');
    }
    
    public static final void title2(final String text) {
        bar(text, '-');
    }
    
    public static final void checkNotNull(final Object o, final String msg) {
        if (o == null) {
            throw new NullPointerException(msg);
        }
    }
    
    public static final void checkDimensions
        (final PuzzleNode pn1, final PuzzleNode pn2) {
        if (pn1.getDimension() != pn2.getDimension()) {
            throw new IllegalArgumentException(
                    "Nodes' dimensions do not match.");
        }
    }
    
    private static final PuzzleNode 
        nextNotNull(final PuzzleNode source, final Random r) {
        PuzzleNode next = null;
        
        do {
            next = next(source, r);
        } while (next == null);
        
        return next;
    }
        
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
