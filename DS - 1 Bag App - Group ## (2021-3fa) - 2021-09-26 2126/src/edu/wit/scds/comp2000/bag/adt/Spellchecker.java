package edu.wit.scds.comp2000.bag.adt;

import edu.wit.scds.comp2000.bag.BagInterface;

public class Spellchecker extends Logistics {
	/*
	 * We have the Spellchecker application that uses the Logistics Classes
	 * methods to find the number of correct, incorrect, and general words that were read.
	 * 
	 * We use the ArrayImplement Class in order to get the words from provided files into the proper arrays.
	 * 
	 * We then just declare the String Arrays we are going to use in the main part of our program and
	 * use the methods we armed ourselves with.
	 * 
	 */
public static void main(String[] args) {
	
	
	String[] Known_Words;
	String[] Incorrect_Spelling;
	String[] Dictionary;
	
	final BagInterface<String> Input = new ResizableArrayBag<>( 3 ) ;
}
}