// This is the base class for a hotel room 
package project_2;

public class Room {
	// class attributes
	protected String type;
	protected int price, roomNum, numOfRooms;

	// default constructor
	public Room(String type, int price, int roomNum, int numOfRooms) {
		this.type = type;
		this.price = price;
		this.roomNum = roomNum;
		this.numOfRooms = numOfRooms;
	}

	// constructor to show room details
	public void displayDetails() {
		System.out.println("Room Type: " + getType());
		System.out.println("Price per Night: $" + getPrice());
	}

	// getters
	public int getRoomNum() {
		return roomNum;
	}

	public int getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public int getNumRooms() {
		return numOfRooms;
	}

	// setters
	public void setNumRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	// keeps track of number of rooms available after a room is reserved
	public int reserveRoom() {
		if (numOfRooms > 0) {
			numOfRooms--;
			return numOfRooms + 1; // Room numbers start from 1, so increment by 1
		} else {
			System.out.println("Error: No regular rooms available for reservation.");
			return 0; // Return 0 to indicate failure
		}
	}
}
