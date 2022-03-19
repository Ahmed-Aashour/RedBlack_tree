package com.example.demo;
public class RedBlack implements BST{
    public Node root = null;
    public int height;
    public int size;
    private final String black = "black";
    private final String red = "red";
    //constructor
    public RedBlack (){
        this.height = -1; // (-1) --> empty tree
        size = 0;
    }

    // return the root of the given Red black tree
    public Node getRoot(){
        return this.root;
    }

    public int getSize(){
        return this.size;
    }    
    // return whether the given tree isEmpty or not
    public boolean isEmpty(){
        return this.size == 0 || this.height == -1;
    }

    // Clear all nodes in the given tree
    public void clear(){
        this.root = null;
        this.height = -1;
        this.size = 0;
    }

    // return the node associated with the given key or null if no value is found
    public Node search(Node node, String word){
        if(node == null){ //Not Found ):
            return null;
        }
        else if (word.compareTo(node.word) == 0){ //Found (;
            return node;
        }
        else if (word.compareTo(node.word) < 0){ //go left
            node = search(node.l, word);
        }
        else if (word.compareTo(node.word) > 0){ //go right
            node = search(node.r, word);
        }
        return node;
    }

    // return true if the tree contains the given value and false otherwise
    public boolean contains(String word){
        return this.search(this.root, word) != null;
    }

    public Node insert(Node node, String word){
        if(node == null){ //Not found, so insert it
            Node newNode = new Node(word);
            if (height == -1) 
            {
                this.root = newNode; //empty tree
                this.root.color = black;
            }
            node = newNode;
            size++; //increment the words number
        }
        else if (word.compareTo(node.word) == 0){ //word inserted before
            // System.out.println("ERROR: \"" + word + "\" is Already exist!!");
        }
        else{ //go down the tree
            if (word.compareTo(node.word) < 0){
                Node lNode = insert(node.l, word); //GOTO left child
                if(lNode.word.equalsIgnoreCase(word))
                {
                    node.l = lNode;
                    lNode.p = node; //Parent link
                    if(lNode.p != null && lNode.p.color == red)
                    {
                        
                        this.insert_fixup(lNode);
                    }  
                } 
            }
            else if (word.compareTo(node.word) > 0){
                Node rNode = insert(node.r, word); //GOTO right child
                if(rNode.word.equalsIgnoreCase(word))
                {
                    node.r = rNode;
                    rNode.p = node; //Parent link
                    if(rNode.p != null && rNode.p.color == red)
                    {
                        this.insert_fixup(rNode);
                    } 
                }
            }
            update_height(node); //updating the Node Height (h)          
        }
        this.height = this.root.h; //update the Tree Height
        return node;
    }

    private void insert_fixup(Node node)
    {
        while (node.p != null && node.p.color == red)
        {
            // if the parent is the left child of the grandparent
            if(node.p == node.p.p.l)
            {
                Node y = node.p.p.r;//the uncle
                //first case red uncle
                if(y != null && y.color == red)
                {
                    node.p.color = black;
                    y.color = black;
                    node.p.p.color = red;
                    node = node.p.p;
                }
                //second case uncle is black and the new node is the right child
                else 
                {
                    if(node == node.p.r)
                    {
                        node = node.p;
                        this.LeftRotation(node);
                    }
                    //third case
                    node.p.color = black;
                    node.p.p.color = red;
                    this.RightRotation(node.p.p);
                }
                
            }
            else
            {
                Node y = node.p.p.l;//the uncle
                //first case red uncle
                if(y!= null && y.color == red)
                {
                    node.p.color = black;
                    y.color = black;
                    node.p.p.color = red;
                    node = node.p.p;
                }
                 //second case uncle is black and the new node is the left child
                else
                {
                    if(node == node.p.l)
                    {
                        node = node.p;
                        this.RightRotation(node);
                    }
                    //third case
                    node.p.color = black;
                    node.p.p.color = red;
                    this.LeftRotation(node.p.p);
                } 
            }
        }
        this.root.color = black;
    }

    private Node LeftRotation(Node node) {
        Node pivot = node;
        Node rNode = node.r; //right node of the pivot
        //change the pointers of (both nodes & parent nodes properly)
        if(rNode.l != null)
            rNode.l.p = pivot;
        rNode.p = pivot.p;
        if(rNode.p != null && rNode.p.l == pivot)
            rNode.p.l = rNode;
        if(rNode.p != null && rNode.p.r == pivot)
            rNode.p.r = rNode;
        pivot.p = rNode;
        pivot.r = rNode.l;
        rNode.l = pivot;
        if(pivot == this.root)
            this.root = rNode;
        //update the height of both nodes
        update_height(pivot);
        update_height(rNode);
        return rNode;
    }

    private Node RightRotation(Node node) {
        Node pivot = node;
        Node lNode = node.l; //left node of the pivot
        //change the pointers of (both nodes & parent nodes properly)
        if(lNode.r != null)
            lNode.r.p = pivot;
        lNode.p = pivot.p;
        if(lNode.p != null && lNode.p.l == pivot)
            lNode.p.l = lNode;
        if(lNode.p != null && lNode.p.r == pivot)
            lNode.p.r = lNode;
        pivot.p = lNode;
        pivot.l = lNode.r;
        lNode.r = pivot;
        if(pivot == this.root)
            this.root = lNode;
        //update the height of both nodes
        update_height(pivot);
        update_height(lNode);
        return lNode;
    }

