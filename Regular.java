// This is the regular room class which inherits from room
package project_2;

public class Regular extends Room {
	// class attributes
	private static String bedType;

	// default constructor
	public Regular(String type, int price, int roomNum, int numOfRooms) {
		super("Regular", 200, roomNum, numOfRooms);
		bedType = "Queen";
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