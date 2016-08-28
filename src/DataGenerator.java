import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


/**
 * Generates test files 
 *
 *
 * @author Nicole Renner, 2016.
 */
public class DataGenerator {
	static String filename; // the base file
	static String savefile;	// the new file with the data generated
	static int[][] originalData;

	public static void main(String Args[]){
		Scanner reader = new Scanner(System.in);

		System.out.println("Input the filename to be read from");
		filename = reader.nextLine();
		
		originalData = pullData();
		System.out.println("Data is extracted successfully.");

		System.out.println("Input the file to be saved in");
		savefile = reader.nextLine();

		System.out.println("Enter 'AV' to generate new vertices, 'AE' to generate new edges, 'RV' to generate vertices to be deleted, 'RE' to generate edges to be deleted, 'S' to generate relationships to be searched.");
		String typechoice = reader.nextLine();

		Random ran = new Random();
		
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(savefile));

			switch (typechoice) {
			case "AV":
				System.out.println("Input the starting number of relationships to be generated");
				int starting = reader.nextInt();
				System.out.println("Input the ending number of relationships to be generated");
				int ending = reader.nextInt();
				int verticesadd = ending - starting;
				
				for (int x = 4039; x < verticesadd + 4039; x++) {
					writer.println("AV " + (x + starting));
					System.out.println("AV " + (x + starting));
				}
				writer.close();
				reader.close();
				break;
			case "AE":
				System.out.println("Input the number of edges to be generated");
				int toBeAdded = reader.nextInt();
				int counter = 0;
				int[][] newDataSet = new int[toBeAdded][2];
		
				// generate randomly the number and check dupicates
				while (counter < toBeAdded) {
					
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					
					if (vertex1 != vertex2) { // avoid adding self-linkage
						if (checkFile(vertex1, vertex2)) { // avoid adding existing edges in original file
							if(checkNewDataSet(newDataSet, vertex1, vertex1)){ // avoid adding existing edges in the new collection
								System.out.println(counter); // ?? temperarily 
								newDataSet[counter][0] = vertex1;
								newDataSet[counter][1] = vertex2;
								counter++;
							}
						}
						else
							System.err.println("Edge found");
					}
				}
				
				// write to file
				for(int i = 0; i < newDataSet.length; i++){
					writer.println(Integer.toString(newDataSet[i][0]) + " " + Integer.toString(newDataSet[i][1]));
				}
				writer.close();
				break;
			case "RV":
				System.out.println("Input the number of vertices to be deleted");
				int verticesdelete = reader.nextInt();
				// use set to avoid duplicates
				Set<Integer> toBeDeleted = new HashSet<Integer>();
				
				while (toBeDeleted.size() < verticesdelete) {
					// randomly remove the integer from the existing 4039 vertices.
					int vertex1 = ran.nextInt(4039);
					toBeDeleted.add(vertex1);
				}
				// write the data in files
				Iterator<Integer> itr = toBeDeleted.iterator();
				while(itr.hasNext()){
					writer.println("RV " + itr.next().toString());
	            }
				writer.close();
				reader.close();
				break;
			case "RE":
				System.out.println("Input the number of edges to be deleted");
				int toBeDeletedE = reader.nextInt();
				int countE = 0;
				int[][] newDataCollection = new int[toBeDeletedE][2];
				while (countE < toBeDeletedE) {
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					if (vertex1 != vertex2) { // check self links
						if (checkFile(vertex1, vertex2) == true) { // check not in the original file
							if(checkNewDataSet(newDataCollection, vertex1, vertex2)){ // check not in the new generated collection
								newDataCollection[countE][0] = vertex1;
								newDataCollection[countE][1] = vertex2;
								countE++;
							}
						}
						else
							System.err.println("Not Found");
					}
				}
				
				for(int i = 0; i < newDataCollection.length; i++){
					writer.println(Integer.toString(newDataCollection[i][0]) + " " + Integer.toString(newDataCollection[i][1]));
				}
				writer.close();
				reader.close();
				break;
			case "N":
				System.out.println("Input the number of neighbours to be generated");
				int verticesNeighbour = reader.nextInt();
				// use set to avoid duplicates
				Set<Integer> toBeGeneratedN = new HashSet<Integer>();
				
				while (toBeGeneratedN.size() < verticesNeighbour) {
					// randomly remove the integer from the existing 4039 vertices.
					int vertex1 = ran.nextInt(4039);
					toBeGeneratedN.add(vertex1);
				}
				// write the data in files
				Iterator<Integer> itrN = toBeGeneratedN.iterator();
				while(itrN.hasNext()){
					writer.println("N " + itrN.next().toString());
	            }
				writer.close();
				reader.close();
				break;	
			case "S":
				System.out.println("Input the number of paths to calculate");
				int pathstocalculate = reader.nextInt();
				int[][] newData = new int[pathstocalculate][2];
				for (int x = 0; x < pathstocalculate; x++) {
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
	
					if (vertex1 != vertex2) {// check if p1 and p2 are the same
						if(checkNewDataSet(newData, vertex1, vertex2)){ // check if they are covered already in the newly generated
							newData[x][0] = vertex1;
							newData[x][1] = vertex2;
						}
					}
				}
				for(int i = 0; i < newData.length; i++){
					writer.println(Integer.toString(newData[i][0]) + " " + Integer.toString(newData[i][1]));
				}
				
				reader.close();
				writer.close();
				break;
			default:
				System.out.println("Error in input. Please quit and try again.");
			}
				writer.close();
		}
		catch (IOException e1) {
		System.out.println("Can't print to file");
		}
		reader.close();
	}

	public static boolean checkFile(int p1, int p2) {
		boolean result = true;
		
		for(int i = 0; i < originalData.length; i++){
			if(originalData[i][0] == p1 && originalData[i][1] == p2){
				result = false;
				break;
			}
			if(originalData[i][0] == p2 && originalData[i][1] == p1){
				result = false;
				break;
			}
				
		}
			
		return result;
	}


	
	public static boolean checkNewDataSet(int[][] newDataSet, int p1, int p2){
		boolean result = true;
		Set<Integer> existing  = new HashSet<Integer>();
		for(int i = 0; i < newDataSet.length; i++){
			if(newDataSet[i][0] == p1){
				existing.add(newDataSet[i][1]);
			}
			
			if(newDataSet[i][1] == p1){
				existing.add(newDataSet[i][0]);
			}
		}
		
		Iterator<Integer> itr = existing.iterator();
		while(itr.hasNext()){
			if(itr.next() == p2){
				result = false;
				break;
			}
        }
		return result;
	}
	
	public static int[][] pullData(){
		int[][] data = new int[90000][2];
		int count = 0;
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));

			String line;
			String delimiter = " ";
			String[] tokens;
			String person1 = "0";
			String person2 = "0";
			
			
			while ((line = file.readLine()) != null) {
				tokens = line.split(delimiter);
				person1 = tokens[0];
				person2 = tokens[1];
				
				int p1 = Integer.parseInt(person1);
				int p2 = Integer.parseInt(person2);
				
				
				data[count][0] = p1;
				data[count][1] = p2;
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File Read Fail");
		} catch (IOException e) {
			System.out.println("Cannot open file ");
		}
		return data;
	}
}
