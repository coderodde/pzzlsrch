package net.coderodde.pzzlsrch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static net.coderodde.pzzlsrch.Utils.title1;
import static net.coderodde.pzzlsrch.Utils.title2;
import net.coderodde.pzzlsrch.ds.IntegerPriorityQueue;
import net.coderodde.pzzlsrch.ds.support.BucketHeap;
import net.coderodde.pzzlsrch.ds.support.DAryHeap;
import net.coderodde.pzzlsrch.model.PuzzleNode;
import net.coderodde.pzzlsrch.solvers.HeuristicFunction;
import net.coderodde.pzzlsrch.solvers.Solver;
import net.coderodde.pzzlsrch.solvers.support.BFSSolver;
import net.coderodde.pzzlsrch.solvers.support.BidirectionalBFSSolver;
import net.coderodde.pzzlsrch.solvers.support.BidirectionalHeuristicBFSSolver;
import net.coderodde.pzzlsrch.solvers.support.DisplacementHeuristicFunction;
import net.coderodde.pzzlsrch.solvers.support.HeuristicBFSSolver;
import net.coderodde.pzzlsrch.solvers.support.ManhattanHeuristicFunction;
import net.coderodde.pzzlsrch.solvers.support.ManhattanHeuristicFunction2;

public class Demo {
    
    public static final void main(final String... args) {
//        profilePriorityQueues();
        profileSolvers();
    }
    
    private static final void profileSolvers() {
        title1("Profiling puzzle solvers");
        final long SEED = 1398780721825L; //System.currentTimeMillis();
        final Random r = new Random(SEED);
        final int STEPS = 90; //r.nextInt(50) + 1;
        final PuzzleNode source = Utils.getRandomPuzzleNode(STEPS, 4, r);
        System.out.println("Seed: " + SEED);
        
        List<List<PuzzleNode>> pathList = new ArrayList<List<PuzzleNode>>();
        
        Solver<PuzzleNode> solver = new BFSSolver<PuzzleNode>();
        
//        pathList.add(profile(source, solver));
        
//        solver = new BidirectionalBFSSolver<PuzzleNode>();
//        
//        pathList.add(profile(source, solver));
//        
//        HeuristicFunction<PuzzleNode> hf = 
//                new DisplacementHeuristicFunction();
//        
//        solver = new HeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(hf)
//                .withPriorityQueue(new DAryHeap(6));
//        
//        pathList.add(profileHeuristic(source, solver));
//        
//        hf = new ManhattanHeuristicFunction();
//        
//        solver = new HeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(hf)
//                .withPriorityQueue(new DAryHeap(6));
//        
//        pathList.add(profileHeuristic(source, solver));
//        
//        hf = new ManhattanHeuristicFunction2(16);
//        
//        solver = new HeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(hf)
//                .withPriorityQueue(new DAryHeap(6));
//        
//        pathList.add(profileHeuristic(source, solver));
//        
//        solver = new BidirectionalHeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(new DisplacementHeuristicFunction());
//        
//        pathList.add(profileHeuristic(source, solver));
//        
//        solver = new BidirectionalHeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(new DisplacementHeuristicFunction())
//                .withPriorityQueue(new BucketHeap<PuzzleNode>(100));
//        
//        pathList.add(profileHeuristic(source, solver));
//        
//        solver = new BidirectionalHeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(new ManhattanHeuristicFunction());
//        
//        pathList.add(profileHeuristic(source, solver));
//                
//        solver = new BidirectionalHeuristicBFSSolver<PuzzleNode>()
//                .withHeuristicFunction(new ManhattanHeuristicFunction2(16));
//        
//        pathList.add(profileHeuristic(source, solver));
        
        final HeuristicFunction<PuzzleNode> dhf =
                new DisplacementHeuristicFunction();
        
        final HeuristicFunction<PuzzleNode> mhf =
                new ManhattanHeuristicFunction();
        
        
        profileHeuristicWithHeap(source,
                                 new HeuristicBFSSolver()
                                        .withHeuristicFunction(dhf),
                                 new BucketHeap<PuzzleNode>(100));
        
        profileHeuristicWithHeap(source,
                                 new HeuristicBFSSolver()
                                        .withHeuristicFunction(mhf),
                                 new BucketHeap<PuzzleNode>(100));
        
        profileHeuristicWithHeap(source,
                                 new HeuristicBFSSolver()
                                        .withHeuristicFunction(dhf),
                                 new DAryHeap<PuzzleNode>(6));
        
        profileHeuristicWithHeap(source,
                                 new HeuristicBFSSolver()
                                        .withHeuristicFunction(mhf),
                                 new DAryHeap<PuzzleNode>(6));
    }
    
