import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

public class AVLTree_Rank_Select_Tests {

    public static void main(String[] args){
        main_tests();
    }
    public static boolean Select_Rank_Test(int count){

        BacktrackingAVL tree = new BacktrackingAVL();
        try{
            tree.Select(5);
        }
        catch(NullPointerException e){
            System.out.println("Empty tree Select() failed: "+e);
            return false;
        }
        catch(Exception e ){
        }
        int successCounter = 0;
        while (successCounter <= 30000000/(count*count)) {
            tree = new BacktrackingAVL();
            Random random = new Random();
            HashSet<Integer> set = new HashSet<>(count*2);
            int[] nums = new int[count];
            for (int i = 0; i < count; ) {
                int num = random.nextInt(500);
                if (set.contains(num) == false) {
                    set.add(num);
                    tree.insert(num);
                    nums[i] = num;
                    i++;
                }
            }
            nums = Arrays.stream(nums).sorted().toArray();
            int selectTracker = 0;
            try{
                for(; selectTracker < count; selectTracker++){
                    if(tree.Select(selectTracker+1) != nums[selectTracker]){
                        throw new Exception("mismatch at "+ (selectTracker+1));
                    }
                }
            }
            catch(Exception e){
                System.out.println("Select() failed, "+e+" | count: "+count);
                System.out.println("Failed at value: "+nums[selectTracker]);
                System.out.println("Expected: "+ nums[selectTracker]);
                try{
                    System.out.println("Actual: "+ tree.Select(selectTracker+1));
                }
                catch(Exception k){
                    System.out.println("Actual: "+ k.getMessage());
                }

                return false;
            }
            int rankTracker = 0;
            try{
                for(; rankTracker < count; rankTracker++){
                    if(tree.Rank(nums[rankTracker]) != rankTracker){
                        throw new Exception("mismatch at "+ rankTracker);
                    }
                }
            }
            catch(Exception e){
                System.out.println("Rank() failed, "+e+" | count: "+count);
                System.out.println("Failed at index: "+rankTracker);
                System.out.println("Expected: "+ rankTracker);
                System.out.println("Actual: "+ tree.Rank(nums[rankTracker]));
                return false;
            }
            successCounter++;
        }
        System.out.println("test passed for count: "+count);
        return true;
    }
    public static void main_tests(){
        if(Select_Rank_Test(5)){
            if(Select_Rank_Test(25)){
                if(Select_Rank_Test(50)){
                    if(Select_Rank_Test(250)){
                        System.out.println("LIKE A BAWS");
                    }
                }
            }
        }
    }
}
