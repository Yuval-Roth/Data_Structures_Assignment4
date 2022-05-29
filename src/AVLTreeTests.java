import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

public class AVLTreeTests {
    public static void main(String[] args) {
//        System.out.println("Test started! this might take a while...");
//        if(backTrackingAVLTree_FindErrors(5,2)){
//            if(backTrackingAVLTree_FindErrors(15,2)){
//                if(backTrackingAVLTree_FindErrors(50,2)){
//                    if(backTrackingAVLTree_FindErrors(250,2)){
//                        if(backTrackingAVLTree_FindErrors(5,5)){
//                            if(backTrackingAVLTree_FindErrors(15,5)){
//                                if(backTrackingAVLTree_FindErrors(50,5)){
//                                    if(backTrackingAVLTree_FindErrors(250,5)){
//                                        System.out.println("YOU ARE THE FUCKING BEST!");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        AVLTreeDebugging(new int[]{125, -457, 383, -362, 438});









    }
    public static void AVLTreeDebugging(int[] nums){
        BacktrackingAVL tree = new BacktrackingAVL();
        for (int i = 0; i < nums.length; i++) {
            tree.insert(nums[i]);
        }
        tree.printTree();
        System.out.println("=====================");
        for(int i = 0; i< nums.length;i++){
            tree.Backtrack();
            tree.printTree();
            System.out.println("=====================");
        }
    }


    public static boolean backTrackingAVLTree_FindErrors(int count,int t) {
        int successCounter = 0;
        while (successCounter <= 30000000/(count*count)) {
            String[][] memory = new String[count][2];
            BacktrackingAVL tree = new BacktrackingAVL();
            Random random = new Random();
            HashSet<Integer> set = new HashSet<>(count*2);
            int[] nums = new int[count];
            for (int i = 0; i < count; ) {
                int num = random.nextInt(500) * (Math.random() > 0.5 ? 1 : -1);
                if (set.contains(num) == false) {
                    memory[i][0] = inOrderToString(tree);
                    memory[i][1] = preOrderToString(tree);
                    set.add(num);
                    tree.insert(num);
                    nums[i] = num;
                    i++;
                }
            }

            int instanceTracker = 1;
            try {
                for (; instanceTracker <= count; instanceTracker++) {
                    try {
                        tree.Backtrack();
                        if(memory[count-instanceTracker][0].compareTo(inOrderToString(tree)) != 0 |
                                memory[count-instanceTracker][1].compareTo(preOrderToString(tree)) != 0){
                            throw new Exception("Expected and Actual are not the same");
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
//                System.out.println("Instace " + successCounter + ": success.");
                successCounter++;
            } catch (Exception e) {
                System.out.println("==========================================================");
                System.out.println("Instace " + successCounter + ": FAIL.");
                System.out.println("Failed at BackTrack number "+instanceTracker);
                System.out.print("input: "+numsArrayToString(nums));
                System.out.println();
                System.out.println("Expected in order: ");
                System.out.println(memory[count-1-instanceTracker-1][0]);
                System.out.println("Expected pre order: ");
                System.out.println(memory[count-1-instanceTracker-1][1]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual in order: ");
                System.out.println(inOrderToString(tree));
                System.out.println("Actual pre order: ");
                System.out.println(preOrderToString(tree));
                System.out.println();
                System.out.println("Show the failed instance? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.next();
                if (answer.compareTo("y") == 0){
                    return showFailedInstanceAVLTree(nums,memory,instanceTracker);
                }
                return false;
            }
        }
        System.out.println("Test passed: t: "+t+", count: "+count);
        return true;
    }
    public static boolean showFailedInstanceAVLTree(int[] nums, String[][] memory, int instanceTracker) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("======================================================");
        System.out.println("Below is the failed instance:");
        System.out.println();
        int count = nums.length;
        BacktrackingAVL tree = new BacktrackingAVL();
        for (int i = 0; i < count; i++)
        {
            tree.insert(nums[i]);
        }

        System.out.println("input: "+numsArrayToString(nums));
        System.out.println("==========================================================");
        System.out.println("Starting position after all insertions:");
        System.out.println();
        tree.printTree();
        System.out.println("==========================================================");
        for (int i = 0; i < count; i++)
        {
            try
            {
                tree.Backtrack();
                System.out.println("Removed: " + nums[nums.length-1-i]);
                System.out.println();
                tree.printTree();
                System.out.println();
                if(memory[count-instanceTracker][0].compareTo(inOrderToString(tree)) != 0 |
                        memory[count-instanceTracker][1].compareTo(preOrderToString(tree)) != 0){
                    throw new Exception("Expected and Actual are not the same");
                }
            }
            catch (Exception e) {
//                System.out.println("==========================================================");
                System.out.println(nums[nums.length-1-i] + " was meant to be removed and it failed");
                System.out.println("Exception occured here: " + e.getMessage());
                System.out.println();
                System.out.println("Expected in order: ");
                System.out.println(memory[count-1-instanceTracker-1][0]);
                System.out.println("Expected pre order: ");
                System.out.println(memory[count-1-instanceTracker-1][1]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual in order: ");
                System.out.println(inOrderToString(tree));
                System.out.println("Actual pre order: ");
                System.out.println(preOrderToString(tree));
                System.out.println();
                System.out.println("You can debug your code by calling this from main: |  AVLTreeDebugging(new int[]"+numsArrayToString(nums)+");  |");
                return false;
            }
            System.out.println("==========================================================");
            return false;
        }
        return false;
    }
    public static String numsArrayToString(int[] nums){
        String numsArray = "{";
        for (int i = 0; i < nums.length; i++) {
            if (i != nums.length - 1)
                numsArray+=(nums[i] + ", ");
            else numsArray+=(nums[i]);
        }
        numsArray+="}";
        return numsArray;
    }
    public static String inOrderToString(AVLTree tree){
        if(tree.root != null)
            return inOrderToString(tree.root,"");
        else return "Empty Tree";
    }
    private static String inOrderToString(AVLTree.Node current,String output){
        if(current.left != null) output = inOrderToString(current.left,output);
        output+= current.value+", ";
        if(current.right != null) output = inOrderToString(current.right,output);
        return output;
    }
    public static String preOrderToString(AVLTree tree){
        if(tree.root != null)
            return preOrderToString(tree.root,"");
        else return "Empty Tree";
    }
    private static String preOrderToString(AVLTree.Node current,String output){
        output+= current.value+", ";
        if(current.left != null) output = preOrderToString(current.left,output);
        if(current.right != null) output = preOrderToString(current.right,output);
        return output;
    }
}
