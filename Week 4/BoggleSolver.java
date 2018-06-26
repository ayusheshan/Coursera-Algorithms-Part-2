/*
 public class BoggleSolver
 {
 // Initializes the data structure using the given array of strings as the dictionary.
 // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
 public BoggleSolver(String[] dictionary)
 
 // Returns the set of all valid words in the given Boggle board, as an Iterable.
 public Iterable<String> getAllValidWords(BoggleBoard board)
 
 // Returns the score of the given word if it is in the dictionary, zero otherwise.
 // (You can assume the word contains only the uppercase letters A through Z.)
 public int scoreOf(String word)
 }
 */
import java.util.Set;
import java.util.HashSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BoggleSolver
{
    private static final int R = 26;
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isWord;
    }
    private Node root;
    private boolean[][] marked;
    public BoggleSolver(String[] dictionary)
    {
        if (dictionary == null)
            throw new java.lang.IllegalArgumentException();
        for(String word: dictionary)
            add(word);
    }
    
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        if (board == null)
            throw new java.lang.IllegalArgumentException();
        int rows = board.rows();
        int cols = board.cols();
       
        Set<String> arr = new HashSet<String>();
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
        {  
            String string ="";
            marked = new boolean[rows][cols];  
            char cur = board.getLetter(i, j);
            string += cur;
            if(cur == 'Q')
                string += "U";
            addAllValidWords(board, i ,j, string, marked, arr);
        }
        
        return arr;
    }
    public int scoreOf(String word)
    {
        if( word == null)
            throw new java.lang.IllegalArgumentException();
        int l = word.length();
        if(!contains(word) || l <= 2)
            return 0;
        if( l ==3 || l == 4)
            return 1;
        if(l == 5)
            return 2;
        if(l == 6)
            return 3;
        if( l == 7)
            return 5;
        else
            return 11;
    }
    private void addAllValidWords(BoggleBoard board, int i, int j,String word, boolean marked[][], Set<String> arr)
    {
        if(!isValidPrefix(word))
            return;
        if(word.length() > 2 && contains(word))
            arr.add(word);
        marked[i][j] = true;
        
        for(int row = i - 1 ; row <= i + 1; row++)
            for(int col = j - 1; col <= j + 1; col++)
        {
            if(isValidVertex(board, row, col) && !marked[row][col])
            {
                char c = board.getLetter(row, col);
                if(c == 'Q')
                addAllValidWords(board, row, col, word + c + 'U', marked, arr);
                else
                    addAllValidWords(board, row, col, word + c, marked, arr);
            }
        }
        marked[i][j] = false;
    }
    private boolean isValidVertex(BoggleBoard board, int i, int j)
    {
        return i < board.rows() && i >=0 && j < board.cols() && j >= 0;
    }
    private void add(String key)
    {
        root = add(root, key, 0);
    }
    
    private Node add(Node x, String key, int d) {
        if (x == null) 
            x = new Node();
        if (d == key.length()) 
        {
            x.isWord = true;
            return x;
        }
        else
        {
            char c = key.charAt(d);
            x.next[c - 65] = add(x.next[c - 65], key, d+1);
        }
        return x;
    }
    private boolean isValidPrefix(String prefix)
    {
        Node x = root;
        for(int i = 0; i < prefix.length() && x != null; i++)
            x = x.next[prefix.charAt(i) - 65];
        return x != null;
    }

    private boolean contains(String key)
    {
        Node x = get(root, key, 0);
        if (x == null) 
            return false;
        return x.isWord;
    }
    
    private Node get(Node x, String key, int d)
    {
        if (x == null) 
            return null;
        if (d == key.length())
            return x;
        char c = (key.charAt(d));
        return get(x.next[c - 65], key, d+1);
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}