import week1.DepthFirstSearchPath;
import week1.Graph;
import edu.princeton.cs.introcs.In;
/**
 * Created by Superman Petrovich on 3/21/14.
 */
public class Main {
    public static void main(String[] args){
        Graph g = new Graph(new In("d:\\Code\\Playground\\Java\\CourseraAlgsPart2\\src\\tinyG.txt"));
        Iterable<Integer> adj = g.adjacent(0);

        DepthFirstSearchPath dfs = new DepthFirstSearchPath(g, 0);
        Iterable<Integer> p1 = dfs.pathTo(3);
        int count = dfs.count();
    }
}
