import java.util.*;
import java.util.Deque;


public class AVLTree implements Iterable<Integer> {
    // You may edit the following nested class:

    public enum AVLActionType{
        INSERT,
        RIGHTROTATE,
        LEFTROTATE,
        ROTATION
    }
    protected class Node {

        //-------------------Node's Fields-------------------//
    	public Node left = null;
    	public Node right = null;
    	public Node parent = null;
    	public int height = 0;
    	public int value;
        protected int size = 1;

        //----------------Node's Constructor----------------//
    	public Node(int val) {
            this.value = val;
        }

        //-----------------Node's Methods-------------------//
        public void updateHeight() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        public int getBalanceFactor() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            return leftHeight - rightHeight;
        }
    }

    //----------------------AVL Tree's Fields-------------------------//
    protected Node root;
    
    //You may add fields here.
    protected Deque<Object> stack;

    //---------------------AVL Tree's Constructor--------------------//
    public AVLTree() {
    	this.root = null;
        stack = new LinkedList<>();
    }

    //----------------------AVL Tree's Methods----------------------//

    /*
     * IMPORTANT: You may add code to both "insert" and "insertNode" functions.
     */


	public void insert(int value) {
    	root = insertNode(root, value);
    }
	
	protected Node insertNode(Node node, int value) {
	    // Perform regular BST insertion
        if (node == null) {
        	Node insertedNode = new Node(value);
            stack.push(new Object[]{AVLActionType.INSERT, insertedNode});
            return insertedNode;
        }
        node.size++;

        if (value < node.value) {
            node.left  = insertNode(node.left, value);
            node.left.parent = node;
        }
        else {
            node.right = insertNode(node.right, value);
            node.right.parent = node;
        }
            
        node.updateHeight();

        /* 
         * Check For Imbalance, and fix according to the AVL-Tree Definition
         * If (balance > 1) -> Left Cases, (balance < -1) -> Right cases
         */
        
        int balance = node.getBalanceFactor();
        
        if (balance > 1) {
            if (value > node.left.value) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        } else if (balance < -1) {
            if (value < node.right.value) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }

        return node;
    }
    
	// You may add additional code to the next two functions.
    protected Node rotateRight(Node y) {

        stack.push(new Object[]{AVLActionType.ROTATION,y, AVLActionType.RIGHTROTATE});

        Node Parent = y.parent;
        boolean leftChild = false;
        if(Parent != null){
            if(Parent.left == y){
                leftChild = true;
            }
        }

        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;
        
        //Update parents
        if (T2 != null) {
        	T2.parent = y;
        }

        x.parent = y.parent;
        y.parent = x;
        
        y.updateHeight();
        x.updateHeight();

        if(Parent != null){
            if(leftChild){
                Parent.left = x;
            } else Parent.right = x;
        }
        else root = x;

        //update size
        int yLeftSize = 0;
        int yRightSize = 0;
        if(y.left != null) yLeftSize = y.left.size;
        if(y.right != null) yRightSize = y.right.size;

        y.size = yLeftSize + yRightSize + 1;

        int xLeftSize = 0;
        if(x.left != null) xLeftSize = x.left.size;

        x.size = xLeftSize + y.size + 1;
        // Return new root
        return x;
    }

    protected Node rotateLeft(Node x) {

        stack.push(new Object[]{AVLActionType.ROTATION,x,AVLActionType.LEFTROTATE});

        Node Parent = x.parent;
        boolean leftChild = false;
        if(Parent != null){
            if(Parent.left == x){
                leftChild = true;
            }
        }

        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;
        
        //Update parents
        if (T2 != null) {
        	T2.parent = x;
        }
        
        y.parent = x.parent;
        x.parent = y;
        
        x.updateHeight();
        y.updateHeight();

        if(Parent != null){
            if(leftChild){
                Parent.left = y;
            } else Parent.right = y;
        }
        else root = y;

        //update size
        int xLeftSize = 0;
        int xRightSize = 0;
        if(x.left != null) xLeftSize = x.left.size;
        if(x.right != null) xRightSize = x.right.size;

        x.size = xLeftSize + xRightSize + 1;

        int yRightSize = 0;
        if(y.right != null) yRightSize = y.right.size;

        y.size = yRightSize + x.size + 1;

        // Return new root
        return y;
    }
    
    public void printTree() {
    	TreePrinter.print(this.root);
    }

    /***
     * A Printer for the AVL-Tree. Helper Class for the method printTree().
     * Not relevant to the assignment.
     */
    private static class TreePrinter {
        private static void print(Node root) {
            if(root == null) {
                System.out.println("(XXXXXX)");
            } else {    
                final int height = root.height + 1;
                final int halfValueWidth = 4;
                int elements = 1;
                
                List<Node> currentLevel = new ArrayList<Node>(1);
                List<Node> nextLevel    = new ArrayList<Node>(2);
                currentLevel.add(root);
                
                // Iterating through the tree by level
                for(int i = 0; i < height; i++) {
                    String textBuffer = createSpaceBuffer(halfValueWidth * ((int)Math.pow(2, height-1-i) - 1));
        
                    // Print tree node elements
                    for(Node n : currentLevel) {
                        System.out.print(textBuffer);
        
                        if(n == null) {
                            System.out.print("        ");
                            nextLevel.add(null);
                            nextLevel.add(null);
                        } else {
                            System.out.printf("(%6d)", n.value);
                            nextLevel.add(n.left);
                            nextLevel.add(n.right);
                        }
                        
                        System.out.print(textBuffer);
                    }
        
                    System.out.println();
                    
                    if(i < height - 1) {
                        printNodeConnectors(currentLevel, textBuffer);
                    }
        
                    elements *= 2;
                    currentLevel = nextLevel;
                    nextLevel = new ArrayList<Node>(elements);
                }
            }
        }
        
        private static String createSpaceBuffer(int size) {
            char[] buff = new char[size];
            Arrays.fill(buff, ' ');
            
            return new String(buff);
        }
        
        private static void printNodeConnectors(List<Node> current, String textBuffer) {
            for(Node n : current) {
                System.out.print(textBuffer);
                if(n == null) {
                    System.out.print("        ");
                } else {
                    System.out.printf("%s      %s",
                            n.left == null ? " " : "/", n.right == null ? " " : "\\");
                }
    
                System.out.print(textBuffer);
            }
    
            System.out.println();
        }
    }
    public String toString()
    {
        if (root != null) return toString(root,"  ","");
        else return("EmptyTree");
    }
    private String toString(AVLTree.Node current,String spaces,String output)
    {
        if(current.right != null) output = toString(current.right,spaces + "        ",output);

        if (current.parent != null) output += spaces + current.value+"("+current.parent.value+":"+current.size+")"+"\n";
        else output += spaces + current.value+"(root:"+current.size+")"+"\n";

        if(current.left != null) output = toString(current.left,spaces + "        ",output);
        return output;
    }

    /***
     * A base class for any Iterator over Binary-Search Tree.
     * Not relevant to the assignment, but may be interesting to read!
     * DO NOT WRITE CODE IN THE ITERATORS, THIS MAY FAIL THE AUTOMATIC TESTS!!!
     */
    private abstract class BaseBSTIterator implements Iterator<Integer> {
        private List<Integer> values;
        private int index;
        public BaseBSTIterator(Node root) {
            values = new ArrayList<>();
            addValues(root);
            
            index = 0;
        }
        
        @Override
        public boolean hasNext() {
            return index < values.size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return values.get(index++);
        }
        
        protected void addNode(Node node) {
            values.add(node.value);
        }
        
        abstract protected void addValues(Node node);
    }
    
    public class InorderIterator extends BaseBSTIterator {
        public InorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(Node node) {
            if (node != null) {
                addValues(node.left);
                addNode(node);
                addValues(node.right);
            }
        }    
      
    }
    
    public class PreorderIterator extends BaseBSTIterator {

        public PreorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(AVLTree.Node node) {
            if (node != null) {
                addNode(node);
                addValues(node.left);
                addValues(node.right);
            }
        }        
    }
    
    @Override
    public Iterator<Integer> iterator() {
        return getInorderIterator();
    }
    
    public Iterator<Integer> getInorderIterator() {
        return new InorderIterator(this.root);
    }
    
    public Iterator<Integer> getPreorderIterator() {
        return new PreorderIterator(this.root);
    }
}
