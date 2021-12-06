package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** Sorts integers from command line using various algorithms 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 1
 * 
 */

public class A1 {

	// TODO: document this method
	private static int PositiveIntPower(int base,int power) {
		int newbase=base;
		if(power!=0) {
		for(int i=1;i<power;i++) {
			newbase*=base;
			
		}
		return newbase;
		}
		
		else
		return 1;
	}
	
	private static int ArrMin(int[] a) {
		int min=2^18-1;
		for(int i=0;i<a.length;i++) {
			if (min> a[i]) {
				min=a[i];			// Method to find minimum value in array
			}
		}
		return min;
	}
	private static int ArrMax(int[] a) { // Method to find maximum value in array
		int max=0;
		for(int i=0;i<a.length;i++) {
			if (max< a[i]) {
				max=a[i];
			}
		}
		return max;
	}
	
	public static int[] countingSort(int[] a) {
		//TODO: implement this method
		int[] index = new int[ArrMax(a)+1];
		for(int i=0;i<index.length;i++) {
			index[i]=i;
		}
		int[] count = new int[index.length];
		int[] sortedarr= new int[a.length];
		
		for(int i=0;i<a.length;i++) {//
			count[a[i]]++;
		}
		int k=0;
		for(int i=0;i<count.length;i++) {
			if (count[i]!=0) {
				count[i]--;
				sortedarr[k]=index[i];
				i--;
				k++;
				
			}
		}
		
		return sortedarr;	// return an array with sorted values
	}

	// TODO: document this method
	public static int[] radixSort(int[] a) {
		// TODO: implement this method
		int[] index = {0,1,2,3,4,5,6,7,8,9};
		int[] count = new int[10];
		int[] pos= new int[10];
		int[] sortedarr= new int[a.length];
		int[] temp = new int[a.length];
		int Max = ArrMax(a);
		int MaxDigits=0;
		
		
	
		for(int i=Max;i>0;i/=10) {//figuring out how many sorts we need to make with 
			MaxDigits++;			// how many digits the largest number has
		}
		for(int k=0;k<MaxDigits;k++) { //sorts as many times as the digits of the last number
		int Digit = PositiveIntPower(10,k);
		
		for(int i=0;i<10;i++) { // resetting count and void arrays for the next loop
			count[i]=0;
			pos[i]=0;
		}
		
			for(int i=0;i<a.length;i++) {
				temp[i]=(a[i]/Digit)%10;//making whatever column we want to look at the singles digit
										//using mod 10 to isolate it from digits to the left
			}
			
		for(int i=0;i<a.length;i++) {//
			count[temp[i]]++;
		}
		for(int i=1;i<pos.length;i++) { //setting up the position array
			pos[i]=pos[i-1]+count[i-1];
		}
		
		for(int i=0;i<sortedarr.length;i++) { // sorting the array	
			sortedarr[pos[temp[i]]]=a[i];
			pos[temp[i]]++;
			
			
		}
		for(int i =0;i<a.length;i++) {
			a[i]=sortedarr[i];
		}
		}
		
		
		return sortedarr;	// return an array with sorted values
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public final static int MAX_INPUT = 262143;
	public final static int MIN_INPUT = 0;

	// example sorting algorithm
	public static int[] insertionSort(int[] a) {

		for (int i = 1; i < a.length; i++) {
			int tmp = a[i];
			int j;
			for (j = i-1; j >= 0 && tmp < a[j]; j--)
				a[j+1] = a[j];
			a[j+1] = tmp;
		}

		return a;
	}

	/**
	 * Implementation note: The sorting algorithm is a Dual-Pivot Quicksort by Vladimir Yaroslavskiy,
	 *  Jon Bentley, and Joshua Bloch. This algorithm offers O(n log(n)) performance on many data 
	 *  sets that cause other quicksorts to degrade to quadratic performance, and is typically 
	 *  faster than traditional (one-pivot) Quicksort implementations.
	 */
	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for(int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([c]ounting, [r]adix, [i]nsertion, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers to sort, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'c':
			sorted_values = countingSort(unsorted_values);
			break;
		case 'r':
			sorted_values = radixSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}
