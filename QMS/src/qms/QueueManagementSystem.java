package qms;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class QueueManagementSystem {
    private static Queue<String> queue = new LinkedList<>();
    private static JLabel currentServingLabel = new JLabel("Currently Serving: None");
    private static int ticketNumber = 1;

    public static void main(String[] args) {
        JFrame window1 = createWindow("Window 1: Ticket Printer", 300, 300);
        JTextArea ticketInfoArea = new JTextArea(6, 35);
        ticketInfoArea.setEditable(false);
        ticketInfoArea.setBackground(Color.DARK_GRAY);
        ticketInfoArea.setForeground(Color.WHITE);

        JButton printTicketButton = new JButton("Print Ticket");
        printTicketButton.setPreferredSize(new Dimension(150, 40));

        printTicketButton.addActionListener(e -> {
            String ticket = "#" + ticketNumber;
            LocalDateTime now = LocalDateTime.now();
            String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            int estimatedTime = queue.size() * 5;
            queue.add(ticket);
            ticketNumber++;

            SwingUtilities.invokeLater(() -> {
                ticketInfoArea.setText(
                    "Ticket Info:\n" +
                    "Queue Number: " + ticket + "\n" +
                    "Date & Time: " + dateTime + "\n" +
                    "Estimated Wait Time: " + estimatedTime + " min"
                );
                ticketInfoArea.revalidate();
                ticketInfoArea.repaint();
            });
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(printTicketButton);

        window1.add(new JScrollPane(ticketInfoArea), BorderLayout.CENTER);
        window1.add(buttonPanel, BorderLayout.SOUTH);

        JFrame window2 = createWindow("Window 2: Call Next", 450, 300);
        JButton callNextButton = new JButton("Next");

        callNextButton.addActionListener(e -> {
            if (!queue.isEmpty()) {
                String nextPerson = queue.poll();
                currentServingLabel.setText("Currently Serving: " + nextPerson);
                currentServingLabel.revalidate();
                currentServingLabel.repaint();
                } else {
                JOptionPane.showMessageDialog(window2, "Queue is empty!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        window2.add(callNextButton, BorderLayout.CENTER);

        JFrame window3 = createWindow("Window 3: Current Serving", 600, 300);
        currentServingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentServingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        currentServingLabel.setForeground(Color.WHITE);
        window3.add(currentServingLabel, BorderLayout.CENTER);
    }

    private static JFrame createWindow(String title, int x, int y) {
        JFrame frame = new JFrame(title);
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(x, y);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
        return frame;
    }
}
