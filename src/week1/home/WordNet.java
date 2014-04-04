package week1.home;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.*;

/**
 * Created by Superman Petrovich on 3/27/14.
 */
public class WordNet {
    private final ST<Integer, String> keys;
    private ST<String, Bag<Integer>> words;
    private final Digraph digraph;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms){
        keys = new ST<Integer, String>();
        words = new ST<String, Bag<Integer>>();

        In synsetsFile = new In(synsets);

        while (!synsetsFile.isEmpty()){
            String[] els = synsetsFile.readLine().split(",");
            String word = els[1];
            int id = Integer.parseInt(els[0]);

            for (String noun : word.split(" ")) {
                if(words.contains(noun)){
                    words.get(noun).add(id);
                }
                else{
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    words.put(noun, bag);
                }
            }

            keys.put(id, word);
        }

        In hypernymsFile = new In(hypernyms);

        digraph = new Digraph(keys.size());

        while (!hypernymsFile.isEmpty()){
            String[] els = hypernymsFile.readLine().split(",");

            for(int i = 1; i < els.length; i++){
                digraph.addEdge(Integer.parseInt(els[0]), Integer.parseInt(els[i]));
            }
        }

        //is acycled
        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }

        int rootNumber = 0;
        for(int i = 0; i < digraph.V(); i++){
            if(!digraph.adj(i).iterator().hasNext()){
                rootNumber++;
            }
        }

        if(rootNumber > 1){
            throw new IllegalArgumentException();
        }

        sap = new SAP(digraph);
    }

    public int distance(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }

        return sap.length(words.get(nounA), words.get(nounB));
    }

    public String sap(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }

        return keys.get(sap.ancestor(words.get(nounA), words.get(nounB)));
    }

    public Iterable<String> nouns(){
        return words.keys();
    }

    public boolean isNoun(String word){
        return words.contains(word);
    }
}
