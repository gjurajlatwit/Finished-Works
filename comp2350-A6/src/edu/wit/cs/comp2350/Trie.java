package edu.wit.cs.comp2350;
import edu.wit.cs.comp2350.TrieNode;
import java.util.*;
/** Implements a trie data structure 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 6
 * 
 */

//TODO: document class
public class Trie implements Speller {
	
	
private TrieNode root;
private TrieNode currentNode;
	
	
public Trie() 
	{
	this.root = new TrieNode();
	this.currentNode = root;
	}
	
@Override	
public void insertWord(String s) {
	this.currentNode = root; //reset
		for(int i=0;i<s.length();i++) //parsing through each letter of the word
		{
			if(this.currentNode.getChild(s.charAt(i)) == null)  // if the word is valid and the next node containing that 
			{															      // character hasn't been established
			this.currentNode.setChild(s.charAt(i));
			}											      
			this.currentNode = currentNode.getChild(s.charAt(i)); //move on to the next node
		}
		this.currentNode.setWord(); //after establishing a path, assure that it is a word
}

	//TODO: document this method
	@Override
	public boolean contains(String s) {
		this.currentNode = root; //reset 
			for (int i = 0; i< s.length(); i++) 
				{
				if (this.currentNode.getChild(s.charAt(i)) == null) 
					{
					return false;
					}
				this.currentNode = currentNode.getChild(s.charAt(i));
				}
			return this.currentNode.isWord();
		
	}

	//TODO: document this method
	@Override
	public String[] getSuggestions(String s) {
		ArrayList<String> Suggestions = new ArrayList<String>(); // getting just 3 suggestions at most
		this.currentNode = root;
		int edits = 2;
		String temp = "";
		getSuggestions(this.currentNode,s,Suggestions,edits,temp);
		String [] str = new String[Suggestions.size()];
		for (int j = 0; j < Suggestions.size(); j++) {
			  
            // Assign each value to String array
            str[j] = Suggestions.get(j);
        }
		
		return str;
	}
	
	private void addWord(TrieNode a, ArrayList<String> Suggestions, String s) {
		for (int j=0;j<26;j++) {
			if (a.getChild((char)('a'+j))!= null && a.getChild((char)('a'+j)).isWord()) {
				if(!Suggestions.contains(s+a.getChild((char)('a'+j)).getData()))
				Suggestions.add(s+a.getChild((char)('a'+j)).getData());
			}
		}
	}
	private void getSuggestions(TrieNode a, String s, ArrayList<String> Suggestions,int edits, String temp) {//temp is empty at the start
		int cEdits = edits; // current edits
		for(int i=0;i<26;i++) {
			if(a.getChild((char) ('a'+i))!=null) {
				a = a.getChild((char) ('a'+i));
				temp = temp + a.getData();
				
				if(a.getDepth()>0) {
				if(a.getData() != s.charAt(a.getDepth()-1)) {
					cEdits--;
				}}
				
				if(cEdits >= 0 && a.getDepth() < s.length())
				getSuggestions(a,s,Suggestions,cEdits,temp);
				
				
				else if (cEdits >= 0 && a.getDepth() == s.length() && a.isWord())
						Suggestions.add(temp);
				
				
				//end of iteration
				if (cEdits < edits) {
					cEdits++;
				}
				
				temp = temp.substring(0,temp.length()-1);
				a = a.getParent();
				
			}
		}
	}
	}
