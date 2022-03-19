package com.example.demo;

public interface BST {
    public Node insert(Node node, String s);
    public boolean delete(String word);
    public Node getRoot();
    public int getSize();
    public Node search(Node node, String word);
    public void setRoot(Node n);
    public void setHeight(int n);
}
