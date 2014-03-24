package week1;

import edu.princeton.cs.introcs.In;

import java.util.LinkedList;

/**
 * Created by Superman Petrovich on 3/24/14.
 */
public class DirectedGraph {
    private final int V;
    private int E;
    private final LinkedList<Integer>[] adj;

    public DirectedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public DirectedGraph(int v) {
        V = v;
        adj = new LinkedList[v];

        for(int i = 0; i < V; i++){
            adj[i] = new LinkedList<Integer>();
        }
    }

    public void addEdge(int v, int w){
        adj[v].add(w);
    }

    //vertices pointing from v
    public Iterable<Integer> adjacent(int v){
        return adj[v];
    }

    public int verticesNumber(){
        return V;
    }

    public int edgesNumber(){
        return E;
    }

//    public DirectedGraph reverse{
//
//    }
}
