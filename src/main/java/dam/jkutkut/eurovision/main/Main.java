package dam.jkutkut.eurovision.main;

import com.formdev.flatlaf.FlatDarculaLaf;
import dam.jkutkut.eurovision.controller.Controller;
import dam.jkutkut.eurovision.db.EurovisionDB;
import dam.jkutkut.eurovision.view.consult.ViewConsult;
import dam.jkutkut.eurovision.view.register.ViewRegister;
import dam.jkutkut.eurovision.view.window.ViewWindow;

import javax.swing.*;
import java.awt.*;

/**
 * Clase ejecutable de la aplicación de Eurovisión.
 *
 * @author Jorge Re González
 */
public class Main {
    public static void main(String[] args) {
        // Cambia el estilo de la interfaz con la dependencia añadida
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            // Si fallara, no pasa nada. Avisa y muéstralo normal.
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewWindow viewWindow = new ViewWindow();
                ViewRegister viewRegistration = new ViewRegister();
                ViewConsult viewModification = new ViewConsult();

                EurovisionDB db = new EurovisionDB();

                Controller controller = new Controller(
                        db,
                        viewWindow,
                        viewRegistration,
                        viewModification
                );
                viewWindow.setController(controller);
                viewModification.setController(controller);
                viewRegistration.setController(controller);
                viewWindow.setVisible(true);
            }
        });
    }
}
