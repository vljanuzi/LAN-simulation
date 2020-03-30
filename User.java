
import java.lang.Comparable;

public class User implements Comparable {

	private String username;
	private String password;
	private Laptop laptop;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Laptop getLaptop() {
		return this.laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Username=" + username + ", " + laptop.toString();
	}

	// Compares two objects
	// Precondition: arg0 is the object that is compared to the user
	// Postcondition: returns 0 if both user's usernames are the same,
	// else return 1
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof User) {
			String temp = ((User) arg0).getUsername();
			return temp.compareTo(username);
		}
		// TODO Auto-generated method stub
		return 1;
	}

	// Method that checks if two users are equal by their username and password
	// Precondition: u1 is the user to be compared
	// Postcondition: Method returns true if both users have the same username and
	// password.
	// Otherwise it returns false;
	public boolean equals(User u1) {
		return (this.username).equals(u1.username) && (this.password).equals(u1.password);
	}

}
