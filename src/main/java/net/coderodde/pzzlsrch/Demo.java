package net.coderodde.pzzlsrch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import static net.coderodde.pzzlsrch.Utils.title1;
import static net.coderodde.pzzlsrch.Utils.title2;
import net.coderodde.pzzlsrch.model.PuzzleNode;
import net.coderodde.pzzlsrch.solvers.Solver;
import net.coderodde.pzzlsrch.solvers.support.BFSSolver;
import net.coderodde.pzzlsrch.solvers.support.BidirectionalBFSSolver;

public class Demo {
    
    public static final void main(final String... args) {
//        PuzzleNode node = new PuzzleNode(3);
//        Scanner scanner = new Scanner(System.in);
//        
//        for (;;) {
//            System.out.println(node);
//            System.out.print("> ");
//            String cmd = scanner.nextLine().trim();
//            
//            if (cmd.equalsIgnoreCase("u")) {
//                node = node.moveUp();
//            } else if (cmd.equalsIgnoreCase("r")) {
//                node = node.moveRight();
//            } else if (cmd.equalsIgnoreCase("d")) {
//                node = node.moveDown();
//            } else if (cmd.equalsIgnoreCase("l")) {
//                node = node.moveLeft();
//            } else if (cmd.equalsIgnoreCase("quit")) {
//                break;
//            }
//        }
//        
//        System.out.println("Bye!");
        title1("Profiling puzzle solvers");
        final long SEED = 1398780721825L; //System.currentTimeMillis();
        final Random r = new Random(SEED);
        final int STEPS = 30; //r.nextInt(50) + 1;
        final PuzzleNode source = Utils.getRandomPuzzleNode(STEPS, 4, r);
        System.out.println("Seed: " + SEED);
        
        List<List<PuzzleNode>> pathList = new ArrayList<List<PuzzleNode>>();
        
        Solver<PuzzleNode> solver = new BFSSolver<PuzzleNode>();
        
        pathList.add(profile(source, solver));
        
        solver = new BidirectionalBFSSolver<PuzzleNode>();
        
        pathList.add(profile(source, solver));
    }
    
    private static final List<PuzzleNode> 
        profile(final PuzzleNode source, final Solver<PuzzleNode> solver) {
        title2(solver.getClass().getName());
        
        long ta = System.currentTimeMillis();
        
        List<PuzzleNode> path = 
                solver.search(source, new PuzzleNode(source.getDimension()));
        
        long tb = System.currentTimeMillis();
        
        System.out.println("Time: " + (tb - ta) + " ms.");
        System.out.println("Solution length: " + (path.size() - 1) + " edges.");
        
        return path;
    }
}
