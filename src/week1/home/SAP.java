package week1.home;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

/**
 * Created by Superman Petrovich on 3/27/14.
 */
public class SAP {
    private final Digraph digraph;

    public SAP(Digraph G){
        //to make it immutable
        digraph = new Digraph(G);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        BreadthFirstDirectedPaths vBf = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBf = new BreadthFirstDirectedPaths(digraph, w);

        return len(vBf, wBf);
    }

    public int length(int v, int w){
        BreadthFirstDirectedPaths vBf = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBf = new BreadthFirstDirectedPaths(digraph, w);

        return len(vBf, wBf);
    }

    public int ancestor(int v, int w){
        BreadthFirstDirectedPaths vBf = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBf = new BreadthFirstDirectedPaths(digraph, w);

        return anc(vBf, wBf);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        BreadthFirstDirectedPaths vBf = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBf = new BreadthFirstDirectedPaths(digraph, w);

        return anc(vBf, wBf);
    }

    private int len(BreadthFirstDirectedPaths vBf, BreadthFirstDirectedPaths wBf){
        int minDistance = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (vBf.hasPathTo(i) && wBf.hasPathTo(i)) {
                int dist = vBf.distTo(i) + wBf.distTo(i);
                if (minDistance < 0 || dist < minDistance) {
                    minDistance = dist;
                }
            }
        }

        return minDistance;
    }

    private int anc(BreadthFirstDirectedPaths vBf, BreadthFirstDirectedPaths wBf){
        int minDistance = -1;
        int minAnc = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (vBf.hasPathTo(i) && wBf.hasPathTo(i)) {
                int dist = vBf.distTo(i) + wBf.distTo(i);
                if (minDistance < 0 || dist < minDistance) {
                    minDistance = dist;
                    minAnc = i;
                }
            }
        }

        return minAnc;
    }
}
