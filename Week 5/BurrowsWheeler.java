/*
 * public class BurrowsWheeler {
 // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
 public static void transform()
 
 // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
 public static void inverseTransform()
 
 // if args[0] is '-', apply Burrows-Wheeler transform
 // if args[0] is '+', apply Burrows-Wheeler inverse transform
 public static void main(String[] args)
 }
 */
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler
{
    public static void transform()
    {
        String s = BinaryStdIn.readString();
        BinaryStdIn.close();
        char[] c = s.toCharArray();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int l = s.length();
        for(int i = 0; i < l; i++)
        {
            if ( csa.index(i) == 0 )
                BinaryStdOut.write(i);
        }
        for(int i = 0; i < l; i++)
        {
            int index = csa.index(i);
            if( index == 0)
                BinaryStdOut.write( c[l - 1],8);
            else
                BinaryStdOut.write( c[csa.index(i) - 1 ],8 );
        }
        BinaryStdOut.close();
    }
    public static void inverseTransform()
    {
        int R = 256;
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        BinaryStdIn.close();
        char[] t = s.toCharArray();
        int n = t.length;
        int[] count = new int[R + 1];
        int[] next = new int[n];
        for(int i = 0; i < n; i++)
            count[t[i] + 1]++;
        for(int r = 0; r < R; r++)
            count[r + 1] += count[r];
        for(int i = 0 ; i < n; i++)
            next[count[t[i]]++] = i;
        int cur = first;
        int x = 0;
        while( x < n - 1)
        {
            cur = next[cur];
            BinaryStdOut.write( t[cur]) ;
            x++;
        }
        BinaryStdOut.write(t[next[cur]]);
        BinaryStdOut.close();
    }
    public static void main(String[] args)
    {
        if ( args[0].equals("-"))
            transform();
        else if( args[0].equals("+") )
            inverseTransform();
    }
}
