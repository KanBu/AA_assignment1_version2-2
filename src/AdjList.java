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
	private HashMap<T, Integer> map = new HashMap<T, Integer>();
	private NKList[] friends = new NKList[5000];
	private int totalPeople;
	private NKList reuseList = new NKList(); // to record the list people having been deleted


	private NKList visitedList = new NKList();
	private HashMap<String, Integer> distMap = new HashMap<String, Integer>();

    /**
	 * Constructs empty graph.
	 */
    public AdjList() {
    	totalPeople = 0;
    } // end of AdjList()


    public void addVertex(T vertLabel) {
    	if(checkPersonInList(vertLabel)){
    		//throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    		return;
    	}
    	
    	int index;
    	// check if the array overflow or not
    	if(totalPeople >= 5000){
    		// get the last index value in the NKList "indexA fterDeletion"
    		index = Integer.parseInt(reuseList.getVertice(reuseList.getLength()-1));
    	}else{
    		// in the condition that no overflow
    		index = totalPeople;
    	}

    	// update the map
    	friends[index] = new NKList();
    	map.put(vertLabel, index);
    	// update the total people
    	totalPeople ++;
    } // end of addVertex()


    public void addEdge(T srcLabel, T tarLabel) {

    	//check if these two people exist
    	if(!checkPersonInList(srcLabel)){
    		return;
    		// throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!checkPersonInList(tarLabel)){
    		return;
    		//throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	//find and update the srcLabel's friends list
    	int index = map.get(srcLabel);
    	friends[index].addVertice((String)tarLabel);

    	//find the update the tarLabel's friends list
    	index = map.get(tarLabel);
    	friends[index].addVertice((String)srcLabel);

    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        // check if the vertLabel exists or not
        if(!checkPersonInList(vertLabel)){
        	return neighbours;
     //   	throw new IllegalArgumentException("The person does not exist! Please add the person first!");
    	}

        // find the neighbours
        int address = map.get(vertLabel);

        NKList targetFriends =friends[address];
        for(int i = 0; i < targetFriends.getLength(); i++){
        	neighbours.add((T)targetFriends.getVertice(i));
        }

        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {

    	// check if the vertLabel exists or not
    	 if(!checkPersonInList(vertLabel)){
    		 return;
    //		 throw new IllegalArgumentException("The person does not exist!");
     	}

    	// get the index and store it it indexAfterDeletion
    	 int index = map.get(vertLabel);
    	 reuseList.addVertice(Integer.toString(index));

    	// remove the person from his/her friends list
    	 removeVFromFriendsList(vertLabel);

    	// clear the friends index of the target person
    	 friends[index] = null;
    	// remove the person in map
    	map.remove(vertLabel);

    	// update total people number
    	totalPeople --;


    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
    	//check if these two people exist and the edge existed?
    	if(!checkPersonInList(srcLabel)){
    		return;
    //		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!checkPersonInList(tarLabel)){
    		return;
    //		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
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
    	if(!checkPersonInList(vertLabel1)){
    		return disconnectedDist; 
    //		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!checkPersonInList(vertLabel2)){
    		return disconnectedDist;
    //		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}


    	distMap.clear();
    	visitedList.clear();

    	if(vertLabel1.equals(vertLabel2)){
    		return disconnectedDist;
    	}

    	// empty the maps and list and then initialize with the starting point

    	distMap.put((String)vertLabel1, disconnectedDist);
    	visitedList.addVertice((String) vertLabel1);
    	disconnectedDist = NKbfsList((String) vertLabel1, (String)vertLabel2);


        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()


    public int NKbfsList(String startPerson, String targPerson){

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
    		return NKbfsList(currentNode.getNext().getValue(), targPerson);
    }


    // to find out whether it person is already in the map
    private boolean checkPersonInList(T person){
    	for(Entry<T, Integer> entry: map.entrySet()){
    		if(person.equals(entry.getKey())){
    			return true;
    		}
    	}
    	return false;
    }

    // to remove a person from his/her friends' list completed
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


    public void removeEdge1Side(T srcLabel, T removedLabel) {

    	//check if these two people exist and the edge existed
    	if(!checkPersonInList(srcLabel)){
   // 		throw new IllegalArgumentException("The first person does not exist! Please add the person first!");
    	}

    	if(!checkPersonInList(removedLabel)){
    //		throw new IllegalArgumentException("The last person does not exist! Please add the person first!");
    	}

    	// remove the edge in screLabel's friend list, one side only
    	int index = map.get(srcLabel);
    	friends[index].deleteVertice((String)removedLabel);

    } // end of removeEdges()


} // end of class AdjList
