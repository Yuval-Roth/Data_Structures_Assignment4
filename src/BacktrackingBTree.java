import java.util.ArrayList;
import java.util.List;

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
		Object[] memory = (Object[])stack.pop();
		T value = (T)memory[1];
		BTreeActionType action = (BTreeActionType) memory[0];
		Node<T> node = getNode(value);
		node.removeKey(value);
		while(((Object[])stack.peek())[0] == BTreeActionType.SPLIT){
			memory = (Object[])stack.pop();
			boolean rootWasSplit = (boolean)memory[2];
			T median = (T)memory[1];
			node = getNode(median);
			int index = node.indexOf(median);
			Node<T> rightChild = node.children[index+1];
			Node<T> leftChild = node.children[index];

			//node is a root
			if(rootWasSplit) {
				root = leftChild;
				leftChild.addKey(median);

				for(int i = 0; i<rightChild.numOfKeys;i++){
					leftChild.addKey(rightChild.getKey(i));
				}
				if(rightChild.isLeaf() == false){
					for(int i = 0; i< rightChild.numOfChildren;i++){
						Node<T> child = rightChild.getChild(i);
						leftChild.addChild(child);
						child.parent = rightChild;
					}
				}
			}

			//node is a leaf
			else{
				node.removeChild(index+1);
				node.removeKey(median);

				leftChild.addKey(median);
				for(int i = 0; i<rightChild.numOfKeys;i++){
					leftChild.addKey(rightChild.getKey(i));
				}
				if(rightChild.isLeaf() == false){
					for(int i = 0; i< rightChild.numOfChildren;i++){
						Node<T> child = rightChild.getChild(i);
						leftChild.addChild(child);
						child.parent = rightChild;
					}
				}
			}

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
		for(int i = 0; i< 12;i++){
			tree.insert(i);
			/*if(i == 10) */System.out.println(tree);
		}
		for(int i = 0; i< 3;i++){
			tree.Backtrack();
			/*if(i == 10) */System.out.println(tree);
		}
		tree.Backtrack();
		System.out.println(tree);
	}
}
