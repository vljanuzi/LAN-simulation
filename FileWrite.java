
import java.io.*; /* Required for handling the IOExceptions */

public class FileWrite {
	public static void main(String[] args) {
		writeToFile("outputfile.txt");
	}

	/* Method that writes contents to a file with the file name 'fileName' */
	public static void writeToFile(String fileName) {
		try {
			/* Create a FileWriter object that handles the low-level details of writing */
			FileWriter theFile = new FileWriter(fileName);

			/* Create a PrintWriter object to wrap around the FileWriter object */
			/* This allows the use of high-level methods like println */
			PrintWriter fileOut = new PrintWriter(theFile);

			/* Print some lines to the file using the println method */
			for (int i = 0; i < 10; i++)
				fileOut.println("Writing line " + i + " to the file.");

			/* Close the file so that it is no longer accessible to the program */
			fileOut.close();
		}

		/* Handle the exception thrown by the FileWriter methods */
		catch (IOException e) {
			System.out.println("Problem writing to the file");
		}
	} /* End of method writeToFile */
} /* End of class FileWrite */