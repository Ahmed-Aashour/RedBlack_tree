package com.example.demo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public ArrayList<Long> times = new ArrayList<>();
    public ArrayList<Long> rB = new ArrayList<>();
    private BST tree;
    private BST redBlack;
    private boolean flag = false;
    public Application(BST avl, BST redBlack)
    {
        this.tree = avl;
        this.redBlack = redBlack;
    }
    private void Load()
    {
        File file = new File("dictionary.txt");
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replace("\n", "").replace("\r", "");
                this.insert(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("error reading dictionary");
        }
    }

    private void batchLookUp()
    {
        File file = new File("queries.txt");
        try (Scanner myReader = new Scanner(file)) {
            int found = 0;
            int existing = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replace("\n", "").replace("\r", "");
                if(this.search(data) != null)
                {
                    found++;
                }
                existing++;
            }
            System.out.println("searched for " + existing + " words");
            System.out.println("found " + found + " words");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("error reading queries");
        }
    }

    private void batchDeletions()
    {
        File file = new File("deletions.txt");
        try (Scanner myReader = new Scanner(file)) {
            
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replace("\n", "").replace("\r", "");
                
                this.delete(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("error reading deletions");
        }
    }

    private void insert(String word)
    {
        long start = System.nanoTime();
        this.tree.insert(tree.getRoot(), word);
        long end = System.nanoTime();
        if(this.flag)
        {
            this.rB.add(end - start);
            return;
        }
        this.times.add(end - start);
    }

    private Node search(String word)
    {
        Node node =  this.tree.search(tree.getRoot(), word);
        if(node == null){
            System.out.println(word + ": NO");
        }
        else
        {
            System.out.println(word + ": YES");
        }
        return node;
    }

    private void delete(String word)
    {
        long start = System.currentTimeMillis();
        this.tree.delete(word);
        long end = System.currentTimeMillis();
        this.times.add(end - start);
    }

    public void startApplication()
    {
        Scanner myReader = new Scanner(System.in);
        int operation = 0;
        while(true)
        {
            System.out.println("---------------------------------------------");
            System.out.println("1- Load");
            System.out.println("2- Print dictionary size");
            System.out.println("3- Insert a word");
            System.out.println("4- Look-up a word");
            System.out.println("5- Remove a word");
            System.out.println("6- Batch Look-ups");
            System.out.println("7- Batch Deletions");
            System.out.println("8- redBlack");
            System.out.println("9- Exit");
            System.out.print("Enter operation number: ");

            try {
                operation = Integer.parseInt(myReader.nextLine());
                if(operation < 1 || operation > 9)
                {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number between 1 and 9");
                continue;
            }

            if(operation == 1)
            {
                this.Load();
            }
            else if(operation == 2)
            {
                System.out.println("there are " + this.tree.getSize() + " words");
            }
            else if(operation == 3 || operation == 4 || operation == 5)
            {
                String word = "";
                try {
                    word = myReader.nextLine();
                    char [] chars = word.toCharArray();
                    for(char c: chars)
                    {
                        if(Character.isDigit(c))
                        {
                            throw new Exception();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("invalid input. Enter a string without numbers");
                    continue;
                }
                if(operation == 3)
                {
                    this.insert(word);
                }
                else if(operation == 4)
                {
                    this.search(word);
                }
                else if(operation == 5)
                {
                    this.delete(word);
                }
                
            }
            else if(operation == 6)
            {
                this.batchLookUp();
            }
            else if(operation == 7)
            {
                this.batchDeletions();
            }
            else if(operation == 8)
            {
                this.tree = this.redBlack;
                this.flag = true;
            }
            else if(operation == 9)
            {
                break;
            }
            else
            {
                System.out.println("Unexpected error.");
            }
        }
        myReader.close();
    }
}
