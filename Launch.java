package project_2;

import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Launch {
	//GUI Variables
	private JFrame frame;
	private JPanel headerPanel, menuPanel, setupPanel, reservationPanel;
	private JLayeredPane contentLayeredPane;
	private JLabel lblHeader, lblMenu, lblSetup, lblSetRoomsPrompt, lblDeluxeNum, lblSuiteNum, lblErrorMsg,
	lblNewLabel, lblFirstName, lblLastName, lblRoomType, lblArrival, lblDepartureDate, lblRegNum;
	private JButton btnStart, btnSetup, btnReset, btnSave;
	private JTextField regTextField, deluxeTextField, suiteTextField, textFieldFN, textFieldLN;
	private JTextPane txtpnCurrNumOfRooms;
	private JComboBox<String> comboBoxRoomType, comboBoxArrival, comboBoxDeparture;
	
	private String[] roomTypes = { "Regular", "Deluxe", "Junior Suite" }; 
	private LocalDate currentDate = LocalDate.now(); // current dates for arrival/departure options

	// instantiate a hotel
	private Hotel abcHotel = new Hotel();
	private JButton btnReserve;
	private JPanel confirmationPanel;
	private JLabel lblConfirmation;
	private JTextArea textAreaConfirmation;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launch window = new Launch();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Launch() {
		initialize();
	}
	
	/**
	 * Generate Error Message
	 */
	
	private void error() {
		lblErrorMsg.setText("Invalid input.");
		lblErrorMsg.setEnabled(true);
	}
	
	/**
	 * Open a specific panel 
	 */
	
	private void displayPanel(JPanel panel) {
		contentLayeredPane.removeAll();
		contentLayeredPane.revalidate();
		contentLayeredPane.repaint();
		contentLayeredPane.add(panel);
	}
	
	/**
	 * Display upon successful reservation
	 */
	private void displayConfirmation(String roomType, String arrival, String departure, String fName, String lName) {
		displayPanel(confirmationPanel);
		try {
			// no rooms available
			if(abcHotel.makeReservation(roomType, arrival, departure) == 0){
				lblConfirmation.setText("Error");
				throw new Exception();
			} else {
				lblConfirmation.setText("Reservation Confirmed");
			switch(roomType) {
			// also account for negative nights stayed
				case "Regular":
					if (!(abcHotel.calculateTotalCharge(abcHotel.regRooms, arrival, departure) < 0.00)) {
					textAreaConfirmation.setText("Dear Customer,\nYour booking details are as follows:\nArrival Date:"+ arrival 
							+ "\nDeparture Date:"+ departure+"\nRoom Number:"+ abcHotel.makeReservation(roomType, arrival, departure) 
							+"\nBooked by: " + fName + " " + lName + "\nTotal: $" + abcHotel.calculateTotalCharge(abcHotel.regRooms, arrival, departure));
					} else {
						lblConfirmation.setText("Error");
						textAreaConfirmation.setText("Departure date cannot be set \nbefore arrival date.");
					}
					break;
				case "Deluxe":
					if (!(abcHotel.calculateTotalCharge(abcHotel.deluxeRooms, arrival, departure) < 0.00)) {
						textAreaConfirmation.setText("Dear Customer,\nYour booking details are as follows:\nArrival Date:"+ arrival 
								+ "\nDeparture Date:"+ departure+"\nRoom Number:"+ abcHotel.makeReservation(roomType, arrival, departure) 
								+"\nBooked by: " + fName + " " + lName + "\nTotal: $" + abcHotel.calculateTotalCharge(abcHotel.deluxeRooms, arrival, departure));
						} else {
							lblConfirmation.setText("Error");
							textAreaConfirmation.setText("Departure date cannot be set \nbefore arrival date.");
						}
						break;
				case "Junior Deluxe":
					if (!(abcHotel.calculateTotalCharge(abcHotel.jrSuite, arrival, departure) < 0.00)) {
						textAreaConfirmation.setText("Dear Customer,\nYour booking details are as follows:\nArrival Date:"+ arrival 
								+ "\nDeparture Date:"+ departure+"\nRoom Number:"+ abcHotel.makeReservation(roomType, arrival, departure) 
								+"\nBooked by: " + fName + " " + lName + "\nTotal: $" + abcHotel.calculateTotalCharge(abcHotel.jrSuite, arrival, departure));
						} else {
							lblConfirmation.setText("Error");
							textAreaConfirmation.setText("Departure date cannot be set \nbefore arrival date.");
						}
					break;
				}
			}
		} catch (Exception excpt) {
			textAreaConfirmation.setText(roomType + " rooms are currently unavailable.");
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		headerPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, headerPanel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, headerPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, headerPanel, 40, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, headerPanel, 440, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(headerPanel);
		
		menuPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, menuPanel, 6, SpringLayout.SOUTH, headerPanel);
		springLayout.putConstraint(SpringLayout.WEST, menuPanel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, menuPanel, 222, SpringLayout.SOUTH, headerPanel);
		springLayout.putConstraint(SpringLayout.EAST, menuPanel, 135, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(menuPanel);
		
		contentLayeredPane = new JLayeredPane();
		springLayout.putConstraint(SpringLayout.NORTH, contentLayeredPane, 6, SpringLayout.SOUTH, headerPanel);
		springLayout.putConstraint(SpringLayout.WEST, contentLayeredPane, 6, SpringLayout.EAST, menuPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, contentLayeredPane, 222, SpringLayout.SOUTH, headerPanel);
		
		lblHeader = new JLabel("ABC Hotel System");
		lblHeader.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		headerPanel.add(lblHeader);
		springLayout.putConstraint(SpringLayout.EAST, contentLayeredPane, 305, SpringLayout.EAST, menuPanel);
		SpringLayout sl_menuPanel = new SpringLayout();
		menuPanel.setLayout(sl_menuPanel);
		
		lblMenu = new JLabel("Menu");
		sl_menuPanel.putConstraint(SpringLayout.NORTH, lblMenu, 5, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblMenu, 39, SpringLayout.WEST, menuPanel);
		lblMenu.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		menuPanel.add(lblMenu);
		
		btnStart = new JButton("Start System");
		sl_menuPanel.putConstraint(SpringLayout.NORTH, btnStart, 30, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.WEST, btnStart, 2, SpringLayout.WEST, menuPanel);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel(reservationPanel);
			}
		});
		menuPanel.add(btnStart);
		
		// Upon click, open Setup Panel and close other open Panels if applicable	
		btnSetup = new JButton("Setup System");
		sl_menuPanel.putConstraint(SpringLayout.NORTH, btnSetup, 64, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.WEST, btnSetup, -1, SpringLayout.WEST, menuPanel);
		btnSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				displayPanel(setupPanel);
			}
		});
		menuPanel.add(btnSetup);
		
		// close program
		JButton btnExit = new JButton("Exit System");
		sl_menuPanel.putConstraint(SpringLayout.NORTH, btnExit, 98, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.WEST, btnExit, 4, SpringLayout.WEST, menuPanel);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuPanel.add(btnExit);
		frame.getContentPane().add(contentLayeredPane);
		
		// note for user to setup the system first
		JTextArea txtritsRecommendedTo = new JTextArea();
		sl_menuPanel.putConstraint(SpringLayout.NORTH, txtritsRecommendedTo, 43, SpringLayout.SOUTH, btnExit);
		sl_menuPanel.putConstraint(SpringLayout.WEST, txtritsRecommendedTo, 0, SpringLayout.WEST, btnStart);
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, txtritsRecommendedTo, -10, SpringLayout.SOUTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.EAST, txtritsRecommendedTo, 0, SpringLayout.EAST, btnStart);
		txtritsRecommendedTo.setBackground(UIManager.getColor("CheckBox.background"));
		txtritsRecommendedTo.setText("**It's recommended to set \nup number of rooms in \nSetup System before starting");
		txtritsRecommendedTo.setFont(new Font("Lucida Grande", Font.ITALIC, 8));
		menuPanel.add(txtritsRecommendedTo);
		txtritsRecommendedTo.setEditable(false);
		
		reservationPanel = new JPanel();
		reservationPanel.setBounds(6, 6, 287, 204);
		
		confirmationPanel = new JPanel();
		confirmationPanel.setBounds(35, 35, 232, 133);
		confirmationPanel.setLayout(null);
		
		lblConfirmation = new JLabel();
		lblConfirmation.setBounds(6, 6, 220, 20);
		lblConfirmation.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmation.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		confirmationPanel.add(lblConfirmation);
		
		// to be displayed after successful reservation
		textAreaConfirmation = new JTextArea();
		textAreaConfirmation.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		textAreaConfirmation.setText("Dear Customer,\nYour booking details are as follows:\nArrival Date: \nDeparture Date:\nRoom Number: \nBooked by:\nTotal: $");
		textAreaConfirmation.setEditable(false);
		textAreaConfirmation.setBounds(6, 27, 220, 100);
		confirmationPanel.add(textAreaConfirmation);
		SpringLayout sl_reservationPanel = new SpringLayout();
		reservationPanel.setLayout(sl_reservationPanel);
		
		// Reservation Form Panel Setup
		lblNewLabel = new JLabel("Reservation Form");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, lblNewLabel, -59, SpringLayout.EAST, reservationPanel);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		reservationPanel.add(lblNewLabel);
		
		lblFirstName = new JLabel("First Name");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblFirstName, 41, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, lblFirstName, -183, SpringLayout.EAST, reservationPanel);
		lblFirstName.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(lblFirstName);
		
		textFieldFN = new JTextField();
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, textFieldFN, 11, SpringLayout.SOUTH, lblNewLabel);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, textFieldFN, 22, SpringLayout.EAST, lblFirstName);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, textFieldFN, 0, SpringLayout.EAST, lblNewLabel);
		textFieldFN.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(textFieldFN);
		textFieldFN.setColumns(10);
		
		lblLastName = new JLabel("Last Name");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblLastName, 6, SpringLayout.SOUTH, lblFirstName);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, lblLastName, 0, SpringLayout.WEST, lblFirstName);
		lblLastName.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(lblLastName);
		
		textFieldLN = new JTextField();
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, textFieldFN, -6, SpringLayout.NORTH, textFieldLN);
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, textFieldLN, 60, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, textFieldLN, 23, SpringLayout.EAST, lblLastName);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, textFieldLN, 0, SpringLayout.EAST, lblNewLabel);
		textFieldLN.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		textFieldLN.setColumns(10);
		reservationPanel.add(textFieldLN);
		
		lblRoomType = new JLabel("Room Type");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblRoomType, 6, SpringLayout.SOUTH, lblLastName);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, lblRoomType, 0, SpringLayout.WEST, lblFirstName);
		lblRoomType.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(lblRoomType);

		comboBoxRoomType = new JComboBox<>();
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, comboBoxRoomType, 79, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, comboBoxRoomType, 21, SpringLayout.EAST, lblRoomType);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, comboBoxRoomType, -51, SpringLayout.EAST, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, textFieldLN, -6, SpringLayout.NORTH, comboBoxRoomType);
		comboBoxRoomType.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		// populate combo box with room types
		for(String type: roomTypes) {
			comboBoxRoomType.addItem(type);
		}
		reservationPanel.add(comboBoxRoomType);
		
		lblArrival = new JLabel("Arrival Date");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblArrival, 6, SpringLayout.SOUTH, lblRoomType);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, lblArrival, 0, SpringLayout.EAST, lblFirstName);
		lblArrival.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(lblArrival);
		
		comboBoxArrival = new JComboBox<>();
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, comboBoxArrival, 98, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, comboBoxRoomType, -6, SpringLayout.NORTH, comboBoxArrival);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, comboBoxArrival, 0, SpringLayout.EAST, comboBoxRoomType);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, comboBoxArrival, 22, SpringLayout.EAST, lblArrival);
		comboBoxArrival.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		lblDepartureDate = new JLabel("Departure Date");
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, lblDepartureDate, 6, SpringLayout.SOUTH, lblArrival);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, lblDepartureDate, 0, SpringLayout.EAST, lblFirstName);
		lblDepartureDate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(lblDepartureDate);
		
		comboBoxDeparture = new JComboBox<>();
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, comboBoxArrival, -6, SpringLayout.NORTH, comboBoxDeparture);
		sl_reservationPanel.putConstraint(SpringLayout.NORTH, comboBoxDeparture, 117, SpringLayout.NORTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, comboBoxDeparture, 0, SpringLayout.EAST, comboBoxRoomType);
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, comboBoxDeparture, -74, SpringLayout.SOUTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.WEST, comboBoxDeparture, 22, SpringLayout.EAST, lblDepartureDate);
		comboBoxDeparture.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		// populate combo boxes with dates
		// Add dates for the rest of the year to the combo box
		for (int i = 0; i < 9; i++) {
			LocalDate nextMonth = currentDate.plusMonths(i);
			for (int day = 1; day <= nextMonth.lengthOfMonth(); day++) {
				comboBoxArrival.addItem(DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), day)));
				comboBoxDeparture.addItem(DateTimeFormatter.ofPattern("MM-dd-yyyy").format(LocalDate.of(nextMonth.getYear(), nextMonth.getMonth(), day)));
			}
		}
		reservationPanel.add(comboBoxArrival);
		reservationPanel.add(comboBoxDeparture);
		
		// call makereservation()
		btnReserve = new JButton("Reserve");
		btnReserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Handle cases where departure date surpasses arrival date (negative stays per night)
				
				// depending on the room type...
				String rType = comboBoxRoomType.getSelectedItem().toString();
				String arrival = comboBoxArrival.getSelectedItem().toString();
				String departure = comboBoxDeparture.getSelectedItem().toString();
				String fName = textFieldFN.getText();
				String lName = textFieldLN.getText();
				switch(rType) {
				case "Regular":
					displayConfirmation(rType,arrival,departure,fName,lName);
					break;
				case "Deluxe":
					break;
				case "Junior Suite":
					break;
				}
			}
		});
		sl_reservationPanel.putConstraint(SpringLayout.SOUTH, btnReserve, -10, SpringLayout.SOUTH, reservationPanel);
		sl_reservationPanel.putConstraint(SpringLayout.EAST, btnReserve, -10, SpringLayout.EAST, reservationPanel);
		btnReserve.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		reservationPanel.add(btnReserve);

		// Setup Panel Form Setup
		setupPanel = new JPanel();
		setupPanel.setBounds(6, 6, 287, 204);
		SpringLayout sl_setupPanel = new SpringLayout();
		setupPanel.setLayout(sl_setupPanel);
		
		lblSetup = new JLabel("Setup Hotel Rooms");
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblSetup, 10, SpringLayout.NORTH, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.EAST, lblSetup, -58, SpringLayout.EAST, setupPanel);
		lblSetup.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblSetup.setHorizontalAlignment(SwingConstants.CENTER);
		setupPanel.add(lblSetup);
		
		lblSetRoomsPrompt = new JLabel("Set the number for each type of hotel room.");
		lblSetRoomsPrompt.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblSetRoomsPrompt, 6, SpringLayout.SOUTH, lblSetup);
		sl_setupPanel.putConstraint(SpringLayout.EAST, lblSetRoomsPrompt, -2, SpringLayout.EAST, setupPanel);
		setupPanel.add(lblSetRoomsPrompt);
		
		lblRegNum = new JLabel("Regular");
		sl_setupPanel.putConstraint(SpringLayout.EAST, lblRegNum, -205, SpringLayout.EAST, setupPanel);
		setupPanel.add(lblRegNum);
		
		regTextField = new JTextField();
		sl_setupPanel.putConstraint(SpringLayout.SOUTH, regTextField, -110, SpringLayout.SOUTH, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.EAST, regTextField, -132, SpringLayout.EAST, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblRegNum, 5, SpringLayout.NORTH, regTextField);
		sl_setupPanel.putConstraint(SpringLayout.WEST, regTextField, 88, SpringLayout.WEST, lblSetRoomsPrompt);
		setupPanel.add(regTextField);
		regTextField.setColumns(10);
		
		lblDeluxeNum = new JLabel("Deluxe");
		sl_setupPanel.putConstraint(SpringLayout.WEST, lblDeluxeNum, 0, SpringLayout.WEST, lblRegNum);
		setupPanel.add(lblDeluxeNum);
		
		deluxeTextField = new JTextField();
		sl_setupPanel.putConstraint(SpringLayout.WEST, deluxeTextField, 98, SpringLayout.WEST, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.EAST, deluxeTextField, -132, SpringLayout.EAST, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblDeluxeNum, 5, SpringLayout.NORTH, deluxeTextField);
		sl_setupPanel.putConstraint(SpringLayout.SOUTH, deluxeTextField, -78, SpringLayout.SOUTH, setupPanel);
		deluxeTextField.setColumns(10);
		setupPanel.add(deluxeTextField);
		
		lblSuiteNum = new JLabel("Junior Suite");
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblSuiteNum, 137, SpringLayout.NORTH, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.WEST, lblSuiteNum, 0, SpringLayout.WEST, lblSetRoomsPrompt);
		setupPanel.add(lblSuiteNum);
		
		suiteTextField = new JTextField();
		sl_setupPanel.putConstraint(SpringLayout.NORTH, suiteTextField, 132, SpringLayout.NORTH, setupPanel);
		sl_setupPanel.putConstraint(SpringLayout.WEST, suiteTextField, 16, SpringLayout.EAST, lblSuiteNum);
		sl_setupPanel.putConstraint(SpringLayout.EAST, suiteTextField, 0, SpringLayout.EAST, regTextField);
		suiteTextField.setColumns(10);
		setupPanel.add(suiteTextField);
		
		txtpnCurrNumOfRooms = new JTextPane();
		sl_setupPanel.putConstraint(SpringLayout.NORTH, txtpnCurrNumOfRooms, 11, SpringLayout.SOUTH, lblSetRoomsPrompt);
		sl_setupPanel.putConstraint(SpringLayout.WEST, txtpnCurrNumOfRooms, 6, SpringLayout.EAST, regTextField);
		sl_setupPanel.putConstraint(SpringLayout.SOUTH, txtpnCurrNumOfRooms, -17, SpringLayout.SOUTH, suiteTextField);
		sl_setupPanel.putConstraint(SpringLayout.EAST, txtpnCurrNumOfRooms, -21, SpringLayout.EAST, setupPanel);
		txtpnCurrNumOfRooms.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		txtpnCurrNumOfRooms.setText("Current Number of Rooms Available\n\n0 Regular Rooms\n0 Deluxe Rooms\n0 Junior Suites");
		txtpnCurrNumOfRooms.setEditable(false);
		txtpnCurrNumOfRooms.setToolTipText("");
		txtpnCurrNumOfRooms.setBackground(new Color(255, 255, 255));
		setupPanel.add(txtpnCurrNumOfRooms);
		
		// reset system/number of rooms available
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clear number of rooms in hotel
				abcHotel.regRooms.setNumRooms(0);
				abcHotel.deluxeRooms.setNumRooms(0);
				abcHotel.jrSuite.setNumRooms(0);
				txtpnCurrNumOfRooms.setText("Current Number of Rooms Available\n\n0 Regular Rooms\n0 Deluxe Rooms\n0 Junior Suites");

			}
		});
		sl_setupPanel.putConstraint(SpringLayout.NORTH, btnReset, 7, SpringLayout.SOUTH, suiteTextField);
		sl_setupPanel.putConstraint(SpringLayout.WEST, btnReset, 0, SpringLayout.WEST, regTextField);
		setupPanel.add(btnReset);
		
		// update current number of rooms available
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) throws NumberFormatException {
				try {
					// throw exceptions if empty or non-integer characters
					if ((regTextField.getText().isEmpty() || deluxeTextField.getText().isEmpty() || suiteTextField.getText().isEmpty())) throw new Exception();
					
					// assign room values in hotel
					abcHotel.regRooms.setNumRooms(Integer.parseInt(regTextField.getText()));
					abcHotel.deluxeRooms.setNumRooms(Integer.parseInt(deluxeTextField.getText()));
					abcHotel.jrSuite.setNumRooms(Integer.parseInt(suiteTextField.getText()));

					// update values in corresponding text pane
					txtpnCurrNumOfRooms.setText("Current Number of Rooms Available\n\n" + abcHotel.regRooms.getNumRooms() +
							" Regular Rooms\n" + abcHotel.deluxeRooms.getNumRooms() + " Deluxe Rooms\n"+ abcHotel.jrSuite.getNumRooms() + " Junior Suites");
					
					// remove error msg if present
					lblErrorMsg.setText(null);
				} catch (Exception excpt) {
					error();
				}
			}
		});
		sl_setupPanel.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnReset);
		sl_setupPanel.putConstraint(SpringLayout.WEST, btnSave, 6, SpringLayout.EAST, btnReset);
		setupPanel.add(btnSave);
		
		lblErrorMsg = new JLabel("");
		lblErrorMsg.setEnabled(false);
		lblErrorMsg.setForeground(new Color(255, 38, 0));
		sl_setupPanel.putConstraint(SpringLayout.NORTH, lblErrorMsg, 4, SpringLayout.SOUTH, txtpnCurrNumOfRooms);
		sl_setupPanel.putConstraint(SpringLayout.WEST, lblErrorMsg, 6, SpringLayout.EAST, suiteTextField);
		sl_setupPanel.putConstraint(SpringLayout.EAST, lblErrorMsg, 111, SpringLayout.EAST, suiteTextField);
		lblErrorMsg.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
		setupPanel.add(lblErrorMsg);
	}
}