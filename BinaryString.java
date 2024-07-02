import java.util.*;
public class BinaryString {

static ArrayList<int[]> result = new ArrayList<int[]>();

public static void generateAllBinaryStrings(int n, int arr[], int i)
{
    
   
    if (i == n)
    {
       
        int[] combination = new int[n];
        for(int j = 0; j < n; j++){combination[j] = arr[j];}
        result.add(combination);
        return;
    }
    
 
    // First assign "0" at ith position
    // and try for all other permutations
    // for remaining positions
    arr[i] = 0;
    generateAllBinaryStrings(n, arr, i + 1);
 
    // And then assign "1" at ith position
    // and try for all other permutations
    // for remaining positions
    arr[i] = 1;
    generateAllBinaryStrings(n, arr, i + 1);

  
}

public static void printTheArray(int arr[], int n)
{
    for (int i = 0; i < n; i++)
    {
        System.out.print(arr[i]+" ");
    }
    System.out.println();
}

public static void printTheDoubleArray(double arr[], int n)
{
    for (int i = 0; i < n; i++)
    {
        System.out.print(arr[i]+" ");
    }
    System.out.println();
}
}
