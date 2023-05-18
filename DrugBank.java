
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * Mohamed Abd El-Gelil
 * 7364557
 * COSC 2P03
 * Assignment 2
 *
 *
 * In this assignment we are tasked with certain tasks to manipulate given data and turn it into a BST and do certain
 * operation on said BST
 */


public class DrugBank {
    static ArrayList<Drug> data = new ArrayList<>(); // instance of array list that stores Drug object
    static BinaryNode root; // the root of the BST
    // intialized printwriter to write to new text file
    // the reason I did this was if I kept inside the recursive method it would either write one line only or not write at all
    PrintWriter printWriter= new PrintWriter("dockedApprovedSorted.tab");
    String caps= "ALLCAPS";


    public DrugBank() throws FileNotFoundException {
    }

    /**
     * This method takes a given text file and stores each line of the text file as a Drug object inside the arraylist
     * data.
     */
    public void readData() {
        try {
            File textFile = new File(("dockedApproved.tab"));
            Scanner sc = new Scanner(textFile);
            sc.nextLine(); // skip the first line
            String drugBankID;
            String genericName;
            String SMILES;
            String url;
            String drugGroups;
            int score;

            while (sc.hasNext()) {
                String textLine = sc.nextLine();
                StringTokenizer tokens = new StringTokenizer(textLine, "\t");
                genericName = tokens.nextToken();
                SMILES = tokens.nextToken();
                drugBankID = tokens.nextToken();
                url = tokens.nextToken();
                drugGroups = tokens.nextToken();
                score = (int) Float.parseFloat(tokens.nextToken());
                Drug drug = new Drug(drugBankID, genericName, SMILES, url, drugGroups, score);
                data.add(drug);
            }
        } catch (FileNotFoundException q) {
            System.out.println("Please choose correct file");
        }
    }







    /**
     *
     * @param left the first element of the array
     * @param right the last element of the array
     * @return returns the root of the BST
     *
     * This method takes the array data and makes its values into a BST
     * The way I did this was I assumed the array was sorted and set the middle value of the array to be the root of
     * the BST then with recursion I would either move the left or right till the entire array has been gone through
     * and each value is the Node of a BST
     */

    public BinaryNode create(int left, int right) {
        if (left > right) {
            return null;
        }
        int middle =left+(right-left)/2;
       BinaryNode node=new BinaryNode(data.get(middle));
        node.left= create(left,middle-1);
        node.right=create(middle+1,right);
        root=node; // did this since if i kept an instance of root and kept changing it would not work for me
        return root;
    }

    
    /**
     * @param root the root of the BST
     *
     *  An in order traversal is very simple there are 3 policies Left,Node,Right
     *  First the root node goes left and the and the node after also checks to go left
     *  once a Node has no more left it visits itself then checks to see if there is a node to the right
     *  This traversal ends up returning all the values of the BST in a sorted manner
     *
     *   instead of visiting a node I would write the data of that Node into a new textfile
     */
    public void inOrderTraverse(BinaryNode root){
        if(root==null){
               return;
        }
        inOrderTraverse(root.left);
        printWriter.println(root.drug.genericName + "\t" +root.drug.SMILES +"\t"+ root.drug.drugBankID + "\t" +
                root.drug.url + "\t" +root.drug.drugGroups + "\t" + root.drug.score);
        inOrderTraverse(root.right);
    }

    /**
     *
     * @param root Root of the BST
     * @param treasure the DrugBankID we are looking for (chose the name since most people do a lot to find treasure)
     *
     *  For this method I chose to use an InOrder traversal to traverse all the node of the BST and see if the current
     *  nodes DbID is equal the id passed if so it just prints found it and thats it if that DbID does not exist it
     *  wont print anything
     */
    public void search(BinaryNode root, String treasure){
        if(root==null){
            return ;
        }
        search(root.left,treasure);
        if(root.key.equals(treasure)) System.out.println("found it");
        search(root.right,treasure);
    }

    /**
     *
     * @param root Root of the BST
     * @param treasure The node with this given DbID that we are trying to delete
     * @return returns the deleted node
     *
     * I took this method mostly from the lecture slides so I will not explain it as if it is my own the lecture slides
     * does a better job
     */
    public BinaryNode delete(BinaryNode root, String treasure){
        if(root==null){
            return null;
        }
        if(splitter(treasure)<splitter(root.key)){
                root.left=delete(root.left,treasure);
        }else if(splitter(treasure)>splitter(root.key)){
            root.right=delete(root.right,treasure);
        }else{
            if(root.left!=null && root.right!=null){
                root.key= String.valueOf(findMin(root.right));
                root.right=delete(root.right,root.key);
            }else{
                root=(root.left!=null) ? root.left : root.right;
            }
        }
        return root;
    }

    /**
     *
     * @param key The given string you want to change into an int
     * @return returns only the number part in the drugBankID
     *
     * This method works by spliting the DbID with "B" being the regex and so the 0th index of the split string with just be
     * D while the 1st index will be the numbers in which we are trying to compare
     * (I am assuming that the DrugBankID number is what sorts them and so when comparing 2 strings you cant use < > operators
     * this was just a way to bypass this)
     */
    public int splitter(String key){
        String[] splitted= key.split("B");
        return Integer.parseInt(splitted[1]);
    }

    /**
     *
     * @param root Root of the BST
     * @param treasure the DrugBankID of the node we are searching for
     * @return return the depth of that Node
     *
     * this method I had a bit of help online getting and did not reach the entire solution on my own so I cant explain it
     * as my own
     * (Did not outsource the work just saw code online)
     */
    public int depth1(BinaryNode root, String treasure){
      int d=-1;
        if(root==null){
            return -1;
        }
      if(splitter(root.key)==splitter(treasure) ||
              (d=depth1(root.left,treasure))>=0 ||
              (d=depth1(root.right,treasure))>=0){
          return d+1;
      }
      return d;
    }

    /**
     *
     * @param root root of the BST
     * @return returns the smallest node in the BST or the leftmost node
     *
     * This method was only given in the lectuer slides so I will not explain it as my own method but in a BST the left
     * Nodes are smaller and so the left most node should be the smallest node in the entire tree and the rightmost probably
     * will be the largest
     */
    public BinaryNode findMin(BinaryNode root){
        if(root!=null){
            while (root.left!=null){
                root=root.left;
            }
        }
        return root;
    }

    /**
     *
     * @param root Root of the BST
     * @return return the depth of the deepest node in the entire BST
     * traverses the left and right and sees which one is deeper
     * each time it goes down it increments either values by 1 and just returns whoever has  the largest values
     */
    public int depth2(BinaryNode root){
        if(root==null){
            return 0;
        }else{
            int deepestLeft=depth2(root.left);
            int deepestRight=depth2(root.right);
            if(deepestLeft>deepestRight){
                return deepestLeft+1;
            }
            return deepestRight+1;
        }

    }

        public static void main(String[] args) throws FileNotFoundException {
         DrugBank db = new DrugBank();
         db.readData();
         db.create(0, data.size()-1);
         db.inOrderTraverse(root);
         System.out.println(db.depth1(root,"DB01050"));
         System.out.println(db.depth2(root));
         db.search(root,"DB01050");
         db.search(root,"DB00316");
         db.delete(root,"DB01065");
         db.search(root,"DB01065");
    }
}
