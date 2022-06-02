package dam.jkutkut.eurovision.view.register;

import dam.jkutkut.eurovision.controller.Controller;
import dam.jkutkut.eurovision.view.EurovisionMenu;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase que representa el menú de registro de puntuaciones del jurado de Eurovisión.
 */
public class ViewRegister extends JFrame implements EurovisionMenu {

    public static final int NULL_INDEX = 0;
    public static final String NULL_VALUE = "-";

    private JLabel lblTitle;
    private JPanel jpForm;
    private JLabel lbl1p;
    private JComboBox cmb1p;
    private JLabel lbl2p;
    private JLabel lbl3p;
    private JLabel lbl4p;
    private JLabel lbl5p;
    private JLabel lbl6p;
    private JLabel lbl7p;
    private JLabel lbl8p;
    private JLabel lbl10p;
    private JLabel lbl12p;
    private JComboBox cmb2p;
    private JComboBox cmb3p;
    private JComboBox cmb4p;
    private JComboBox cmb5p;
    private JComboBox cmb6p;
    private JComboBox cmb7p;
    private JComboBox cmb8p;
    private JComboBox cmb10p;
    private JComboBox cmb12p;

    private JComboBox[] cmbs = {
            cmb1p, cmb2p, cmb3p, cmb4p, cmb5p, cmb6p, cmb7p, cmb8p, cmb10p, cmb12p
    };
    private JPanel jpBody;
    private JPanel jpBotonera;
    private JButton btnSave;
    private JButton btnClear;

    public ViewRegister() {
        initComponents();
    }

    public void initComponents() {
    }

    public void setController(Controller c) {
        btnSave.addActionListener(c);
        btnClear.addActionListener(c);
    }

    public void updateForm(ArrayList<String> f) {
        for (JComboBox cmb : cmbs)
            cmb.setModel(new DefaultComboBoxModel(f.toArray()));
        resetForm();
    }

    public void resetForm() {
        for (JComboBox cmb : cmbs)
            cmb.setSelectedIndex(NULL_INDEX);
    }

    public void setError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setInfo(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // GETTERS
    public JPanel getMenu() {
        return this.jpBody;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public String[] getForm() {
        String[] form = new String[cmbs.length];
        for (int i = 0; i < cmbs.length; i++)
            form[i] = (String) cmbs[i].getSelectedItem();
        return form;
    }
}
