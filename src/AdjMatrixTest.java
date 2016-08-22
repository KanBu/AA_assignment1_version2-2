import java.io.PrintWriter;
import java.util.ArrayList;

public class AdjMatrixTest {

		public static void main(String Args[]){
			AdjMatrix<String> adjMatrix1 = new AdjMatrix<String>();

			PrintWriter os = new PrintWriter(System.out, true);


			String A = new String("A");
			String B = new String("B");
			String C = new String("C");
			String D = new String("D");
			String E = new String("E");
			String F = new String("F");
			String G = new String("G");
			adjMatrix1.addVertex(A);
			adjMatrix1.addVertex(B);
			adjMatrix1.addVertex(C);
			adjMatrix1.addVertex(D);
			adjMatrix1.addVertex(E);
			adjMatrix1.addVertex(F);
			adjMatrix1.addEdge(A, B);
			adjMatrix1.addEdge(C, B);
			adjMatrix1.addEdge(B, D);
			adjMatrix1.addEdge(A, E);
			adjMatrix1.addEdge(D, C);
			

			

			ArrayList<String> neighbours = new ArrayList<String>();
			String line = new String();
			neighbours = adjMatrix1.neighbours(A);
			for(int i = 0; i < neighbours.size(); i++){
				line = line + '\t' + neighbours.get(i);
			}
			System.out.println("A: " + line);
			
			line = null;
			neighbours = adjMatrix1.neighbours(F);
			for(int i = 0; i < neighbours.size(); i++){
				line = line + '\t' + neighbours.get(i);
			}
			System.out.println("F: " + line);
			
			int answer;
			answer = adjMatrix1.shortestPathDistance(E, B);
			System.out.println(answer);
			answer = adjMatrix1.shortestPathDistance(E, C);
			System.out.println(answer);
			answer = adjMatrix1.shortestPathDistance(B, F);

			System.out.println(answer);

			adjMatrix1.removeVertex(D);

			adjMatrix1.removeEdge(A, B);
	
			adjMatrix1.addVertex(G);
			adjMatrix1.printVertices(os);

			adjMatrix1.printEdges(os);

	}
}
