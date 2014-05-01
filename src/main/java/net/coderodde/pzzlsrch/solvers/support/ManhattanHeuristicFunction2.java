package net.coderodde.pzzlsrch.solvers.support;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.coderodde.pzzlsrch.model.PuzzleNode;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;

/**
 *
 * @author Rodion Efremov
 * @version 1.0
 */
public class ManhattanHeuristicFunction2 
extends HeuristicFunction<PuzzleNode> {
    
    private int[] xarr;
    private int[] yarr;
    
    public ManhattanHeuristicFunction2(final int n) {
        this.xarr = new int[n];
        this.yarr = new int[n];
    }
    
    public ManhattanHeuristicFunction2 withTarget(final PuzzleNode pn) {
        final int N = pn.getDimension();
        
        for (int y = 0; y != N; ++y) {
            for (int x = 0; x != N; ++x) {
                byte tile = pn.get(x, y);
                xarr[tile] = x;
                yarr[tile] = y;
            }
        }
        
        return this;
    }
    
    public int get(final PuzzleNode pn) {
        final int N = pn.getDimension();
        int h = 0;
        
        for (int y = 0; y != N; ++y) {
            for (int x = 0; x != N; ++x) {
                byte tile = pn.get(x, y);
                h += Math.abs(xarr[tile] - x) + Math.abs(yarr[tile] - y);
            }
        }
        
        return h;
    }
    
    public int get(final PuzzleNode pn1, final PuzzleNode pn2) {
        throw new UnsupportedOperationException(
                "This operation is not supported due to performance issues.");
    }

    @Override
    public HeuristicFunction<PuzzleNode> newInstance() {
        return new ManhattanHeuristicFunction2(xarr.length);
    }
}
