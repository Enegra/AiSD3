package org.carrot;

import java.util.ArrayList;

/**
 * Created by agnie on 4/28/2016.
 */
public class Tester {

    private ArrayList<Object> input;
    private ArrayList<Object> output;
    private ArrayList<Long> calculationTimes;
    private Generator generator = new Generator();
    private Sorter sorter = new Sorter();
    private long startTime;
    private long endTime;

    public Tester() {
        input = new ArrayList<Object>();
        output = new ArrayList<Object>();
        calculationTimes = new ArrayList<Long>();
    }


    public int[] runSorting(int algorithmNumber, int[] array) {
        switch (algorithmNumber) {
            case 0:
                return sorter.bubbleSort(array);
            case 1:
                return sorter.selectionSort(array);
            case 2:
                return sorter.insertionSort(array);
            case 3:
                return sorter.bucketSort(array);
            case 4:
                return sorter.mergeSort(array);
            case 5:
                return sorter.combSort(array);
            case 6:
                return sorter.shellSort(array);
            case 7:
                return sorter.quickSort(array);
            case 8:
                return sorter.radixSort(array);
            case 9:
                return sorter.librarySort(array);
        }
        return null;
    }

    private void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    private void setEndTime() {
        endTime = System.currentTimeMillis();
    }

    private long calculateTime() {
        return endTime - startTime;
    }

    private int[] generateArray(int variant, int numbers) {
        switch (variant) {
            case 1:
                return generator.generateRandomArray(numbers);
            case 2:
                return generator.generateSortedArray(numbers);
            case 3:
                return generator.generateDescendingArray(numbers);
        }
        return null;
    }

    private int[] generateArray(int numbers, double threshold) {
        return generator.generatePartlySortedArray(numbers, threshold);
    }

    public void test(int algorithmType, int variant, int numbers) {
        int inputArray[] = generateArray(variant, numbers);
        test(algorithmType, inputArray);
    }

    public void test(int algorithmType, int numbers, double threshold) {
        int inputArray[] = generateArray(numbers, threshold);
        test(algorithmType, inputArray);
    }

    private void test(int algorithmType, int[] inputArray) {
        input.add(inputArray);
        setStartTime();
        int[] outputArray = runSorting(algorithmType, inputArray);
        setEndTime();
        output.add(outputArray);
        long time = calculateTime();
        calculationTimes.add(time);
    }

    public void research(int iterations, int algorithmType, int variant, int numbers) {
        input.clear();
        output.clear();
        calculationTimes.clear();
        for (int i = 0; i < iterations; i++) {
            test(algorithmType, variant, numbers);
        }
    }

    public void research(int iterations, int algorithmType, int numbers, double threshold) {
        input.clear();
        output.clear();
        calculationTimes.clear();
        for (int i = 0; i < iterations; i++) {
            test(algorithmType, numbers, threshold);
        }
    }

    public long getWorstTime() {
        long worst = calculationTimes.get(0);
        for (Long time : calculationTimes) {
            if (time > worst) {
                worst = time;
            }
        }
        return worst;
    }

    public long getBestTime() {
        long best = calculationTimes.get(0);
        for (Long time : calculationTimes) {
            if (time < best) {
                best = time;
            }
        }
        return best;
    }

    public long getAverageTime() {
        long totalTime = 0;
        for (Long time : calculationTimes) {
            totalTime = totalTime + time;
        }
        return totalTime / calculationTimes.size();
    }


    public double getStandardDeviation() {
        long averageTime = getAverageTime();
        long differenceSum = 0;
        for (int i=0; i<calculationTimes.size(); i++){
            differenceSum = (long) (differenceSum + Math.pow((calculationTimes.get(i)-averageTime),2));
        }
        return Math.sqrt(differenceSum/calculationTimes.size());
    }

    public ArrayList<Long> getCalculationTimes() {
        return calculationTimes;
    }

    public ArrayList<Object> getInput() {
        return input;
    }

    public ArrayList<Object> getOutput() {
        return output;
    }
}
