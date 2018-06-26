/*
 * public class CircularSuffixArray {
 public CircularSuffixArray(String s)    // circular suffix array of s
 public int length()                     // length of s
 public int index(int i)                 // returns index of ith sorted suffix
 public static void main(String[] args)  // unit testing (required)
 }
 */
//import java.util.ArrayList;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class CircularSuffixArray
{
    private final int CUTOFF = 15; 
    private final int R = 256;
    private int n;
    private String s;
    private int[] index;
    public CircularSuffixArray(String s)
    {
        if( s == null)
            throw new java.lang.IllegalArgumentException();
        this.s = s;
        n = s.length();
        index = new int[n];
        /*circ = new String[n];
         StringBuilder sb = new StringBuilder(s);
         for(int i = 0; i < n; i++)
         {
         circ[i] = sb.toString();
         sb.append(sb.deleteCharAt(0));
         }
         */
        for(int i = 0; i < n; i++)
            index[i] = i;
        int[] aux = new int[n];
        sort(0, n-1, 0 , aux);
    }
    public int length()
    {
        return n;
    }
    public int index(int i)
    {
        if ( i < 0 || i > length() - 1)
            throw new java.lang.IllegalArgumentException();
        return index[i];
    }
    private int charAt(int i, int d) {
        assert d >= 0 && d <= s.length();   
        if (d < n-i) return s.charAt(d + i);        
        else
            if ( d - n + 1 == s.length())
            return -1;
        return s.charAt(d - n + i);             
    }
    private void sort(int lo, int hi, int d, int[] aux) {
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(index[i], d);
            count[c+2]++;
        }
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(index[i], d);
            aux[count[c+1]++] = index[i];
        }
        for (int i = lo; i <= hi; i++) 
            index[i] = aux[i - lo];
        for (int r = 0; r < R; r++)
            sort(lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(index[j], index[j-1], d); j--)
            exch(j, j-1);
    }
    private void exch(int i, int j) {
        int temp = index[i];
        index[i] = index[j];
        index[j] = temp;
    }
    private boolean less(int i, int j, int d) {
        for (int x = d; x < n; x++) 
            if (charAt(i, x) != charAt(j, x))
            return charAt(i, x) < charAt(j, x);  
        return false;
    }
    public static void main(String[] args)
    {
        String s = "";
        //while (!BinaryStdIn.isEmpty()) {
        s = BinaryStdIn.readString();
        //BinaryStdOut.write(c);
        //s += c;
        //}
        //String s = BinaryStdIn.readString();
        BinaryStdIn.close();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        //BinaryStdOut.write("i       Original Suffixes           Sorted Suffixes         index[i]");
        BinaryStdOut.flush();
    }
}
