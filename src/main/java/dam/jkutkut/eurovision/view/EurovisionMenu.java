package dam.jkutkut.eurovision.view;

import dam.jkutkut.eurovision.controller.Controller;

import javax.swing.*;

public interface EurovisionMenu {
    /**
     * Inicializa los componentes de la interfaz.
     */
    void initComponents();

    /**
     * Une los componentes con el controlador dato
     * @param c Controlador de la aplicación.
     */
    void setController(Controller c);

    /**
     * Lanza un error al usuario.
     * @param msg Mensaje de error.
     */
    void setError(String msg);

    /**
     * Lanza un mensaje de información al usuario.
     * @param title Título del mensaje.
     * @param msg Mensaje.
     */
    void setInfo(String title, String msg);

    /**
     * @return Jpanel con la interfaz.
     */
    JPanel getMenu();
}
