import java.lang.reflect.Array;


public class NKList{

	protected Node nkHead;
	protected int nkLength;

	// constructor
	public NKList(){
		nkHead = null;
		nkLength = 0;
	}

	public NKList(Node head){
		nkHead = head;
		nkLength = 0;
		Node currentNode = nkHead;
		while(currentNode != null){
			nkLength++;
			currentNode = currentNode.getNext();
		}
	}

	// accessor
	public int getLength(){
		return nkLength;
	}

	// get the node at the specified index, which starts at 0.
	public String getVertice(int index){
		// verify the validity of the index
		if(index < 0 || index > nkLength){
			return null;
		}

		Node currentNode = nkHead;
		// move to the node before the specified
		for(int i = 0; i < index; i++){
			currentNode = currentNode.getNext();
		}

		if(currentNode == null)
			return null;
		else
			return currentNode.getValue();
	}



	// get the index of a specific node, which starts at 0
	public int getIndex(String vertice){
		int index = -1;
		boolean found = false;

		Node currentNode = nkHead;
		while(currentNode != null){
			if(currentNode.getValue().equals(vertice)){
				index ++;
				found = true;
				break;
			}else{
				currentNode = currentNode.getNext();
				index ++;
			}
		}

		if(found){
			return index;
		}else{
			index = -1;
			return index;
		}

	}

	// add a node, no duplicates
	public void addVertice(String newValue){
		// check duplicates
		if(alreadyExist(newValue))
			return;

		// initialize the head with the new value if the list is vacant
		if(nkHead == null){
			nkHead = new Node(newValue);
	    }else{
	   		Node currentNode = nkHead;
	   		// move to the end of the list
	    	while(currentNode.getNext() != null){
	    		currentNode = currentNode.getNext();
	    	}
	    	// add the new node
	    	currentNode.setNext(new Node(newValue));
	    }
		// update the length
	    nkLength++;
	}

	// add a node to a specific index, index start at 0
	public void addVertice(String newValue, int index){
		// check index's validity
		if(index < 0 || index > nkLength)
			return;

		// check duplicates
		if(alreadyExist(newValue))
			return;

		Node newNode = new Node(newValue);
		Node currentNode = nkHead;

		// initialize the head with the new value if the list is vacant
		if(currentNode == null){
			currentNode = new Node(newValue);
	    }else{

	   		// if it is the head to be changed
	    	if(index == 0){
	    		newNode.setNext(currentNode);
	    		nkHead = newNode;
	    	}

	    	else{
	    		// move to the specified index of the list
	    		for(int i = 0; i < index - 1; i++){
		    		currentNode = currentNode.getNext();
		    	}
		    	// add the new node before the existing
	    		if(currentNode.getNext() != null){
	    			// not add to the last
	    			newNode.setNext(currentNode.getNext());
			    	currentNode.setNext(newNode);
	    		}else{
	    			// add to the last
	    			currentNode.setNext(newNode);
	    		}

	    	}

	    }
		// update the length
	    nkLength++;
	}

	// delete the node with specific name
	public void deleteVertice(String targPerson){
		Node currentNode = nkHead;

		// if the head is the one to be deleted
		if(nkHead.getValue().equals(targPerson)){
			nkHead = nkHead.getNext();
			nkLength --;
			return;
		}

		// to find the target in the middle of the list
		while(currentNode.getNext().getNext() != null){
			if(currentNode.getNext().getValue().equals(targPerson)){  // ?? is it the right way to compare the value??
				currentNode.setNext(currentNode.getNext().getNext());
				nkLength--;
				return;
			}
			currentNode = currentNode.getNext();
		}

		// if the last is the one to be deleted
		if(currentNode.getNext().getValue().equals(targPerson)){
			currentNode.setNext(null);
			nkLength--;
		}

	}

	// delete the node at the specified index; index starts at 0
	public void deleteVertice(int index){
		// check index's validity
		if(index < 0 || index >= nkLength)
			return;

		Node currentNode = nkHead;

		// remove the head if there is only one node
		if(index == 0){
			nkHead = nkHead.getNext();
		}else{
			// move to the node just before the specified
			for(int i = 0; i < index-1; i++){
				currentNode = currentNode.getNext();
			}
			// skip over the node
			currentNode.setNext(currentNode.getNext().getNext());
		}
		nkLength--;
	}

	// clear everything in NKList
	public void clear(){
		int original = getLength();
		for(int i = 0; i < original; i++){
			deleteVertice(0);
		}
	}

	// check the existing node values to avoid duplicates
	public boolean alreadyExist(String newValue){
		Node currentNode = nkHead;
		while(currentNode != null){
			if(currentNode.getValue().equals(newValue)){
				return true;
			}else
				currentNode = currentNode.getNext();
		}
		return false;
	}
}
