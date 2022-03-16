public class RedBlack_tree {
    public static void main(String[] args) {
        
        BST t = new BST();
        t.insert(t.root, "a");
        t.insert(t.root, "b");
        t.insert(t.root, "c");
        t.insert(t.root, "d");
        t.insert(t.root, "e");

        
        System.out.println("Height = " +  t.height);
    }
}
