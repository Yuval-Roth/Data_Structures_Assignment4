import javax.naming.OperationNotSupportedException;
import java.util.Random;
import java.util.Scanner;

public class TreeTests {
    public static void main(String[] args) {
        backTrackingBTree_FindErorrs(5);

    }
    public static void backTrackingBTree_FindErorrs(int count) {
        int successCounter = 0;
        String[] memory = new String[count];
        while (true) {
            BacktrackingBTree<Integer> tree = new BacktrackingBTree<>();
            Random random = new Random();
            int[] nums = new int[count];
            for (int i = 0; i < count; ) {
                int num = random.nextInt(500) * (Math.random() > 0.5 ? 1 : -1);
                if (tree.contains(num) == false) {
                    tree.insert(num);
                    memory[i] = tree.toString();
                    nums[i] = num;
                    i++;
                }
            }

            int instanceTracker = 0;
            String currentState = tree.toString();
            try {
                for (; instanceTracker < count; instanceTracker++) {
                    try {
                        tree.Backtrack();
                        if(memory[count-1-instanceTracker].compareTo(tree.toString()) != 0){
                            throw new Exception("Expected and Actual are not the same");
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                }
                System.out.println("Instace " + successCounter + ": success.");
                successCounter++;
            } catch (Exception e) {
                System.out.println("==========================================================");
                System.out.println("Instace " + successCounter + ": FAIL.");
                System.out.println();
                System.out.print("input: "+numsArrayToString(nums));
                System.out.println();
                System.out.println("Expected: ");
                System.out.println(currentState);
                System.out.println(memory[instanceTracker]);
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("Actual: ");
                System.out.println(tree.toString());
                System.out.println("Show the failed instance? y/n");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.next();
                if (answer.compareTo("y") == 0){
                    showFailedInstanceBTree(nums);
                }
                break;
            }
        }
    }
    public static void showFailedInstanceBTree(int[] nums) {
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
            }
            catch (Exception e) {
                System.out.println(nums[nums.length-1-i] + " was meant to be removed and it failed");
                System.out.println("Exception occured here: " + e.getMessage());
                System.out.println();
                System.out.println("You can use debug your code by calling this from main: |  BtreeDebugging(new int[]"+numsArrayToString(nums)+");  |");
                break;
            }
            System.out.println("==========================================================");
        }
    }
    public static void BtreeDebugging(int[] nums) throws Exception {
         throw new Exception("not implemented yet");
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
