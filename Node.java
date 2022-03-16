public class Node {
    
    public String word;   //data
    public boolean color; //color of the node (true, red) & (false, black)
    public Node p;        //parent
    public Node l;        //left child
    public Node r;        //right child
    public int h = 0;     //height of the subtree (if root --> height of the tree)
    public int Bh = 0;    //black height of the node

    //contructors
    public Node(){
        this.word = null;
        this.color = true; //new node is always (true, red)
        this.p = null;
        this.l = null;
        this.r = null;
    }
    public Node (String word) {
        this.word = word;
        this.color = true; //new node is always (true, red)
        this.p = null;
        this.l = null;
        this.r = null;
    }
}