
import java.io.PrintWriter;


public class AdjListTest<T extends Object> {
	
	
	public static void main(String Args[]){
		AdjList<String> adjList1 = (AdjList<String>)new AdjList();
		
		String s1 = new String("S");
		String s2 = new String("A");
		String s3 = new String("P");
		String s4 = new String("M");
		String s5 = new String("B");
		String s6 = new String("H");
		String s7 = new String("X");
		
		adjList1.addVertex(s1);
		adjList1.addVertex(s2);
		adjList1.addVertex(s3);
		adjList1.addVertex(s4);
		adjList1.addVertex(s5);
		adjList1.addVertex(s6);

		adjList1.addEdge(s1, s2);
		adjList1.addEdge(s1, s3);
		adjList1.addEdge(s1, s5);
		adjList1.addEdge(s3, s5);
		adjList1.addEdge(s2, s3);
		adjList1.addEdge(s3, s4);
		adjList1.addEdge(s5, s6);
		
		
		// test printing
		PrintWriter os = new PrintWriter(System.out, true);
		adjList1.printVertices(os);
		System.out.println();
		adjList1.printEdges(os);

		// test shortest distance
		String start = s2;
		String stop = s7;
		int shortDist = adjList1.shortestPathDistance(start, stop);
		System.out.print("Shortest distance from " + start + " to " + stop + " is: " + shortDist);
		
	}
}
