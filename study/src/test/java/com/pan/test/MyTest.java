package com.pan.test;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * @author Administrator
 * 
 */
public class MyTest {
	public static void main(String[] args) {
		int num=10000;
		int[] arr=new int[num];
		for(int i=0;i<num;i++){
			arr[i]=(int) (Math.random()*1000);
		}
		int[] arr2=ArrayUtils.clone(arr);
		int[] arr3=ArrayUtils.clone(arr);
		System.out.println("原数组为："+Arrays.toString(arr));
		System.out.println("-------------------");
		bubbleSort1(arr);
		bubbleSort2(arr2);
		long start=System.nanoTime();
		fastSort(arr3, 0, num-1);
		long end=System.nanoTime();
		System.out.println("快速排序后数组为："+Arrays.toString(arr3));
		System.out.println("耗时："+(end-start));
		
	}
	
	public static void fastSort(int[] array,int low,int high){
		
		if(low<high){
	        int pos = OneFastSort(array,low,high);
	        fastSort(array,low,pos-1);
	        fastSort(array,pos+1,high);
	    }
	}
	
	public static int OneFastSort(int[] array,int low,int high){
	    //实现一次快速排序
	    int key = array[low];
	    int flag = 0;
	    while (low != high) {
	        if (flag == 0) {
	            //flag为0表示指针从high的一端开始移动
	            if (array[high] < key) {
	                array[low] = array[high];
	                low++;
	                flag = 1;
	            } else {
	                high--;
	            }
	        } else {
	            //指针从low的一端开始移动
	            if (array[low] > key) {
	                array[high] = array[low];
	                high--;
	                flag = 0;
	            } else {
	                low++;
	            }
	        }
	    }
	    array[low] = key;
	    return low;
	}
	
	public static void bubbleSort1(int[] arr){
		long start=System.nanoTime();
		int count=0;
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					int temp=arr[j+1];
					arr[j+1]=arr[j];
					arr[j]=temp;
				}
				count++;
			}	
		}
		long end=System.nanoTime();
		System.out.println("1-冒泡排序后数组为："+Arrays.toString(arr));
		System.out.println("耗时："+(end-start));
		System.out.println("count:"+count);
	}
	
	public static void bubbleSort2(int[] arr){
		long start=System.nanoTime();
		int count=0;
		for(int i=0;i<arr.length;i++){
			boolean flag=true;
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					int temp=arr[j+1];
					arr[j+1]=arr[j];
					arr[j]=temp;
					flag=false;
				}
				count++;
			}	
			if(flag){
				break;
			}
		}
		long end=System.nanoTime();
		System.out.println("2-冒泡排序后数组为："+Arrays.toString(arr));
		System.out.println("耗时："+(end-start));
		System.out.println("count:"+count);
	}
	
}
