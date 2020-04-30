/* Class: CISC 3130
 * Section: TY9
 * Name: Mehdi Imam
 */

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			
			String[][] topList = parseFile("regional-global-weekly-2020-01-17--2020-01-24.csv");

			FileWriter writer = new FileWriter("Top Streaming Artists.txt");
			
			TopStreamingArtists artists = new TopStreamingArtists();
			
			String highest = "";
			for(int i = 0; i < 200; i++) {
				
				for(String[] rows : topList) {
					if(rows[2].compareToIgnoreCase(highest) > 0) {
						highest = rows[2];
						rows[2] = "";
					}
				}
				artists.insert(artists, highest);
				highest = "";
			}
			writer.write(artists.printList(artists));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static String[][] parseFile(String fileName) throws FileNotFoundException, IOException {
		String[][] topStreamedList = new String[200][5];
		BufferedReader reader = null;

		// Scanner was not reading entire file so found BufferedReader online
		// Create reader in one line
		reader = new BufferedReader(new FileReader(new File("src/main/resources/" + fileName)));

		// Skip note and row with column names
		reader.readLine();
		reader.readLine();

		for (int row = 0; row < topStreamedList.length; row++) {
			
			// Eventually inserted into topStreamedList[row]
			String[] finalRow = new String[5];
			
			// Used for fixing 'Artist' section with extra commas after dealing with 'Track Name' commas
			String[] afterTitle;

			// Simplify line by removing quotes and replacing brackets with parenthesis
			String line = reader.readLine().replace("\"", "").replace("[", "(").replace("]", ")");
			
			// First, dealing with extra commas in 'Track Name'
			// If line has a (feat. [artists]) in the title
			if (line.contains("(")) {

				// Fill in properly at first two indexes
				finalRow[0] = line.substring(0, line.indexOf(","));
				finalRow[1] = line.substring(line.indexOf(",") + 1).substring(0, line.substring(line.indexOf(",") + 1).indexOf(")") + 1);

				// Split from end of title so the commas in (feat. [artists]) don't get split
				afterTitle = line.substring(line.lastIndexOf(")"))
						.substring(line.substring(line.lastIndexOf(")")).indexOf(",") + 1).split(",");
			}

			else {
				finalRow[0] = line.substring(0, line.indexOf(","));
				finalRow[1] = line.substring(line.indexOf(",") + 1).substring(0, line.substring(line.indexOf(",") + 1).indexOf(","));
				afterTitle = (line.substring(line.indexOf(",") + 1) .substring(line.substring(line.indexOf(",") + 1).indexOf(",") + 1)).split(",");
			}

			// Then, dealing with extra commas in 'Artists'

			// If there more than one artist listed, add them to index 0 of afterTitle where they belong
			if (afterTitle.length > 3) {
				for(int i = 1; i < afterTitle.length - 2; i++) {
					afterTitle[0] += "," + afterTitle[i];
				}
				// Push 'Streams' and 'URL' to where they belong
				afterTitle[1] = afterTitle[afterTitle.length - 2];
				afterTitle[2] = afterTitle[afterTitle.length - 1];
			}

			// Finish filling in finalRow
			for (int i = 0; i < 3; i++)
				finalRow[i + 2] = afterTitle[i];

			topStreamedList[row] = finalRow;
		}
		
		reader.close();
		return topStreamedList;
	}

	public static void print(String[][] finalRow) {
		for (String[] rows : finalRow) {
			for (String string : rows)
				System.out.println(string + " ");
			System.out.println();
		}
	}
}
