import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.lang.reflect.Array;


/**
 * Adjacency list implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjList <T extends Object> implements FriendshipGraph<T>
{
	private HashMap<T, Integer> map = new HashMap<T, Integer>(); // recording people and their index in linked list array 
	private NKList[] friends = new NKList[5000]; // array of people's friends' lists
	private int totalPeople; // total number of existing people 
	private NKList reuseList = new NKList(); // recording the spare indexed by people being deleted for reuse
	private NKList visitedList = new NKList(); // recording the people being visited in shortest distance calculation	
	private HashMap<String, Integer> distMap = new HashMap<String, Integer>(); // recording people and their distance from start perpson

    /**
	 * Constructs empty graph.
	 */
    public AdjList() {
    	totalPeople = 0;
    } // end of AdjList()


    public void addVertex(T vertLabel) {
    	// check if the person exists or not
    	if(checkPersonInList(vertLabel)){
    		return;
    	}
    	
    	int index; // index of new person
    	if(totalPeople >= 5000){
    		// use the last index value in the reuse list if it reaches the capacity
    		index = Integer.parseInt(reuseList.getVertice(reuseList.getLength()-1));
    	}else{
    		// if there has not been overflow
    		index = totalPeople;
    	}
    	
    	// update the map and total people number
    	friends[index] = new NKList();
    	map.put(vertLabel, index);
    	totalPeople ++;
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {

    	//check if these two people exist
    	if(!checkPersonInList(srcLabel)){
    		 throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}
    	if(!checkPersonInList(tarLabel)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	//find and update the srcLabel's friends list
    	int index = map.get(srcLabel);
    	friends[index].addVertice((String)tarLabel);

    	//find and update the tarLabel's friends list
    	index = map.get(tarLabel);
    	friends[index].addVertice((String)srcLabel);

    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        // check if the vertLabel exists or not
        if(!checkPersonInList(vertLabel)){
        	throw new IllegalArgumentException("The person does not exist! Please add the person first!");
    	}

        // find the responding friend list index in the linked list array
        int address = map.get(vertLabel);

        // transfer the people from the linked list to the array list
        NKList targetFriends =friends[address];
        for(int i = 0; i < targetFriends.getLength(); i++){
        	neighbours.add((T)targetFriends.getVertice(i));
        }
        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {

    	// check if the vertLabel exists or not
    	 if(!checkPersonInList(vertLabel)){
    		 throw new IllegalArgumentException("The person does not exist!");
     	}

    	// get the index and add it in reuse list
    	 int index = map.get(vertLabel);
    	 reuseList.addVertice(Integer.toString(index));

    	// remove the person from his/her friends list
    	 removeVFromFriendsList(vertLabel);

    	// clear the friend list on the target person
    	 friends[index] = null;
    	 
    	// remove the person in map
    	map.remove(vertLabel);

    	// update total people number
    	totalPeople --;
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
    	//check if these two people exist and the edge existed?
    	if(!checkPersonInList(srcLabel)){
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}
    	if(!checkPersonInList(tarLabel)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	// remove the edge on both sides
    	int index = map.get(srcLabel);
    	friends[index].deleteVertice((String)tarLabel);

    	//find and update the tarLabel's friends list
    	index = map.get(tarLabel);
    	friends[index].deleteVertice((String)srcLabel);
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
    	for(Entry<T, Integer> entry: map.entrySet()){
    		os.println((String)entry.getKey() + '\t');
    	}
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	NKList friendList = new NKList();
    	for(Entry<T, Integer> entry: map.entrySet()){
    		friendList = friends[entry.getValue()];
    		for(int i = 0; i < friendList.getLength(); i++){
    			os.println((String)entry.getKey() + "  " + friendList.getVertice(i));
    		}
    	}
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
    	int disconnectedDist = 0;
    	
    	// check if the two peopple exist or not
    	if(!checkPersonInList(vertLabel1)){ 
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}
    	if(!checkPersonInList(vertLabel2)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	// reset the disMap and visited list
    	distMap.clear();
    	visitedList.clear();
    	
    	// check if the distance equals zero
    	if(vertLabel1.equals(vertLabel2)){
    		return disconnectedDist;
    	}

    	// initialize with the starting point
    	distMap.put((String)vertLabel1, disconnectedDist);
    	visitedList.addVertice((String) vertLabel1);
    	
    	// recursive through breath first search
    	disconnectedDist = NKbfsList((String) vertLabel1, (String)vertLabel2);

        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()

    
    // breath first search 
    public int NKbfsList(String startPerson, String targPerson){
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
    		// check if the friend is visited
    		if(!visitedList.alreadyExist(tempFriend)){
    			// if not visited, check whether it is the targPerson 
    			if(tempFriend.equals(targPerson))
    				return (currentDist +1);
    			else{
    				// if not visited and not the targPersonn, add the person to the visited list and  distance map
    				visitedList.addVertice(tempFriend);
        			distMap.put(tempFriend, currentDist+1);
    			}
    		}
    	}

    	// find the next person from the head of the visited list
    	Node currentNode = visitedList.nkHead;
    	while(!currentNode.getValue().equals(startPerson)){
    		currentNode = currentNode.getNext();
    	}

    	// if targPerson cannot be found throughout the list, then return -1, else start next recurse
    	if(currentNode.getNext() == null) {
    		return -1;
    	}
    	else
    		return NKbfsList(currentNode.getNext().getValue(), targPerson);
    }


    // to find out whether the person is already in the map
    private boolean checkPersonInList(T person){
    	for(Entry<T, Integer> entry: map.entrySet()){
    		if(person.equals(entry.getKey())){
    			return true;
    		}
    	}
    	return false;
    }

    // remove a person from his/her friends' list 
    private void removeVFromFriendsList(T targVertice){

    	// get his/her friends list index
    	int index = map.get(targVertice);
    	// get his/her friend list
    	NKList VFriends = friends[index];

    	// go through each person and remote the target from their friend lists
    	for(int i = 0; i < VFriends.getLength(); i++){
    		removeEdge1Side((T)VFriends.getVertice(i), targVertice);
    	}
    }

    // only remove removedLabel from scrLabel's friend list  
    public void removeEdge1Side(T srcLabel, T removedLabel) {

    	//check if these two people exist and the edge existed
    	if(!checkPersonInList(srcLabel)){
    		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}
    	if(!checkPersonInList(removedLabel)){
    		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	// remove the edge in screLabel's friend list, one side only
    	int index = map.get(srcLabel);
    	friends[index].deleteVertice((String)removedLabel);

    } // end of removeEdges()


} // end of class AdjList
