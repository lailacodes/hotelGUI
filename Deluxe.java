// This is the deluxe room class which inherits from room
package project_2;

public class Deluxe extends Room {
	// class attributes
	private static String bedType;

	// default constructor
	public Deluxe(String type, int price, int roomNum, int numOfRooms) {
		super("Deluxe", 300, roomNum, numOfRooms);
		bedType = "Queen, Twin";
	}

	// getters
	public String getBedType() {
		return bedType;
	}

	// methods
	@Override
	public void displayDetails() {
		super.displayDetails();
		System.out.println("Bed Type: " + getBedType());
	}
}
