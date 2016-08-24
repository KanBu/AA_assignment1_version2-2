import java.io.*;
import java.util.*;


/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{

	/**
	 * Contructs empty graph.
	 */

	private int arraySize = 2; // array size start from 2 in constructor
	private boolean[][] friends = new boolean[arraySize][arraySize]; // boolean array to indicate edges
	private int totalPeople = 0; // total people number
	public static int resizeFactor; // array expand by the factor of 2 in constructor
	private HashMap<String, Integer> distMap = new HashMap<String, Integer>(); // recording people and their distance from start person
	private NKList visitedList = new NKList(); // recording the people being visited in shortest distance calculation
	private HashMap<String, Integer> map = new HashMap<String, Integer>(); //recording the person and their index in the boolean array

    public AdjMatrix() {
    	arraySize = 2;
    	resizeFactor = 2;
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
    	if(map.containsKey((String) vertLabel)){
    		return;
    	}
    	
    	// expand if overflow
    	if(totalPeople >= arraySize){
    		expand(resizeFactor);
    	}
    	
    	// update the map and total people number
    	map.put((String) vertLabel, totalPeople);
    	totalPeople++;
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {
    	// check if the two people exist or not
    	if(!map.containsKey((String) srcLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	if(!map.containsKey((String) tarLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	
    	// get the index and update the boolean value in array
    	int srcIndex = map.get((String)srcLabel);
    	int tarIndex = map.get((String)tarLabel);
    	friends[srcIndex][tarIndex] = true;
    	friends[tarIndex][srcIndex] = true;
    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) throws IllegalArgumentException {
        ArrayList<T> neighbours = new ArrayList<T>();
        
        // check if the person exists
    	if(!map.containsKey((String) vertLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    	}

    	 // find the index in the array
    	int index = map.get((String)vertLabel);

    	// find that specific row in the matrix
        for(int i = 0; i < friends[index].length; i++){
        	if(friends[index][i] == true){
        		String targPerson = findVertice(i);
        		neighbours.add((T)targPerson);
        	}
        }
        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {
    	// check if the person exists
       	if(!map.containsKey((String) vertLabel)){
       		throw new IllegalArgumentException("The person does not exists");
       	}
       	
       	// get the index and update the boolean value 
       	int index = map.get(vertLabel);
       	for(int i = 0; i < friends.length; i++){
       		friends[index][i] = false;
       		friends[i][index] = false;
       	}

       	// remove the person and the index from the map
    	map.remove((String) vertLabel);
    	
    	// update the total people
       	totalPeople --;
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
    	// check if the two people exist or not
    	if(!map.containsKey((String) srcLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	if(!map.containsKey((String) tarLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	
      	// remove the edge on both sides
    	int scrIndex = map.get((String)srcLabel);
    	int tarIndex = map.get((String)tarLabel);
    	friends[scrIndex][tarIndex] = false;
    	friends[tarIndex][scrIndex] = false;
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
    	String outputvertices = new String();
    	for(Map.Entry<String, Integer> entry : map.entrySet()){
    		outputvertices = outputvertices + " " + entry.getKey();
    	}

    	os.println(outputvertices);
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	System.err.println("TotalPeole: " + totalPeople);
    	 for(int i = 0; i < friends.length; i++){
         	for(int j =0; j < friends.length; j++){
         		if((friends[i][j] == true)){
         			System.err.println("i: " + i + "  " + "j: " + j);
         			os.println(findVertice(i) +'\t'+ findVertice(j));
         		}
         	}
         }
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	int disconnectedDist = 0;
    	
    	// check if the two people exist
    	if(!map.containsKey((String) vertLabel1)){
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	if(!map.containsKey((String) vertLabel2)){
    		throw new IllegalArgumentException("The person does not exists");
    	}

    	// clear the map and list
    	distMap.clear();
    	visitedList.clear();

    	// check if the distance is zero
    	if(vertLabel1.equals(vertLabel2)){
    		return disconnectedDist;
    	}

    	// initialize with the starting point
    	distMap.put((String)vertLabel1, disconnectedDist);
    	visitedList.addVertice((String) vertLabel1);
    	
    	// recursive 
    	disconnectedDist = NKbfsMatrix((String) vertLabel1, (String)vertLabel2);

        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()
    
    // breath first search
    public int NKbfsMatrix(String startPerson, String targPerson){
    	// take the start person the distance from the targPerson
    	int currentDist = distMap.get(startPerson);

    	// check if startPerson is the target person
    	if(startPerson.equals(targPerson)){
    		return currentDist;
    	}

    	// get the friend list of startPerson
    	ArrayList<T> friendsNearby = neighbours((T)startPerson);
    	
    	// loop through the friend list
    	for(int i = 0; i < friendsNearby.size(); i++){
    		String tempFriend = (String)friendsNearby.get(i);
    		// check if it is visited
    		if(!visitedList.alreadyExist(tempFriend)){
    			// if not visited, add to list and map
    			if(tempFriend.equals(targPerson))
    				return (currentDist +1);
    			else{
    				// if not visited and not the targPersonn, add the person to the visited list and  distance map
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

    // find the vertice on given index
    public String findVertice(int index){
    	String person = new String();
    	for(Map.Entry<String, Integer> entry : map.entrySet()){
    		if(entry.getValue() == index){
    			person = entry.getKey();
                break;
    		}
    	}
    	return person;
    }

    // expand the array size by the factor given
    public void expand(int factor){
    	arraySize = arraySize * factor;
    	boolean[][] largerFriends = new boolean[arraySize][arraySize];
    	
    	// move data from all array to new one
    	for(int i = 0; i < friends.length; i++){
    		for(int j =0; j < friends[i].length; j++){
    			largerFriends[i][j] = friends[i][j];
    		}
    	}
    	friends = largerFriends;
    }

} // end of class AdjMatrix