
import java.lang.Comparable;

public class Laptop implements Comparable {

	private String hostName;
	private String ip;

	public Laptop(String hostName) {
		super();
		this.hostName = hostName;
	}

	public Laptop() {
		super();
		this.hostName = null;
		this.ip = null;

	}

	public String getHostName() {
		return hostName;
	}

	public String getIp() {
		return ip;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void connect(String ip) {
		this.ip = ip;
	}

	public String disconnect() {
		String temp = ip;
		ip = null;
		return temp;
	}

	// Method that checks if two lapotops are equal by their hostName and ip
	// Precondition: l1 is the laptop to be compared
	// Postcondition: Method returns true if both laptops have the same hostName and
	// ip
	// Otherwise it returns false;
	public boolean equals(Laptop l1) {
		return (this.ip).equals(l1.ip) && (this.hostName).equals(l1.hostName);
	}

	// Compares two objects
	// Precondition: arg0 is the object that is compared to the laptop
	// Postcondition: returns 0 if both laptop's hostNames are the same,
	// else return 1
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Laptop) {
			String temp = ((Laptop) arg0).getHostName();
			return temp.compareTo(hostName);
		}
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String toString() {
		return "hostName=" + hostName + ", IP=" + ip;
	}

}
