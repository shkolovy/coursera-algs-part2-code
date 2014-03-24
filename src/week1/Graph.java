package week1;

import java.util.LinkedList;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.introcs.In;

/**
 * Created by Superman Petrovich on 3/21/14.
 */
public class Graph {
    private final int V;
    private int E;
    private final Bag<Integer>[] adj;

    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public Graph(int v) {
        V = v;
        E = 0;
        adj = (Bag<Integer>[])new Bag[v];

        for(int i = 0; i < V; i++){
            adj[i] = new Bag<Integer>();
        }
    }

    public int verticesNumber(){
        return V;
    }

    public int edgesNumber(){
        return E;
    }

    public void addEdge(int v, int w){
        adj[v].add(w);
        adj[w].add(v);

        E++;
    }

    public Iterable<Integer> adjacent(int v){
        return adj[v];
    }
}
