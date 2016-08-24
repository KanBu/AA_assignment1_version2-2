
import java.io.PrintWriter;
import java.util.ArrayList;


public class AdjListTest<T extends Object> {
	
	
	public static void main(String Args[]){
		AdjList<String> list = new AdjList<String>();

		PrintWriter os = new PrintWriter(System.out, true);


		String A = new String("A");
		String B = new String("B");
		String C = new String("C");
		String D = new String("D");
		String E = new String("E");
		String F = new String("F");
		String G = new String("G");
		String H = new String("H");
		String I = new String("I");
		String J = new String("J");
		String K = new String("K");
		
		String Z = new String("Z");
		String Y = new String("Y");
		
		list.addVertex(A);
		list.addVertex(B);
		list.addVertex(C);
		list.addVertex(D);
		list.addVertex(E);
		list.addVertex(F);
		list.addVertex(G);
		list.addVertex(H);
		list.addVertex(I);
		list.addVertex(J);
		list.addVertex(K);
		
		list.addEdge(A, C);
		list.addEdge(A, F);
		list.addEdge(A, G);
		list.addEdge(C, G);
		list.addEdge(B, D);
		list.addEdge(C, D);
		list.addEdge(D, E);
		list.addEdge(D, J);
		list.addEdge(D, I);
		list.addEdge(E, K);
		list.addEdge(F, K);
		list.addEdge(I, J);
		list.addEdge(H, I);
		
		
		// TEST 3 --------------
		
		
		list.addVertex(B);
		list.addVertex(Z);
		list.addEdge(K,J);
		//list.addEdge(B,Y);
		list.addEdge(Z,F);
		
		list.removeVertex(A);
		list.removeVertex(B);
		
		list.removeEdge(D, C);
		list.removeEdge(H, I);
		
		list.removeVertex(E);
		
		// list.addEdge(E,D);
		list.addEdge(C,D);
		
		
		// list.neighbours(B);
		System.out.println("neighbours of D");
		ArrayList<String> neigh = list.neighbours(D);
		for(int i = 0; i < neigh.size(); i++){
			System.out.println(neigh.get(i));
		}
		System.out.println("End of the neighbours of D");
		list.shortestPathDistance(H, K);
		list.shortestPathDistance(G, K);
		
		
		list.printVertices(os);

		list.printEdges(os);



	}
}
