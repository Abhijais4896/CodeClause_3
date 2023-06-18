import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private JFrame frame;
    private JComboBox<String> countryComboBox;
    private JLabel label;
    private JTextField textField;
    private JButton convertButton;

    public Main() {
        // Create the main frame
        frame = new JFrame("Time Conversion");

        // Create the components
        label = new JLabel("Enter time in UTC:");
        textField = new JTextField(20);
        countryComboBox = new JComboBox<>(new String[] { "New York", "London", "Tokyo" });
        convertButton = new JButton("Convert");

        // Set up the layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(label);
        panel.add(textField);
        panel.add(countryComboBox);
        panel.add(convertButton);

        // Add action listener to the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputTime = textField.getText();

                // Get the selected country from the combo box
                String selectedCountry = (String) countryComboBox.getSelectedItem();

                // Perform time conversion
                String convertedTime = convertTime(inputTime, selectedCountry);

                // Display the result
                JOptionPane.showMessageDialog(frame, "Converted time: " + convertedTime);
            }
        });

        // Set up the main frame
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private String convertTime(String inputTime, String country) {
        // Get the current time in UTC
        LocalDateTime utcTime = LocalDateTime.parse(inputTime, DateTimeFormatter.ISO_DATE_TIME);

        // Convert to the selected country's time zone
        ZoneId selectedZone = null;
        switch (country) {
            case "New York":
                selectedZone = ZoneId.of("America/New_York");
                break;
            case "London":
                selectedZone = ZoneId.of("Europe/London");
                break;
            case "Tokyo":
                selectedZone = ZoneId.of("Asia/Tokyo");
                break;
        }
        ZonedDateTime convertedTime = utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(selectedZone);

        // Format the converted time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return convertedTime.format(formatter);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
