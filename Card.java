/**Creates and manages Card objects*/

public class Card {

	private String title, infoText;
	private int attrib1, attrib2, attrib3, attrib4, attrib5;
	
	/**constructor
	 * @param String containing card info
	 * */
	public Card(String infor) {	  //takes a string and breaks it into chunks
		this.infoText=infor;
    	String [] info=infoText.split("\\s+");
    	this.title=info[0];					    // title is the first word in the String
    	this.attrib1=Integer.parseInt(info[1]); // the next four chunks have attribute values
    	this.attrib2=Integer.parseInt(info[2]);
    	this.attrib3=Integer.parseInt(info[3]);
    	this.attrib4=Integer.parseInt(info[4]);
    	this.attrib5=Integer.parseInt(info[5]);
	}
	

	/**
	 * returns a String formatted for being displayed on the screen
	 * includes the title and values of card attributes*/
	public String displayCard(){	
		return infoText;  // return String used in constructor
	}
	
	
	/**
	 * returns attribute value.
	 * @param int [1..5] marks which attribute value
	 * is requested*/
	public int getCategoryValue(int categoryIn){
		if (categoryIn==1)
			return attrib1;
		if (categoryIn==2)
			return attrib2;
		if (categoryIn==3)
			return attrib3;
		if (categoryIn==4)
			return attrib4;
		else  				 // must be 5
			return attrib5;
	}
	
	
	/**in case we need this
	 * returns card title*/
	public String getTitle(){return title;}
	
}
