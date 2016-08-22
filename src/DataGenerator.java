import java.util.Random;
import java.util.Scanner;

public class DataGenerator {
	// local variables
		public int exitingTotalVertices = 4500; // ?? needs double-check
		public int exitingTotalEdges = 100000; // ?? needs double-check
		public int totalVertices = 0;
		public int totalEdges = 0;
		
		static String[] menuOptions = new String[6];
		Random rd = new Random();
		
		
		public int density = -1;
		public static int lowDensity = 0;
		public static int mediumDensity = 1;
		public static int highDensity = 2;
		
		public static int lowDensThres = 100;
		public static int medDensThres = 500;
		public static int higDensThres = 1000;	

	
	public static void main(String Args[]){
		Scanner sc = new Scanner(System.in);
		// initialize menu
		menu();
		int fileType = sc.nextInt();
		// asking for density
		density();
			
		
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
