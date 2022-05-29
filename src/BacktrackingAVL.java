import java.util.ArrayList;
import java.util.List;

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
                    if(node.parent != null){
                        rotateRight(node.parent);
                    }else rotateRight(node);
                    stack.pop();
                    break;
                case RIGHTROTATE:
                    if(node.parent != null){
                        rotateLeft(node.parent);
                    }
                    else rotateLeft(node);
                    stack.pop();
                    break;
                }

        }
        if(stack.isEmpty() != false){
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
        // You should remove the next two lines, after double-checking that the signature is valid!
        IntegrityStatement.signature(); // Reminder!
        throw new UnsupportedOperationException("You should implement this");
    }
    
    public int Rank(int value) {
        // You should remove the next two lines, after double-checking that the signature is valid!
        IntegrityStatement.signature(); // Reminder!
        throw new UnsupportedOperationException("You should implement this");
    }
}
