// This is the junior suite room class which inherits from room

package project_2;

public class JuniorSuite extends Room {
	// class attributes
	private static String bedType;

	// default constructor
	public JuniorSuite(String type, int price, int roomNum, int numOfRooms) {
		super("Junior Suite", 330, roomNum, numOfRooms);
		bedType = "Queen, Twin, Sofa";
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