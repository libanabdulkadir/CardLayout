import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Contact {
    String name, phone, email;

    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class ContactApp extends JFrame {

    // the main for switching between screens or cards

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

             // Lists to store contacts and model
    private List<Contact> contacts = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> contactList = new JList<>(listModel);

    // Form fields for adding/editing contact information (create form panel)

    private JTextField nameField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField emailField = new JTextField();

    // Labels for displaying contact details ( down to create details)

    private JLabel nameLabel = new JLabel();
    private JLabel phoneLabel = new JLabel();
    private JLabel emailLabel = new JLabel();

    public ContactApp() {
        setTitle("Contact Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        setupPanels();
        add(cardPanel);
        cardLayout.show(cardPanel, "LIST");
    }

    private void setupPanels() {
        cardPanel.add(createContactListPanel(), "LIST");
        cardPanel.add(createDetailsPanel(), "DETAILS");
        cardPanel.add(createFormPanel(), "FORM");
    }

    private JPanel createContactListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Contact");
        JButton viewButton = new JButton("View Details");

        //to switch between cards

        addButton.addActionListener(e -> showCard("FORM"));
        viewButton.addActionListener(e -> showContactDetails());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(nameLabel);
        panel.add(phoneLabel);
        panel.add(emailLabel);

        JButton backButton = new JButton("Back to List");
        backButton.addActionListener(e -> showCard("LIST"));
        panel.add(backButton);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Action listeners to save contact or cancel

        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> showCard("LIST"));

        panel.add(saveButton);
        panel.add(cancelButton);

        return panel;
    }

    private void saveContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        // Validation to ensure no fields are empty
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

   // Create a new contact and add it to the list
        contacts.add(new Contact(name, phone, email));
        listModel.addElement(name);
        clearFormFields();
        showCard("LIST");
    }

    private void showContactDetails() {
        int index = contactList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Select a contact");
            return;
        }
// Displaying the selected contacts details

        Contact contact = contacts.get(index);
        nameLabel.setText("Name: " + contact.name);
        phoneLabel.setText("Phone: " + contact.phone);
        emailLabel.setText("Email: " + contact.email);
        showCard("DETAILS");
    }

    private void clearFormFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void showCard(String card) {
        cardLayout.show(cardPanel, card);
    }


}
