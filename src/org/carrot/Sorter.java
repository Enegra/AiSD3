package org.carrot;

import java.util.ArrayList;

/**
 * Created by agnie on 4/8/2016.
 */
public class Sorter {

    public int[] bubbleSort(int[] unsortedArray) {
        //w tym algorytmie zamieniane są miejscami sąsiednie elementy, jeżeli następny jest mniejszy od poprzedniego
        //zewnętrzna pętla będzie wykonywana tak długo jak będą nieuporządkowane elementy do zamiany
        //jeżeli nie zostaną znalezione elementy do zamiany, flaga pozostanie false i pętla zakończy działanie
        int[] array = unsortedArray.clone();
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return array;
    }

    public int[] selectionSort(int[] unsortedArray) {
        //dla każdego indeksu w tablicy algorytm przechodzi przez resztę tablicy i wyszukuje najmniejszy indeks, po czym zamienia go miejscami z obecnym elementem
        int[] array = unsortedArray.clone();
        for (int i = 0; i < array.length - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[smallest]) {
                    smallest = j;
                }
            }
            if (smallest != i) {
                int temp = array[i];
                array[i] = array[smallest];
                array[smallest] = temp;
            }
        }
        return array;
    }

    public int[] insertionSort(int[] unsortedArray) {
        //kolejno jest brany każdy element w tablicy, po czym przesuwany jest na odpowiednie miejsce w posortowanej już części
        // przechodzi się przez posortowaną część i dopóki element jest mniejszy od poprzedniego następuje zamiana miejsc
        int[] array = unsortedArray.clone();
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        return array;
    }

    public int[] bucketSort(int[] unsortedArray, int bucketCount) {
        // lista elementów do posortowania jest dzielona na n mniejszych list, zwanych "kubełkami"
        // każdy z tych kubełków jest sortowany przy użyciu jednego z prostszych algorytmów, najczęściej jest to insertion sort
        // nastepnie te kubełki są łączone ponownie w całość
        int[] array = unsortedArray.clone();
        ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>(bucketCount);
        if (bucketCount <= 0) throw new IllegalArgumentException("Invalid bucket count");
        int min = array[0];
        int max = array[0];
        for (int element : array) {
            if (element > max) {
                max = element;
            }
            if (element < min) {
                min = element;
            }
        }
        int range = (max - min) / bucketCount + 1;
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Integer>());
        }
        for (int element : array) {
            buckets.get((element - min) / range).add(element);
        }
        int currentIndex = 0;
        // tutaj niestety musiałam przeiterowac kubełki zamiast uzyc toArray() ze względu na to, że tablica miała prymitywny typ
        for (int i = 0; i < buckets.size(); i++) {
            int[] bucket = new int[buckets.get(i).size()];
            for (int j = 0; j < bucket.length; j++) {
                bucket[j] = buckets.get(i).get(j);
            }
            bucket = insertionSort(bucket);
            for (int element : bucket) {
                array[currentIndex++] = element;
            }
        }
        return array;
    }

    public int[] bucketSort(int[] unsortedArray) {
        int bucketCount = (int) Math.sqrt(unsortedArray.length);
        return bucketSort(unsortedArray, bucketCount);
    }

    public int[] mergeSort(int[] unsortedArray) {
        //jest to klasyczny przykład "dziel i rządź", tablica jest rekurencyjnie dzielona na mniejsze części
        int[] array = unsortedArray.clone();
        if (array.length > 1) {
            int leftHalfCount = array.length / 2;
            int rightHalfCount;
            if (array.length % 2 == 1) {
                rightHalfCount = leftHalfCount + 1;
            } else {
                rightHalfCount = leftHalfCount;
            }
            int[] leftHalf = new int[leftHalfCount];
            int[] rightHalf = new int[rightHalfCount];
            //kopiowanie elementów tablicy do połówek
            System.arraycopy(array, 0, leftHalf, 0, leftHalfCount);
            System.arraycopy(array, leftHalfCount, rightHalf, 0, rightHalfCount);
            //rekurencyjne wywołanie merge sortu
            leftHalf = mergeSort(leftHalf);
            rightHalf = mergeSort(rightHalf);
            int iMain = 0, iLeft = 0, iRight = 0;
            // dopóki jedna z tablic nie będzie pusta
            while (leftHalf.length != iLeft && rightHalf.length != iRight) {
                if (leftHalf[iLeft] < rightHalf[iRight]) {
                    array[iMain] = leftHalf[iLeft];
                    iMain++;
                    iLeft++;
                } else {
                    array[iMain] = rightHalf[iRight];
                    iMain++;
                    iRight++;
                }
            } // po zakończeniu działania pętli reszta elementów w pozostałej tablicy ma posortowane elementy, więc mozna je po prostu dokleić
            while (leftHalf.length != iLeft) {
                array[iMain] = leftHalf[iLeft];
                iMain++;
                iLeft++;
            }
            while (rightHalf.length != iRight) {
                array[iMain] = rightHalf[iRight];
                iMain++;
                iRight++;
            }
        }
        return array;
    }

    public int[] combSort(int[] unsortedArray) {
        //comb sort jest ulepszeniem bubble sort, działa tak samo tyle że zamiast przeszukiwania co 1 element jest większy odstęp,który jest zmniejszany przy każdej iteracji
        int[] array = unsortedArray.clone();
        int gap = array.length;
        double gapCoefficient = 1.3;
        boolean swapped = true;
        while (swapped || gap > 1) {
            if (gap > 1) {
                gap = (int) (gap / gapCoefficient);
            }
            swapped = false;
            for (int i = 0; i + gap < array.length; i++) {
                if (array[i] > array[i + gap]) {
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;
                    swapped = true;
                }
            }
        }
        return array;
    }

    public int[] shellSort(int[] unsortedArray) {
        int[] array = unsortedArray.clone();
        //shellsort zaczyna od zamiany elementów w duzych odstępach do coraz mniejszych
        int gap = array.length / 2;
        // pętla będzie wykonywana dopóki odstęp jest większy od 0, ostatnia iteracja będzie mieć odstęp 1
        while (gap > 0) {
            for (int i = 0; i < array.length - gap; i++) {
                int j = i + gap;
                int temp = array[j];
                //jeżeli wartość pod temp jest mniejsza id wartości pod różnicą pierwotnego indeksu temp - odstępu, następuje zamiana elementów
                while (j >= gap && array[j - gap] >= temp) {
                    array[j] = array[j - gap];
                    j = j - gap;
                }
                array[j] = temp;
            }
            if (gap == 2) {
                gap = 1;
            } else {
                gap = (int) (gap / 2.2);
            }
        }
        return array;
    }

    public int[] quickSort(int[] unsortedArray) {
        int[] array = unsortedArray.clone();
        quicksort(array, 0, array.length - 1);
        return array;
    }

    private int[] quicksort(int[] unsortedArray, int left, int right) {
        if (right <= left) {
            return unsortedArray;
        }
        int i = left, j = right;
        //ze środka tablicy wybierana jest kotwica (pivot)
        int pivot = unsortedArray[left + (right - left) / 2];
        while (i <= j) {
            //jeżeli wartość z lewej jest mniejsza od kotwicy następuje przejście do następnego elementu w lewej liście
            while (unsortedArray[i] < pivot) {
                i++;
            }
            //jeżeli wartość z prawej jest większa od kotwicy następuje przejście do następnego elementu w prawejliście
            while (unsortedArray[j] > pivot) {
                j--;
            }
            //jeżeli wartość z lewej jest większa od kotwicy, a z prawej mniejsza następuje zamiana
            if (i <= j) {
                int temp = unsortedArray[i];
                unsortedArray[i] = unsortedArray[j];
                unsortedArray[j] = temp;
                i++;
                j--;
            }
        }
        if (left < j) {
            quicksort(unsortedArray, left, j);
        }
        if (i < right) {
            quicksort(unsortedArray, i, right);
        }
        return unsortedArray;
    }

    public int[] radixSort(int[] unsortedArray) {
        if (unsortedArray.length <= 1) {
            return unsortedArray;
        }
        int[] array = unsortedArray.clone();
        //ustalenie największej liczby by znać ilość cyfr
        int max = getMax(array);
        //sortowanie dla każdej cyfry
        for (int exponent = 1; max / exponent > 0; exponent *= 10) {
            array = countSort(array, exponent);
        }
        return array;
    }

    private int[] countSort(int[] array, int exponent) {
        // zliczanie ile liczb w tablicy ma poszczególne cyfry
        int count[] = new int[10];
        int[] sortedByDigits = new int[array.length];
        for (int i = 0; i < array.length; i++)
            count[(array[i] / exponent) % 10]++;
        //ustalenie miejsca w posortowanej tablicy
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        //sortowanie
        for (int i = array.length - 1; i >= 0; i--) {
            sortedByDigits[count[(array[i] / exponent) % 10] - 1] = array[i];
            count[(array[i] / exponent) % 10]--;
        }
        array = sortedByDigits;
        return array;
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int element : array) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public int[] librarySort(int[] unsortedArray) {
        int[] array = unsortedArray.clone();
        //na początku tworzona jest kopia tablicy zawierająca odstępy
        int[] bookshelf = new int[array.length * 2 - 1];
        for (int i = 0; i < array.length; i++) {
            bookshelf[2 * i] = array[i];
        }
        //działanie identyczne jak zwykły insertion sort, tyle że po każdym wstawianiu półka jest porzadkowana na nowo
        for (int i = 1; i < array.length; i++) {
            for (int j = 2*i; j > 0; j=j-2) {
                if (bookshelf[j] < bookshelf[j-2]) {
                    int temp = bookshelf[j];
                    bookshelf[j] = bookshelf[j-2];
                    bookshelf[j-2] = temp;
                    rebalance(bookshelf, j-2, j);
                }
            }
        }
        //zwracana jest tablica bez odstępów
        for (int i=0; i<array.length; i++){
            array[i] = bookshelf[2*i];
        }
        return array;
    }


    public int[] rebalance(int[] array, int begin, int end) {
        //po każdej zamianie elementów w jednym miejscu będą 3 zera, a w innym będzie brakować dwóch
        //wstawiamy zera na odpowiednie miejsca, po czym przesuwamy resztę tablicy do mniejsca, w którym jest ok o 2 indeksy
        int[] newArray = array.clone();
        newArray[begin] = array[end];
        newArray[begin+1] = array[begin];
        newArray[begin+2] = array[end-1];

        for (int i=begin+4; i<end+2; i++){
            newArray[i] = array[i-2];
        }
        return newArray;
    }

}
