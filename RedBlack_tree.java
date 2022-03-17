public class RedBlack_tree {
    public static void main(String[] args) {
        
        BST t = new BST();
        t.insert(t.root, "c");
        t.insert(t.root, "b");
        t.insert(t.root, "e");
        t.insert(t.root, "h");
        t.insert(t.root, "i");
        t.insert(t.root, "q");
        t.insert(t.root, "p");
        t.insert(t.root, "d");
        // t.insert(t.root, "e");
        t.printPreorder(t.root);
        System.out.println("------------");
        t.delete("b");
        // t.delete("b");
        // t.delete("d");
        // t.delete("c");
        t.printPreorder(t.root);

        System.out.println("Height = " +  t.height);
        // System.out.println(t.getRoot().word);
        // System.out.println(t.isEmpty());
        // System.out.println(t.contains("a"));
        // // t.clear();
        // System.out.println(t.isEmpty());
    }
}