    private void update_height(Node node){
        if(node.l == null
        && node.r == null)       node.h = 0; //leaf node
        else if (node.l == null) node.h = node.r.h + 1; //no left  subtree
        else if (node.r == null) node.h = node.l.h + 1; //no right subtree
        else node.h = (node.l.h > node.r.h )? node.l.h + 1: node.r.h + 1; //general case
    }

    //a method to update height and balance of the node's ancestors after deletion operation
    private void update_height_for_deletion(Node node){
        Node parent = node.p;
        //if we reached the root
        if(parent == null)
        {
            this.update_height(node);
            return;
        }
        this.update_height(node);
        this.update_height_for_deletion(parent);
    }

    // Delete the node associated with the given value.
    // Return true in case of success and false otherwise
    public boolean delete(String word){
        Node node = this.search(this.root, word);
        //if the word to be deleted is null return
        if(node == null){
            System.out.println(word + " not found");
            return false;
        }
        // Node Replacement = null;
        String colorOfDeletedNode = node.color;
        //if the node has no children
        Node parent = node.p;
        if(node.l == null && node.r == null)
        {
            if(node == this.root)
            {
                this.root = null;
            }
            else
            {
                Node s = new Node(parent, black);
                if(parent.l == node){
                    parent.l = s;
                    //fixing-up
                    if(colorOfDeletedNode == black){
                        this.delete_fixup(parent.l);
                    }
                }
                else{
                    parent.r = s;
                    //fixing-up
                    if(colorOfDeletedNode == black){
                        this.delete_fixup(parent.r);
                    }
                }
                if(s.p.l == s){
                    s.p.l = null;
                }
                else{
                    s.p.r = null;
                }
                node.p = null;
                this.update_height_for_deletion(parent);
            }
            this.size--;
        }
        //if the node has two children the successor will be the minimum value in the right subtree
        else if(node.l != null && node.r != null)
        {
            Node successor = this.findMin(node.r);
            String temp = successor.word;
            this.delete(successor.word);
            node.word = temp;
        }
        //if the node has only one child
        else
        {
            Node child = node.l == null? node.r : node.l;
            if(node == this.root)
            {
                this.root = child;
                this.root.p = null;
                this.root.h--;
            }
            else
            {
                child.p = parent;
                if(parent.l == node){
                    parent.l = child;   
                }
                else{parent.r = child;}
                node.p = null;
                this.update_height_for_deletion(parent);
                this.size--;
            }
            //fixing-up
            if(colorOfDeletedNode == black){
                this.delete_fixup(child);
            }
        }
        this.height = this.root == null ? -1 : this.root.h;
        return true;
    }

    private void delete_fixup(Node node){
        Node sib; //sibling
        while(node != this.root && node.color == black){
            if(node == node.p.l)
            {
                // System.out.println("-->left child");
                sib = node.p.r;
                if(sib.color == red){ //case 1
                    // System.out.println("case 1");
                    sib.color = black;
                    node.p.color = red;
                    this.LeftRotation(node.p);
                    sib = node.p.r;
                }
                if( (sib.l == null || sib.l.color == black) &&  (sib.r == null || sib.r.color == black)){ //case 2
                    // System.out.println("case 2");
                    sib.color = red;
                    node = node.p;
                }
                else{
                    if(sib.r == null || sib.r.color == black){ //case 3
                        // System.out.println("case 3");
                        sib.l.color = black;
                        sib.color = red;
                        this.RightRotation(sib);
                        sib = node.p.r;
                    }
                    // System.out.println("case 4");
                    sib.color = node.p.color; //case 4
                    node.p.color = black;
                    sib.r.color = black;
                    this.LeftRotation(node.p);
                    node = this.root;
                }
            }
            else
            {
                // System.out.println("right child");
                sib = node.p.l;
                if(sib.color == red){ //case 1
                    // System.out.println("case 1");
                    sib.color = black;
                    node.p.color = red;
                    this.RightRotation(node.p);
                    sib = node.p.l;
                }
                if((sib.r == null || sib.r.color == black) && (sib.l == null || sib.l.color == black)){ //case 2
                    // System.out.println("case 2");
                    sib.color = red;
                    node = node.p;
                }
                else{
                    if(sib.l == null || sib.l.color == black){ //case 3
                        // System.out.println("case 3");
                        sib.r.color = black;
                        sib.color = red;
                        this.LeftRotation(sib);
                        sib = node.p.l;
                    }
                    // System.out.println("case 4");
                    sib.color = node.p.color; //case 4
                    node.p.color = black;
                    sib.l.color = black;
                    this.RightRotation(node.p);
                    node = this.root;
                }
            }
        }
        node.color = black;
    }


    // a method that finds the minimum element in a subtree
    private Node findMin(Node node)
    {
        Node current = node;
        while (current.l != null)
        current = current.l;
 
        return current;
    }

    public void printPreorder(Node node)
    {
        if (node == null){
            return;
        }
            
        
        /* first print data of node */
        System.out.println(node.word + " " + node.color + " " + node.h);
 
        /* then recur on left subtree */
        this.printPreorder(node.l);
 
        /* now recur on right subtree */
        this.printPreorder(node.r);
    }
    @Override
    public void setRoot(Node n) {
        this.root = n;
    }
    @Override
    public void setHeight(int n) {
        this.height = n;
    }
}
