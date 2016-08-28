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
	

	public static void main(String Args[]){
		Scanner reader = new Scanner(System.in);

		System.out.println("Input the filename to be read from");
		filename = reader.nextLine();

		System.out.println("Input the file to be saved in");
		savefile = reader.nextLine();

		System.out.println("Enter 'AV' to generate new vertices, 'AE' to generate new edges, 'RV' to generate vertices to be deleted, 'RE' to generate edges to be deleted, 'S' to generate relationships to be searched.");
		String typechoice = reader.nextLine();

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
				int tobeAdded = reader.nextInt();
				int counter = 0;
				int[][] newDataSet = new int[tobeAdded][2];
				while (counter < tobeAdded) {
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					
					if (!p1.equals(p2)) { // avoid adding self-linkage
						if (checkFile(filename, p1, p2) == false) { // avoid adding existing edges in original file
							if(checkNewDataSet(newDataSet, vertex1, vertex1)){ // avoid adding existing edges in the new collection
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
					Random ran = new Random();
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
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					if (!p1.equals(p2)) { // check self links
						if (checkFile(filename, p1, p2) == true) { // check not in the original file
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
					Random ran = new Random();
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
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					if (!p1.equals(p2)) {// check if p1 and p2 are the same
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

	public static boolean checkFile(String filename, String p1, String p2) {
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

				if (person1.equals(p1) && person2.equals(p2)) {
					file.close();
					return true;
				}
				if (person1.equals(p2) && person2.equals(p1)) {
					file.close();
					return true;
				}
			}
			file.close();
			return false;
		}
		catch (FileNotFoundException e) {
			System.out.println("File Read Fail");
		} catch (IOException e) {
			System.out.println("Cannot open file ");
		}
		return false;
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
}
