import java.io.*;
import java.util.*;
import java.text.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import com.toedter.calendar.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.beans.*;

public class HomeInventory extends JFrame {
	JToolBar toolBar = new JToolBar();
	JButton newBtn = new JButton();
	JButton delBtn = new JButton();
	JButton saveBtn = new JButton();
	JButton prevBtn = new JButton();
	JButton nextBtn = new JButton();
	JButton printBtn = new JButton();
	JButton exitBtn = new JButton();
	JLabel item = new JLabel();
	JTextField itemText = new JTextField();
	JLabel location = new JLabel();
	JComboBox locationBox = new JComboBox();
	JCheckBox marked = new JCheckBox();
	JLabel serial = new JLabel();
	JTextField serialText = new JTextField();
	JLabel price = new JLabel();
	JTextField priceText = new JTextField();
	JLabel date = new JLabel();
	JDateChooser dateDateChooser = new JDateChooser();
	JLabel store = new JLabel();
	JTextField storeText = new JTextField();
	JLabel note = new JLabel();
	JTextField noteText = new JTextField();
	JLabel photo = new JLabel();
	static JTextArea photoText = new JTextArea();
	JButton photoBtn = new JButton();
	JPanel  searchPanel = new JPanel();
	JButton[] searchButton = new JButton[26];
	PhotoPanel photoPanel = new PhotoPanel();
	
	static final int maxEntries= 300;
	static int numEntries;
	int currEntry;
	static final int entriesPerPage = 2;
	static int lastPage;
	static InventoryItem[] myInventory = new InventoryItem[maxEntries];
	
