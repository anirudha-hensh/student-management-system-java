import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean clicked;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton("Edit/Delete");
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "Edit/Delete" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            int selectedRow = table.getSelectedRow();
            int id = (int) table.getValueAt(selectedRow, 0);

            int option = JOptionPane.showConfirmDialog(button, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                deleteRecord(id);
            }
        }
        clicked = false;
        return label;
    }

    private void deleteRecord(int id) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_db", "root", "root1234");
            PreparedStatement stmt = con.prepareStatement("DELETE FROM attendance WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(button, "Record deleted!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, "Error: " + e.getMessage());
        }
    }

    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
