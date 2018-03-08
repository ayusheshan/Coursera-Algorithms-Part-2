/*
 * public class SAP {
 * 
 // constructor takes a digraph (not necessarily a DAG)
 public SAP(Digraph G)
 
 // length of shortest ancestral path between v and w; -1 if no such path
 public int length(int v, int w)
 
 // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
 public int ancestor(int v, int w)
 
 // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
 public int length(Iterable<Integer> v, Iterable<Integer> w)
 
 // a common ancestor that participates in shortest ancestral path; -1 if no such path
 public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
 
 // do unit testing of this class
 public static void main(String[] args)
 }
 */
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
//import java.util.Iterator;
public class SAP
{
    private Digraph x;
    private int anc,anc1;
    public SAP( Digraph g)
    {
        if( g == null)
            throw new java.lang.IllegalArgumentException();
        x = new Digraph( g );
        anc = -1;
        anc1 = -1;
    }
   /* private int root()
    {
     BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths( x, 0);
     Iterator<Integer> i = x.adj(0).iterator();
     while(i.hasNext())
     {
         if( x.outdegree(i.next()) == 0)*/
             
    public int length(int v, int w)
    {
        if( v < 0 || v > x.V() - 1 || w < 0 || w > x.V() - 1)
            throw new java.lang.IllegalArgumentException();
        
         java.util.Queue<Integer> q = new java.util.LinkedList<Integer>();
         BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths( x, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths( x, w);
        for(int i = 0; i < x.V(); i++)
        {
            if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i))
                q.add(i);
        }
        if(q.peek() == null)
        {
            anc = -1;
            return -1;
        }
        int shortest = Integer.MAX_VALUE;
        for(int i : q)
        {
            if( bfsv.distTo(i) + bfsw.distTo(i) < shortest)
            {
                shortest = bfsv.distTo(i) + bfsw.distTo(i);
                anc = i;
            }
        }
        if( anc == -1 )
            return -1;
        return shortest;
        /*BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths( x, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths( x, w);
        
        java.util.Iterator<Integer> i1 = x.adj(v).iterator();
        java.util.Queue<Integer> q1 = new java.util.LinkedList<Integer>();
        java.util.Iterator<Integer> i2 = x.adj(w).iterator();
        java.util.Queue<Integer> q2 = new java.util.LinkedList<Integer>();
        
        
        while( i1.hasNext() )
        {
            int ele = (int)i1.next();
            q1.add(ele);
        }
         while( i2.hasNext() )
        {
            int ele = (int)i2.next();
            q2.add(ele);
        }
         while( q1.peek() != null && q2.peek() != null)
         {
             if( q1.element() == q2.element() )
             {
                 min = bfs1.distTo(q1.element()) + bfs2.distTo(q1.element());
                 anc = q1.element();
             }*/
             
                 
         /*
        //java.util.Queue q2 = x.adj(w);
        int min = 9999;
        
        while( q1.peek() != null)
        {
                if( bfs2.distTo(q1.element()) < min )
                {
                    min = bfs2.distTo(q1.element());
                    anc = q1.element();
                }
            
            i = x.adj(q1.remove()).iterator();
            while( i.hasNext() )
            {
                int ele = (int)i.next();
                q1.add(ele);
            }
        }
        if( anc == -1)
            return -1;
        else if( anc != v)
            return min + bfs1.distTo(anc) - 2;
        else
            return min -2 ;*/
    }
    public int ancestor(int v, int w)
    {
        if( v < 0 || v > x.V() - 1 || w < 0 || w > x.V() - 1)
            throw new java.lang.IllegalArgumentException();
            
        length(v,w);
        return anc;
    }
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if( v == null || w == null)
            throw new java.lang.IllegalArgumentException();
        for(int i: v)
        {
            if( i < 0 || i > x.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }
        for(int i: w)
        {
            if( i < 0 || i > x.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(x,v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(x,w);
        
         java.util.Queue<Integer> q = new java.util.LinkedList<Integer>();
         for(int i = 0; i < x.V(); i++)
             if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i))
             q.add(i);
         
         int shortest = Integer.MAX_VALUE;
        for( int i : q)
        {
            if( bfsv.distTo(i) + bfsw.distTo(i) < shortest)
            {
                anc1 = i;
                shortest = bfsv.distTo(i) + bfsw.distTo(i);
            }
        }
        if(anc1 == -1)
            return -1;
        return shortest;
        /*Iterator<Integer> iv = v.iterator();
        Iterator<Integer> iw = w.iterator();
        int min = 9999;
        while( iv.hasNext()  )
        {
            int a = (int)iv.next();
            while( iw.hasNext() )
            {
                int b = (int)iw.next();
                int l = length(a,b);
                if(l < min)
                {
                    min = l;
                    anc1 = ancestor(a,b);
                }
            }
        }
        return min;*/
    }
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
         if( v == null || w == null)
            throw new java.lang.IllegalArgumentException();
        for(int i: v)
        {
            if( i < 0 || i > x.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }
        for(int i: w)
        {
            if( i < 0 || i > x.V() - 1)
                throw new java.lang.IllegalArgumentException();
        }
        return anc1;
    }
    public static void main(String[] args)
    {
        edu.princeton.cs.algs4.In in = new edu.princeton.cs.algs4.In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!edu.princeton.cs.algs4.StdIn.isEmpty()) 
        {
            int v = edu.princeton.cs.algs4.StdIn.readInt();
            int w = edu.princeton.cs.algs4.StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            edu.princeton.cs.algs4.StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}