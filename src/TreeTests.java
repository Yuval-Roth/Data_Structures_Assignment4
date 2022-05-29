import javax.naming.OperationNotSupportedException;
import java.util.Random;
import java.util.Scanner;

public class TreeTests {
    public static void main(String[] args) {
        backTrackingBTree_FindErorrs(5,2);
        backTrackingBTree_FindErorrs(15,2);
        backTrackingBTree_FindErorrs(50,2);
        backTrackingBTree_FindErorrs(250,2);
        backTrackingBTree_FindErorrs(5,5);
        backTrackingBTree_FindErorrs(15,5);
        backTrackingBTree_FindErorrs(50,5);
        backTrackingBTree_FindErorrs(250,5);

    }
    public static void BtreeDebugging(int[] nums){
        BacktrackingBTree<Integer> tree = new BacktrackingBTree<>();
        Random random = new Random();
        for (int i = 0; i < nums.length; ) {
            int num = random.nextInt(500) * (Math.random() > 0.5 ? 1 : -1);
            if (tree.contains(num) == false) {
                tree.insert(num);
                nums[i] = num;
                i++;
            }
        }
        for(int i = 0; i< nums.length;i++){
            tree.Backtrack();
        }
    }


    public static void backTrackingBTree_FindErorrs(int count,int t) {
        int successCounter = 0;
        while (successCounter <= 1000000/count) {
            String[] memory = new String[count];
            BacktrackingBTree<Integer> tree = new BacktrackingBTree<>(t);
            Random random = new Random();
            int[] nums = new int[count];
            for (int i = 0; i < count; ) {
                int num = random.nextInt(500) * (Math.random() > 0.5 ? 1 : -1);
                if (tree.contains(num) == false) {
                    memory[i] = tree.toString();
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
                        if(memory[count-instanceTracker].compareTo(tree.toString()) != 0){
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
                System.out.println("Expected: ");
                System.out.println(memory[count-instanceTracker]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual: ");
                System.out.println(tree.toString());
                System.out.println("Show the failed instance? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.next();
                if (answer.compareTo("y") == 0){
                    showFailedInstanceBTree(nums,memory,instanceTracker);
                }
                break;
            }
        }
        System.out.println("Test passed: t: "+t+", count: "+count);
    }
    public static void showFailedInstanceBTree(int[] nums, String[] memory, int instanceTracker) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("======================================================");
        System.out.println("Below is the failed instance:");
        System.out.println();
        int count = nums.length;
        BacktrackingBTree<Integer> tree = new BacktrackingBTree<>();
        for (int i = 0; i < count; i++)
        {
            tree.insert(nums[i]);
        }

        System.out.println("input: "+numsArrayToString(nums));
        System.out.println("==========================================================");
        System.out.println("Starting position after all insertions:");
        System.out.println();
        System.out.print(tree);
        System.out.println("==========================================================");
        for (int i = 0; i < count; i++)
        {
            try
            {
                tree.Backtrack();
                System.out.println("Removed: " + nums[nums.length-1-i]);
                System.out.println();
                System.out.println(tree);
                System.out.println();
                if(memory[count-1-instanceTracker-1].compareTo(tree.toString()) != 0){
                    throw new Exception("Expected and Actual are not the same");
                }
            }
            catch (Exception e) {
                System.out.println("==========================================================");
                System.out.println(nums[nums.length-1-i] + " was meant to be removed and it failed");
                System.out.println("Exception occured here: " + e.getMessage());
                System.out.println();
                System.out.println("Expected: ");
                System.out.println(memory[count-1-instanceTracker-1]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual: ");
                System.out.println(tree.toString());
                System.out.println();
                System.out.println("You can debug your code by calling this from main: |  BtreeDebugging(new int[]"+numsArrayToString(nums)+");  |");
                break;
            }
            System.out.println("==========================================================");
        }
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
}
