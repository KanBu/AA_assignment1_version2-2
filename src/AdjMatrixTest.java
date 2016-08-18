import java.io.PrintWriter;

public class AdjMatrixTest {
	public static void main(String Args[]){
		AdjMatrix<String> adjMatrix1 = new AdjMatrix<String>();
		
		String s1 = new String("S");
		String s2 = new String("A");
		String s3 = new String("P");
		String s4 = new String("M");
		String s5 = new String("B");
		String s6 = new String("H");
		String s7 = new String("X");
		
		adjMatrix1.addVertex(s1);
		adjMatrix1.addVertex(s2);
		adjMatrix1.addVertex(s3);
		adjMatrix1.addVertex(s4);
		adjMatrix1.addVertex(s5);
		adjMatrix1.addVertex(s6);
		adjMatrix1.addVertex(s7);
		
		
		adjMatrix1.addEdge(s1, s2);
		adjMatrix1.addEdge(s1, s3);
		adjMatrix1.addEdge(s1, s5);
		adjMatrix1.addEdge(s3, s5);
		adjMatrix1.addEdge(s2, s3);
		adjMatrix1.addEdge(s3, s4);
		adjMatrix1.addEdge(s5, s6);
		
		
		// test printing
		PrintWriter os = new PrintWriter(System.out, true);
		adjMatrix1.printVertices(os);
		System.out.println();
		adjMatrix1.printEdges(os);

		// test shortest distance
		String start = s2;
		String stop = s7;
		int shortDist = adjMatrix1.shortestPathDistance(start, stop);
		System.out.print("Shortest distance from " + start + " to " + stop + " is: " + shortDist);
	}
}
