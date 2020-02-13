public class TreeNode{

    public TreeNode left,parent, right;
    WordFreq Item ;
    int number;

    TreeNode (){}

    protected TreeNode(WordFreq Item) {
        if (Item == null) throw new IllegalArgumentException();
        this.Item = Item;
        this.left = null;
        this.right = null;
    }

    protected void unlink() {
        Item = null;
        parent = left = right = null;
    }

}