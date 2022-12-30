package com.adolesce.server.javabasic.algorithm;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2022/11/13 10:14
 */
public class TwoSum {
    /**
     * 解法一：利用Map，时间复杂度为O(n)
     * 核心思路：
     * 1、遍历数组中的每个元素，判断目标数值减去当前元素后的值是否存在于 map的key中，如果不存在则将当前元素添加至map中，key为当前元素，value为对应数组的角标
     * 2、接下来的循环中，如果map中包含步骤一put进去的key，此时就是符合条件的另一半，则将两个元素的角标put进结果集Map中
     *
     * @param nums
     * @param target
     * @return
     */
    public Map<Integer, Integer> twoSum1(int[] nums, int target) {
        Map<Integer, Integer> results = new HashMap<>();
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                results.put(hashtable.get(target - nums[i]), i);
            }
            hashtable.put(nums[i], i);
        }
        return results;
    }

    /**
     * 解法二：利用排序后的有序数组和双指针的方法，时间复杂度为（排序复杂度 + O(n)）
     * 核心思想：
     * 先对数组进行排序，形成有序数组
     * 通过双指针的方法，解决这个问题
     * 1、使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。
     * 2、指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。
     * 3、如果 sum > target，移动较大的元素，使得 sum 变小一些；
     * 4、如果 sum < target，移动较小的元素，使得 sum 变大一些；
     * 5、如果 sum = target，得到结果。
     *
     * @param nums
     * @param target
     * @return
     */
    public Map<Integer, Integer> twoSum2(int[] nums, int target) {
        Map<Integer, Integer> results = new HashMap<>();
        if (nums == null) return null;
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                results.put(i, j);
                i++;
                j--;
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return results;
    }

    /**
     * 解法三：暴力双层for循环
     *
     * @param nums
     * @param target
     * @return
     */
    public Map<Integer, Integer> twoSum3(int[] nums, int target) {
        Map<Integer, Integer> results = new HashMap<>();
        //双层for循环对数组进行比较和求和
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                //数组元素比较
                if (nums[i] == nums[j]) {
                    //跳出此次循环
                    continue;
                } else {
                    //数组元素求和
                    int twoSum = nums[i] + nums[j];
                    //将所求和与目标值进行比较
                    if (twoSum == target) {
                        results.put(i, j);
                    }
                }
            }
        }
        return results;
    }


    /**
     * 给一个无序数组，给一个目标值，求数组中两数之和等于目标值的坐标
     */
    @Test
    public void testTwoSum() {
        int[] nums = {6, 5, 3, 1, 4, 2, 2};
        int target = 7;
        //解法一：利用【Map】方式
        //Map map = twoSum1(nums,target);

        //解法二：利用 【排序+头尾】 双指针方式（得出的是排序后的角标，不适用于无序集合）
        //Order.mergeSort(nums, 0, nums.length - 1);
        //Arrays.stream(nums).forEach(a -> System.out.print(a + " "));
        //Map map = twoSum2(nums,target);

        //解法三：暴力双层for循环
        Map map = twoSum3(nums, target);
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
