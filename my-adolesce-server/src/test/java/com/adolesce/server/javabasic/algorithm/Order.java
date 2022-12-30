package com.adolesce.server.javabasic.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/11/13 10:13
 */
public class Order {
    //排序算法：https://blog.csdn.net/java_atguigu/article/details/123919491
    @Test
    public void testSort() {
        int[] arr = {35, 12, 44, 33, 97, 34, 2, 87, 23, 11, 42, 85, 5, 90, 75, 3, 26, 13, 45, 98, 55, 1, 96, 53};
        //1、冒泡排序
        //bubbleSortOpt(arr);

        //2、快速排序
        //quickSort(arr,0,arr.length-1);

        //3、直接插入排序
        //insertSort(arr);

        //4、希尔排序
        //shellSort(arr);

        //5、简单选择排序
        //selectSort(arr);

        //6、堆排序
        //heapSort(arr);

        //7、归并排序
        mergeSort(arr, 0, arr.length - 1);

        //8、基数排序
        //radixSort(arr);

        Arrays.stream(arr).forEach(a -> System.out.print(a + " "));
    }


    /**
     * 排序算法-1：冒泡排序（隶属于：交换排序）
     *
     * @param arr 待排序数组
     *            <p>
     *            核心思想（以升序排序为例）：
     *            1、从第一个元素开始，比较相邻的两个元素。如果第一个比第二个大，则进行交换。
     *            2、轮到下一组相邻元素，执行同样的比较操作，再找下一组，直到没有相邻元素可比较为止，此时最后的元素应是最大的数。
     *            3、除了每次排序得到的最后一个元素，对剩余元素重复以上步骤，直到没有任何一对元素需要比较为止。
     */
    public void bubbleSortOpt(int[] arr) {
        if (arr == null) {
            throw new NullPointerException();
        }
        if (arr.length < 2) {
            return;
        }
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 排序算法-2：快速排序（隶属于：交换排序）
     *
     * @param arr   待排序数组
     * @param start
     * @param end   核心思想：
     *              快速排序的思想很简单，就是先把待排序的数组拆成左右两个区间，左边都比中间的基准数小，右边都比基准数大。接着左右两边各自再做同样的操作，完成后再拆分再继续，一直到各区间只有一个数为止。
     *              举个例子，现在我要排序 4、9、5、1、2、6 这个数组。一般取首位的 4 为基准数，第一次排序的结果是：
     *              2、1、4、5、9、6
     *              可能有人觉得奇怪，2 和 1 交换下位置也能满足条件，为什么 2 在首位？这其实由实际的代码实现来决定，并不影响之后的操作。以 4 为分界点，对 2、1、4 和 5、9、6 各自排序，得到：
     *              1、2、4、5、9、6
     *              不用管左边的 1、2、4 了，将 5、9、6 拆成 5 和 9、6，再排序，至此结果为：
     *              1、2、4、5、6、9
     */
    public void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            // 把数组中的首位数字作为基准数
            int stard = arr[start];
            // 记录需要排序的下标
            int low = start;
            int high = end;
            // 循环找到比基准数大的数和比基准数小的数
            while (low < high) {
                // 右边的数字比基准数大
                while (low < high && arr[high] >= stard) {
                    high--;
                }
                // 使用右边的数替换左边的数
                arr[low] = arr[high];
                // 左边的数字比基准数小
                while (low < high && arr[low] <= stard) {
                    low++;
                }
                // 使用左边的数替换右边的数
                arr[high] = arr[low];
            }
            // 把标准值赋给下标重合的位置
            arr[low] = stard;
            // 处理所有小的数字
            quickSort(arr, start, low);
            // 处理所有大的数字
            quickSort(arr, low + 1, end);
        }
    }

    /**
     * 排序算法-3：直接插入排序（隶属于：插入排序：将一个记录插入到已经排好序的有序表中，使得被插入数的序列同样是有序的。按照此法对所有元素进行插入，直到整个序列排为有序的过程。）
     *
     * @param arr 待排序数据
     *            核心思想：
     *            1、直接插入排序就是插入排序的粗暴实现。对于一个序列，选定一个下标，认为在这个下标之前的元素都是有序的。将下标所在的元素插入到其之前的序列中。
     *            2、接着再选取这个下标的后一个元素，继续重复操作。直到最后一个元素完成插入为止。我们一般从序列的第二个元素开始操作。
     */
    public void insertSort(int[] arr) {
        // 遍历所有数字
        for (int i = 1; i < arr.length; i++) {
            // 当前数字比前一个数字小
            if (arr[i] < arr[i - 1]) {
                int j;
                // 把当前遍历的数字保存起来
                int temp = arr[i];
                for (j = i - 1; j >= 0 && arr[j] > temp; j--) {
                    // 前一个数字赋给后一个数字
                    arr[j + 1] = arr[j];
                }
                // 把临时变量赋给不满足条件的后一个元素
                arr[j + 1] = temp;
            }
        }
    }

    /**
     * 排序算法-4：希尔排序（隶属于：插入排序）
     *
     * @param arr 待排序数组
     *            核心思想：
     *            1、某些情况下直接插入排序的效率极低。比如一个已经有序的升序数组，这时再插入一个比最小值还要小的数，也就意味着被插入的数要和数组所有元素比较一次。我们需要对直接插入排序进行改进。
     *            2、怎么改进呢？前面提过，插入排序对已经排好序的数组操作时，效率很高。因此我们可以试着先将数组变为一个相对有序的数组，然后再做插入排序。
     *            3、希尔排序能实现这个目的。希尔排序把序列按下标的一定增量（步长）分组，对每组分别使用插入排序。随着增量（步长）减少，一直到一，算法结束，整个序列变为有序。因此希尔排序又称缩小增量排序。
     *            一般来说，初次取序列的一半为增量，以后每次减半，直到增量为一。
     */
    public void shellSort(int[] arr) {
        // gap 为步长，每次减为原来的一半。
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 对每一组都执行直接插入排序
            for (int i = 0; i < gap; i++) {
                // 对本组数据执行直接插入排序
                for (int j = i + gap; j < arr.length; j += gap) {
                    // 如果 a[j] < a[j-gap]，则寻找 a[j] 位置，并将后面数据的位置都后移
                    if (arr[j] < arr[j - gap]) {
                        int k;
                        int temp = arr[j];
                        for (k = j - gap; k >= 0 && arr[k] > temp; k -= gap) {
                            arr[k + gap] = arr[k];
                        }
                        arr[k + gap] = temp;
                    }
                }
            }
        }
    }

    /**
     * 排序算法-5：简单选择排序（隶属于：选择排序：在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。）
     *
     * @param arr 待排序数组
     *            核心思想：
     *            1、选择排序思想的暴力实现，每一趟从未排序的区间找到一个最小元素，并放到第一位，直到全部区间有序为止。
     */
    public void selectSort(int[] arr) {
        // 遍历所有的数
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            // 把当前遍历的数和后面所有的数进行比较，并记录下最小的数的下标
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    // 记录最小的数的下标
                    minIndex = j;
                }
            }
            // 如果最小的数和当前遍历的下标不一致，则交换
            if (i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * 排序算法-6：堆排序
     *
     * @param arr 待排序的整型数组
     *            核心思想：
     *            1、对于任何一个数组都可以看成一颗完全二叉树。堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
     *            或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
     *            2、
     */
    public void heapSort(int[] arr) {
        // 开始位置是最后一个非叶子结点，即最后一个结点的父结点
        int start = (arr.length - 1) / 2;
        // 调整为大顶堆
        for (int i = start; i >= 0; i--) {
            maxHeap(arr, arr.length, i);
        }
        // 先把数组中第 0 个位置的数和堆中最后一个数交换位置，再把前面的处理为大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            maxHeap(arr, i, 0);
        }
    }

    /**
     * 转化为大顶堆
     *
     * @param arr   待转化的数组
     * @param size  待调整的区间长度
     * @param index 结点下标
     */
    public void maxHeap(int[] arr, int size, int index) {
        // 左子结点
        int leftNode = 2 * index + 1;
        // 右子结点
        int rightNode = 2 * index + 2;
        int max = index;
        // 和两个子结点分别对比，找出最大的结点
        if (leftNode < size && arr[leftNode] > arr[max]) {
            max = leftNode;
        }
        if (rightNode < size && arr[rightNode] > arr[max]) {
            max = rightNode;
        }
        // 交换位置
        if (max != index) {
            int temp = arr[index];
            arr[index] = arr[max];
            arr[max] = temp;
            // 因为交换位置后有可能使子树不满足大顶堆条件，所以要对子树进行调整
            maxHeap(arr, size, max);
        }
    }

    /**
     * 排序算法-7：归并排序
     * 核心思想：
     * 归并排序是建立在归并操作上的一种有效，稳定的排序算法。该算法采用分治法的思想，是一个非常典型的应用。归并排序的思路如下：
     * 1、将 n 个元素分成两个各含 n/2 个元素的子序列
     * 2、借助递归，两个子序列分别继续进行第一步操作，直到不可再分为止
     * 3、此时每一层递归都有两个子序列，再将其合并，作为一个有序的子序列返回上一层，再继续合并，全部完成之后得到的就是一个有序的序列
     * <p>
     * 关键在于两个子序列应该如何合并。假设两个子序列各自都是有序的，那么合并步骤就是：
     * 1、创建一个用于存放结果的临时数组，其长度是两个子序列合并后的长度
     * 2、设定两个指针，最初位置分别为两个已经排序序列的起始位置
     * 3、比较两个指针所指向的元素，选择相对小的元素放入临时数组，并移动指针到下一位置
     * 4、重复步骤 3 直到某一指针达到序列尾
     * 5、将另一序列剩下的所有元素直接复制到合并序列尾
     */
    public static void mergeSort(int[] arr, int start, int end) {
        // 只剩下一个数字，停止拆分
        if (start == end) return;
        int middle = (end + start) / 2;
        if (start < end) {
            // 处理左边数组
            mergeSort(arr, start, middle);
            // 处理右边数组
            mergeSort(arr, middle + 1, end);
            // 归并
            merge(arr, start, middle, end);
        }
    }

    /**
     * 合并数组
     */
    public  static void merge(int[] arr, int low, int middle, int high) {
        // 用于存储归并后的临时数组
        int[] temp = new int[high - low + 1];
        // 记录第一个数组中需要遍历的下标
        int i = low;
        // 记录第二个数组中需要遍历的下标
        int j = middle + 1;
        // 记录在临时数组中存放的下标
        int index = 0;
        // 遍历两个数组，取出小的数字，放入临时数组中
        while (i <= middle && j <= high) {
            // 第一个数组的数据更小
            if (arr[i] <= arr[j]) {
                // 把更小的数据放入临时数组中
                temp[index] = arr[i];
                // 下标向后移动一位
                i++;
            } else {
                temp[index] = arr[j];
                j++;
            }
            index++;
        }
        // 处理剩余未比较的数据
        while (i <= middle) {
            temp[index] = arr[i];
            i++;
            index++;
        }
        while (j <= high) {
            temp[index] = arr[j];
            j++;
            index++;
        }
        // 把临时数组中的数据重新放入原数组
        for (int k = 0; k < temp.length; k++) {
            arr[k + low] = temp[k];
        }
    }

    /**
     * 排序算法-8：基数排序
     * 核心思想：
     * 将整数按位数切割成不同的数字，然后按每个位数分别比较。为此需要将所有待比较的数值统一为同样的数位长度，数位不足的数在高位补零。
     */
    public static void radixSort(int[] arr) {
        // 存放数组中的最大数字
        int max = Integer.MIN_VALUE;
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        // 计算最大数字是几位数
        int maxLength = (max + "").length();
        // 用于临时存储数据
        int[][] temp = new int[10][arr.length];
        // 用于记录在 temp 中相应的下标存放数字的数量
        int[] counts = new int[10];
        // 根据最大长度的数决定比较次数
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 每一个数字分别计算余数
            for (int j = 0; j < arr.length; j++) {
                // 计算余数
                int remainder = arr[j] / n % 10;
                // 把当前遍历的数据放到指定的数组中
                temp[remainder][counts[remainder]] = arr[j];
                // 记录数量
                counts[remainder]++;
            }
            // 记录取的元素需要放的位置
            int index = 0;
            // 把数字取出来
            for (int k = 0; k < counts.length; k++) {
                // 记录数量的数组中当前余数记录的数量不为 0
                if (counts[k] != 0) {
                    // 循环取出元素
                    for (int l = 0; l < counts[k]; l++) {
                        arr[index] = temp[k][l];
                        // 记录下一个位置
                        index++;
                    }
                    // 把数量置空
                    counts[k] = 0;
                }
            }
        }
    }

//    排序法	            最好情形	     平均时间	    最差情形	    稳定度	 空间复杂度
//    1、冒泡排序        O(n)	      O(n^2)	    O(n^2)	    稳定	 O(1)
//    2、快速排序	    O(nlogn)	  O(nlogn)	    O(n^2)	    不稳定	 O(nlogn)
//    3、直接插入排序	O(n)	      O(n^2)	    O(n^2)	    稳定	 O(1)
//    4、希尔排序			                                    不稳定	 O(1)
//    5、直接选择排序	O(n^2)        O(n^2)	    O(n^2)	    不稳定	 O(1)
//    6、堆排序	        O(nlogn)	  O(nlogn)	    O(nlogn)	不稳定	 O(nlogn)
//    7、归并排序	    O(nlogn)	  O(nlogn)	    O(nlogn)	稳定	 O(n)
}
