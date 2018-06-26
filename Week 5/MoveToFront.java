/*
 * public class MoveToFront {
 // apply move-to-front encoding, reading from standard input and writing to standard output
 public static void encode()
 
 // apply move-to-front decoding, reading from standard input and writing to standard output
 public static void decode()
 
 // if args[0] is '-', apply move-to-front encoding
 // if args[0] is '+', apply move-to-front decoding
 public static void main(String[] args)
 }
 */
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class MoveToFront
{
    public static void encode()
    {
        int ord[] = new int[256];
        for(int i = 0; i < 256; i++)
            ord[i] = i;
        while(!BinaryStdIn.isEmpty())
        {
            int c = BinaryStdIn.readChar();
            for(int i = 0;i < 256;i ++)
            {
                if ( c == ord[i] )
                {
                    BinaryStdOut.write(i,8);
                    for(int k = i; k > 0 ; k--)
                        ord[k] = ord[ k - 1 ];
                    ord[0] = c;
                    break;
                }
            }
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }
    public static void decode()
    {
        int ord[] = new int[256];
        for(int i = 0; i < 256; i++)
            ord[i] = i;
        while(!BinaryStdIn.isEmpty())
        {
            int j = BinaryStdIn.readChar();
            BinaryStdOut.write(ord[j],8);
            int t = ord[j];
            for(int k = j; k > 0 ; k--)
                ord[k] = ord[ k - 1 ];
            ord[0] = t;
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }
    public static void main(String[] args)
    {
        if( args[0].equals("+"))
            decode();
        else if( args[0].equals("-"))
            encode();
    }
}