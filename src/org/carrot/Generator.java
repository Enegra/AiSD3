package org.carrot;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by agnie on 4/26/2016.
 */
public class Generator {

    public Generator(){

    }

    public int[] generateRandomArray(int numbers){
        int[] array = new int[numbers];
        Random random = new Random();
        for (int i=0; i<array.length; i++){
            array[i] = random.nextInt(numbers * 4);
        }
        return array;
    }

    public int[] generatePartlySortedArray(int numbers, double threshold){
        int thresholdIndex = (int)(numbers*threshold);
        int[] partlySortedArray = new int[numbers];
        int[] sortedPart = generateSortedArray(thresholdIndex);
        System.arraycopy(sortedPart, 0, partlySortedArray, 0, thresholdIndex);
        int[] randomPart = generateRandomArray(numbers-thresholdIndex);
        System.arraycopy(randomPart, 0, partlySortedArray, thresholdIndex, numbers-thresholdIndex);
        return partlySortedArray;
    }

    public int[] generateSortedArray(int numbers){
        int[] array = generateRandomArray(numbers);
        Arrays.sort(array);
        return array;
    }

    public int[] generateDescendingArray(int numbers){
        int[] array = generateRandomArray(numbers);
        Arrays.sort(array);
        int[] reverseArray = new int[numbers];
        for (int i=0; i<reverseArray.length; i++){
            reverseArray[i] = array[numbers-1-i];
        }
        return reverseArray;
    }

}
