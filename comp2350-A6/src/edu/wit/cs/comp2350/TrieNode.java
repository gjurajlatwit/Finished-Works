package edu.wit.cs.comp2350;


public class TrieNode {
	   
	//root node
    
    private TrieNode[] next;
    private char data;
    private boolean word;
    private TrieNode parent;
    public int depth;


    
public TrieNode() //for constructing root
    { 
    this.parent = null;
	//this.depth = 0;
	this.word = false;
	this.next = new TrieNode[26];
    } //end TrieNode

public char getData()
    {
    return this.data ;
    }   // end getData()

/**
 * Retrieve the next field
 *
 * @return reference to the next trie node in the chain
 */
public TrieNode getChild(final char letter)
    {
    return this.next[letter - 'a'];

    }   // end getNextNode()

public TrieNode getParent() 
	{
	return this.parent;
	} // end getParent()

/**
 * Retrieve the depth field
 *
 * @return this trie node's depth
 */
public int getDepth()
    {
    return this.depth;
    }   // end getDepth()


public void setData( final char newData ) // could use to overwrite character if ever needed
    {
    this.data = newData ;
    }   // end setData()

public void setWord()
	{
	this.word = true;
	}

public boolean isWord()
	{
	return this.word;
	}

public void setChild( char letter )
    {
	this.next[letter - 'a'] = new TrieNode();
    this.next[letter - 'a'].data = letter;
    this.next[letter - 'a'].parent = this;
    this.next[letter - 'a'].depth = this.depth + 1;
    } // end setNextNode()
}
