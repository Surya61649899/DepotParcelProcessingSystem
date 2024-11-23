package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Manager {
    private QueueOfCustomers clientQueue;
    private ParcelMap parcelMap;
    private Worker worker;
    private Log log;
    private JFrame frame;
    private JTextArea parcelTextArea;
    private JTextArea customerTextArea;
    private JTextArea currentParcelTextArea;
    private JButton processButton;
    private JLabel statusBar;

    
 // Constructor to initialize member variables and create GUI
    public Manager() {
        this.clientQueue = new QueueOfCustomers();
        this.parcelMap = new ParcelMap();
        this.worker = new Worker(parcelMap);
        this.log = Log.getInstance();
        createGUI();
    }

    
    // Initialize parcels from a file
    public void initializeParcels(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String packageID = parts[0];
                    int daysInDepot = Integer.parseInt(parts[1]);
                    double weight = Double.parseDouble(parts[2]);
                    double length = Double.parseDouble(parts[3]);
                    double width = Double.parseDouble(parts[4]);
                    double height = Double.parseDouble(parts[5]);

                    Parcel parcel = new Parcel(packageID, daysInDepot, weight, length, width, height);
                    parcelMap.addPackage(parcel);
                }
            }
            updateParcelTextArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
 // Initialize customers from a file
    public void initializeCustomers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int queueNumber = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String packageID = parts[2];

                    Customer customer = new Customer(queueNumber, name, packageID);
                    clientQueue.addCustomer(customer);
                }
            }
            updateCustomerTextArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 // Start processing the next customer in the queue
    public void startProcessing() {
        if (!clientQueue.isEmpty()) {
            Customer customer = clientQueue.removeCustomer();
            worker.processCustomer(customer);
            updateCustomerTextArea();
            updateParcelTextArea();
            currentParcelTextArea.setText("Currently Processing: \n" + customer.toString());
            updateStatusBar("Processing customer: " + customer.getName());
        } else {
            currentParcelTextArea.setText("No more customers to process.");
            updateStatusBar("No more customers to process.");
        }
        log.writeLogToFile("log.txt");
    }
    
    
    private void createGUI() {
        frame = new JFrame("Package Depot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 800);
        frame.setLayout(new BorderLayout());

        // Header Panel with Title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("Package Depot System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadPackages = new JMenuItem("Load Packages");
        loadPackages.addActionListener(e -> initializeParcels("packages.txt"));
        JMenuItem loadClients = new JMenuItem("Load Clients");
        loadClients.addActionListener(e -> initializeCustomers("customers.txt"));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(loadPackages);
        fileMenu.add(loadClients);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Package Depot System v1.0", "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        // Package Display Area
        parcelTextArea = new JTextArea();
        parcelTextArea.setEditable(false);
        parcelTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        parcelTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane packageScrollPane = new JScrollPane(parcelTextArea);
        packageScrollPane.setBorder(BorderFactory.createTitledBorder("Packages"));

        // Customer Display Area
        customerTextArea = new JTextArea();
        customerTextArea.setEditable(false);
        customerTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        customerTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane customerScrollPane = new JScrollPane(customerTextArea);
        customerScrollPane.setBorder(BorderFactory.createTitledBorder("Customers"));

        // Current Package Display Area
        currentParcelTextArea = new JTextArea();
        currentParcelTextArea.setEditable(false);
        currentParcelTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        currentParcelTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane currentPackageScrollPane = new JScrollPane(currentParcelTextArea);
        currentPackageScrollPane.setBorder(BorderFactory.createTitledBorder("Current Package"));

        // Split Panes for Layout
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, customerScrollPane, packageScrollPane);
        splitPane1.setDividerLocation(500);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, currentPackageScrollPane);
        splitPane2.setDividerLocation(900);

        // Process Button for Customer Processing
        processButton = new JButton("Process Next Customer");
        processButton.setToolTipText("Click to process the next customer in the queue");
        processButton.setFont(new Font("Arial", Font.BOLD, 16));
        processButton.setBackground(new Color(76, 175, 80));  // Green Color
        processButton.setFocusPainted(false);
        processButton.addActionListener(e -> startProcessing());

        // Package Form Panel
        JPanel packageFormPanel = new JPanel();
        packageFormPanel.setLayout(new BoxLayout(packageFormPanel, BoxLayout.Y_AXIS));
        packageFormPanel.setBorder(BorderFactory.createTitledBorder("Add Package"));
        JTextField packageIDField = new JTextField();
        JTextField daysInDepotField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField lengthField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();

        JButton addPackageButton = new JButton("Add Package");
        addPackageButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPackageButton.setBackground(new Color(76, 175, 80)); // Green Color
        addPackageButton.setFocusPainted(false);
        addPackageButton.addActionListener(e -> {
            try {
                String packageID = packageIDField.getText();
                int daysInDepot = Integer.parseInt(daysInDepotField.getText());
                double weight = Double.parseDouble(weightField.getText());
                double length = Double.parseDouble(lengthField.getText());
                double width = Double.parseDouble(widthField.getText());
                double height = Double.parseDouble(heightField.getText());

                Parcel parcel = new Parcel(packageID, daysInDepot, weight, length, width, height);
                parcelMap.addPackage(parcel);
                updateParcelTextArea();
                packageIDField.setText("");
                daysInDepotField.setText("");
                weightField.setText("");
                lengthField.setText("");
                widthField.setText("");
                heightField.setText("");
            } catch (NumberFormatException ex) {
                updateStatusBar("Error: Please enter valid numeric values.");
            }
        });

        packageFormPanel.add(new JLabel("Package ID:"));
        packageFormPanel.add(packageIDField);
        packageFormPanel.add(new JLabel("Days in Depot:"));
        packageFormPanel.add(daysInDepotField);
        packageFormPanel.add(new JLabel("Weight:"));
        packageFormPanel.add(weightField);
        packageFormPanel.add(new JLabel("Length:"));
        packageFormPanel.add(lengthField);
        packageFormPanel.add(new JLabel("Width:"));
        packageFormPanel.add(widthField);
        packageFormPanel.add(new JLabel("Height:"));
        packageFormPanel.add(heightField);
        packageFormPanel.add(addPackageButton);

        // Customer Form Panel
        JPanel customerFormPanel = new JPanel();
        customerFormPanel.setLayout(new BoxLayout(customerFormPanel, BoxLayout.Y_AXIS));
        customerFormPanel.setBorder(BorderFactory.createTitledBorder("Add Customer"));
        JTextField queueNumberField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField packageIDForCustomerField = new JTextField();

        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setFont(new Font("Arial", Font.BOLD, 14));
        addCustomerButton.setBackground(new Color(76, 175, 80)); // Green Color
        addCustomerButton.setFocusPainted(false);
        addCustomerButton.addActionListener(e -> {
            try {
                int queueNumber = Integer.parseInt(queueNumberField.getText());
                String name = customerNameField.getText();
                String packageID = packageIDForCustomerField.getText();

                Customer customer = new Customer(queueNumber, name, packageID);
                clientQueue.addCustomer(customer);
                updateCustomerTextArea();
                queueNumberField.setText("");
                customerNameField.setText("");
                packageIDForCustomerField.setText("");
            } catch (NumberFormatException ex) {
                updateStatusBar("Error: Please enter valid numeric values.");
            }
        });

        customerFormPanel.add(new JLabel("Queue Number:"));
        customerFormPanel.add(queueNumberField);
        customerFormPanel.add(new JLabel("Customer Name:"));
        customerFormPanel.add(customerNameField);
        customerFormPanel.add(new JLabel("Package ID:"));
        customerFormPanel.add(packageIDForCustomerField);
        customerFormPanel.add(addCustomerButton);

        // Form Panel for Both Sections
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        formPanel.add(packageFormPanel);
        formPanel.add(customerFormPanel);

        // Center Panel with Split Pane and Process Button
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(splitPane2, BorderLayout.CENTER);
        centerPanel.add(processButton, BorderLayout.SOUTH);

        // Status Bar
        statusBar = new JLabel("Status: Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.add(statusBar, BorderLayout.SOUTH);

        // Adding Panels to Frame
        frame.add(formPanel, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }



    private void updateParcelTextArea() {
        parcelTextArea.setText(parcelMap.toString());
    }

    private void updateCustomerTextArea() {
        customerTextArea.setText(clientQueue.toString());
    }

    private void updateStatusBar(String message) {
        statusBar.setText("Status: " + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Manager());
    }
}