    private static final void profileHeuristicWithHeap
            (final PuzzleNode source,
             final Solver<PuzzleNode> solver,
             final IntegerPriorityQueue<PuzzleNode> queue) {
     
        title2("" + solver.getClass().getSimpleName() + " with " +
               queue.getClass().getSimpleName());
        
        solver.withPriorityQueue(queue.newInstance());
        
        long ta = System.currentTimeMillis();
        
        List<PuzzleNode> path = 
                solver.search(source, new PuzzleNode(source.getDimension()));
        
        long tb = System.currentTimeMillis();
        
        System.out.println("Time: " + (tb - ta) + " ms.");
        System.out.println("Path length: " + (path.size() - 1) + " edges.");
    }
    
    private static final List<PuzzleNode> profileHeuristic
        (final PuzzleNode source, final Solver<PuzzleNode> solver) {
        title2(solver.getClass().getSimpleName() + ", " + 
               solver.getHeuristicFunction().getClass().getSimpleName());

        long ta = System.currentTimeMillis();

        List<PuzzleNode> path = 
                solver.search(source, new PuzzleNode(source.getDimension()));

        long tb = System.currentTimeMillis();

        System.out.println("Time: " + (tb - ta) + " ms.");
        System.out.println("Solution length: " + (path.size() - 1) + " edges.");

        return path;
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
        
    private static final void profilePriorityQueues() {
        title1("Profiling priority queues");
        final long SEED = System.currentTimeMillis();
        
        List<int[]> results = new ArrayList<int[]>();
        results.add(profile(new DAryHeap<Integer>(2), new Random(SEED)));
        results.add(profile(new DAryHeap<Integer>(3), new Random(SEED)));
        results.add(profile(new DAryHeap<Integer>(4), new Random(SEED)));
        results.add(profile(new DAryHeap<Integer>(5), new Random(SEED)));
        results.add(profile(new DAryHeap<Integer>(6), new Random(SEED)));
        System.out.println("Priority sequences match: " + resultsMatch(results));
    }
    
    private static final boolean resultsMatch(List<int[]> r) {
        for (int i = 0; i < r.size() - 1; ++i) {
            if (r.get(i).length != r.get(i + 1).length) {
                return false;
            }
        }
        
        for (int i = 0; i != r.get(0).length; ++i) {
            for (int j = 0; j < r.size() - 1; ++j) {
                if (r.get(j)[i] != r.get(j + 1)[i]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static final int[]
        profile(final IntegerPriorityQueue<Integer> queue, final Random r) {
            String msg = "";
            
            if (queue instanceof DAryHeap) {
                msg = " : degree " + ((DAryHeap<Integer>) queue).getDegree();
            }
            
            title1(queue.getClass().getName() + msg);
            queue.clear();
            final int SIZE = 1000000;
            int[] array = new int[SIZE];
            
            for (int i = 0; i != SIZE; ++i) {
                array[i] = r.nextInt();
            }
            
            title2("add()");
            
            long ta = System.currentTimeMillis();
            
            for (int i : array) {
                queue.add(i, i);
            }
            
            long tb = System.currentTimeMillis();
            
            System.out.println("Time: " + (tb - ta) + " ms.");
            
            title2("decreasePriority()");
            
            ta = System.currentTimeMillis();
            
            for (int i : array) {
                queue.decreasePriority(i, r.nextInt());
            }
            
            tb = System.currentTimeMillis();
            
            System.out.println("Time: " + (tb - ta) + " ms.");
            
            ta = System.currentTimeMillis();
            
            title2("extractMinimum()");
            
            int index = 0;
            
            while (queue.size() > 0) {
                array[index++] = queue.getMinimumPriority();
                queue.extractMinimum();
            }
            
            tb = System.currentTimeMillis();
            
            System.out.println("Time: " + (tb - ta) + " ms.");
            
            return array;
        }
}