	public HomeInventory() {
		setTitle("Home Inventory Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);	
			}
		});
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.BLUE);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		gbc=new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridheight=8;
		gbc.fill=GridBagConstraints.VERTICAL;
		getContentPane().add(toolBar,gbc);
		
		toolBar.addSeparator();
		
		Dimension bSize = new Dimension(70,50);
		
		newBtn.setText("New");
		sizeButton(newBtn,bSize);
		newBtn.setToolTipText("Add New Item");
		newBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		newBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		newBtn.setFocusable(false);
		toolBar.add(newBtn);
		newBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newButtonActionPerformed(e);
			}
		});
		
		delBtn.setText("Delete");
		sizeButton(delBtn,bSize);
		delBtn.setToolTipText("Delete Current Item");
		delBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		delBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		delBtn.setFocusable(false);
		toolBar.add(delBtn);
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delBtnActionPerformed(e);
			}
		});
		
		saveBtn.setText("Save");
		sizeButton(saveBtn,bSize);
		saveBtn.setToolTipText("Save Current Item");
		saveBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		saveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		saveBtn.setFocusable(false);
		toolBar.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBtnActionPerformed(e);
			}
		});
		
		toolBar.addSeparator();
		
		prevBtn.setText("Previous");
		sizeButton(prevBtn,bSize);
		prevBtn.setToolTipText("Display Previous Item");
		prevBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		prevBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		prevBtn.setFocusable(false);
		toolBar.add(prevBtn);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prevBtnActionPerformed(e);
			}
		});
		
		nextBtn.setText("Next");
		sizeButton(nextBtn,bSize);
		nextBtn.setToolTipText("Display Next Item");
		nextBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		nextBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		nextBtn.setFocusable(false);
		toolBar.add(nextBtn);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextBtnActionPerformed(e);
			}
		});
		
		toolBar.addSeparator();
		
		printBtn.setText("Print");
		sizeButton(printBtn,bSize);
		printBtn.setToolTipText("Print Inventory List");
		printBtn.setHorizontalTextPosition(SwingConstants.CENTER);
		printBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		printBtn.setFocusable(false);
		toolBar.add(printBtn);
		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printBtnActionPerformed(e);
			}
		});
		
		exitBtn.setText("Exit");
		sizeButton(exitBtn,bSize);
		exitBtn.setToolTipText("Exit Program");
		exitBtn.setFocusable(false);
		toolBar.add(exitBtn);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitBtnActionPerformed(e);
			}
		});
		
		item.setText("Inventory Item");
		gbc = new GridBagConstraints();
		gbc.gridx=1;
		gbc.gridy=0;
		gbc.insets=new Insets(10,10,0,10);
		gbc.anchor=GridBagConstraints.EAST;
		getContentPane().add(item,gbc);
		itemText.setPreferredSize(new Dimension(400,25));
		gbc = new GridBagConstraints();
		gbc.gridx=2;
		gbc.gridy=0;
		gbc.gridwidth=5;
		gbc.insets=new Insets(10,0,0,10);
		gbc.anchor=GridBagConstraints.WEST;
		getContentPane().add(itemText,gbc);
		itemText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemTextActionPerformed(e);
			}
		});
		
		location.setText("Location");
		gbc = new GridBagConstraints();
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.insets=new Insets(10,10,0,10);
		gbc.anchor=GridBagConstraints.EAST;
		getContentPane().add(location,gbc);
		locationBox.setPreferredSize(new Dimension(270,25));
		locationBox.setFont(new Font("Arial",Font.PLAIN,12));
		locationBox.setEditable(true);
		locationBox.setBackground(Color.WHITE);
		gbc = new GridBagConstraints();
		gbc.gridx=2;
		gbc.gridy=1;
		gbc.gridwidth=3;
		gbc.insets=new Insets(10,0,0,10);
		gbc.anchor=GridBagConstraints.WEST;
		getContentPane().add(locationBox,gbc);
		locationBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locationBoxActionPerformed(e);
			}
		});
		
		marked.setText("Marked?");
		marked.setFocusable(false);
		gbc = new GridBagConstraints();
		gbc.gridx=5;
		gbc.gridy=1;
		gbc.gridwidth=3;
		gbc.insets=new Insets(10,10,0,0);
		gbc.anchor=GridBagConstraints.WEST;
		getContentPane().add(marked,gbc);
		
		serial.setText("Serial Number");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10,10,0,10);
		gbc.anchor = GridBagConstraints.EAST;
		getContentPane().add(serial,gbc);
		serialText.setPreferredSize(new Dimension(270,25));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(serialText,gbc);
		serialText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serialTextActionPerformed(e);
			}
		});
		
		date.setText("Date Purchased");
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 10, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(date, gbc);

		dateDateChooser.setPreferredSize(new Dimension(120, 25));
		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 0, 0, 10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(dateDateChooser, gbc);
		dateDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				dateDateChooserPropertyChange(e);
			}
		});
		
		price.setText("Purchase Price");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10,10,0,10);
		gbc.anchor = GridBagConstraints.EAST;
		getContentPane().add(price,gbc);
		priceText.setPreferredSize(new Dimension(160,25));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(priceText,gbc);
		priceText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				priceTextActionPerformed(e);
			}
		});
		
		store.setText("Store/Website");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(10,10,0,10);
		gbc.anchor = GridBagConstraints.EAST;
		getContentPane().add(store,gbc);
		storeText.setPreferredSize(new Dimension(400,25));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 5;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(storeText,gbc);
		storeText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeTextActionPerformed(e);
			}
		});
		
		note.setText("Note");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(10,10,0,10);
		gbc.anchor = GridBagConstraints.EAST;
		getContentPane().add(note,gbc);
		noteText.setPreferredSize(new Dimension(400,25));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 5;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(noteText,gbc);
		noteText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noteTextActionPerformed(e);
			}
		});
		
		photo.setText("Photo");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(10,10,0,10);
		gbc.anchor = GridBagConstraints.EAST;
		getContentPane().add(photo,gbc);
		photoText.setPreferredSize(new Dimension(350,35));
		photoText.setFont(new Font("Arial",Font.PLAIN,12));
		photoText.setEditable(false);
		photoText.setLineWrap(true);
		photoText.setWrapStyleWord(true);
		photoText.setBackground(new Color(255,255,192));
		photoText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(photoText,gbc);
		photoBtn.setText("...");
		gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 6;
		gbc.insets = new Insets(10,0,0,10);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(photoBtn,gbc);
		photoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				photoBtnActionPerformed(e);
			}
		});
		
		searchPanel.setPreferredSize(new Dimension(240,160));
		searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
		searchPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(10,0,10,0);
		gbc.anchor = GridBagConstraints.CENTER;
		getContentPane().add(searchPanel,gbc);
		int x=0,y=0;
		for(int i=0;i<26;i++) {
			searchButton[i] = new JButton();
			searchButton[i].setText(String.valueOf((char)(65+i)));
			searchButton[i].setFont(new Font("Arial",Font.BOLD,12));
			searchButton[i].setMargin(new Insets(-10,-10,-10,-10));
			sizeButton(searchButton[i],new Dimension(37,27));
			searchButton[i].setBackground(Color.YELLOW);
			gbc = new GridBagConstraints();
			gbc.gridx = x;
			gbc.gridy = y;
			searchPanel.add(searchButton[i],gbc);
			searchButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchButtonActionPerformed(e);
				}
			});
			x++;
			if(x%6==0) {
				x=0;
				y++;
			}
		}
		
		photoPanel.setPreferredSize(new Dimension(240,160));
		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 7;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(10,0,10,10);
		gbc.anchor = GridBagConstraints.CENTER;
		getContentPane().add(photoPanel,gbc);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)(0.5*(screenSize.width-getWidth())),(int)(0.5*(screenSize.height-getHeight())),
				getWidth(),getHeight());
		
		int n;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("inventory.txt"));
			numEntries = Integer.valueOf(br.readLine()).intValue();
			if(numEntries !=0) {
				for(int i=0;i<numEntries;i++) {
					myInventory[i]=new InventoryItem();
					myInventory[i].description = br.readLine();
					myInventory[i].location = br.readLine();
					myInventory[i].serialNumber = br.readLine();
					myInventory[i].marked = Boolean.valueOf(br.readLine()).booleanValue();
					myInventory[i].purchasePrice = br.readLine();
					myInventory[i].purchaseDate = br.readLine();
					myInventory[i].purchaseLocation = br.readLine();
					myInventory[i].note = br.readLine();
					myInventory[i].photoFile = br.readLine();
				}
			}
			n = Integer.valueOf(br.readLine()).intValue();
			if(n!=0) {
				for(int i=0;i<n;i++) {
					locationBox.addItem(br.readLine());
				}
			}
			br.close();
			currEntry = 1;
			showEntry(currEntry);
		}
		catch(Exception ex) {
			numEntries = 0;
			currEntry = 0;
		}
		if(numEntries == 0) {
			newBtn.setEnabled(false);
			delBtn.setEnabled(false);
			nextBtn.setEnabled(false);
			prevBtn.setEnabled(false);
			printBtn.setEnabled(false);
		}
	}

	private void newButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkSave();
		blankValues();
	}

	private void delBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete Inventory Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
			return;
		delEntry(currEntry);
		if(numEntries==0) {
			currEntry = 0;
			blankValues();
		}else {
			currEntry--;
			if(currEntry==0)
				currEntry = 1;
			showEntry(currEntry);
		}
	}
	private void saveBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		itemText.setText(itemText.getText().trim());
		if(itemText.getText().equals("")) {
			JOptionPane.showConfirmDialog(null, "Must have item description.","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
			itemText.requestFocus();
			return;
		}
		if(newBtn.isEnabled()) {
			delEntry(currEntry);
		}
		String s = itemText.getText();
		itemText.setText(s.substring(0,1).toUpperCase()+s.substring(1));
		numEntries++;
		currEntry = 1;
		if(numEntries!=1) {
			do {
				if(itemText.getText().compareTo(myInventory[currEntry-1].description)<0)
					break;
				currEntry++;
			}
			while(currEntry<numEntries);
		}
		if(currEntry!=numEntries) {
			for(int i=numEntries;i>=currEntry+1;i++) {
				myInventory[i-1] = myInventory[i-2];
				myInventory[i-2] = new InventoryItem();
			}
		}
		myInventory[currEntry-1] = new InventoryItem();
		myInventory[currEntry-1].description = itemText.getText();
		myInventory[currEntry-1].location = locationBox.getSelectedItem().toString();
		myInventory[currEntry-1].marked = marked.isSelected();
		myInventory[currEntry-1].serialNumber = serialText.getText();
		myInventory[currEntry-1].purchasePrice = priceText.getText();
		myInventory[currEntry-1].purchaseDate = dateToString(dateDateChooser.getDate());
		myInventory[currEntry-1].purchaseLocation = storeText.getText();
		myInventory[currEntry-1].photoFile = photoText.getText();
		myInventory[currEntry-1].note = noteText.getText();
		showEntry(currEntry);
		if(numEntries<maxEntries)
			newBtn.setEnabled(true);
		else
			newBtn.setEnabled(false);
		delBtn.setEnabled(true);
		printBtn.setEnabled(true);
	}
	
	private void prevBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkSave();
		currEntry--;
		showEntry(currEntry);
	}

	private void nextBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		checkSave();
		currEntry++;
		showEntry(currEntry);
	}

	private void printBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		lastPage = (int)(1+(numEntries-1)/entriesPerPage);
		PrinterJob inventoryPJ = PrinterJob.getPrinterJob();
		inventoryPJ.setPrintable(new InventoryDocs());
		if(inventoryPJ.printDialog()) {
			try {
				inventoryPJ.print();
			}
			catch(PrinterException ex) {
				JOptionPane.showConfirmDialog(null, ex.getMessage(),"Print Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void itemTextActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		locationBox.requestFocus();
	}
	
	private void locationBoxActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(locationBox.getItemCount()!=0) {
			for(int i=0;i<locationBox.getItemCount();i++) {
				if(locationBox.getSelectedItem().toString().equals(locationBox.getItemAt(i).toString())) {
					serialText.requestFocus();
					return;
				}
			}
		}
		locationBox.addItem(locationBox.getSelectedItem());
		serialText.requestFocus();
	}

	private void serialTextActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		priceText.requestFocus();
	}

	private void priceTextActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dateDateChooser.requestFocus();
	}

	private void storeTextActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		noteText.requestFocus();
	}

	private void noteTextActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		photoBtn.requestFocus();
	}
	
	private void photoBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFileChooser openChooser = new JFileChooser();
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setDialogTitle("Open Photo File");
		openChooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files",
				"jpg"));
		if(openChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			showPhoto(openChooser.getSelectedFile().toString());
	}
	
	private void searchButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i;
		if(numEntries==0)
			return;
		String letterClick = e.getActionCommand();
		i=0;
		do {
			if(myInventory[i].description.substring(0,1).equals(letterClick)) {
				currEntry = i+1;
				showEntry(currEntry);
				return;
			}
			i++;
		}
		while(i<numEntries);
		JOptionPane.showConfirmDialog(null, "No "+letterClick+" inventory items.","None Found",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
	}

	private void exitBtnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		exitForm(null);
	}
	
	private void blankValues() {
		newBtn.setEnabled(false);
		delBtn.setEnabled(false);
		saveBtn.setEnabled(true);
		prevBtn.setEnabled(false);
		nextBtn.setEnabled(false);
		printBtn.setEnabled(false);
		itemText.setText("");
		locationBox.setSelectedItem("");
		marked.setSelected(false);
		serialText.setText("");
		priceText.setText("");
		dateDateChooser.setDate(new Date());
		storeText.setText("");
		noteText.setText("");
		photoText.setText("");
		photoPanel.repaint();
		itemText.requestFocus();
	}

	private void dateDateChooserPropertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub
		storeText.requestFocus();
	}

	private void sizeButton(JButton b, Dimension d) {
		// TODO Auto-generated method stub
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);
	}

	private void checkSave() {
		// TODO Auto-generated method stub
		boolean edited = false;
		if(!myInventory[currEntry-1].description.equals(itemText.getText()))
			edited = true;
		else if(!myInventory[currEntry-1].location.equals(locationBox.getSelectedItem().toString()))
			edited = true;
		else if(!myInventory[currEntry-1].marked!=marked.isSelected())
			edited = true;
		else if(!myInventory[currEntry-1].serialNumber.equals(serialText.getText()))
			edited = true;
		else if(!myInventory[currEntry-1].purchasePrice.equals(priceText.getText()))
			edited = true;
		else if(!myInventory[currEntry-1].purchaseDate.equals(dateToString(dateDateChooser.getDate())))
			edited = true;
		else if(!myInventory[currEntry-1].purchaseLocation.equals(storeText.getText()))
			edited = true;
		else if(!myInventory[currEntry-1].note.equals(noteText.getText()))
			edited = true;
		else if(!myInventory[currEntry-1].photoFile.equals(photoText.getText()))
			edited = true;
		if(edited) {
			if(JOptionPane.showConfirmDialog(null, "You have edited this item.Do you want to save the changes?","Save Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
				saveBtn.doClick();
		}
	}

	private void delEntry(int j) {
		// TODO Auto-generated method stub
		if(j!=numEntries) {
			for(int i=j;i<numEntries;i++) {
				myInventory[i-1] = new InventoryItem();
				myInventory[i-1] = myInventory[i];
			}
		}
		numEntries--;
	}

	private void exitForm(WindowEvent evt) {
		// TODO Auto-generated method stub
		if(JOptionPane.showConfirmDialog(null,"Any unsaved changes will be lost.\nAre you sure you want to exit?","Exit Program",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
			return;
		try {
			PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
			outputFile.println(numEntries);
			if(numEntries!=0) {
				for(int i=0;i<numEntries;i++) {
					outputFile.println(myInventory[i].description);
					outputFile.println(myInventory[i].location);
					outputFile.println(myInventory[i].serialNumber);
					outputFile.println(myInventory[i].marked);
					outputFile.println(myInventory[i].purchasePrice);
					outputFile.println(myInventory[i].purchaseDate);
					outputFile.println(myInventory[i].purchaseLocation);
					outputFile.println(myInventory[i].note);
					outputFile.println(myInventory[i].photoFile);
				}
			}
			outputFile.println(locationBox.getItemCount());
			if(locationBox.getItemCount()!=0) {
				for(int i=0;i<locationBox.getItemCount();i++) {
					outputFile.println(locationBox.getItemAt(i));
				}
			}
			outputFile.close();
		}
		catch(Exception ex) {
			
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new HomeInventory().setVisible(true);
	}
	
	private void showEntry(int j) {
		itemText.setText(myInventory[j-1].description);
		locationBox.setSelectedItem(myInventory[j-1].location);
		marked.setSelected(myInventory[j-1].marked);
		serialText.setText(myInventory[j-1].serialNumber);
		priceText.setText(myInventory[j-1].purchasePrice);
		dateDateChooser.setDate(stringToDate(myInventory[j-1].purchaseDate));
		storeText.setText(myInventory[j-1].purchaseLocation);
		noteText.setText(myInventory[j-1].note);
		showPhoto(myInventory[j-1].photoFile);
		nextBtn.setEnabled(true);
		prevBtn.setEnabled(true);
		if(j==1)
			prevBtn.setEnabled(false);
		if(j==numEntries)
			nextBtn.setEnabled(false);
		itemText.requestFocus();
	}
	
	private void showPhoto(String photoFile) {
		// TODO Auto-generated method stub
		if(!photoFile.equals("")) {
			try {
				photoText.setText(photoFile);
			}
			catch(Exception ex) {
				photoText.setText("");
			}
		}else {
			photoText.setText("");
		}
		photoPanel.repaint();
	}

	private Date stringToDate(String s) {
		// TODO Auto-generated method stub
		int m = Integer.valueOf(s.substring(0,2)).intValue()-1;
		int d = Integer.valueOf(s.substring(3,5)).intValue();
		int y = Integer.valueOf(s.substring(6)).intValue()-1900;
		return (new Date(y,m,d));
	}
		
	private String dateToString(Date dd) {
		String y = String.valueOf(dd.getYear()+1900);
		int m = dd.getMonth()+1;
		String ms = new DecimalFormat("00").format(m);
		int d = dd.getDate();
		String ds = new DecimalFormat("00").format(d);
		return (ms+"/"+ds+"/"+y);	
	}
}



class PhotoPanel extends JPanel{
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g2D);
		g2D.setPaint(Color.BLACK);
		g2D.draw(new Rectangle2D.Double(0,0,getWidth()-1,getHeight()-1));
		Image photoImg = new ImageIcon(HomeInventory.photoText.getText()).getImage();
		int w = getWidth();
		int h = getHeight();
		double rWidth = (double)getWidth()/(double)photoImg.getWidth(null);
		double rHeight = (double)getHeight()/(double)photoImg.getHeight(null);
		if(rWidth>rHeight) {
			w = (int)(photoImg.getWidth(null)*rHeight);
		}else {
			h = (int)(photoImg.getHeight(null)*rWidth);
		}
		g2D.drawImage(photoImg, (int)(0.5*(getWidth()-w)), (int)(0.5*(getHeight()-h)), w, h, null);
		g2D.dispose();
	}
}



class InventoryDocs implements Printable{
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		Graphics2D g2D = (Graphics2D)g;
		if((pageIndex+1)>HomeInventory.lastPage) {
			return NO_SUCH_PAGE;
		}
		int i,iEnd;
		g2D.setFont(new Font("Arial",Font.BOLD,14));
		g2D.drawString("Home Inventory Items - Page "+String.valueOf(pageIndex+1),(int)(pf.getImageableX()), (int)(pf.getImageableY()+25));
		int dy = (int)g2D.getFont().getStringBounds("S", g2D.getFontRenderContext()).getHeight();
		int y = (int)(pf.getImageableY()+4*dy);
		iEnd = HomeInventory.entriesPerPage*(pageIndex+1);
		if(iEnd>HomeInventory.numEntries)
			iEnd = HomeInventory.numEntries;
		for(i=0+HomeInventory.entriesPerPage*pageIndex;i<iEnd;i++) {
			Line2D.Double divider = new Line2D.Double(pf.getImageableX(),y,pf.getImageableX()+pf.getImageableWidth(),y);
			g2D.draw(divider);
			y+=dy;
			g2D.setFont(new Font("Arial",Font.BOLD,12));
			g2D.drawString(HomeInventory.myInventory[i].description,(int)pf.getImageableX(), y);
			y+=dy;
			g2D.setFont(new Font("Arial",Font.PLAIN,12));
			g2D.drawString("Location: "+HomeInventory.myInventory[i].location, (int)(pf.getImageableX()+25), y);
			y+=dy;
			if(HomeInventory.myInventory[i].marked)
				g2D.drawString("Item is marked with identifying information.", (int)
						(pf.getImageableX() + 25), y);
			else
				g2D.drawString("Item is NOT marked with identifying information.", (int)
						(pf.getImageableX() + 25), y);
			y+=dy;
			g2D.drawString("Serial Number: " +
					HomeInventory.myInventory[i].serialNumber, (int) (pf.getImageableX() + 25), y);
			y += dy;
			g2D.drawString("Price: $" + HomeInventory.myInventory[i].purchasePrice + ",Purchased on: " + HomeInventory.myInventory[i].purchaseDate, (int) (pf.getImageableX() +25), y);
			y += dy;
			g2D.drawString("Purchased at: "+HomeInventory.myInventory[i].purchaseLocation, (int) (pf.getImageableX() + 25), y);
			y += dy;
			g2D.drawString("Note: " + HomeInventory.myInventory[i].note, (int)(pf.getImageableX() + 25), y);
			y += dy;
			try {
				Image inventoryImg = new ImageIcon(HomeInventory.myInventory[i].photoFile).getImage();
				double ratio = (double)(inventoryImg.getWidth(null))/(double)inventoryImg.getHeight(null);
				g2D.drawImage(inventoryImg,(int)(pf.getImageableX()+25),y,(int)(100*ratio),100,null);
			}
			catch(Exception ex) {
				
			}
			y+=2*dy+100;
		}
		return PAGE_EXISTS;
	}
}
