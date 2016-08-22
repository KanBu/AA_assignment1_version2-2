import java.util.Random;
import java.util.Scanner;

public class DataGenerator {
	// local variables
		public int exitingTotalVertices; // = 4500; // ?? needs double-check
		public int exitingTotalEdges;// = 100000; // ?? needs double-check
		public int totalVertices;// = 0;
		public int totalEdges;// = 0;
		
		public String[] menuOptions = new String[6];
		
		public int density; 
		public int lowDensity;
		public int mediumDensity;
		public int highDensity;
		public int lowDensThres;
		public int medDensThres;
		public int higDensThres;
	
	public DataGenerator(){
		exitingTotalVertices = 4038;
		exitingTotalEdges  = 88234 * 2;
	
		density = -1;
		lowDensity = 0;
		mediumDensity = 1;
		highDensity = 2;
		
		lowDensThres = 100;
		medDensThres = 500;
		higDensThres = 1000;	

	}
		
	
	public void main(String Args[]){
		Scanner sc = new Scanner(System.in);
		// initialize menu
		menu();
		int fileType = sc.nextInt();
		// asking for density
		density();
		double density = sc.nextDouble();
		
		
		
	}
	
	
	public void menu(){
		menuOptions[0].equals("Add Vertices");
		menuOptions[1].equals("Add Edges");
		menuOptions[2].equals("Remove Vertice");
		menuOptions[3].equals("Remove Edges");
		menuOptions[4].equals("Query Surrounds");
		menuOptions[5].equals("Query Shortest Distance");
		
		// display
		System.out.println('\t' + "Menu" + '\t');
		for(int i = 0; i < menuOptions.length; i++){
			System.out.println(i + '\t' + menuOptions[i]);
		}
	}
	
	public void density(){
		int[] densityOptions = new int[3];
		densityOptions[0] = this.lowDensity;
		densityOptions[1] = this.mediumDensity;
		densityOptions[2] = this.highDensity;
	}
	
	public double calculateDensity(){
		double results = -1;
		
		
		return results;
	}
	
	
	public boolean testBreakInForLoop(String str){
		for(int i = 1; i < 2; i++){
			if(str.equals("break"))
				return true;
		}
		return false;
	}
}
