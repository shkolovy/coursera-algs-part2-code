package week1.home;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.*;

/**
 * Created by Superman Petrovich on 3/28/14.
 */
public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns){
        ST<String, Integer> dis = new ST<String, Integer>();
        int maxD = 0;
        int id = 0;

        for(int i = 0; i < nouns.length; i++){
            int t = 0;

            for(int j = 0; j < nouns.length; j++){
                if(j != i){
                    if(dis.contains(Integer.toString(i) + "," + Integer.toString(j))){
                        t += dis.get(Integer.toString(i) + "," + Integer.toString(j));
                    }
                    else if(dis.contains(Integer.toString(j) + "," + Integer.toString(i))){
                        t += dis.get(Integer.toString(j) + "," + Integer.toString(i));
                    }
                    else{
                        int d = wordnet.distance(nouns[i], nouns[j]);
                        dis.put(Integer.toString(i) + "," + Integer.toString(j), d);
                        t += d;
                    }
                }
            }

            if(maxD < t){
                maxD = t;
                id = i;
            }
        }

        return nouns[id];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
