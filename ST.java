import java.util.*;
import java.io.*;


public class ST{
    private TreeNode head;
    List stopwords = new List();
    String w;
    protected Comparator cmp;
    protected int size;

    public ST() {
        this(new DefaultComparator());
    }

    public ST(Comparator cmp) {
        this.size = 0;
        this.cmp = cmp;
    }

    void update(String w) {
        WordFreq temp = find(w).Item;
        if (temp !=null){

            temp.setFreq(temp.Freq() + 1);

        } else {
            WordFreq nitem = new WordFreq(w, 1);
            insert(nitem);

        }


    }


    WordFreq search(String w) {
        TreeNode p = find(w);
        remove(w);
        head = rootInsert(head,p.Item,null);
        return p.Item;
    }


    void insert(WordFreq item) {
        if (item == null) throw new IllegalArgumentException();
        TreeNode n = head;
        TreeNode p = null;
        int result = 0;
        while (n != null) {
            // Compare element with the element in the current subtree
            result = cmp.compare(item.Key(), n.Item.Key());

            // Go left or right based on comparison result
            // Keep a reference in the last non
            // node encountered
            p = n;
            n = result < 0 ? n.left : n.right;
        }
        // Create and connect a new node
        TreeNode node = new TreeNode(item);
        node.parent = p;
        // The new node must be a left child of p
        if (result < 0) {
            p.left = node;
        }
        // The new node must be a right child of p
        else if (result > 0) {
            p.right = node;
        }
        // The tree is empty; root must be set
        else {
            head = node;
        }

    }


