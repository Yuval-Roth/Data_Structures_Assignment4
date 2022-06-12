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
        if(stack.isEmpty() == false){
            Object[] memory = (Object[])stack.pop();
            Node node = (Node)memory[1];

            //delete the last inserted node
            if(node != root){
                if(node.parent.left == node){ //node is a left child
                    node.parent.left = null;
                }
                else{ //node is a right child
                    node.parent.right = null;
                }
                //update size and height
                Node current = node.parent;
                while(current != null){
                    current.size--;
                    current.updateHeight();
                    current = current.parent;
                }
            }

            //The removed node was the root
            else{
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

       int initialIndex = index;
       if(root == null) throw new NoSuchElementException("root is null");

        Node current = root;
       while(current != null){

           int rank = 1;
           if(current.left != null){
               rank += current.left.size;
           }
           if(rank == index){
               return current.value;
           }
           else if(rank > index){
               current = current.left;
           }
           else{
               index -= rank;
               current = current.right;
           }
       }
        throw new NoSuchElementException("index "+initialIndex+" doesn't exist");
    }

    public int Rank(int value){

        if(root == null) return 0;

        Node current = root;
        int counter = 0;


        while(current != null){

            if(current.value == value){
                if(current.left != null){
                    counter+=current.left.size;
                }
                return counter;
            }
            if(current.value < value) {
                if (current.left != null) {
                    counter += current.left.size+1;
                }
                else counter +=1;
                current = current.right;
            }
            else if ((current.value > value)){
                current = current.left;
            }
        }
        return counter;


    }
    public static void main(String[] args){
        BacktrackingAVL tree = new BacktrackingAVL();
        int counter = 0;
        for(int )
        tree.insert(6);


        tree.printTree();
        System.out.println(tree.Rank(11));
    }
}

