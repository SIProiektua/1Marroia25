package presentation;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import businessLogic.FlightManager;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import domain.ConcreteFlight;
import domain.Flight;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlightBooking extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane= null;
	private JLabel lblDepartCity = new JLabel("Departing city:");
	private JLabel lblArrivalCity = new JLabel("Arrival City");
	private JLabel lblYear = new JLabel("Year:");
	private JLabel lblRoomType = new JLabel("Room Type:");
	private JLabel lblMonth = new JLabel("Month:");
	private JLabel lblDay = new JLabel("Day:");;
	private JLabel jLabelResult = new JLabel();
	private JLabel searchResult =   new JLabel();
	private JTextField day = null;
	private JComboBox<String> months = null;
	private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> depart = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> arrive = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> flightInfo_c = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> nextdate = new DefaultComboBoxModel<String>();
	private JTextField year = null;
	
	private JRadioButton bussinesTicket = null;
	private JRadioButton firstTicket = null;
	private JRadioButton touristTicket = null;

	private ButtonGroup fareButtonGroup = new ButtonGroup();  
	
	private JButton lookforFlights = null;
	private DefaultListModel<ConcreteFlight> flightInfo = new DefaultListModel<ConcreteFlight>();

	
	private JList<ConcreteFlight> flightList = null;
	private JComboBox<String> flightList_1;
	private JButton bookFlight = null;
	
	

	
	private Collection<ConcreteFlight> concreteFlightCollection;
	
	private FlightManager businessLogic;  //  @jve:decl-index=0:
	private JScrollPane flightListScrollPane = new JScrollPane();
	
	
	private ConcreteFlight selectedConcreteFlight;
	private JComboBox dCities;
	private JComboBox aCities;
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlightBooking frame = new FlightBooking();
					frame.setBusinessLogic(new FlightManager());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	//Custom operations
	public void setBusinessLogic(FlightManager g) {businessLogic = g;}
	private Date newDate(int year,int month,int day) {

	     Calendar calendar = Calendar.getInstance();
	     calendar.set(year, month, day,0,0,0);
	     calendar.set(Calendar.MILLISECOND, 0);

	     return calendar.getTime();
	}
	/**
	 * Create the frame
	 * 
	 * @return void
	 */
	private  FlightBooking() {
		
		setTitle("Book Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDepartCity = new JLabel("Depart City");
		lblDepartCity.setBounds(21, 11, 84, 16);
		contentPane.add(lblDepartCity);;
		
		
		lblYear = new JLabel("Year:");
		lblYear.setBounds(21, 62, 33, 16);
		contentPane.add(lblYear);
		
		lblMonth = new JLabel("Month:");
		lblMonth.setBounds(117, 62, 50, 16);
		contentPane.add(lblMonth);
	    
		months = new JComboBox<String>();
		months.setBounds(163, 58, 116, 27);
		contentPane.add(months);
		months.setModel(monthNames);
		
		monthNames.addElement("January");
		monthNames.addElement("February");
		monthNames.addElement("March");
		monthNames.addElement("April");
		monthNames.addElement("May");
		monthNames.addElement("June");
		monthNames.addElement("July");
		monthNames.addElement("August");
		monthNames.addElement("September");
		monthNames.addElement("October");
		monthNames.addElement("November");
		monthNames.addElement("December");
		months.setSelectedIndex(1);
		
		
		
		lblDay = new JLabel("Day:");
		lblDay.setBounds(291, 62, 38, 16);
		contentPane.add(lblDay);
		
		day = new JTextField();
		day.setText("23");
		day.setBounds(331, 57, 50, 26);
		contentPane.add(day);
		day.setColumns(10);
		
		lblRoomType = new JLabel("Seat Type:");
		lblRoomType.setBounds(21, 242, 84, 16);
		contentPane.add(lblRoomType);
		
		
		
		bussinesTicket = new JRadioButton("Business");
		bussinesTicket.setSelected(false);
		fareButtonGroup.add(bussinesTicket);
		bussinesTicket.setBounds(99, 238, 101, 23);
		contentPane.add(bussinesTicket);
		
		firstTicket = new JRadioButton("First");
		fareButtonGroup.add(firstTicket);
		firstTicket.setBounds(202, 238, 77, 23);
		contentPane.add(firstTicket);
		
		touristTicket = new JRadioButton("Tourist");
		fareButtonGroup.add(touristTicket);
		touristTicket.setBounds(278, 238, 77, 23);
		contentPane.add(touristTicket);
		
		lookforFlights = new JButton("Look for Concrete Flights");
		lookforFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFlight.setEnabled(true);
				flightInfo.clear();
				flightInfo_c.removeAllElements();
				bookFlight.setText("");
				int yearI = 2025;
				int dayI = 23;
				try {
					yearI = Integer.parseInt(year.getText());
				} catch (NumberFormatException e1) {
					year.setText("2025");
				}
				try {
					dayI = Integer.parseInt(day.getText());
				} catch (NumberFormatException e2) {
					day.setText("01");
				}
				
				java.util.Date date =newDate(yearI,months.getSelectedIndex(),dayI);
				String z = (String) dCities.getSelectedItem();
				String a = (String) aCities.getSelectedItem();
				if(z.equals(a)){
					return;
				}else {
					concreteFlightCollection=businessLogic.getConcreteFlights(((String)dCities.getSelectedItem()),((String)aCities.getSelectedItem()),date);
				}
				
				Iterator<ConcreteFlight> flights=concreteFlightCollection.iterator();
				while (flights.hasNext()) {
					flightInfo_c.addElement(flights.next().toString());
				}
				if (concreteFlightCollection.isEmpty()) searchResult.setText("No flights in that city in that date");
				else searchResult.setText("Choose an available flight in this list:");
			}
		});
		lookforFlights.setBounds(81, 90, 261, 40);
		contentPane.add(lookforFlights);	
		
		jLabelResult = new JLabel("");
		jLabelResult.setBounds(109, 180, 243, 16);
		contentPane.add(jLabelResult);
		
		flightList_1 = new JComboBox();
		flightList_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flightList_1.getSelectedItem() != null) {
					String banatu = (String) flightList_1.getSelectedItem();
					String [] banatu1 = banatu.split("/");
					String [] banatu2 = banatu1[2].split("-");
					String [] banatu3 = banatu1[5].split("-->");
					selectedConcreteFlight = new ConcreteFlight(banatu1[1], newDate(Integer.parseInt(banatu2[1]),Integer.parseInt(banatu1[3]),  Integer.parseInt(banatu1[4])), Integer.parseInt(banatu3[1]), Integer.parseInt(banatu1[6]), Integer.parseInt(banatu1[7]), banatu3[0], new Flight(banatu1[0],banatu1[1], banatu1[2]));
					bookFlight.setText("Book: " + selectedConcreteFlight.toString());
					if(selectedConcreteFlight != null) {
						  if(selectedConcreteFlight.getBussinesNumber() <= 0) {
		                        bussinesTicket.setEnabled(false);
		                    } else if(selectedConcreteFlight.getBussinesNumber()>0) {
		                        bussinesTicket.setEnabled(true);
		                    }
		                    if(selectedConcreteFlight.getFirstNumber() <= 0) {
		                        firstTicket.setEnabled(false);
		                    } else if(selectedConcreteFlight.getFirstNumber()>0) {
		                        firstTicket.setEnabled(true);
		                    }
		                    if(selectedConcreteFlight.getTouristNumber() <= 0) {
		                        touristTicket.setEnabled(false);
		                    } else if(selectedConcreteFlight.getTouristNumber()>0) {
		                        touristTicket.setEnabled(true);
		                    }
					}
				}
			}});
		
		
		flightList_1.setModel(flightInfo_c);
		flightListScrollPane.setBounds(new Rectangle(64, 159, 336, 71));
		flightListScrollPane.setViewportView(flightList_1);
		contentPane.add(flightListScrollPane);
		
		
		bookFlight = new JButton("");
		bookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num=0;
				boolean error=false;
				if (bussinesTicket.isSelected()) { 
				    num=selectedConcreteFlight.getBussinesNumber();
				}
				if (firstTicket.isSelected()) {
					num=selectedConcreteFlight.getFirstNumber();
					if (num>0) selectedConcreteFlight.setFirstNumber(num-1); else error=true;
				}
				if (touristTicket.isSelected()) {
					num=selectedConcreteFlight.getTouristNumber();
					if (num>0) selectedConcreteFlight.setTouristNumber(num-1); else error=true;
				}
				if (error) bookFlight.setText("Error: There were no seats available!");
				else bookFlight.setText("Booked. #seat left: "+(num-1));
				bookFlight.setEnabled(false);
				flightList_1.setEnabled(false);
				flightInfo_c.removeAllElements();
				lookforFlights.setEnabled(false);
			}
		});
		bookFlight.setBounds(31, 273, 399, 40);
		contentPane.add(bookFlight);

		year = new JTextField();
		year.setText("2025");
		year.setBounds(57, 57, 50, 26);
		contentPane.add(year);
		year.setColumns(10);
		
		lblArrivalCity.setBounds(21, 39, 84, 16);
		contentPane.add(lblArrivalCity);
		
		searchResult.setBounds(57, 130, 314, 16);
		contentPane.add(searchResult);
		
		FlightManager a1 = new FlightManager();
		Iterator<String> it = a1.getAllDepartingCities().iterator();
		while(it.hasNext()) {
			depart.addElement(it.next());
		}
		depart.addElement("Sevilla");
		dCities = new JComboBox();
		dCities.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == e.SELECTED) {
					String s = (String) dCities.getSelectedItem();
					arrive.removeAllElements();
					Iterator<String> it2 = a1.getArrivalCitiesFrom(s).iterator();
					while(it2.hasNext()) {
						String a2 = it2.next();
						arrive.addElement(a2);
					}
				}
			}
		});
		dCities.setBounds(95, 10, 247, 18);
		contentPane.add(dCities);
		dCities.setModel(depart);
		dCities.setSelectedIndex(1);
		
		String s = (String) dCities.getSelectedItem();
		aCities = new JComboBox();
		aCities.setBounds(95, 31, 247, 21);
		/**for(int i = 0; i<depart.getSize(); i++) {
			Iterator<String> it2 = a1.getArrivalCitiesFrom(depart.getElementAt(i)).iterator();
			while(it2.hasNext()) {
				s = it2.next();
				if(arrive.getIndexOf(s)<0) {
					arrive.addElement(s);
				}
			}
		}*/
		arrive.addAll(a1.getArrivalCitiesFrom(s));
		arrive.addListDataListener(flightList_1);
		contentPane.add(aCities);
		aCities.setModel(arrive);
		aCities.setSelectedIndex(1);
		
	}
}  //  @jve:decl-index=0:visual-constraint="18,9"
