import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public class harkkatyö1 {

    public static void insertionSort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; ++i) {
            int key = data[i];
            int j = i - 1;

            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }

    public static void bubbleSort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    public static void shellSort(int[] data) {
        int n = data.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {

            for (int i = gap; i < n; i++) {
                int key = data[i];
                int j = i;

                while (j >= gap && data[j - gap] > key) {
                    data[j] = data[j - gap];
                    j -= gap;
                }
                data[j] = key;
            }
        }
    }

    public static void quickSort(int[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private static void quickSort(int[] a, int low, int high) {
        while (low < high) {
            int pivot = a[low + (high - low) / 2];
            int i = low;
            int j = high;

            while (i <= j) {
                while (a[i] < pivot) i++;
                while (a[j] > pivot) j--;
                if (i <= j) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    i++;
                    j--;
                }
            }

            if (j - low < high - i) {
                quickSort(a, low, j);
                low = i;
            } else {
                quickSort(a, i, high);
                high = j;
            }
        }
    }

    public static void mergeSort(int[] data) {
        int[] temp = new int[data.length];
        mergeSort(data, temp, 0, data.length - 1);
    }

    private static void mergeSort(int[] a, int[] temp, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;
        mergeSort(a, temp, low, mid);
        mergeSort(a, temp, mid + 1, high);

        if (a[mid] <= a[mid + 1]) {
            return;
        }

        merge(a, temp, low, mid, high);
    }

    private static void merge(int[] a, int[] temp, int low, int mid, int high) {
        int i = low;
        int j = mid + 1;
        int k = low;

        while (i <= mid && j <= high) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }

        for (k = low; k <= high; k++) {
            a[k] = temp[k];
        }
    }

    private static final int INSERTION_CUTOFF = 16;

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void insertionSortRange(int[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    public static void quickSortOptimized(int[] data) {
        quickSortOptimized(data, 0, data.length - 1);
    }

    private static void quickSortOptimized(int[] a, int low, int high) {
        while (high - low >= INSERTION_CUTOFF) {
            int mid = low + (high - low) / 2;
            if (a[mid] < a[low])  swap(a, mid, low);
            if (a[high] < a[low]) swap(a, high, low);
            if (a[high] < a[mid]) swap(a, high, mid);
            int pivot = a[mid];

            int i = low;
            int j = high;
            while (i <= j) {
                while (a[i] < pivot) i++;
                while (a[j] > pivot) j--;
                if (i <= j) {
                    swap(a, i, j);
                    i++;
                    j--;
                }
            }

            if (j - low < high - i) {
                quickSortOptimized(a, low, j);
                low = i;
            } else {
                quickSortOptimized(a, i, high);
                high = j;
            }
        }

        insertionSortRange(a, low, high);
    }

    public static void printData(int[] data) {
        for (int x : data) {
            System.out.print(x + " ");
        }
        System.out.println("--------------------------");
        System.out.println();
    }

    public static int[] CreateAscendingTable(int n) {
        int[] data = new int[n];

        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }

        return data;
    }

    public static int[] CreateDescendingTable(int n) {
        int[] data = new int[n];

        for (int i = 0; i < data.length; i++) {
            data[i] = n - 1 - i;
        }

        return data;
    }

    public static int[] createRandomData(int n) {
        int[] data = new int[n];

        Random gen = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = gen.nextInt(n);
        }

        return data;
    }

    public static double measure(Runnable action) {
        long start = System.nanoTime();
        action.run();
        long stop = System.nanoTime();
        return (stop - start) / 1000000000.0;
    }

    public static void timeSort(String name, Consumer<int[]> sorter, int[] original) {
        int[] copy = Arrays.copyOf(original, original.length);
        double seconds = measure(() -> sorter.accept(copy));
        System.out.println("  " + name + " " + seconds);
    }

    public static void runTest(String label, IntFunction<int[]> creator, int size,
                               Map<String, Consumer<int[]>> sorters) {
        System.out.println(label);

        int[][] data = new int[1][];
        double createTime = measure(() -> data[0] = creator.apply(size));
        System.out.println("  Datan luonti " + createTime);

        for (Map.Entry<String, Consumer<int[]>> sorter : sorters.entrySet()) {
            timeSort(sorter.getKey(), sorter.getValue(), data[0]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 100000;
        Map<String, Consumer<int[]>> sorters = new LinkedHashMap<>();
        sorters.put("Insertion sort", SortTest::insertionSort);
        sorters.put("Bubble sort", SortTest::bubbleSort);
        sorters.put("Shell sort", SortTest::shellSort);
        sorters.put("Quick sort", SortTest::quickSort);
        sorters.put("Quick sort (optimoitu)", SortTest::quickSortOptimized);
        sorters.put("Merge sort", SortTest::mergeSort);
        sorters.put("Arrays.sort", a -> Arrays.sort(a));

        Map<String, IntFunction<int[]>> creators = new LinkedHashMap<>();
        creators.put("Satunnainen data", SortTest::createRandomData);
        creators.put("Nouseva data", SortTest::CreateAscendingTable);
        creators.put("Laskeva data", SortTest::CreateDescendingTable);

        for (Map.Entry<String, IntFunction<int[]>> creator : creators.entrySet()) {
            runTest(creator.getKey(), creator.getValue(), size, sorters);
        }
    }
}
