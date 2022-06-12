import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BacktrackingAVL extends AVLTree {
    // For clarity only, this is the default ctor created implicitly.
    public BacktrackingAVL() {
        super();
    }

	//You are to implement the function Backtrack.
    public void Backtrack() {

        //While reversing rotations
        while(stack.isEmpty() == false && ((Object[])stack.peek())[0] == AVLActionType.ROTATION){
            Object[] memory = (Object[])stack.pop();
            Node node = (Node)memory[1];
            AVLActionType action = (AVLActionType)memory[2];
            // Reverse the rotations that were performed
            switch (action){
                // Rotation was LeftRotate - do RightRotate
                case LEFTROTATE:
                    rotateRight(node.parent);
                    stack.pop();
                    break;
                // Rotation was RightRotate - do LeftRotate
                case RIGHTROTATE:
                    rotateLeft(node.parent);
                    stack.pop();
                    break;
            }

        }

        //After reversing rotations, remove the inserted node
        if(stack.isEmpty() == false) {
            Object[] memory = (Object[]) stack.pop();
            Node node = (Node) memory[1];

            //delete the last inserted node
            if (node != root) {
                if (node.parent.left == node) { //node is a left child
                    node.parent.left = null;
                } else { //node is a right child
                    node.parent.right = null;
                }
                //update size and height
                Node current = node.parent;
                while (current != null) {
                    current.size--;
                    current.updateHeight();
                    current = current.parent;
                }
            }

            //The removed node was the root
            else {
                root = null;
            }


        }
    }
    
    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample() {
        // You should remove the next two lines, after double-checking that the signature is valid!
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.add(1);
        nums.add(2);
        nums.add(3);

        return nums;
    }
    
    public int Select(int index) {

        //remember the parameter
        int initialIndex = index;

        //if tree is empty, don't continue
        if(root == null) throw new NoSuchElementException("root is null");

        // start from the root
        Node current = root;


        while(current != null){

           //Assume current has no children
           int rank = 1;

           //if current has a left child, add its size to the rank
           if(current.left != null){
               rank += current.left.size;
           }

           //check if we found the target
           if(rank == index){
               return current.value;
           }

           //else pick a side to go down the tree
           else if(rank > index){
               current = current.left;
           }
           else{
               index -= rank;
               current = current.right;
           }
           //-----------------------------------
       }
        //if the index doesn't exist, use the parameter we remembered to throw an exception
        throw new NoSuchElementException("index "+initialIndex+" doesn't exist");
    }

    public int Rank(int value){

        //No nodes in the tree
        if(root == null) return 0;

        //start at the root
        Node current = root;
        int counter = 0;


        while(current != null){

            //check if we found a node with the given value
            if(current.value == value){

                //add the size of the left child to the counter
                if(current.left != null){
                    counter+=current.left.size;
                }
                return counter;
            }

            //decide where to go down the tree

            //if the current node is smaller than the given value,
            //count it as well and go right
            if(current.value < value) {
                if (current.left != null) {
                    counter += current.left.size+1;
                }
                else counter +=1;
                current = current.right;
            }

            //else just go left
            else if ((current.value > value)){
                current = current.left;
            }
        }
        return counter;


    }
    public static void main(String[] args){
        BacktrackingAVL tree = new BacktrackingAVL();
        int counter = 0;
        for (int i =0; i < 150 ;i++){
            tree.insert(counter++);
        }

        for (int i =0; i < 40 ; i++){
            tree.Backtrack();
        }
        for (int i =0; i < 20 ; i++){
            tree.insert(counter++);
        }
        for (int i =0; i < 80 ; i++){}
            tree.Backtrack();

        tree.printTree();
    }
}

