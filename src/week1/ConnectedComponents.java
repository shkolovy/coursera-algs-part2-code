package week1;

/**
 * Created by Superman Petrovich on 3/24/14.
 */
//находит все кучки (групы)
public class ConnectedComponents {
    private final boolean[] marked;
    private int count;
    private int[] ids;

    public ConnectedComponents(Graph graph) {
        marked = new boolean[graph.verticesNumber()];
        ids = new int[graph.verticesNumber()];
        count = 0;

        for(int i = 0; i < graph.verticesNumber(); i ++){
            if(!marked[i]){
                dfs(graph, i);
                count++;
            }
        }
    }

    public boolean connected(int v, int w){
        return ids[v] == ids[w];
    }

    public int count(){
        return count;
    }

    private void dfs(Graph g, int s){
        ids[s] = count;
        marked[s] = true;

        for(int v: g.adjacent(s)){
            if(!marked[v]){
                dfs(g, v);
            }
        }
    }
}
