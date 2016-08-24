
public class Node {
	protected String data;	// holds the data
	protected Node next;	// position	
	
	// constructor
	public Node(String value){
		data = value;
		next = null;
	}
	
	public Node(String value, Node next){
		data = value;
		this.next = next;
	}
	
	// accessors
	public String getValue(){
		return data;
	}
	
	public Node getNext(){
		return next;
	}
	
	// mutators
	public void setValue(String newValue){
		data = newValue;
	}
	
	
	public void setNext(Node newNext){
		next = newNext;
	}
}
