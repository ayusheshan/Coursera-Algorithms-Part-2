/*
 * public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
 public              int numberOfTeams()                        // number of teams
 public Iterable<String> teams()                                // all teams
 public              int wins(String team)                      // number of wins for given team
 public              int losses(String team)                    // number of losses for given team
 public              int remaining(String team)                 // number of remaining games for given team
 public              int against(String team1, String team2)    // number of remaining games between team1 and team2
 public          boolean isEliminated(String team)              // is given team eliminated?
 public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
 */
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
public class BaseballElimination
{
    //private FlowNetwork flow;
    private int v;
    private int c;
    private int n;
    private int g[][];
    private ArrayList<String> team;
    private int r[];
    private int w[];
    private int l[];
    private ArrayList<String> team1 ;
    // private boolean trivial;
   // private FordFulkerson ff;
    private ArrayList<String> elem ;
    public BaseballElimination(String filename) 
    {
        //trivial = false;
        if(filename == null)
            throw new java.lang.IllegalArgumentException();
        In in = new In(filename);
        n = in.readInt();
        
        g = new int[n][n];
        team = new ArrayList<String>(n);
        r = new int[n];
        w = new int[n];
        l = new int[n];
        int i = 0;
        for(int k = 0; k < n; k++)
        {
            team.add(in.readString());
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt(); 
            for(int j = 0; j < n; j++)
                g[i][j] = in.readInt();       
            i++; 
        }
        int c = 0;
        for(i = 0; i < n - 1; i++)
            for(int j =  i + 1; j < n -1 ; j++)
            c++;
        this.c = c;
        v = 2 + this.c + n - 1;  
    }
    public int numberOfTeams()
    {
        return n;
    }
    public Iterable<String> teams()
    {
        return team;
    }
    public int wins(String team)
    {
       if(team == null || this.team.contains(team) == false)
            throw new java.lang.IllegalArgumentException();
        return w[this.team.indexOf(team)];
    }
    public int losses(String team)
    {
        if(team == null || this.team.contains(team) == false)
            throw new java.lang.IllegalArgumentException();
        return l[this.team.indexOf(team)];
    }
    public int remaining(String team)
    {
        if(team == null || this.team.contains(team) == false)
            throw new java.lang.IllegalArgumentException();
        return r[this.team.indexOf(team)];
    }
    public int against(String team1, String team2)
    {
        if(team1 == null || team2 == null || this.team.contains(team1) == false ||this.team.contains(team2) == false)
            throw new java.lang.IllegalArgumentException();
        return g[this.team.indexOf(team1)][ this.team.indexOf(team2)];
    }
    public boolean isEliminated(String team) 
    {
        if(team == null || this.team.contains(team) == false)
            throw new java.lang.IllegalArgumentException();
        elem =  new ArrayList<String>();
        for(int i =0 ; i < n; i++)
            if((wins(team) + remaining(team)) < w[i])
        {
            elem.add(this.team.get(i));
            return true;
        }
        team1 =  new ArrayList<String>();// new list of teams excluding the one passed as parameter

        for(int i = 0; i < n; i++)
            if(this.team.get(i).equals(team) == false)
            team1.add(this.team.get(i));
        FlowNetwork flow = new FlowNetwork(v);
        for(int i = 1; i <= n - 1; i ++ )
            flow.addEdge(new FlowEdge(i, v - 1, wins(team) + remaining(team) - w[this.team.indexOf(team1.get(i - 1))]));
        
        int x = n ;
        //double maxpos = 0;
        for(int i = 0; i < n - 1; i++)
            for(int j =  i + 1; j < n -1 ; j++)
        {
            //  maxpos += g[i][j];
            flow.addEdge(new FlowEdge(0, x, g[this.team.indexOf(team1.get(i))][this.team.indexOf(team1.get(j))]));
            flow.addEdge(new FlowEdge(x, i + 1, Double.POSITIVE_INFINITY));
            flow.addEdge(new FlowEdge(x, j + 1, Double.POSITIVE_INFINITY));             
            x++;
        }
        // System.out.println(flow);
        FordFulkerson ff = new FordFulkerson(flow, 0, v -1);
        int check = 0;
        for(int i = 1; i <= n -1 ; i++)
            if( ff.inCut(i) == true)
        {
            elem.add(team1.get(i - 1));
            check = 1;
        } 
        if(check == 1)
            return true;
        return false;
    }
    public Iterable<String> certificateOfElimination(String team)
    {
        if(team == null || this.team.contains(team) == false)
            throw new java.lang.IllegalArgumentException();
        if(isEliminated(team) == false)
         return null;
        return elem;
    }
}