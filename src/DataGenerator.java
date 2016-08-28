import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
				System.out.println("Input the number of vertices to be generated");
				int verticesadd = reader.nextInt();
				for (int x = 4039; x < verticesadd + 4039; x++) {
					writer.println("AV " + x);
					System.out.println("AV " + x);
				}
				writer.close();
				reader.close();
				break;
			case "AE":
				System.out.println("Input the number of relationships to be generated");
				int tobeadded = reader.nextInt();
				int counter = 0;
				while (counter < tobeadded) {
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					if (!p1.equals(p2)) {
						if (checkFile(filename, p1, p2) == false) {
							writer.println(vertex1 + " " + vertex2);
							System.out.println("Found " + p1 + " " + p2);
							counter++;
							}
						}
						else
							System.err.println("Edge found");
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
				int tobedeleted = reader.nextInt();
				int count = 0;
				while (count < tobedeleted) {
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					if (!p1.equals(p2)) {
						if (checkFile(filename, p1, p2) == true) {
							writer.println("RE " + vertex1 + " " + vertex2);
							System.out.println("Found " + p1 + " " + p2);
							count++;
						}
						else
							System.err.println("Not Found");
					}
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
					writer.println("RV " + itrN.next().toString());
	            }
				writer.close();
				reader.close();
				break;	
			case "S":
				System.out.println("Input the number of paths to calculate");
				int pathstocalculate = reader.nextInt();
				for (int x = 0; x < pathstocalculate; x++) {
					Random ran = new Random();
					int vertex1 = ran.nextInt(4039);
					int vertex2 = ran.nextInt(4039);
					String p1 = Integer.toString(vertex1);
					String p2 = Integer.toString(vertex2);
					if (!p1.equals(p2)) {
						writer.println("S " + p1 + " " + p2);
						}
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
}
