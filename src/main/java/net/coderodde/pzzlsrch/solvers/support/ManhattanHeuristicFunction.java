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
public class ManhattanHeuristicFunction 
extends HeuristicFunction<PuzzleNode> {
    
    private Map<Byte, Point> map1 = new HashMap<Byte, Point>();
    private Map<Byte, Point> map2 = new HashMap<Byte, Point>();
    
    public ManhattanHeuristicFunction withTarget(final PuzzleNode pn) {
        final int N = pn.getDimension();
        map2.clear();
        
        for (int y = 0; y != N; ++y) {
            for (int x = 0; x != N; ++x) {
                map2.put(pn.get(x, y), new Point(x, y));
            }
        }
        
        return this;
    }
    
    public int get(final PuzzleNode pn) {
        final int N = pn.getDimension();
        map1.clear();
        
        for (int y = 0; y != N; ++y) {
            for (int x = 0; x != N; ++x) {
                map1.put(pn.get(x, y), new Point(x, y));
            }
        }
        
        int h = 0;
        
        for (final byte tile : map1.keySet()) {
            Point p1 = map1.get(tile);
            Point p2 = map2.get(tile);
            h += Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
        }
        
        return h;
    }
    
    public int get(final PuzzleNode pn1, final PuzzleNode pn2) {
        throw new UnsupportedOperationException(
                "This operation is not supported due to the " +
                "performance issues.");
    }

    @Override
    public HeuristicFunction<PuzzleNode> newInstance() {
        return new ManhattanHeuristicFunction();
    }
}
