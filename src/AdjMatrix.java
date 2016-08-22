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

	private int arraySize = 2;
	private boolean[][] friends = new boolean[arraySize][arraySize];
	private int totalPeople = 0;
	public static int resizeFactor = 2;
	private HashMap<String, Integer> distMap = new HashMap<String, Integer>();
	private NKList visitedList = new NKList();
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	int count = 0;


    public AdjMatrix() {
    	// Implement me!
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
    	if(map.containsKey((String) vertLabel)){
    		//System.err.println("Already exists");
    		//throw new IllegalArgumentException("The person already exists");
    		return;
    	}

    	

    	if(totalPeople >= arraySize){
    		expand(resizeFactor);
    	}

    	map.put((String) vertLabel, totalPeople);
    	totalPeople++;

    	System.err.println(totalPeople);
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {
    	if(!map.containsKey((String) srcLabel)){
    		//return;
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	if(!map.containsKey((String) tarLabel)){
    		//return;
    		throw new IllegalArgumentException("The person does not exists");
    	}
    	int srcIndex = map.get((String)srcLabel);
    	int tarIndex = map.get((String)tarLabel);
    	friends[srcIndex][tarIndex] = true;
    	friends[tarIndex][srcIndex] = true;
    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) throws IllegalArgumentException {
        ArrayList<T> neighbours = new ArrayList<T>();

    	if(!map.containsKey((String) vertLabel)){
    		//return neighbours;
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
       	if(!map.containsKey((String) vertLabel)){
       		throw new IllegalArgumentException("The person does not exists");
       	//return;
       	}
       	int index = map.get(vertLabel);
       	for(int i = 0; i < map.size(); i++){
       		friends[index][i] = false;
       		friends[i][index] = false;
       	}

    	map.remove((String) vertLabel);

       	totalPeople --;
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
    	if(!map.containsKey((String) srcLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    		//return;
    	}
    	if(!map.containsKey((String) tarLabel)){
    		throw new IllegalArgumentException("The person does not exists");
    		//return;
    	}
      	// remove the edge on both sides
    	int scrIndex = map.get((String)srcLabel);
    	int tarIndex = map.get((String)tarLabel);

    	friends[scrIndex][tarIndex] = false;
    	friends[tarIndex][scrIndex] = false;
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
    	String outputvertices = "";
    	for(Map.Entry<String, Integer> entry : map.entrySet()){
    		outputvertices = outputvertices + " " + entry.getKey();
    	}

    	os.println(outputvertices);
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	 for(int i = 0; i < totalPeople; i++){
         	for(int j =0; j < totalPeople; j++){
         		if((friends[i][j] == true)){
         			os.println(findVertice(i) +'\t'+ findVertice(j));
         		}
         	}
         }
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	int disconnectedDist = 0;
       if(!map.containsKey((String) vertLabel1)){
    		throw new IllegalArgumentException("The person does not exists");
    	  // return -1;
       }
       if(!map.containsKey((String) vertLabel2)){
    		throw new IllegalArgumentException("The person does not exists");
    	 //  return -1;
       }

    	distMap.clear();
    	visitedList.clear();

    	if(vertLabel1.equals(vertLabel2)){
    		return disconnectedDist;
    	}

    	// initialize with the starting point
    	distMap.put((String)vertLabel1, disconnectedDist);
    	visitedList.addVertice((String) vertLabel1);
    	disconnectedDist = NKbfsMatrix((String) vertLabel1, (String)vertLabel2);


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

    public void expand(int factor){
    	arraySize = arraySize * factor;
    	boolean[][] largerFriends = new boolean[arraySize][arraySize];
    	for(int i = 0; i < friends.length; i++){
    		for(int j =0; j < friends[i].length; j++){
    			largerFriends[i][j] = friends[i][j];
    		}
    	}
    	friends = largerFriends;
    }

} // end of class AdjMatrix