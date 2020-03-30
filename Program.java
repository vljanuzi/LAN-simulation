
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Program {

	// check if the server is started (launched) before choosing any server related
	// action
	static boolean launched = false;

	public static void main(String[] args) {
		QueueInterface ipQueue = new QueueReferenceBased();
		ListInterface currentlyConnected = new ReferenceBasedList();
		ListInterface userList = new ReferenceBasedList();

		ipQueue.enqueue("192.168.0.1");
		ipQueue.enqueue("192.168.0.2");
		ipQueue.enqueue("192.168.0.3");
		ipQueue.enqueue("192.168.0.4");
		ipQueue.enqueue("192.168.0.5");

		int input;
		do {
			System.out.println("Please input the number corresponding to the option you want:\n" + "1.Network Server\n"
					+ "2.Network User\n" + "3.Exit Program\n");
			input = EasyIn.getInt();
			switch (input) {
			case 1:
				serverMenu(currentlyConnected, userList);
				break;
			case 2:
				userMenu(currentlyConnected, userList, ipQueue);
				break;
			case 3:
				if (launched)
					writeToFile("inputfile.txt", userList);
				break;
			default:
				System.out.println("That's not a valid option. Please pick between 1-3");
			}
		} while (input != 3);
	}

	// Network server
	public static void serverMenu(ListInterface currentlyConnected, ListInterface userList) {
		int input;
		String username;
		do {
			System.out.println("Please input the number corresponding to the option you want:\n" + "1.Start Server\n"
					+ "2.Add User\n" + "3.Remove User\n" + "4.View Computers\n" + "5.Go back");
			input = EasyIn.getInt();
			switch (input) {
			case 1:
				if (!launched)
					readFromFile("inputfile.txt", userList);
				else {
					// Shouldn't be able to start the server twice
					System.out.println("Server is already started!");
				}
				break;
			case 2:
				if (launched) {
					User temp = promptForUser();
					if (!userList.comparesToItems(temp))
						userList.addSorted(temp);
					else
						System.out.println("User already exists");
				} else
					System.out.println("Server isn't running!");
				break;
			case 3:
				if (launched) {
					System.out.println("Input the username you want to remove");
					username = EasyIn.getString();
					remove(new User(username, null), userList);
				} else
					System.out.println("Server isn't running!");

				break;
			case 4:
				if (currentlyConnected.size() == 0) {
					System.out.println("There are no connected PCs");
				} else
					currentlyConnected.print();
				break;
			case 5:
				System.out.println(" - - - - - - ");
				break;
			default:
				System.out.println("That's not a valid option. Please pick between 1-5");
			}
		} while (input != 5);
	}

	// Network user
	public static void userMenu(ListInterface currentlyConnected, ListInterface userList, QueueInterface ipQueue) {
		int input;
		User laptopUser;
		Laptop laptop;
		String hostName;
		do {
			System.out.println("Please input the number corresponding to the option you want:\n"
					+ "1.Connect to network\n" + "2.Disconnect from network\n" + "3.Ping Computer\n" + "4.Go back");
			input = EasyIn.getInt();
			switch (input) {
			case 1:
				if (!launched)
					System.out.println("Server isn't running!");
				else {
					if (currentlyConnected.size() >= 5)
						System.out.println("Network is full!");
					else {
						laptopUser = promptForUser();
						if (currentlyConnected.comparesToItems(laptopUser)) {
							System.out.println("User is already connected");
						} else if (!authenticate(userList, laptopUser)) {
							System.out.println("Wrong username or password");
						} else {
							System.out.println("What's the hostname of the PC you're in");
							hostName = EasyIn.getString();
							laptop = new Laptop(hostName);
							laptopUser.setLaptop(laptop);
							laptop.connect(ipQueue.dequeue().toString());
							currentlyConnected.add(1, laptopUser);
						}
					}
				}
				break;
			case 2:
				if (!launched)
					System.out.println("Server isn't running!");
				else {
					laptopUser = promptForUser();

					if (!currentlyConnected.comparesToItems(laptopUser)) {
						System.out.println("User is not connected");
					} else {
						ipQueue.enqueue(find(laptopUser.getUsername(), currentlyConnected).disconnect());
						remove(laptopUser, currentlyConnected);
					}
				}
				break;
			case 3:
				if (!launched)
					System.out.println("Server isn't running!");
				else {
					System.out.println("Type 1 if you want to ping by ip, type 2 if you want to ping by hostname");
					input = EasyIn.getInt();
					if (input == 1) {
						System.out.println("Type the ip you want to ping");
						pingByIP(EasyIn.getString(), currentlyConnected);
					} else if (input == 2) {
						System.out.println("Type the hostname you want to ping");
						pingByHostName(EasyIn.getString(), currentlyConnected);
					} else {
						System.out.println("That's not a valid input");
						input = 0;
					}
				}
				break;
			case 4:
				System.out.println(" - - - - - - ");
				break;
			default:
				System.out.println("That's not a valid option. Please pick between 1-4");
			}
		} while (input != 4);
	}

	public static void readFromFile(String fileName, ListInterface userList) {
		String username;
		String password;
		launched = true;
		try {
			/* Create a FileWriter object that handles the low-level details of reading */
			FileReader theFile = new FileReader(fileName);

			/* Create a BufferedReader object to wrap around the FileWriter object */
			/* This allows the use of high-level methods like readline */
			BufferedReader fileIn = new BufferedReader(theFile);

			/* Read the first line of the file */
			username = fileIn.readLine();
			password = fileIn.readLine();

			/* Read the rest of the lines of the file and output them on the screen */
			while (username != null && password != null) { /* A null string indicates the end of file */
				User laptopUser = new User(username, password);
				userList.add(1, laptopUser);
				// System.out.println(username + "\n" + password);
				username = fileIn.readLine();
				password = fileIn.readLine();
			}

			/* Close the file so that it is no longer accessible to the program */
			fileIn.close();
		}

		/*
		 * Handle the exception thrown by the FileReader constructor if file is not
		 * found
		 */
		catch (FileNotFoundException e) {
			System.out.println("Unable to locate the file: " + fileName);
		}

		/* Handle the exception thrown by the FileReader methods */
		catch (IOException e) {
			System.out.println("There was a problem reading the file: " + fileName);
		}
	} /* End of method readFromFile */

	// Finds a laptop through which a user is connected to the server
	// Precondition: the username of a currently connected user whose laptop is to
	// be returned
	// Postcondition: returns the laptop of the user, foung with the given username
	// If the username is not found in the currently connected, the method returns
	// null
	public static Laptop find(String username, ListInterface currentlyConnected) {
		String tmpUsername;
		for (int i = 1; i <= currentlyConnected.size(); i++) {
			tmpUsername = ((User) currentlyConnected.get(i)).getUsername();
			if (tmpUsername.equals(username)) {
				return ((User) currentlyConnected.get(i)).getLaptop();
			}
		}
		return null;
	}

	// Removes from the list of users the user with the given name
	// Precondition: name of the user we want to remove,
	// name of the list from which we want to remove the user
	// Postcondition: removes the wanted user from the list
	// If the user with the given name is not in the list, an appropriate message is
	// shown
	public static void remove(User obj, ListInterface userList) {
		boolean foundUser = false;
		for (int i = 1; i <= userList.size(); i++) {
			if (((User) userList.get(i)).compareTo(obj) == 0) {
				userList.remove(i);
				foundUser = true;
				break;
			}
		}
		if (!foundUser) {
			System.out.println("User not found!");
		}
	}

	// Given a hostname, displays the corresponding laptop
	// Precondition: hostName of the laptop which is searched in the given list
	// (currently connected)
	// Postcondition: If a laptop is connected to the server, the hostname and ip of
	// that
	// laptop is shown
	// If laptop not found, a message that the ping failed will be displayed.
	public static void pingByHostName(String hostName, ListInterface currentlyConnected) {
		Laptop tmpLaptop;
		boolean found = false;
		for (int i = 1; i <= currentlyConnected.size(); i++) {
			tmpLaptop = ((User) currentlyConnected.get(i)).getLaptop();
			if (tmpLaptop.getHostName().equals(hostName)) {
				System.out.println("Successfully pinged hostname " + hostName + " with ip: " + tmpLaptop.getIp());
				found = true;
			}
		}
		if (!found) {
			System.out.println("Failed to ping hostname : " + hostName + ". User is not connected to the network.");
		}
	}

	// Given the ip, displays the corresponding laptop
	// Precondition: ip is the laptop's IP address, which
	// is to be checked if it is connected
	// Postcondition: If a laptop is connected to the server, IP address of that
	// laptop is shown, otherwise a message that the ping failed will be visible.
	public static void pingByIP(String ip, ListInterface currentlyConnected) {
		Laptop tmpLaptop;
		boolean found = false;
		for (int i = 1; i <= currentlyConnected.size(); i++) {
			tmpLaptop = ((User) currentlyConnected.get(i)).getLaptop();
			if (tmpLaptop.getIp().equals(ip)) {
				System.out.println("Successfully pinged ip : " + ip);
				found = true;
			}
		}
		if (!found) {
			System.out.println("Failed to ping ip : " + ip);
		}
	}

	// checks if a user exists in the user's list
	// Precondition: userList is the list of users
	// user is the user that is checked if exists within the list
	// Postcondition: returns true if a user exists in the list with the same
	// username and password as the given user's
	// Returns false otherwise
	public static boolean authenticate(ListInterface userList, User user) {

		for (int i = 1; i <= userList.size(); i++) {
			if (((User) userList.get(i)).getUsername().equals(user.getUsername())
					&& (((User) userList.get(i)).getPassword().equals(user.getPassword())))
				return true;
		}
		return false;
	}

	// creates a laptop user after asking for the appropriate information
	// Precondition:none
	// Postcondition: returns the user created with the given information
	// (username,password)
	public static User promptForUser() {
		System.out.println("Input a username");
		String username = EasyIn.getString();
		System.out.println("Input a password");
		String password = EasyIn.getString();
		User laptopUser = new User(username, password);
		return laptopUser;
	}

	public static void writeToFile(String fileName, ListInterface userList) {
		try {
			/* Create a FileWriter object that handles the low-level details of writing */
			FileWriter theFile = new FileWriter(fileName);

			/* Create a PrintWriter object to wrap around the FileWriter object */
			/* This allows the use of high-level methods like println */
			PrintWriter fileOut = new PrintWriter(theFile);

			/* Print some lines to the file using the println method */
			for (int i = userList.size(); i > 0; i--) {
				if ((userList.get(i) instanceof User)) {
					fileOut.println(((User) userList.get(i)).getUsername());
					fileOut.println(((User) userList.get(i)).getPassword());
				}
			}
			/* Close the file so that it is no longer accessible to the program */
			fileOut.close();
			System.out.println(".                                                                         \r\n"
					+ ".....                                                             \r\n"
					+ ".....                         YOU SHALL PASS!!!                                          \r\n"
					+ "........                          ,.,..                                        \r\n"
					+ "...........               ..*(%%&&&&&&&&&&&%%(/,*.                             \r\n"
					+ "...........           .,(#&@@@@@@@@&&@@@@@@&&&&&%%#*..                         \r\n"
					+ "................    .(%&&@@@@@@@@@@@@@@@@&@@@@&&&&&%#,.                        \r\n"
					+ "....................*@@@@@@@@@@@@@@@@@@@@@@&@@@@@@&&&(*.                       \r\n"
					+ "...................,&&&@@@@@@@@@@@&@&@@@@@@@@&@&@@@&&&%/.                      \r\n"
					+ "...................,&@@@@@@@@@@@&&%%%%&&&&&&&%%%##%&&&&%.                      \r\n"
					+ ",,,................*@@@@@&%%###((/////////////****//%&&%/                      \r\n"
					+ ",,,,,,,............*&@@%#((((////**************,,,,,/%%%#                      \r\n"
					+ ",,,,,,,,,,,,.......*&&%#((((/////***********,,,,,,,,*#%%#                      \r\n"
					+ ",,,,,,,,,,,,,,,....,&&%#((((/////**********,,,,,,,,,/#%%#                      \r\n"
					+ ",,,,,,,,,,,,,,,.....%((((((((///*********,,***/*,,,,##(                      \r\n"
					+ ",,,,,,,,,,,,,,,,....%&(((#&&@@&&&%#(****/(##%%%#(/,,*#(                      \r\n"
					+ ",,,,,,,,,,,,,,,,,,..#&((#&%%###%%%%#(/***//(((((/***/,,%#                      \r\n"
					+ ",,,,,,,,,,,,,,,,,.../&((#%%&&@@&%&&&%(*,,/#%&%&&/(&(*,,/#,.                    \r\n"
					+ ",,,,,,,,,,,,,,,,,,.((&((#%&&%%%%%%%%%(*,,*/###(/*****,,./,,                    \r\n"
					+ ",,,,,,,,,,,,,,,,,,.#(%((#####%%%#(#(((/,,,,,********,,,,(,*                    \r\n"
					+ ",,,,,,,,,,,,,,,,,,,((%(((#((((((((((((/*,,,,,,,,,,,,,,,,(,                     \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,(&(((#(((((((((##(/*,.,***,,,,,,,,,,#,                     \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,,&%(((((((/(((#(((/*..,,/(*,,,...,,*%,                     \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,,((((((///((#%&(////**//,,.,,,*##.                     \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,,,&#((((((((#&&@&&&&&((#(/,**((%#                      \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,,,#@&%%%##%%%&&@@@@&@&&%%#%%%%#//(#%.                      \r\n"
					+ ",,,,,,,,,,,,,,,,,,,,,,,&&&&&&&&&&&%%####(/##(#%&&((##(                       \r\n"
					+ ",,,,,*,*,,**************&&&&@@@@@&%%##((//**/(((%%####/                        \r\n"
					+ ",,***********************&&@@@@@@&&&&&&@&&((#(%%%&&,                       \r\n"
					+ ",**********************//(@@@@@@@@&&%%%&%%(###%%%&&%%@@/(%(.                   \r\n"
					+ "**************/////////&&&@@@@@@@@@@&&&%%%%###%&&&@@%(((%#%%#(,              \r\n"
					+ "************///////#%&&&&@@@@@@@@@@&&&&&&&%%%%%&&&@@@###&%(%#&(/.        \r\n"
					+ "***********///#%&&&@&&@&&&&@@@@@@@@@@&&&&&&&&&&@@@@@(/#(@#%/&%##%&&(##%#/.   \r\n"
					+ "********(%%&%&&%&&&&%@@&&&@@@@@@@@@@@@@@@@@@@@@@@@@&@(#(@(#(&%#(%(/%#&(#%%(\r\n"
					+ "****#%&%#&&&@%&%&&%@@@&&@@@@@&@@@@@&&&&&%%@@@@@&&@@@(##&#(&%&(%%(&%(%#&\r\n"
					+ "#&&%&&@%@&%%&@%&&&@&%&&@@&@@@@&%@@@@@@@@@@@@@@@&&@@@(%&@#%%%%(#%%((&%/%#&\r\n"
					+ "&@%%@&@&%%&@%@&%&&&&&@&@&@@@@@&@@@@@@@@@@@@#&&&%@@@%@&/#%%(#(((##((%(/%%&\r\n"
					+ "%&&&%%@&%&&@%@&&%&@%&&&%@@@@@@&@@@@@@@@@@@(%#%#%&@@#&@%%(%#%/%##(&&%(%(#(/&%&\r\n"
					+ "&%#@%%&&%@&&%@&&%@&%@@@@@@@@@@@@@@@@@(%#%@@@%@%%/%(%/%##(&%%/#(#//%#&\r\n"
					+ "&%%@%&%%#&&%&&@#%&%&%&%&@&@@@@@#%@@@@@/%#&(%@&@%&&(%(&(#/&%&%%/###&%%\r\n"
					+ "%&&%#&&&@&@#&&%&%&%&&@%@@@&@@(@@@(%#%&%@%&%@&%(%(%(#/%#%(###/###%\r\n"
					+ "%&&%&%&%&@%&&&%#&%%%(%%%##&&&%@&@@@%%(#/&%&&&&%%(#/%#&(%(%(%%%#/%(#(&%&&%\r\n"
					+ "%(&%&%#%%&&@&%&%#&%%%#&%&%&&&&%@&@(%(%&%&%/%#%(&(%#%((%#%/#(#/%#&((%(\r\n"
					+ "##&%&&%@#&&&%#&%%%(%&%%(%%#&%@%&@//((%%(%#%//%#%(&(%%%%(%#%((#%(#&&%(&%\r\n"
					+ "#%&%&&%&&&&%&%&(%%%%#&%@%&@#(%(%#&%%(&((%#%/%##(/#(#(%#%(#%\r\n"
					+ "#&&%&@%&&&&%&%(%%#%(%%%%(%%#(#/#%#&%(&(&%#&%%(%#%(/%#%(&(#&");
		}

		/* Handle the exception thrown by the FileWriter methods */
		catch (IOException e) {
			System.out.println("Problem writing to the file");
		}
	}
}
