
class EasyInExample {
	// This method is just here to test the class
	public static void main(String args[]) {

		int i;
		char x;
		String y;

		System.out.println("First an int !");
		i = EasyIn.getInt(); // reads an int from System.in
		System.out.println("You entered: " + i);

		System.out.println("Now a char !");
		x = EasyIn.getChar(); // reads an int from System.in
		System.out.println("You entered: " + x);

		System.out.println("Finally a string !");
		y = EasyIn.getString(); // reads an int from System.in
		System.out.println("You entered: " + y);

	}

}
