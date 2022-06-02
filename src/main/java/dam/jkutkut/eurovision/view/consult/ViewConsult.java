package dam.jkutkut.eurovision.view.consult;

import dam.jkutkut.eurovision.controller.Controller;
import dam.jkutkut.eurovision.db.EurovisionDB;
import dam.jkutkut.eurovision.model.Participante;
import dam.jkutkut.eurovision.view.EurovisionMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Menú de consulta de datos de la aplicación Eurovisión.
 */
public class ViewConsult extends JFrame implements EurovisionMenu {

    private DefaultTableModel dtm;

    private JLabel lblTitle;
    private JPanel jpForm;
    private JLabel lblFormTitle;
    private JScrollPane jspTable;
    private JTable table;
    private JPanel jpBody;

    public ViewConsult() {
        initComponents();
    }

    public void initComponents() {
        // Table
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(dtm);
        for (int i = 0; i < EurovisionDB.TABLE_ATRIBUTES.length; i++)
            dtm.addColumn(EurovisionDB.TABLE_ATRIBUTES[i]);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // SETTERS
    public void setController(Controller c) {

    }

    public void setError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setInfo(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateTable(ArrayList<Participante> participantes) {
        dtm.setRowCount(0); // clear table
        if (participantes == null || participantes.size() == 0) {
            setError("No hay participantes añadidos en la base de datos.");
            return;
        }

        for (int i = 0; i < participantes.size(); i++) {
            dtm.addRow(participantes.get(i).toArrayWithPos(i));
        }
    }

    // GETTERS
    public JPanel getMenu() {
        return jpBody;
    }
}
