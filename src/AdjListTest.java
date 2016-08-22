
import java.io.PrintWriter;


public class AdjListTest<T extends Object> {
	
	
	public static void main(String Args[]){
		AdjList<String> adjList1 = (AdjList<String>)new AdjList();
		
		PrintWriter os = new PrintWriter(System.out, true);


		String A = new String("A");

		String B = new String("B");

		String C = new String("C");

		String D = new String("D");

		String E = new String("E");

		String F = new String("F");

		String G = new String("G");

		

		adjList1.addVertex(A);

		adjList1.addVertex(B);

		adjList1.addVertex(C);

		adjList1.addVertex(D);

		adjList1.addVertex(E);

		adjList1.addVertex(F);

		

		

		adjList1.addEdge(A, B);

		adjList1.addEdge(C, B);

		adjList1.addEdge(B, D);

		adjList1.addEdge(A, E);

		adjList1.addEdge(D, C);

		adjList1.printVertices(os);

		adjList1.printEdges(os);

		

		

		adjList1.removeVertex(D);

		adjList1.removeEdge(A, B);

		

		adjList1.addVertex(G);

		

		

		adjList1.printVertices(os);

		adjList1.printEdges(os);

	}
}
