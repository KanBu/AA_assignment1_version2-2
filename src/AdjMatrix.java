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
	// private HashMap<T, Integer> map = new HashMap<T, Integer>();
	private int arraySize;
	private NKList list = new NKList();
	private int[][] friends;
	private int totalPeople;
	public static int expandFactor = 2;
	
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
    		expand(expandFactor);
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
    	   
    	//??check if these two people exist ?? Try-catch-throw??
    	if(!list.alreadyExist((String)srcLabel)){
    		System.out.println("The first person does not exist! Please add the person first!");
    		return;
    	}
    	
    	if(!list.alreadyExist((String)tarLabel)){
    		System.out.println("The last person does not exist! Please add the person first!");
    		return;
    	}
    	
    	//find and update the srcLabel's friends list
    	int srcIndex = list.getIndex((String)srcLabel);
    	int tarIndex = list.getIndex((String)tarLabel);
    	friends[srcIndex][tarIndex] = 1;
    	friends[tarIndex][srcIndex] = 1;
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        
        // ?? check if the vertLabel exists or not?? try-catch??
        if(!list.alreadyExist((String)vertLabel)){
    		System.out.println("The person does not exist! Please add the person first!");
    		return neighbours;
    	}
        
        // find the index in the array
        int index = list.getIndex((String)vertLabel);
        
        // find that specific row in the matrix  
        for(int i = 0; i < friends[index].length; i++){
        	if(friends[index][i] == 1){
        		neighbours.add((T)list.getVertice(i));
        	}
        }
        
        return neighbours;
    } // end of neighbours()
    
    
    public void removeVertex(T vertLabel) {
    	// ?? check if the vertLabel exists or not?? try-catch??
	   	 if(!list.alreadyExist((String)vertLabel)){
	    		System.out.println("The person does not exist!");
	    		return;
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
	    	
    	
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
    	
    	//??check if these two people exist and the edge existed? ?? Try-catch-throw??
    	if(!list.alreadyExist((String)srcLabel)){
    		System.out.println("The first person does not exist! Please add the person first!");
    		return;
    	}
    	
    	if(!list.alreadyExist((String)tarLabel)){
    		System.out.println("The last person does not exist! Please add the person first!");
    		return;
    	}
    	 
    	// remove the edge on both sides
    	int scrIndex = list.getIndex((String)srcLabel);
    	int tarIndex = list.getIndex((String)tarLabel);
    	
    	friends[scrIndex][tarIndex] = 0;
    	friends[tarIndex][scrIndex] = 0;
    	
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    	for(int i = 0; i< totalPeople; i++){
    		os.print(list.getVertice(i) + '\t');
    	}
    	
    	os.print('\n');
    	
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
    	// Implement me!
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
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
    
    


    
    
    
} // end of class AdjMatrix