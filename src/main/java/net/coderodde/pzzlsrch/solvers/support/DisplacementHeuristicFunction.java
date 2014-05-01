package net.coderodde.pzzlsrch.solvers.support;

import net.coderodde.pzzlsrch.model.PuzzleNode;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import static net.coderodde.pzzlsrch.Utils.checkDimensions;

/**
 *
 * @author rodionefremov
 */
public class DisplacementHeuristicFunction 
extends HeuristicFunction<PuzzleNode> {

    @Override
    public int get(final PuzzleNode pn) {
        return get(pn, target);
    }
    
    @Override
    public int get(final PuzzleNode pn1, final PuzzleNode pn2) {
        checkDimensions(pn1, pn2);
        
        final int N = pn1.getDimension();
        int h = 0;
        
        for (int y = 0; y != N; ++y) {
            for (int x = 0; x != N; ++x) {
                if (pn1.get(x, y) != pn2.get(x, y)) {
                    ++h;
                }
            }
        }
        
        return h;
    }

    @Override
    public HeuristicFunction<PuzzleNode> newInstance() {
        return new DisplacementHeuristicFunction();
    }
}
