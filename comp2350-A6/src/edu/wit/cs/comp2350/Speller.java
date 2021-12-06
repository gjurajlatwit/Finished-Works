package edu.wit.cs.comp2350;

public interface Speller {

	public abstract void insertWord(String s); //inserts word into dictionary
	public abstract boolean contains(String s); // checks to see if word is in the dictionary
	public abstract String[] getSuggestions(String s); // checks words with the same number of characters, but one or 2 different characters

}
