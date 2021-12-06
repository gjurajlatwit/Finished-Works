package edu.wit.scds.comp2000.bag.adt;

import edu.wit.scds.comp2000.bag.BagInterface;

/*
 * We have introduced 3 integers to keep track of the items we are interested in.
 * 
 * While going through the input, we will increment them each time it is appropriate by 1.
 * 
 * 
 */
public class Logistics extends DictionaryImplement {
public int GeneralCount =0;
public int CorrectCount =0;
public int IncorrectCount =0;
final BagInterface<String> Misspells = new ResizableArrayBag<>( 10 ) ;
	

//Method that checks if the input is contained within bag and Fills up misspells bag.
private void Compare(String[] Input, BagInterface<String> Dictionary) {
	for (int i=0;i<Input.length;i++) {
	if(Dictionary.contains(Input[i])) {
	GeneralCount++;
	CorrectCount++;
	}
	else {
		IncorrectCount++;
		Misspells.add(Input[i]);
	}
	
	}
}

}