    void remove(String w) {
        TreeNode p = find(w);

        if (p.left != null && p.right != null) {
            TreeNode succ = successor(p);
            p.Item = succ.Item;
            p = succ;
        }
        TreeNode parent = p.parent;
        TreeNode child = p.left != null ? p.left : p.right;
        // The root is being removed
        if (parent == null) {
            head = child;
        }
        // Bypass p
        else if (p == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        if (child != null) {
            child.parent = parent;
        }
        // Dispose p
        p.unlink();
        --size;
    }
    //traverses the tree and sum the frequencies of the words
    int getTotalWords() {
        int counter = 0;
        TreeNode h;
        StringStackImpl<TreeNode> s = new StringStackImpl<TreeNode>();
        if (head != null)s.push(head);
        while (!s.isEmpty()) {
            h = s.pop();
            counter += h.Item.Freq();
            if (h.right != null) s.push(h.right);
            if (h.left != null) s.push(h.left);
        }
        return counter;
    }



    int getDistinctWords() {
        int counter = 1;
        TreeNode h;
        StringStackImpl<TreeNode> s = new StringStackImpl<TreeNode>();
        if (head != null)s.push(head);
        while (!s.isEmpty()) {
            h = s.pop();
            counter++;
            if (h.right != null) s.push(h.right);
            if (h.left != null) s.push(h.left);
        }
        return counter;

    }

    int getFrequency(String w) {
        TreeNode p = find(w);
        return p.Item.Freq();

    }

    //treverses the tree and find the node with the max frequency
    WordFreq getMaximumFrequency() {
        int max ;
        WordFreq maxword;
        TreeNode h;
        StringStackImpl<TreeNode> s = new StringStackImpl<TreeNode>();
        s.push(head);
        max = head.Item.Freq();
        maxword = head.Item;

        while (!s.isEmpty()) {
            h = s.pop();
            if(h.Item.Freq() > max){
                max = h.Item.Freq();
                maxword = h.Item;
            }
            if (h.right != null) s.push(h.right);
            if (h.left != null) s.push(h.left);
        }
        return maxword;
    }


    double getMeanFrequency() {
        return getTotalWords()/getDistinctWords();

    }
    //printing functions
    //it uses inorder function to traversal  the tree with recursion
    void printΤreeAlphabetically(PrintStream stream) {
        inorder(head,stream);
    }

    void inorder(TreeNode node,PrintStream stream){
        if (node == null) {
           return;
        }
        inorder(node.left,stream);
        stream.println(node.Item.toString());
        inorder(node.right,stream);
    }



    void printΤreeByFrequency(PrintStream stream) {
         WordFreq li[] = new WordFreq[getDistinctWords()];
        TreeNode h;
        StringStackImpl<TreeNode> s = new StringStackImpl<TreeNode>();
        s.push(head);
        int c =0;
        while (!s.isEmpty()) {
            h = s.pop();
            li[c] = h.Item;
            c++;
            if (h.right != null) s.push(h.right);
            if (h.left != null) s.push(h.left);
        }
        sort(li,stream);




    }
    //array sort function
    public void sort(WordFreq[] array,PrintStream stream) {

        WordFreq temp= array[0];
        boolean fixed = false;
        while (fixed == false) {
            fixed = true;
            for (int i = 0; i < array.length -2; i++ ) {

                if (array[i].Freq() > array[i+1].Freq()){
                    temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    fixed = false;
                }

            }
        }


            for (int i = 0; i < array.length-1; i++ ) {
                stream.println(array[i].toString());
            }
        }




    //list functions
    void addStopWord(String w) {
        stopwords.insertAtFront(w);

    }

    void removeStopWord(String w) {
        stopwords.remove(w);


    }


    //load
    //reads the text file and create the tree with distinct words

    void load(String filename) throws Exception {

        int counter = 0;
        File f = null;

        String data;


        f = new File(filename);
        Scanner sc = new Scanner(f);

        while (sc.hasNextLine()) {
            data = sc.nextLine();
            data = data.replace("!", "");
            data = data.replace("@", "");
            data = data.replace("#", "");
            data = data.replace("$", "");
            data = data.replace("^", "");
            data = data.replace("&", "");
            data = data.replace(":", "");
            data = data.replace(";", "");
            data = data.replace("/", "");
            data = data.replace(".", "");
            data = data.replace(",", "");
            String [] array = data.split(" ");
            counter++;

            for (String val : array) {
                if (!val.contains("[0-9]")) {
                    if (val.valueOf(0).equals(" ")) {
                        val = val.substring(1);
                    }
                    if (val.valueOf(0).equals("(")) {
                        val = val.substring(1);

                    }
                    if (val.valueOf(val.length()).equals(")")) {
                        val = val.substring(0, val.length() - 1);
                    }
                    if (val.valueOf(val.length()).equals(" ")) {
                        val = val.substring(0, val.length() - 1);
                    }
                    val = val.toLowerCase();
                    boolean check = stopwords.findNode(val);
                    if (check) {
                        update(val);
                    }
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
//additional functions
    private TreeNode successor(TreeNode q) {
        // The successor is the leftmost leaf of q�s right subtree
        if (q.right != null) {
            TreeNode p = q.right;
            while (p.left != null) p = p.left;
            return p;
        }
        // The successor is the nearest ancestor on the right
        else {
            TreeNode p = q.parent;
            TreeNode ch = q;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private TreeNode predecessor(TreeNode q) {
        // The successor is the rightmost leaf of q�s left subtree
        if (q.left != null) {
            TreeNode p = q.left;
            while (p.right != null) p = p.right;
            return p;
        }
        // The successor is the nearest ancestor on the right
        else {
            TreeNode p = q.parent;
            TreeNode ch = q;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }


    //insert an item at the root of the tree
    private TreeNode rootInsert(TreeNode p, WordFreq item, TreeNode parent){
        if (p == null){
            TreeNode node = new TreeNode(item);
            node.parent = parent;
            ++size;
            return node;
        }
        int result = cmp.compare(item.Key(), p.Item.Key());
        if (result == 0) return p;
        if (result<0){
            p.left = rootInsert(p.left, item, p);
            p = rotateRight(p);
        } else {
            p.right = rootInsert(p.right, item, p);
            p = rotateLeft(p);
        }
        return p;
    }

    private TreeNode rotateLeft(TreeNode pivot) {
        TreeNode parent = pivot.parent;
        TreeNode child = pivot.right;
        if (parent == null) {
            head = child;
        } else if (parent.left == pivot) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        child.parent = pivot.parent;
        pivot.parent = child;
        pivot.right = child.left;
        if (child.left != null) child.left.parent = pivot;
        child.left = pivot;
        return child;
    }

    private TreeNode rotateRight(TreeNode pivot) {
        TreeNode parent = pivot.parent;
        TreeNode child = pivot.left;
        if (parent == null) {
            head = child;
        } else if (parent.left == pivot) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        child.parent = pivot.parent;
        pivot.parent = child;
        pivot.left = child.right;
        if (child.right != null) child.right.parent = pivot;
        child.right = pivot;
        return child;
    }

    TreeNode find(String w) {
        TreeNode p = head;
        while (p != null) {
            // Compare element with the element in the current subtree
            int result = cmp.compare(w, p.Item.Key());
            if (result == 0) {
                return p;
            }
            // Go left or right based on comparison result
            p = result < 0 ? p.left : p.right;
        }
        TreeNode k = new TreeNode();
        return k;
    }


    void insertbyfreq(WordFreq item){
        if (item == null) throw new IllegalArgumentException();
        TreeNode n = head;
        TreeNode p = null;
        int result = 0;
        while (n != null) {
            // Go left or right based on comparison result
            // Keep a reference in the last non
            // node encountered
            p = n;
            n = item.Freq()< n.Item.Freq() ? n.left : n.right;
        }
        // Create and connect a new node
        TreeNode node = new TreeNode(item);
        node.parent = p;
        // The new node must be a left child of p
        if (item.Freq()< n.Item.Freq()) {
            p.left = node;
        }
        // The new node must be a right child of p
        else if (item.Freq()> n.Item.Freq()) {
            p.right = node;
        }
        // The tree is empty; root must be set
        else {
            head = node;
        }
    }



    public static void main(String args[]) throws Exception{
        ST st = new ST();
        st.addStopWord("nok");
        st.addStopWord("a");
        st.removeStopWord("a");

        st.load("test.txt");



        PrintStream stream = new PrintStream(System.out);
        st.printΤreeAlphabetically(stream);

        stream.println("/n");
        st.printΤreeByFrequency(stream);
    }
}
