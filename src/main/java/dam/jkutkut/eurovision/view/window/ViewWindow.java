package dam.jkutkut.eurovision.view.window;

import dam.jkutkut.eurovision.controller.Controller;
import dam.jkutkut.eurovision.view.EurovisionMenu;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Clase reprentando la ventana de la aplicación.
 */
public class ViewWindow extends JFrame implements WindowListener, EurovisionMenu {
    private static final String TITLE = "Resultados Eurovisión";
    private JPanel jpBody;
    private JScrollPane jspMenuContainer;
    private JMenuItem jmiConsult;
    private JMenuItem jmiRegister;
    private JMenuItem jmiMainMenu;

    public ViewWindow() {
        setTitle(TITLE);
        setContentPane(jpBody);
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 600);
        addWindowListener(this);

        initComponents();
    }

    public void initComponents() {
    }

    public void setController(Controller c) {
        jmiConsult.addActionListener(c);
        jmiRegister.addActionListener(c);
        jmiMainMenu.addActionListener(c);
    }

    public void openMenu(JPanel menu) {
        jspMenuContainer.setViewportView(menu);
    }

    public void closeMenus() {
        jspMenuContainer.setViewportView(null);
        System.out.println("clear");
    }

    public void setError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setInfo(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // GETTERS
    public JMenuItem getRegisterJmi() {
        return jmiRegister;
    }

    public JMenuItem getConsultJmi() {
        return jmiConsult;
    }

    public JPanel getMenu() {
        return jpBody;
    }


    // Action listener methods

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Exit", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
