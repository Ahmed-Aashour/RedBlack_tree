package com.example.demo;

public class Node {
    
    public String word;   //data
    public String color; //color of the node
    public Node p;        //parent
    public Node l;        //left child
    public Node r;        //right child
    public int h = 0;     //height of the subtree (if root --> height of the tree)
    public int Bf = 0;

    //contructors
    public Node(){
        this.word = null;
        this.color = "red"; //new node is always "red"
        this.p = null;
        this.l = null;
        this.r = null;
    }
    public Node (String word) {
        this.word = word;
        this.color = "red"; //new node is always "red"
        this.p = null;
        this.l = null;
        this.r = null;
    }

    //constructor made only for delete() in RB tree
    public Node(Node parent, String color){
        this.word = null;
        this.color = color; //new node is always "red"
        this.p = parent;
        this.l = null;
        this.r = null;
    }
}