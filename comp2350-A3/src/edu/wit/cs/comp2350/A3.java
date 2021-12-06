package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
/** Sorts geographic points in-place in an array, ordering
 * by surface distance to a specific point 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 3
 * 
 */

public class A3 {

	private static void swap(Coord[] a, int i, int j) {
	Coord temp;
	temp = a[i];
	a[i] = a[j];
	a[j] = temp;
	}
	
	private static int partition(Coord[] destinations, int l, int r) {
		double p = destinations[l].getDist();
		int i=l;
		
		for (int j=l+1;j<=r;j++) {
			if(destinations[j].getDist()<p) {
				i++;
				swap(destinations,i,j);
			}
		}
		swap(destinations,i,l);
		return i;
	}
	
	private static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	private static int randPartition(Coord[] destinations,int l, int r) {
		int z = getRandomNumber(l,r);
		swap(destinations,l,z);
		double p = destinations[l].getDist();
		int i=l;
		for (int j=l+1;j<=r;j++) {
			if(destinations[j].getDist()<p) {
				i++;
				swap(destinations,i,j);
			}
		}
		swap(destinations,i,l);
		return i;
	}
	private static void quickSort(Coord[] destinations,int l,int r) {
		if(l<r) {
			int pindex = partition(destinations,l,r);
			quickSort(destinations,l,pindex - 1);
			quickSort(destinations,pindex+1,r);
		} // other partition index
		
	}
	private static void randQuickSort(Coord[] destinations,int l,int r) {
		if(l<r) {
			int pindex = randPartition(destinations,l,r);
			randQuickSort(destinations,l,pindex - 1);
			randQuickSort(destinations,pindex+1,r);
		} // other partition index
		
	}
	
	public static void quickSort(Coord[] destinations) {
		int l = 0;
		int r = destinations.length - 1;
		quickSort(destinations,l,r);	
		
	}

	//TODO: document this method
	public static void randomizedQuickSort(Coord[] destinations) {
		int l = 0;
		int r = destinations.length - 1;
		randQuickSort(destinations,l,r);
	}


	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	/**
	 * Implementation note: This implementation is a stable, adaptive, iterative mergesort
	 *  that requires far fewer than n lg(n) comparisons when the input array is partially
	 *  sorted, while offering the performance of a traditional mergesort when the input
	 *  array is randomly ordered. If the input array is nearly sorted, the implementation
	 *  requires approximately n comparisons. Temporary storage requirements vary from a
	 *  small constant for nearly sorted input arrays to n/2 object references for randomly
	 *  ordered input arrays.
	 */
	public static void systemSort(Coord[] destinations) {
		Arrays.sort(destinations, (a, b) -> Double.compare(a.getDist(), b.getDist()));
	}

	// Insertion sort eventually sorts an array
	public static void insertionSort(Coord[] a) {

		for (int i = 1; i < a.length; i++) {
			Coord tmpC = a[i];
			int j;
			for (j = i-1; j >= 0 && tmpC.getDist() < a[j].getDist(); j--)
				a[j+1] = a[j];
			a[j+1] = tmpC;
		}
	}

	private static Coord getOrigin(Scanner s) {
		double lat = s.nextDouble();
		double lon = s.nextDouble();

		Coord ret = new Coord(lat, lon);
		return ret;
	}

	private static Coord[] getDests(Scanner s, Coord start) {
		ArrayList<Coord> a = new ArrayList<>();

		while (s.hasNextDouble())
			a.add(new Coord(s.nextDouble(), s.nextDouble(), start));

		Coord[] ret = new Coord[a.size()];
		a.toArray(ret);

		return ret;
	}

	private static void printCoords(Coord start, Coord[] a) {

		System.out.println(start.toColorString("#000000"));
		
		int interp = 0;
		if (a.length > 0) {
			interp = 255 / a.length;
		}
		
		for (int i = 0; i < a.length; ++i) {
			String s = String.format("#FF%02XFF", 255 - (interp * i));
			System.out.println(a[i].toColorString(s));
		}

		System.out.println();
		System.out.println("Paste these results into https://mobisoftinfotech.com/tools/plot-multiple-points-on-map/ if you want to visualize the coordinates.");
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use [i]nsertion sort, [q]uicksort, [r]andomized quicksort, or [s]ystem quicksort): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter your starting coordinate in \"latitude longitude\" format as doubles: (e.g. 42.33612 -71.094016): ");
		Coord start = getOrigin(s);

		System.out.printf("Enter your end coordinates one at a time in \"latitude longitude\" format as doubles: (e.g. 38.897386 -77.037400). End your input with a non-double character:%n");
		Coord[] destinations = getDests(s, start);

		s.close();
		
		switch (algo) {
		case 'i':
			insertionSort(destinations);			
			break;
		case 'q':
			quickSort(destinations);
			break;
		case 'r':
			randomizedQuickSort(destinations);
			break;
		case 's':
			systemSort(destinations);
			break;
		default:
			System.out.println("Invalid search algorithm");
			System.exit(0);
			break;
		}

		printCoords(start, destinations);

	}

}
