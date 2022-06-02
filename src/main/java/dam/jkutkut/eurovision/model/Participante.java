package dam.jkutkut.eurovision.model;

/**
 * Clase que representa las inserciones de la base de datos de Eurovisión 2022.
 */
public class Participante {

    private String pais;
    private String interprete;
    private String cancion;
    private int ptsJurado;
    private int ptsPublico;
    private int pts;

    public Participante(String pais, String interprete, String cancion, int ptsJurado, int ptsPublico, int pts) {
        this.pais = pais;
        this.interprete = interprete;
        this.cancion = cancion;
        this.ptsJurado = ptsJurado;
        this.ptsPublico = ptsPublico;
        this.pts = pts;
    }

    // GETTERS
    public String getPais() {
        return pais;
    }

    public String getInterprete() {
        return interprete;
    }

    public String getCancion() {
        return cancion;
    }

    public int getPtsJurado() {
        return ptsJurado;
    }

    public int getPtsPublico() {
        return ptsPublico;
    }

    public int getPts() {
        return pts;
    }

    /**
     * Transforma el participante en un array con la posición dada, los datos básicos y los puntos totales.
     * @param position Entero que representa la posición del participante.
     * @return Array de objetos con la información necesaria.
     */
    public Object[] toArrayWithPos(int position) {
        return new Object[]{
                position,
                getCancion(),
                getInterprete(),
                getPais(),
                getPts()
        };
    }
}
