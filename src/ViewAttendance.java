import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class ViewAttendance extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private String studentId = null;  // For filtering student-specific data


   public ViewAttendance() {
        this(null); // Default: admin view
    }

    public ViewAttendance(String studentId) {
        this.studentId = studentId;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("View Attendance Records");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only 'Actions' column editable
            }
        };

        model.addColumn("ID");
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Status");
        model.addColumn("Actions");

        table = new JTable(model);
        table.setRowHeight(30);

        // Set custom renderer and editor for "Actions" column
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load attendance data
        loadData();

        // Export Button
        JButton exportBtn = new JButton("📁 Export to CSV");
        exportBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exportBtn.setBackground(new Color(70, 130, 180));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.addActionListener(e -> exportToCSV());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(exportBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/attendance_db", "root", "root1234");
            String sql = "SELECT * FROM attendance ORDER BY date DESC";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("student_id"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        rs.getString("status"),
                        "Edit/Delete"
                });
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as CSV");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileChooser.getSelectedFile() + ".csv"))) {
                for (int i = 0; i < model.getColumnCount() - 1; i++) { // skip last column
                    pw.print(model.getColumnName(i));
                    if (i < model.getColumnCount() - 2) pw.print(",");
                }
                pw.println();

                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount() - 1; col++) { // skip last column
                        pw.print(model.getValueAt(row, col));
                        if (col < model.getColumnCount() - 2) pw.print(",");
                    }
                    pw.println();
                }

                JOptionPane.showMessageDialog(this, "Exported successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
            }
        }
    }

    // Renderer for buttons in the table
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton updateBtn = new JButton("Update");
        private final JButton deleteBtn = new JButton("Delete");

        public ButtonRenderer() {
            setLayout(new FlowLayout());
            updateBtn.setBackground(new Color(255, 165, 0));
            updateBtn.setForeground(Color.WHITE);
            deleteBtn.setBackground(new Color(220, 20, 60));
            deleteBtn.setForeground(Color.WHITE);
            add(updateBtn);
            add(deleteBtn);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor for buttons in the table
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton updateBtn;
        private JButton deleteBtn;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout());

            updateBtn = new JButton("Update");
            updateBtn.setBackground(new Color(255, 165, 0));
            updateBtn.setForeground(Color.WHITE);
            deleteBtn = new JButton("Delete");
            deleteBtn.setBackground(new Color(220, 20, 60));
            deleteBtn.setForeground(Color.WHITE);

            updateBtn.addActionListener(e -> updateRecord(selectedRow));
            deleteBtn.addActionListener(e -> deleteRecord(selectedRow));

            panel.add(updateBtn);
            panel.add(deleteBtn);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return panel;
        }

        public Object getCellEditorValue() {
            return "Edit/Delete";
        }

        private void updateRecord(int row) {
            int id = (int) model.getValueAt(row, 0);
            String name = (String) model.getValueAt(row, 2);
            String status = (String) model.getValueAt(row, 4);

            String newStatus = JOptionPane.showInputDialog(ViewAttendance.this,
                    "Update status for " + name + ":", status);

            if (newStatus != null && !newStatus.trim().isEmpty()) {
                try {
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/attendance_db", "root", "root1234");
                    String sql = "UPDATE attendance SET status = ? WHERE id = ?";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, newStatus);
                    pstmt.setInt(2, id);
                    pstmt.executeUpdate();

                    model.setValueAt(newStatus, row, 4);
                    JOptionPane.showMessageDialog(ViewAttendance.this, "Updated successfully!");

                    pstmt.close();
                    con.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ViewAttendance.this, "Update failed: " + e.getMessage());
                }
            }
        }

        private void deleteRecord(int row) {
            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(ViewAttendance.this,
                    "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/attendance_db", "root", "root1234");
                    String sql = "DELETE FROM attendance WHERE id = ?";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();

                    model.removeRow(row);
                    JOptionPane.showMessageDialog(ViewAttendance.this, "Deleted successfully!");

                    pstmt.close();
                    con.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ViewAttendance.this, "Delete failed: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAttendance().setVisible(true));
    }
}
