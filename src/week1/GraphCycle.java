package week1;

/**
 * Created by Superman Petrovich on 3/26/14.
 */
public class GraphCycle {
    private final boolean[] marked;
    private boolean hasCycle;

    public GraphCycle(Graph g) {
        this.marked = new boolean[g.verticesNumber()];

        for(int i = 0; i < g.verticesNumber(); i++){
            if(!hasCycle && !marked[i]){
                dfs(g, i, i);
            }
        }
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    private void dfs(Graph g, int v, int w){
        marked[v] = true;

        for(int u: g.adjacent(v)){
            if(!marked[u]){
                dfs(g, u, w);
            }
            else if(u != w){
                hasCycle = true;
            }
        }
    }
}
