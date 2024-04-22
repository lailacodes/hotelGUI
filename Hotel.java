// This is the hotel class which will be used to create a hotel filled with three different types of rooms and allow for reservations

package project_2;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class Hotel {
	// instantiate each type of room
	public Regular regRooms = new Regular("Regular", 200, 0, 0);
	public Deluxe deluxeRooms = new Deluxe("Deluxe", 300, 0, 0);
	public JuniorSuite jrSuite = new JuniorSuite("Junior Suite", 330, 0, 0);

	// methods
	public void setupHotel(int numOfReg, int numOfDeluxe, int numOfJrSuite) {
		regRooms.setNumRooms(numOfReg);
		deluxeRooms.setNumRooms(numOfDeluxe);
		jrSuite.setNumRooms(numOfJrSuite);
	}

	// calculate total number of rooms overall
	public int getRoomTotal(Regular reg, Deluxe deluxe, JuniorSuite jrSuite) {
		return reg.getNumRooms() + deluxe.getNumRooms() + jrSuite.getNumRooms();
	}

	// Calculate the number of nights
	public int getNumberOfNights(String arrival, String departure) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate arrivalDate = LocalDate.parse(arrival, formatter);
		LocalDate departureDate = LocalDate.parse(departure, formatter);

		// Calculate the difference in days
		long numberOfNights = ChronoUnit.DAYS.between(arrivalDate, departureDate);

		return (int) numberOfNights;
	}

	// generate general cost of regular room
	public double calculateTotalCharge(Regular room, String arrival, String departure) {
		return room.getPrice() * getNumberOfNights(arrival, departure);
	}

	// generate general cost of deluxe room
	public double calculateTotalCharge(Deluxe room, String arrival, String departure) {
		return room.getPrice() * getNumberOfNights(arrival, departure);
	}

	// generate general cost of junior suite
	public double calculateTotalCharge(JuniorSuite room, String arrival, String departure) {
		return room.getPrice() * getNumberOfNights(arrival, departure);
	}

	// reserve a room
	public int makeReservation(String roomType, String arrival, String departure) {
		int roomNumber = 0;

		// assign room number based on availability
		switch (roomType) {
		case "Regular":
			if (regRooms.getNumRooms() > 0) {
				regRooms.setNumRooms(regRooms.getNumRooms() - 1);
				roomNumber = regRooms.getNumRooms() + 1;
			}
		case "Deluxe":
			if (deluxeRooms.getNumRooms() > 0) {
				deluxeRooms.setNumRooms(deluxeRooms.getNumRooms() - 1);
				roomNumber = deluxeRooms.getNumRooms() + 1;
			}
		case "Junior Suite":
			if (jrSuite.getNumRooms() > 0) {
				jrSuite.setNumRooms(jrSuite.getNumRooms() - 1);
				roomNumber = jrSuite.getNumRooms() + 1;
			}
		}
		return roomNumber;
	}
}
