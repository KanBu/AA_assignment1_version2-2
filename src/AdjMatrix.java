import java.io.*;
import java.util.*;
import java.util.Map.Entry;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{

	private int arraySize;
	private NKList list = new NKList();
	private int[][] friends;
	private int totalPeople;
	public static int resizeFactor = 2;
	private HashMap<String, Integer> distMap = new HashMap<String, Integer>();
	private NKList visitedList = new NKList();


	/**
	 * Constructs empty graph.
	 */
    public AdjMatrix() {
    	arraySize = 100;
//    	list = null;
    	friends = new int[arraySize][arraySize];
    	totalPeople = 0;
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
    	int index;
    	// check if the array overflow or not
    	if(totalPeople >= arraySize){
    		// expand the arraySize by doubling
    		index = arraySize;
    		expand(resizeFactor);
    	}else{
    		// in the condition that no overflow
    		index = totalPeople;
    	}


    	// register new person
    	for(int i = 0; i < index; i++)
    		friends[index][i] = 0;
    	friends[index][index] = 1;
    	// update the map
    	list.addVertice((String)vertLabel);
    	// update the total people
    	totalPeople ++;
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {

    	//check if these two people exist 

    	if(!list.alreadyExist((String)srcLabel))
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");



    	if(!list.alreadyExist((String)tarLabel)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	//find and update the srcLabel's friends list
    	int srcIndex = list.getIndex((String)srcLabel);
    	int tarIndex = list.getIndex((String)tarLabel);
    	friends[srcIndex][tarIndex] = 1;
    	friends[tarIndex][srcIndex] = 1;
    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        // check if the vertLabel exists or not
        if(!list.alreadyExist((String)vertLabel)){
        	throw new IllegalArgumentException("The person does not exist! Please add the person first!");
    	}

        // find the index in the array
        int index = list.getIndex((String)vertLabel);

        // find that specific row in the matrix
        for(int i = 0; i < friends[index].length; i++){
        	if(friends[index][i] == 1 && index != i){
        		neighbours.add((T)list.getVertice(i));
        	}
        }

        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {
    	//  check if the vertLabel exists or not
	   	 if(!list.alreadyExist((String)vertLabel)){
	   		throw new IllegalArgumentException("The person does not exist!");
	    	}

	   	 // get the index of the vertLabel
	   	 int index = list.getIndex((String)vertLabel);

	   	 // up every row below the index
	   	 for(int i = index; i < totalPeople - 1; i++){
	   		 for(int j = 0; j < totalPeople; j++){
	   			 friends[i][j] = friends[i][j+1];
	   		 }
	   	 }
	   	 // left every column on the right of the verLabel
	   	 for(int j = index; j < totalPeople - 1; j++){
	   		 for(int i = 0; i < totalPeople; i++){
	   			 friends[i][j] = friends[i+1][j];
	   		 }
	   	 }

	   	 // clear the last row and column
	   	 for(int i = 0; i < totalPeople; i++){
	   		 friends[totalPeople-1][i] = 0;
	   		 friends[i][totalPeople-1] =0;
	   	 }

	   	 // delete the vertice from the NKList
	   	 list.deleteVertice((String)vertLabel);

	   	 // update total people number
	   	 totalPeople --;


	   	 // downsize if the current size if less than half full
	   	 if(totalPeople  < Math.round(friends.length/2)){
	   		 downSize(this.resizeFactor);
	   	 }


    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {

    	//check if these two people exist and the edge existed
    	if(!list.alreadyExist((String)srcLabel)){
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!list.alreadyExist((String)tarLabel)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	// remove the edge on both sides
    	int scrIndex = list.getIndex((String)srcLabel);
    	int tarIndex = list.getIndex((String)tarLabel);

    	friends[scrIndex][tarIndex] = 0;
    	friends[tarIndex][scrIndex] = 0;

    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
    	for(int i = 0; i< totalPeople; i++){
    		os.println(list.getVertice(i) + '\t');
    	}

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for(int i = 0; i < totalPeople; i++){
        	for(int j =0; j < totalPeople; j++){
        		if((friends[i][j] == 1) && (i != j)){
        			os.println(list.getVertice(i) +'\t'+ list.getVertice(j));
        		}

        	}
        }
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	//check if these two people exist and the edge existed
    	if(!list.alreadyExist((String)vertLabel1)){
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!list.alreadyExist((String)vertLabel2)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	int disconnectedDist = 0;
    	distMap.clear();
    	visitedList.clear();

    	if(vertLabel1.equals(vertLabel2)){
    		return disconnectedDist;
    	}

    	// initialize with the starting point
    	distMap.put((String)vertLabel1, disconnectedDist);
    	visitedList.addVertice((String) vertLabel1);
    	disconnectedDist = NKbfsMatrix((String) vertLabel1, (String)vertLabel2);


   // 	distMap.
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()


    public int NKbfsMatrix(String startPerson, String targPerson){

    	int currentDist = distMap.get(startPerson);

    	// check if startPerson is the target person
    	if(startPerson.equals(targPerson)){
    		return currentDist;
    	}


    	ArrayList<T> friendsNearby = neighbours((T)startPerson);
    	for(int i = 0; i < friendsNearby.size(); i++){
    		String tempFriend = (String)friendsNearby.get(i);
    		// check if it is visited
    		if(!visitedList.alreadyExist(tempFriend)){
    			// if not visited, add to list and map
    			if(tempFriend.equals(targPerson))
    				return (currentDist +1);
    			else{
    				visitedList.addVertice(tempFriend);
        			distMap.put(tempFriend, currentDist+1);
    			}
    		}
    	}

    	// find the next person to recurse in the visitedList
    	Node currentNode = visitedList.nkHead;
    	while(!currentNode.getValue().equals(startPerson)){
    		currentNode = currentNode.getNext();
    	}

    	// if targPerson cannot be found throughout the list, return -1
    	if(currentNode.getNext() == null) {
    		return -1;
    	}
    	else
    		return NKbfsMatrix(currentNode.getNext().getValue(), targPerson);
    }



    public void expand(int factor){
    	arraySize = arraySize * factor;
    	int[][] largerFriends = new int[arraySize*factor][arraySize*factor];
    	for(int i = 0; i < friends.length; i++){
    		for(int j = 0; j < friends[i].length; j++){
    			largerFriends[i][j] = friends[i][j];
    		}
    	}
    	friends = largerFriends;
    }

    public void downSize(int factor){
    	arraySize = Math.round(arraySize/factor);
    	int[][] newArray = new int[arraySize][arraySize];
    	for(int i = 0; i < newArray.length; i++){
    		for(int j = 0; j < newArray[i].length; j++){
    			newArray[i][j] = friends[i][j];
    		}
    	}
    	friends = newArray;
    }

} // end of class AdjMatrix