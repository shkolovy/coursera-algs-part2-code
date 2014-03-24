package week1;

import edu.princeton.cs.algs4.Queue;

import java.util.Stack;

/**
 * Created by Superman Petrovich on 3/24/14.
 */
public class BreadthFirstSearchPath {
    private final boolean[] marked;
    private final int[] edgeTo;
    private int count;
    private int root;

    public BreadthFirstSearchPath(Graph graph, int v) {
        this.marked = new boolean[graph.verticesNumber()];
        this.edgeTo = new int[graph.verticesNumber()];
        root = v;
        bfs(graph, v);
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)){
            return null;
        }

        Stack<Integer> result = new Stack<Integer>();
        int el = v;

        while (el != root){
            result.add(el);
            int a = edgeTo[el];
            el = a;
        }

        result.add(root);

        return result;
    }

    public int count(){
        return count;
    }

    private void bfs(Graph graph, int v){
        Queue<Integer> queued = new Queue<Integer>();
        queued.enqueue(v);
        marked[v] = true;
        count++;

        while (!queued.isEmpty()){
            int el = queued.dequeue();

            for(int w : graph.adjacent(el)){
                if(!marked[w]){
                    count++;
                    queued.enqueue(w);
                    edgeTo[w] = el;
                    marked[w] = true;
                }
            }
        }
    }
}
