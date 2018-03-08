/*
 *public class WordNet {
 * 
 // constructor takes the name of the two input files
 public WordNet(String synsets, String hypernyms)
 
 // returns all WordNet nouns
 public Iterable<String> nouns()
 
 // is the word a WordNet noun?
 public boolean isNoun(String word)
 
 // distance between nounA and nounB (defined below)
 public int distance(String nounA, String nounB)
 
 // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
 // in a shortest ancestral path (defined below)
 public String sap(String nounA, String nounB)
 
 // do unit testing of this class
 public static void main(String[] args)
 }
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.Map;
import java.util.Set;
import edu.princeton.cs.algs4.Topological;
import java.util.HashMap;
import java.util.HashSet;
public class WordNet
{
    // private int[] id;
    private Digraph g;
    private Map<String,Set<Integer>> map = new HashMap<String,Set<Integer>>();
    public WordNet(String synsets, String hypernyms)
    {
        if (synsets == null || hypernyms == null)
            throw new java.lang.IllegalArgumentException();
        
        
        int p = 0;
        In sc = new In(synsets);
        for(String line : sc.readAllLines())
        {
            String fields[] = line.split(",");
            for(String noun : fields[1].split(" "))
            {
                    if(map.get(noun) == null)
                    map.put(noun, new HashSet<Integer>());
                
                map.get(noun).add(Integer.parseInt(fields[0])); 
            }
            p++;
        }
           /* String str; StringBuilder temp = new StringBuilder();
            while((str = sc.readLine()) != null)
                temp.append( str);
            str = temp.toString();
            String[] fields = str.split(",");
            //id = new int[Integer.parseInt(fields[fields.length - 3])];
            
            for(int i = 1; i < fields.length; i += 2)
            {
                if(map.get(fields[i]) == null)
                    map.put(fields[i], new HashSet<Integer>());
                String[] fi = fields[i - 1].split(" ");
                map.get(fields[i]).add(Integer.parseInt(fi[fi.length - 1])); 
                p++;
            }
            /* for(int i = 0; i < fields.length; i += 2)
             id[i] = Integer.parseInt(fields[i]);*/
            
            g = new Digraph(p);
            //String str;
            sc = new In(hypernyms);
        for( String line : sc.readAllLines())
        {
            String[] fields = line.split(",");
            for(int i = 1; i < fields.length; i++)
            {
                 g.addEdge(Integer.parseInt(fields[0]),Integer.parseInt(fields[i]));
            }
        }
          /*  StringBuilder t = new StringBuilder();
            while(( str = sc.readLine()) != null)
                t.append( str);
            str = t.toString();
            String[] fields = str.split(",");
            
            for(int i = 0; i < fields.length; i++)
            {
                int j = i+1;
                while( j < fields.length && Integer.parseInt(fields[j]) != i + 1)
                    g.addEdge(i,Integer.parseInt(fields[j++]));
            }*/
            Topological tp = new Topological(g);
            if(!tp.hasOrder())
                throw new java.lang.IllegalArgumentException();
            }
    public Iterable<String> nouns()
    {
        return map.keySet();
    }
    public boolean isNoun(String word)
    {
        if(word == null)
            throw new java.lang.IllegalArgumentException();
        if(map.containsKey(word))
            return true;
        return false;
    }
    public int distance(String nounA, String nounB)
    {
        if( !isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        SAP obj = new SAP(g);
        return obj.length(map.get(nounA) , map.get(nounB));
    }
    public String sap(String nounA, String nounB)
    {
        if( !isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        SAP obj = new SAP(g);
        int anc = obj.ancestor(map.get(nounA), map.get(nounB));
        for( String s : map.keySet())
        {
            if((map.get(s)).contains(anc))
                return s;
        }
        return null;
    }
    public static void main(String args[])
    {
        
         //String filename = args[0];
          In sc = new In("hypernyms15Tree.txt");
        for(String line : sc.readAllLines())
        System.out.println(line);
         //WordNet w = new WordNet(filename,"");
         
    }
    }