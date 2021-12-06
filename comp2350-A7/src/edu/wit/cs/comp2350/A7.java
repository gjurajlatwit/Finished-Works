package edu.wit.cs.comp2350;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/** Aligns strings in two text files by matching their longest common substring 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 7
 * 
 */

public class A7 {

	//TODO Document this method
	public static String[] findLCSdyn(String text1, String text2) {
		// TODO Implement this method
		int m = text1.length();
		int n = text2.length();
		int[][] lenTable = new int[m+1][n+1];
		String[][] dirTable = new String[m+1][n+1];
		
		for(int i=1; i<=m;i++) { // first column of 0
			lenTable[i][0] = 0;
		}
		for(int i=0; i<=n;i++) { // first row of 0
			lenTable[0][i] = 0;
		}
		
		
		for (int i=1;i<=m;i++) {
			for (int j=1;j<=n;j++) 
			{
				if(text1.charAt(i-1) == text2.charAt(j-1) && text1.charAt(i-1) != '\n')
				{
					lenTable[i][j] = lenTable[i-1][j-1] + 1;
					dirTable[i][j] = "Diag"; //diagonal movement
				}				
				else if (lenTable[i-1][j]>= lenTable[i][j-1]) 
				{						
				lenTable[i][j] = lenTable[i-1][j];
				dirTable[i][j] = "Up";//arrow pointing up
				}
				else				
				{
					lenTable[i][j] = lenTable[i][j-1];
					dirTable[i][j] = "Left"; // arrow pointing left
				}
			}
			
		}
	/*	for (int i=1;i<=m;i++) {
			for (int j=1;j<=n;j++) 
			{
				System.out.print(lenTable[i][j]);
				if(j==n)
					System.out.print("\n");
			}
		}*/
		longest = lenTable[m][n];
		
		String t1a = "";
		String t2a = "";
		
		while (m >= 0 || n >=0)
		{
			if	(dirTable[m][n] == "Diag")
			{
				m--;
				n--;
				t1a = text1.charAt(m) + t1a;
				t2a = text2.charAt(n) + t2a;
			}
			else if	(dirTable[m][n] == "Up")
			{
				m--;
				t1a = text1.charAt(m) + t1a;
				t2a = '-' + t2a;
			}
			else if	(dirTable[m][n] == "Left")
			{
				n--;
				t1a = '-' + t1a;
				t2a = text2.charAt(n) + t2a;
			}
			else if	(m == 0 && n > 0)
			{
				n--;
				t2a = text2.charAt(n) + t2a;
				t1a = '-' + t1a;
			}
			else if	(n == 0 && m > 0)
			{
				m--;
				t1a = text1.charAt(m) + t1a;
				t2a = '-' + t2a;
			}
			else
			{
				m--;
				n--;
			}
		}
		
		// TODO set static var "longest" to longest common subsequence length
		return new String[]{t1a, t2a};	// return an array with aligned text1 and text2 strings
	}
	
	
	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	private static int longest = -1;

	// recursive helper for DFS
	private static void dfs_solve(int i1, int i2, String s1, String s2, char[] out1, char[] out2, int score, int index) {

		if ((i1 >= s1.length()) && (i2 >= s2.length())) {
			if (score > longest) {
				out1[index] = '\0';
				out2[index] = '\0';
				longest = score;
				sol1 = String.valueOf(out1).substring(0, String.valueOf(out1).indexOf('\0'));
				sol2 = String.valueOf(out2).substring(0, String.valueOf(out2).indexOf('\0'));
			}
		}
		else if ((i1 >= s1.length()) && (i2 < s2.length())) {	// at the end of first string
			out1[index] = '-';
			out2[index] = s2.charAt(i2);
			dfs_solve(i1, i2 + 1, s1, s2, out1, out2, score, index+1);
		}
		else if ((i1 < s1.length()) && (i2 >= s2.length())) {	// at the end of second string
			out1[index] = s1.charAt(i1);
			out2[index] = '-';
			dfs_solve(i1 + 1, i2, s1, s2, out1, out2, score, index+1);
		}
		else {
			if (s1.charAt(i1) == s2.charAt(i2)) {	// matching next character
				out1[index] = s1.charAt(i1);
				out2[index] = s2.charAt(i2);
				dfs_solve(i1 + 1, i2 + 1, s1, s2, out1, out2, score + 1, index + 1);
			}

			out1[index] = '-';
			out2[index] = s2.charAt(i2);
			dfs_solve(i1, i2 + 1, s1, s2, out1, out2, score, index + 1);

			out1[index] = s1.charAt(i1);
			out2[index] = '-';
			dfs_solve(i1 + 1, i2, s1, s2, out1, out2, score, index + 1);
		}

	}

	// Used for DFS solution
	private static String sol1, sol2;

	// recursively searches for longest substring, checking all possible alignments
	public static String[] findLCSdfs(String text1, String text2) {
		int max_len = text1.length() + text2.length() + 1;
		char[] out1 = new char[max_len];
		char[] out2 = new char[max_len];

		dfs_solve(0, 0, text1, text2, out1, out2, 0, 0);

		String[] ret = new String[2];
		ret[0] = sol1; ret[1] = sol2;
		return ret; 
	}	

	// returns the length of the longest string
	public static int getLongest() {
		return longest;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1, file2, text1 = "", text2 = "";
		System.out.printf("Enter <text1> <text2> <algorithm>, ([dfs] - depth first search, [dyn] - dynamic programming): ");
		System.out.printf("(e.g: text/a.txt text/b.txt dfs)\n");
		file1 = s.next();
		file2 = s.next();

		try {
			text1 = new String(Files.readAllBytes(Paths.get(file1)));
			text2 = new String(Files.readAllBytes(Paths.get(file2)));
		} catch (IOException e) {
			System.err.println("Cannot open files " + file1 + " and " + file2 + ". Exiting.");
			System.exit(0);
		}

		String algo = s.next();
		String[] result = {""};

		switch (algo) {
		case "dfs":
			result = findLCSdfs(text1, text2);
			break;
		case "dyn":
			result = findLCSdyn(text1, text2);
			break;
		default:
			System.out.println("Invalid algorithm");
			System.exit(0);
			break;
		}

		s.close();

		System.out.printf("Overlapping characters: %d\nLongest string alignment:\n%s\n\n%s\n", longest, result[0], result[1]);	
	}
}
