import java.io.PrintWriter;
import java.util.ArrayList;

public class AdjMatrixTest {

		public static void main(String Args[]){
			AdjMatrix<String> mat = new AdjMatrix<String>();

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
			
			mat.addVertex(A);
			mat.addVertex(B);
			mat.addVertex(C);
			mat.addVertex(D);
			mat.addVertex(E);
			mat.addVertex(F);
			mat.addVertex(G);
			mat.addVertex(H);
			mat.addVertex(I);
			mat.addVertex(J);
			mat.addVertex(K);
			
			mat.addEdge(A, C);
			mat.addEdge(A, F);
			mat.addEdge(A, G);
			mat.addEdge(C, G);
			mat.addEdge(B, D);
			mat.addEdge(C, D);
			mat.addEdge(D, E);
			mat.addEdge(D, J);
			mat.addEdge(D, I);
			mat.addEdge(E, K);
			mat.addEdge(F, K);
			mat.addEdge(I, J);
			mat.addEdge(H, I);
			
			
			// TEST 3 --------------
			
			
			mat.addVertex(B);
			mat.addVertex(Z);
			mat.addEdge(K,J);
			//mat.addEdge(B,Y);
			mat.addEdge(Z,F);
			
			mat.removeVertex(A);
			mat.removeVertex(B);
			
			mat.removeEdge(D, C);
			mat.removeEdge(H, I);
			
			mat.removeVertex(E);
			
			// mat.addEdge(E,D);
			mat.addEdge(C,D);
			
			
			// mat.neighbours(B);
			mat.neighbours(D);
			
			mat.shortestPathDistance(H, K);
			mat.shortestPathDistance(G, K);
			
			
			mat.printVertices(os);

			mat.printEdges(os);

	}
}
