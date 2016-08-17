package com.dearho.cs.util;

/**
 * Java 快速排序法
 * @author hzw
 *
 */
public class Quick {
    public int[] quick_sort(int[] arrays, int lenght) {
        if (null == arrays || lenght < 1) {
            System.out.println("input error!");
            return arrays;
        }
       return _quick_sort(arrays, 0, lenght - 1);
    }

    public int[] _quick_sort(int[] arrays, int start, int end) {
        if(start>=end){
            return arrays;
        }
        
        int i = start;
        int j = end;
        int value = arrays[i];
        boolean flag = true;
        while (i != j) {
            if (flag) {
                if (value > arrays[j]) {
                    swap(arrays, i, j);
                    flag=false;

                } else {
                    j--;
                }
            }else{
                if(value<arrays[i]){
                    swap(arrays, i, j);
                    flag=true;
                }else{
                    i++;
                }
            }
        }
//        snp(arrays);
        _quick_sort(arrays, start, j-1);
        _quick_sort(arrays, i+1, end);
        return arrays;
    }

    public void snp(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + " ");
        }
        System.out.println();
    }

    private void swap(int[] arrays, int i, int j) {
        int temp;
        temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

    public static void main(String args[]) {
        Quick q = new Quick();
        int[] a = {49,38,65,12,45,5,1,6,8};
        int [] arrays = q.quick_sort(a,a.length);
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(arrays[i] + " ");
        }
        System.out.println();
    } 

}
