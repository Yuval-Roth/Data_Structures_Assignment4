import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	Stack<Object[]> stack;

	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
		stack = new Stack<>();
	}
	//You are to implement the function Backtrack.
	public void Backtrack() {
		// You should remove the next two lines, after double-checking that the signature is valid!
	    IntegrityStatement.signature(); // Reminder!
	    throw new UnsupportedOperationException("You should implement this");
    }
	
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		ArrayList<Integer> nums = new ArrayList<Integer>();
		nums.add(1);
		nums.add(2);
		nums.add(3);
		nums.add(4);
		nums.add(10);
		nums.add(5);
		return nums;
	}
	public static void main(String[] args){
		BTree<Integer> tree = new BTree<>(2);
		for(int i = 0; i< 12;i++){
			tree.insert(i);
		}
		System.out.println(tree);
	}
}
