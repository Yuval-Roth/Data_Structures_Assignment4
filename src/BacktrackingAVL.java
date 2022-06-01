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

        while(stack.isEmpty() == false && ((Object[])stack.peek())[0] == AVLActionType.ROTATION){
            Object[] memory = (Object[])stack.pop();
            Node node = (Node)memory[1];
            AVLActionType action = (AVLActionType)memory[2];
            switch (action){
                case LEFTROTATE:
                    rotateRight(node.parent);
                    stack.pop();
                    break;
                case RIGHTROTATE:
                    rotateLeft(node.parent);
                    stack.pop();
                    break;
                }

        }
        if(stack.isEmpty() == false){
            Object[] memory = (Object[])stack.pop();
            AVLActionType action = (AVLActionType) memory[0];
            Node node = (Node)memory[1];

            if(node != root){
                if(node.parent.left == node){
                    node.parent.left = null;
                }
                else{
                    node.parent.right = null;
                }
                node.parent.updateHeight();
                Node current = node.parent;
                while(current != null){
                    current.size--;
                    current = current.parent;
                }
            }
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
        throw new NoSuchElementException("index doesn't exist");
    }

    public int Rank(int value) {

    }
    public static void main(String[] args){
        BacktrackingAVL tree = new BacktrackingAVL();

    }
}

