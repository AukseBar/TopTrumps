/**Creates and manages Card objects*/

public class Card {

	private String title, infoText;
	private final int NrOfAttributes=5;
	private int[] attribute= new int[NrOfAttributes];	
	/**constructor
	 * @param String containing card info
	 * */
	public Card(String infor) {	  //takes a string and breaks it into chunks
		this.infoText = infor;
    	String [] info = infoText.split("\\s+");
    	this.title = info[0];					    	// title is the first word in the String
    	for (int i = 0; i < NrOfAttributes; i++){		// put category keywords into attribute array
    		attribute[i] = Integer.parseInt(info[i+1]);
    	}

    	// for testing draws::::::::::::::: this.attribute[4]=1;
	}
	

	/**
	 * returns a String formatted for being displayed on the screen
	 * includes the title and value of card attributes*/
	public String toString(){	
		return infoText;  // return String used in constructor
	}
	
	
	/**
	 * returns attribute value.
	 * @param int [1..5] marks which attribute value
	 * is requested*/
	public int getCategoryValue(int categoryIn){
		return attribute[categoryIn-1];
	}
	
	
	/**in case we need this
	 * returns card title*/
	public String getTitle(){return title;}
	
}
