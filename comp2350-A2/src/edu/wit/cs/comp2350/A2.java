package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** Adds floating point numbers with varying precision 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 2
 * 
 */


public class A2 {
	private static boolean leftChildValid(int i,int count) {
		if(2*i+1>count) {
			return false;
		}
		else 
			return true;
	}
	
	private static boolean rightChildValid(int i,int count) {
		if(2*i+2>count) {
			return false;
		}
		else 
			return true;
	}
	
	private static float[] sortArrMin(float[] a) {
		
		for(int i=a.length-1;i>0;i--) {
			float temp = 0;
			if(a[i]<a[(i-1)/2]) {
				temp=a[(i-1)/2];
				a[(i-1)/2]=a[i];
				a[i]=temp;
			}
		}
		
		return a;
	}
private static float[] sortArrMax(float[] a) {
		// not needed
		for(int i=a.length-1;i>0;i--) {
			float temp = 0;
			if(a[i]>a[(i-1)/2]) {
				temp=a[(i-1)/2];
				a[(i-1)/2]=a[i];
				a[i]=temp;
			}
		}
		
		return a;
	}
	private static int FindMin(float[] a,int min,int i,int count) {
		if(leftChildValid(i,count) && rightChildValid(i,count)) {
				if (a[i]>a[2*i+1] && a[2*i+1]<a[2*i+1]) {
					min=2*1+1;
					}
				else if(a[i]>a[2*i+2] && a[2*i+2]<a[2*i+1]) {
					min=2*1+2;
				}
				}
		else if(leftChildValid(i,count) && !rightChildValid(i,count)) {
			if (a[i]>a[2*i+1]) {
				min=2*i+1;
			}
		}
		else if(!leftChildValid(i,count) && rightChildValid(i,count)) {
			if (a[i]>a[2*i+2]) {
				min=2*i+2;
			}
		}
		return min;
	}
	private static float[] compareSwitch(float[] a,int i,int count) {
		float temp = 0;
		int min=i;
		if(FindMin(a,min,i,count)!=i) {
			temp=a[i];
			a[i]=a[min];
			a[min]=temp; 
		}
		
		return a;
	}
	private static float[] pushDown(float[] a,int count) {
		for(int i=0;i<count;i++) {
			compareSwitch(a,i,count);
		}
		return a;
	}
	public static float heapAdd(float[] a) {
		//TODO: implement this method
		if(a.length==0) {
			return 0;
		}
		int count = a.length;
		sortArrMin(a);
		float sum=0;
		
		while(count>1) {
			
			
			sum+=a[0];
			a[0]=a[count-1];
			a[count-1]=0;
			count--;
			pushDown(a,count);
			sum+=a[0];
			a[0]=sum;
			pushDown(a,count);
			sum=0;
		}
		
		return a[0]; // return the error-minimized sum of floats
	}
	

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	// sum an array of floats sequentially - high rounding error
	public static float seqAdd(float[] a) {
		float ret = 0;

		for (int i = 0; i < a.length; i++)
			ret += a[i];

		return ret;
	}

	// sort an array of floats and then sum sequentially - medium rounding error
	public static float sortAdd(float[] a) {
		Arrays.sort(a);
		return seqAdd(a);
	}

	// scan linearly through an array for two minimum values,
	// remove them, and put their sum back in the array. repeat.
	// minimized rounding error, inefficient operations
	public static float min2ScanAdd(float[] a) {
		int min1, min2;
		float tmp;

		if (a.length == 0) return 0;

		for (int i = 0, end = a.length; i < a.length - 1; i++, end--) {

			if (a[0] < a[1]) { min1 = 0; min2 = 1; }	// initialize
			else { min1 = 1; min2 = 0; }

			for (int j = 2; j < end; j++) {		// find two min indices
				if (a[min1] > a[j]) { min2 = min1; min1 = j; }
				else if (a[min2] > a[j]) { min2 = j; }
			}

			tmp = a[min1] + a[min2];	// add together
			if (min1<min2) {			// put into first slot of array
				a[min1] = tmp;			// fill second slot from end of array
				a[min2] = a[end-1];
			}
			else {
				a[min2] = tmp;
				a[min1] = a[end-1];
			}
		}

		return a[0];
	}

	// read floats from a Scanner
	// returns an array of the floats read
	private static float[] getFloats(Scanner s) {
		ArrayList<Float> a = new ArrayList<Float>();

		while (s.hasNextFloat()) {
			float f = s.nextFloat();
			if (f >= 0)
				a.add(f);
		}
		return toFloatArray(a);
	}

	// copies an ArrayList to an array
	private static float[] toFloatArray(ArrayList<Float> a) {
		float[] ret = new float[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the adding algorithm to use ([h]eap, [m]in2scan, se[q], [s]ort): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the non-negative floats that you would like summed, followed by a non-numeric input: ");
		float[] values = getFloats(s);
		float sum = 0;

		s.close();

		if (values.length == 0) {
			System.out.println("You must enter at least one value");
			System.exit(0);
		}
		else if (values.length == 1) {
			System.out.println("Sum is " + values[0]);
			System.exit(0);

		}

		switch (algo) {
		case 'h':
			sum = heapAdd(values);
			break;
		case 'm':
			sum = min2ScanAdd(values);
			break;
		case 'q':
			sum = seqAdd(values);
			break;
		case 's':
			sum = sortAdd(values);
			break;
		default:
			System.out.println("Invalid adding algorithm");
			System.exit(0);
			break;
		}

		System.out.printf("Sum is %f\n", sum);		

	}

}
