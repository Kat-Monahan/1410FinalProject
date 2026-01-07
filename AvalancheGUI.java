package final01;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
/**
 * 
 * @author Jasey Christensen
 * @author KMonahan
 * @author Latifah Wanyana
 */
public class AvalancheGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField dateTextField;
	private JTextField placeTextField;
	private JTextField triggerTextField;
	private JComboBox<String> sortComboBox;
	private JLabel sortLabel;
	private JLabel totalAvalanchesLabel;
	private JLabel averageDepthLabel;
	private JLabel totalFatalitiesLabel;
	private JTablePanel jtable;
	private JButton saveFileButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AvalancheGUI frame = new AvalancheGUI();
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public AvalancheGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1288, 731);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel topLabelBar = newtoLabelBar();
		contentPane.add(topLabelBar);
		topLabelBar.setLayout(null);
		JPanel sortByPane = newsortByPane();
		contentPane.add(sortByPane);
		sortByPane.setLayout(null);
		JPanel filterByPane = newFilterByPane();
		contentPane.add(filterByPane);
		filterByPane.setLayout(null);
		JPanel statsPane = newStatsPane();
		contentPane.add(statsPane);
		jtable = new JTablePanel(); // the jatable
		contentPane.add(jtable);
		jtable.setBounds(261, 254, 1013, 440);
		
		
		updateStats();
		HeaderPanel headerpanel = new HeaderPanel();
		contentPane.add(headerpanel);
		headerpanel.setBounds(0, 0, 1274, 228);
		
		
		
		
	}
	/**
	 * The panel that contains our Avalanches stats
	 */
	private JPanel newStatsPane() {
	       JPanel statsPane = new JPanel();
	       statsPane.setForeground(new Color(57, 78, 94));
	       statsPane.setBackground(new Color(57, 78, 94));
	       statsPane.setBounds(0, 575, 263, 160);
	       statsPane.setLayout(null);
	       JLabel statsTitle = new JLabel("Statistics");
	       statsTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
	       statsTitle.setHorizontalAlignment(SwingConstants.CENTER);
	       statsTitle.setForeground(new Color(255, 255, 255));
	       statsTitle.setBounds(89, 10, 100, 20);
	       statsPane.add(statsTitle);
	       totalAvalanchesLabel = new JLabel("Total Avalanches : 0");
	       totalAvalanchesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       totalAvalanchesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
	       totalAvalanchesLabel.setForeground(new Color(255, 255, 255));
	       totalAvalanchesLabel.setBounds(37, 40, 200, 20);
	       statsPane.add(totalAvalanchesLabel);
	       averageDepthLabel = new JLabel("Average Depth : 0 ft");
	       averageDepthLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       averageDepthLabel.setForeground(new Color(255, 255, 255));
	       averageDepthLabel.setBounds(37, 60, 200, 20);
	       statsPane.add(averageDepthLabel);
	       totalFatalitiesLabel = new JLabel("Total Fatalities : 0");
	       totalFatalitiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       totalFatalitiesLabel.setForeground(new Color(255, 255, 255));
	       totalFatalitiesLabel.setBounds(37, 81, 200, 20);
	       statsPane.add(totalFatalitiesLabel);
	       return statsPane;
	   }
	    /**
	    * Updates the stats labels based on current JTable data.
	    */
		
	   private void updateStats() {
	       if (jtable != null) {
	           AvalancheStats stats = jtable.calculateStats();
	           totalAvalanchesLabel.setText("Total Avalanches: " + stats.totalAvalanches);
	           averageDepthLabel.setText(String.format("Average Depth: %.2f ft", stats.averageDepth));
	           totalFatalitiesLabel.setText("Total Fatalities: " + stats.totalFatalities);
	      
	       }
	   }
	 
	
	
	/**
	 * the panel that contains the buttons that will filter our jtable based on what
	 * is typed in the text fields
	 */
	
	private JPanel newFilterByPane() {
		
		JPanel filterByPane = new JPanel();
		filterByPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		filterByPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		filterByPane.setBackground(new Color(57, 78, 94));
		filterByPane.setBounds(0, 375, 263, 201);
		JLabel filterLabel = new JLabel("Filter Avalanches By:");
		filterLabel.setForeground(new Color(255, 255, 255));
		filterLabel.setOpaque(true);
		filterLabel.setBackground(new Color(57, 78, 94));
		filterLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		filterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		filterLabel.setBounds(62, 12, 155, 21);
		filterByPane.add(filterLabel);
		
		/**
		 * the panel for our place button
		 */
		JPanel datePanel = new JPanel();
		CardLayout clDate = new CardLayout(0, 0);
		datePanel.setBounds(68, 43, 139, 21);
		filterByPane.add(datePanel);
		datePanel.setLayout(clDate);
		JButton dateButton = new JButton("Date");
		dateButton.setBackground(new Color(64, 128, 128));
		
	
		dateTextField = new JTextField();
		/**
		 * makes the text black when the mouse is clicked also clears out the text field
		 */
		dateTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		       dateTextField.setText("");
				dateTextField.setForeground(Color.BLACK);
			}
		});
		dateTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		dateTextField.setColumns(10);
		
		
		/**
		 * the panel for our place panel
		 */
		JPanel placePanel = new JPanel();
		CardLayout clPlace = new CardLayout(0, 0);
		placePanel.setBounds(68, 90, 139, 21);
		filterByPane.add(placePanel);
		placePanel.setLayout(clPlace);
		JButton placeButton = new JButton("Place");
		placeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		placeButton.setBackground(new Color(64, 128, 128));
		placeTextField = new JTextField();
		
		/**
		 * makes the text black when the mouse is clicked also clears out the text field
		 */
		placeTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				placeTextField.setForeground(Color.BLACK);
				placeTextField.setText("");
			}
		});
		placeTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
       placeTextField.setColumns(10);
		
		/**
		 * The panel for our trigger button
		 */
		JPanel triggerPanel = new JPanel();
		CardLayout clTrigger = new CardLayout(0, 0);
		triggerPanel.setBounds(68, 138, 139, 21);
		filterByPane.add(triggerPanel);
		triggerPanel.setLayout(clTrigger);
		JButton triggerButton = new JButton("Trigger");
		triggerButton.setBackground(new Color(64, 128, 128));
		
		triggerTextField = new JTextField();
		/**
		 * makes the text black when the mouse is clicked also clears out the text field
		 */
		triggerTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				triggerTextField.setText("");
				triggerTextField.setForeground(Color.BLACK);
			}
		});
		triggerTextField.setBackground(new Color(255, 255, 255));
		triggerTextField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		triggerPanel.add(triggerTextField, "triggerTextField");
		triggerTextField.setColumns(10);
		/**
		 * Reset button
		 */
		JButton resetButton = new JButton("Reset Data Table");
		resetButton.setForeground(new Color(255, 255, 255));
		resetButton.setBackground(new Color(64, 157, 136));
		resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		resetButton.addActionListener(new ActionListener() {
			/**
			 * resets our table
			 */
			public void actionPerformed(ActionEvent e) {
				jtable.reset();
				saveFileButton.setText("Save to File"); 
				clDate.show(datePanel, "dateButton");
				clPlace.show(placePanel, "placeButton");
				clTrigger.show(triggerPanel, "triggerButton");
				 updateStats();
			}
		});
		resetButton.setBounds(10, 165, 119, 22);
		filterByPane.add(resetButton);
		/**
		 * Save file button
		 */
		saveFileButton = new JButton("Save to File");
		saveFileButton.setForeground(new Color(255, 255, 255));
		saveFileButton.setBackground(new Color(64, 157, 136));
		saveFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveFileButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    /**
	     * Saves our table to a file
	     */
		saveFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtable.saveToFile();
				saveFileButton.setText("File Saved!"); 
			}
		});
		saveFileButton.setBounds(139, 165, 119, 22);
		filterByPane.add(saveFileButton);
		/**
		 * or label
		 */
		JLabel orLabel1 = new JLabel("OR");
		orLabel1.setForeground(new Color(255, 255, 255));
		orLabel1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		orLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		orLabel1.setBounds(114, 64, 48, 21);
		filterByPane.add(orLabel1);
		/**
		 * or label
		 */
		JLabel orLabel2 = new JLabel("OR");
		orLabel2.setForeground(new Color(255, 255, 255));
		orLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		orLabel2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		orLabel2.setBounds(114, 111, 48, 21);
		filterByPane.add(orLabel2);
		
		triggerPanel.add(triggerButton, "triggerButton");
		placePanel.add(placeButton, "placeButton");
		datePanel.add(dateButton, "dateButton");
		
		datePanel.add(dateTextField, "dateTextField");
		placePanel.add(placeTextField, "placeTextField");
		triggerPanel.add(triggerTextField, "triggerTextField");
		
		/**
		 * The block below is the functionality for our date filter 
		 */
		  dateButton.addActionListener(e -> clDate.show(datePanel, "dateTextField"));
		  dateButton.addActionListener(e -> clPlace.show(placePanel, "placeButton"));
		  dateButton.addActionListener(e -> clTrigger.show(triggerPanel, "triggerButton"));
		  dateButton.addActionListener(e -> {
			  dateTextField.setForeground(Color.LIGHT_GRAY);
			  dateTextField.setText("Ex: Jan 01, 2024");
		  });
		 
		  dateTextField.addActionListener(e -> {
	           clDate.show(datePanel, "dateButton");
	           jtable.dateFilter(dateTextField.getText());
	           updateStats();
	       });
		/**
		 * The block below is the functionality for our place filter 
		 */
	       placeButton.addActionListener(e -> clPlace.show(placePanel, "placeTextField"));
	       placeButton.addActionListener(e -> clDate.show(datePanel, "dateButton"));
	       placeButton.addActionListener(e -> clTrigger.show(triggerPanel, "triggerButton"));
	       placeButton.addActionListener(e -> {
	    	   placeTextField.setForeground(Color.LIGHT_GRAY);
	           placeTextField.setText("Ex: Sun Dial");
	       });
	       placeTextField.addActionListener(e -> {
	           clPlace.show(placePanel, "placeButton");
	           jtable.placeFilter(placeTextField.getText());
	           updateStats();
	       });
		/**
		 *  the block below is the functionality for our trigger filter 
		 */
	       triggerButton.addActionListener(e -> clTrigger.show(triggerPanel, "triggerTextField"));
	       triggerButton.addActionListener(e -> clDate.show(datePanel, "dateButton"));
	       triggerButton.addActionListener(e -> clPlace.show(placePanel, "placeButton"));
	       triggerButton.addActionListener(e -> {
	    	   triggerTextField.setText("Ex: Natural");
	    	   triggerTextField.setForeground(Color.LIGHT_GRAY);
	       });
	       triggerTextField.addActionListener(e -> {
	           clTrigger.show(triggerPanel, "triggerButton");
	           jtable.triggerFilter(triggerTextField.getText());
	           updateStats();
	       });
		return filterByPane;
	}
	/**
	 * a panel that contains our jcombobox that will sort out table depending on
	 * what is selected
	 */
	private JPanel newsortByPane() {
		JPanel sortByPane = new JPanel();
		sortByPane.setBorder(null);
		sortByPane.setBackground(new Color(57, 78, 94));
		sortByPane.setBounds(0, 254, 263, 121);
		sortLabel = new JLabel("SORT BY :");
		sortLabel.setForeground(new Color(255, 255, 255));
		sortLabel.setBounds(10, 10, 80, 25);
		sortByPane.add(sortLabel);
		String[] sortOptions = { "DATE (OLDEST TO NEWEST)", "DATE (NEWEST TO OLDEST)", "DEPTH (SHALLOWEST TO DEEPEST)",
				"DEPTH (DEEPEST TO SHALLOWEST)", "FATALITIES (LEAST TO MOST)", "FATALITIES (MOST TO LEAST)" };
		sortComboBox = new JComboBox<>(sortOptions);
		sortComboBox.setBackground(new Color(64, 157, 136));
		sortComboBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sortComboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		/**
		 * the functionality for our sort by combo box
		 */
		sortComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int columnIndex;
				boolean ascending = false;
				String choice = (String) sortComboBox.getSelectedItem();
				switch (choice) {
				case "DATE (OLDEST TO NEWEST)":
					columnIndex = 0;
					ascending = true;
					break;
				case "DATE (NEWEST TO OLDEST)":
					columnIndex = 0;
					ascending = false;
					break;
				case "DEPTH (SHALLOWEST TO DEEPEST)":
					columnIndex = 4;
					ascending = true;
					break;
				case "DEPTH (DEEPEST TO SHALLOWEST)":
					columnIndex = 4;
					ascending = false;
					break;
				case "FATALITIES (LEAST TO MOST)":
					ascending = true;
					columnIndex = 8;
					break;
				case "FATALITIES (MOST TO LEAST)":
					ascending = false;
					columnIndex = 8;
					break;
				default:
					columnIndex = -1;
				}
				if (columnIndex != -1) {
					jtable.sortByColumnIndex(columnIndex, ascending);
				}
			}
		});
		sortComboBox.setBounds(77, 10, 176, 25);
		sortByPane.add(sortComboBox);
		return sortByPane;
	}
	/**
	 * our label at the top
	 */
	private JPanel newtoLabelBar() {
		JPanel topLabelBar = new JPanel();
		topLabelBar.setBackground(new Color(64, 128, 128));
		topLabelBar.setBounds(0, 228, 1274, 29);
		return topLabelBar;
	}
}





