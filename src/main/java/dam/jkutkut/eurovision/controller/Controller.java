package dam.jkutkut.eurovision.controller;

import dam.jkutkut.eurovision.db.EurovisionDB;
import dam.jkutkut.eurovision.model.Participante;
import dam.jkutkut.eurovision.view.consult.ViewConsult;
import dam.jkutkut.eurovision.view.register.ViewRegister;
import dam.jkutkut.eurovision.view.window.ViewWindow;
import dam.jkutkut.exception.InvalidDataException;
import dam.jkutkut.exception.SQLiteQueryException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Controlador de la interfaz de la aplicación.
 */
public class Controller implements ActionListener {
    // Base de datos
    private EurovisionDB db;

    // Ventanas
    private ViewWindow vWindow;
    private ViewRegister vRegister;
    private ViewConsult vConsult;

    public Controller(EurovisionDB db, ViewWindow vWindow, ViewRegister vRegister, ViewConsult vConsult) {
        this.db = db;
        this.vWindow = vWindow;
        this.vRegister = vRegister;
        this.vConsult = vConsult;

        initMenusWithDB();
    }

    /**
     * Actualiza la información de los menús de acuerdo a los datos de la base de datos.
     */
    private void initMenusWithDB() {
        try {
            ArrayList<String> paises = db.getPaises();
            paises.add(ViewRegister.NULL_INDEX, ViewRegister.NULL_VALUE); // Añade un valor extra que será el valor nulo.
            vRegister.updateForm(paises);
        }
        catch (SQLiteQueryException e) {
            vWindow.setError(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) e.getSource();

            // Window
            if (item == vWindow.getRegisterJmi())
                vWindow.openMenu(vRegister.getMenu());
            else if (item == vWindow.getConsultJmi())
                openConsult();
            else // Home
                vWindow.closeMenus();
        }
        else if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();

            // Register
            if (btn == vRegister.getBtnSave())
                registerPoints();
            else if (btn == vRegister.getBtnClear())
                vRegister.resetForm();
        }
    }

    /**
     * Verifica la validez del formulario de registro de puntuaciones.
     *
     * Actualiza la base de datos si los datos son correctos
     */
    private void registerPoints() {
        String[] form = vRegister.getForm();
        HashSet<String> yaIntroducidos = new HashSet<>();
        try {
            for (int i = 0; i < EurovisionDB.POINTS.length; i++) {
                if (form[i] == ViewRegister.NULL_VALUE) // Si campo no seleccionado
                    throw new InvalidDataException(String.format(
                        "No se han asignado %s punto%s a ningún participante",
                        (i > 0) ? "los " + EurovisionDB.POINTS[i] : "el",
                        (i > 0) ? "s" : ""
                    ));
                else if (yaIntroducidos.contains(form[i])) // Si campo con valor ya seleccionado
                    throw new InvalidDataException(String.format(
                       "No se le puede har %d punto%s a %s ya que ya se le ha dado puntos.",
                        EurovisionDB.POINTS[i],
                        (i > 0) ? "s" : "",
                        form[i]
                    ));
                yaIntroducidos.add(form[i]);
            }
            db.addPts(form);
            vRegister.setInfo("Actualización BBDD", "Los datos introducidos han sido guardados con exíto.");
            vRegister.resetForm();
        }
        catch (InvalidDataException e) {
            vRegister.setError(e.getMessage());
        }
    }

    /**
     * Abre el menú de consulta con los valores actuales de la base de datos.
     */
    private void openConsult() {
        vWindow.openMenu(vConsult.getMenu());
        try {
            ArrayList<Participante> participantes = db.getResultados();
            vConsult.updateTable(participantes);
        }
        catch (SQLiteQueryException e) {
            vConsult.setError(e.getMessage());
        }
    }
}
