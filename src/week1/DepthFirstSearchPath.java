package week1;

import java.util.Stack;

/**
 * Created by Superman Petrovich on 3/24/14.
 */
public class DepthFirstSearchPath {
    private final boolean[] marked;
    private final int[] edgeTo;
    private int count;
    private int root;

    public DepthFirstSearchPath(Graph graph, int v) {
        marked = new boolean[graph.verticesNumber()];
        edgeTo = new int[graph.verticesNumber()];
        root = v;
        dfs(graph, v);
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

    private void dfs(Graph graph, int v){
        Stack<Integer> queued = new Stack<Integer>();
        queued.push(v);
        marked[v] = true;
        count++;

        while (!queued.isEmpty()){
            int el = queued.pop();

            for(int w : graph.adjacent(el)){
                if(!marked[w]){
                    count++;
                    queued.push(w);
                    edgeTo[w] = el;
                    marked[w] = true;
                }
            }
        }
        //с рекурсией
//        marked[v] = true;
//        count++;
//        for(int w : graph.adjacent(v)){
//            if(!marked[w]){
//                edgeTo[w] = v;
//                dfs(graph, w);
//            }
//        }
    }
}
