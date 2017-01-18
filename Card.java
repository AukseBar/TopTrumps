/***/
public class Card {

	private String title;
	private int attrib1, attrib2, attrib3, attrib4, attrib5; // specific names instead?
	
	/**constructor
	 * @param String containing card info
	 * */
	public Card (String infor) {	
		//takes a string and breaks it into chunks

    	String [] info=infor.split("\\s+");
    	this.title=info[0];  // title is the first word in the String
    	this.attrib1=Integer.parseInt(info[1]);  // 
    	this.attrib2=Integer.parseInt(info[2]);
    	this.attrib2=Integer.parseInt(info[3]);
    	this.attrib2=Integer.parseInt(info[4]);
    	this.attrib2=Integer.parseInt(info[5]);
	}
	

	public String displayCard(){
		String formattedString=(String.format());
		
		
		return formattedString;
	}
	
	
	//getter methods
	
	
	
}
