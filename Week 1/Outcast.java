/*
 * public class Outcast {
 public Outcast(WordNet wordnet)         // constructor takes a WordNet object
 public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
 public static void main(String[] args)  // see test client below
 }
 */
//import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
//import java.util.ArrayList;
public class Outcast
{
    private WordNet w;
    public Outcast(WordNet wordnet)
    {
        w = wordnet;
    }
    public String outcast(String[] nouns)
    {
        String out = "";
         int max = 0;
        for(int i = 0; i < nouns.length; i++)
        {
           
            int dist = 0;
            for(int j = 0; j < nouns.length; j++)
            {
                if(!nouns[i].equals( nouns[j]))
                    dist += w.distance(nouns[i],nouns[j]);
            }
            if( dist > max)
            {
                out = nouns[i];
                max = dist;
            }
        }
            return out;
            /*ArrayList<String> arr = new ArrayList<String>();
             Iterator<String> i1 = w.nouns().iterator();
             while(i1.hasNext())
             {
             arr.add(i1.next());
             }
             String out = "";
             for(String x: nouns)
             {
             Iterator<String> i = arr.iterator();
             int max = 0;
             int t = 0;
             while(i.hasNext())
             {
             String cur = (i.next());
             t += w.distance(x, cur);
             }
             if( t > max)
             {
             max = t;
             out = x;
             }
             }
             return out; */
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