import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.

	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}
	//You are to implement the function Backtrack.
	public void Backtrack() {
		if(stack.isEmpty() == false) {
			Object[] memory = (Object[]) stack.pop();
			BTreeActionType action = (BTreeActionType) memory[0];
			T value = (T) memory[1];
			Node<T> node = getNode(value);
			node.removeKey(value);
			size--;
			while (stack.isEmpty() == false && ((Object[]) stack.peek())[0] == BTreeActionType.SPLIT) {
				memory = (Object[]) stack.pop();
				T median = (T) memory[1];
				node = getNode(median);
				int index = node.indexOf(median);
				Node<T> rightChild = node.children[index + 1];
				Node<T> leftChild = node.children[index];

				if (node == root & root.numOfKeys == 1) root = node.getChild(node.indexOf(median));

				node.removeChild(index + 1);
				node.removeKey(median);

				leftChild.addKey(median);
				for (int i = 0; i < rightChild.numOfKeys; i++) {
					leftChild.addKey(rightChild.getKey(i));
				}
				if (rightChild.isLeaf() == false) {
					for (int i = 0; i < rightChild.numOfChildren; i++) {
						Node<T> child = rightChild.getChild(i);
						leftChild.addChild(child);
						child.parent = rightChild;
					}
				}
			}
//			if (root.numOfKeys == 0) root = null;
		}
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
		BacktrackingBTree<Integer> tree = new BacktrackingBTree<>(2);
		Random rand = new Random();
		for(int i = 0; i< 250;i++){
			tree.insert(rand.nextInt(500)*(Math.random() > 0.5 ? 1:-1));
//			/*if(i == 10) */System.out.println(tree);
		}
		for(int i = 0; i< 500;i++){
			tree.Backtrack();
			/*if(i == 10) */System.out.println(tree);
		}
//		tree.Backtrack();
//		System.out.println(tree);
	}
}
