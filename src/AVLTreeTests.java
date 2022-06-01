import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;



/**
 *&nbsp;&nbsp;&nbsp;&nbsp;    Instructions:                                       <br/><br/>
 *
 * mainTests() will run a set of tests of random instances of insertions
 * and backtrackings.                                                             <br/><br/>
 * The tests will halt if the algorithm finds a mistake in the backtracking
 * and will prompt you to show the failed test and explain why it failed.         <br/><br/>
 *
 * At the end of the failed instance presentation, the last line will have
 * a function call printed out which you can copy and paste to main
 * and run it to debug the failed test
 *
 * You can edit BtreeDebugging() as much as you like for debugging but don't
 * edit anything else.
 *
 * @author Yuval Roth
 */
public class AVLTreeTests {
    public static void main(String[] args) {

        boolean Read_The_Instructions = false;
        if(Read_The_Instructions == false) throw new RuntimeException("READ THE INSTRUCTIONS FIRST!");

        // main tests
        mainTests();

//        showFailedInstanceAVLTree(new int[]{399, -307, 41, -331, 481});


        /**   example call from end of failed instance presentation  **/
        //AVLTreeDebugging(new int[]{125, -457, 383, -362, 438});
    }

    public static void AVLTreeDebugging(int[] nums){

        /** You can add code here for debugging
         * The nums array contains the inserted numbers by the order of indexes **/

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

    //==========================================
    // DO NOT EDIT THE FUNCTIONS BELOW THIS LINE
    //==========================================

    public static boolean backTrackingAVLTree_FindErrors(int count,int t) {

        /** DON'T EDIT THIS FUNCTION */

        int successCounter = 0;
        while (successCounter <= 30000000/(count*count)) {
            String[] memory = new String[count];
            BacktrackingAVL tree = new BacktrackingAVL();
            Random random = new Random();
            HashSet<Integer> set = new HashSet<>(count*2);
            int[] nums = new int[count];
            for (int i = 0; i < count; ) {
                int num = random.nextInt(500) * (Math.random() > 0.5 ? 1 : -1);
                if (set.contains(num) == false) {
                    memory[i] = treeToString(tree);
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
                        if(memory[count-instanceTracker].compareTo(treeToString(tree)) != 0 ){
                            throw new Exception("Expected and Actual are not the same");
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
                successCounter++;
            } catch (Exception e) {
                System.out.println("==========================================================");
                System.out.println("Instace " + successCounter + ": FAIL.");
                System.out.println("Failed at BackTrack number "+instanceTracker);
                System.out.print("input: "+numsArrayToString(nums));
                System.out.println();
                System.out.println("Expected: ");
                System.out.println(memory[count-instanceTracker]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual :");
                System.out.println(treeToString(tree));
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
    public static boolean showFailedInstanceAVLTree(int[] nums, String[] memory, int instanceTracker) {

        /** DON'T EDIT THIS FUNCTION */


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
        System.out.println(treeToString(tree));
        System.out.println("==========================================================");
        for (int i = 0; i < count; i++)
        {
            try
            {
                tree.Backtrack();
                System.out.println("Allegedly Removed: " + nums[nums.length-1-i]);
                System.out.println();
                System.out.println(treeToString(tree));
                System.out.println();
                if(memory[count-1-i].compareTo(treeToString(tree)) != 0 ){
                    throw new Exception("Expected and Actual are not the same");
                }
            }
            catch (Exception e) {
                System.out.println(nums[nums.length-1-i] + " was meant to be removed and it failed");
                System.out.println("Exception occured here: " + e.getMessage());
                System.out.println();
                System.out.println("Expected: ");
                System.out.println(memory[count-instanceTracker]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual :");
                System.out.println(treeToString(tree));
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

        /** DON'T EDIT THIS FUNCTION */

        String numsArray = "{";
        for (int i = 0; i < nums.length; i++) {
            if (i != nums.length - 1)
                numsArray+=(nums[i] + ", ");
            else numsArray+=(nums[i]);
        }
        numsArray+="}";
        return numsArray;
    }
    public static String treeToString(AVLTree tree) {

        /** DON'T EDIT THIS FUNCTION */


        if (tree.root != null) return treeToString(tree.root,"  ","");
        else return("EmptyTree");
    }
    private static String treeToString(AVLTree.Node current,String spaces,String output) {

        /** DON'T EDIT THIS FUNCTION */

        if(current.right != null) output = treeToString(current.right,spaces + "        ",output);

        if (current.parent != null) output += spaces + current.value+"("+current.parent.value+")"+"\n";
        else output += spaces + current.value+"(root)"+"\n";

        if(current.left != null) output = treeToString(current.left,spaces + "        ",output);
        return output;
    }
    public static void mainTests(){

        /** DON'T EDIT THIS FUNCTION */

        int t2 = 2; //degree 2
        int t5 = 5; // degree 5

        System.out.println("Test started! this might take a while...");
        if(backTrackingAVLTree_FindErrors(5,t2)){
            if(backTrackingAVLTree_FindErrors(15,t2)){
                if(backTrackingAVLTree_FindErrors(50,t2)){
                    if(backTrackingAVLTree_FindErrors(250,t2)){
                        if(backTrackingAVLTree_FindErrors(5,t5)){
                            if(backTrackingAVLTree_FindErrors(15,t5)){
                                if(backTrackingAVLTree_FindErrors(50,t5)){
                                    if(backTrackingAVLTree_FindErrors(250,t5)){
                                        System.out.println("YOU ARE THE FUCKING BEST!");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
