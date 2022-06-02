package dam.jkutkut.eurovision.db;

import dam.jkutkut.db.AccessDB;
import dam.jkutkut.db.SQLiteQuery;
import dam.jkutkut.eurovision.model.Participante;
import dam.jkutkut.exception.InvalidDataException;
import dam.jkutkut.exception.SQLiteQueryException;

import java.util.ArrayList;

/**
 * Implementación específica de la base de datos de la aplicación Eurovisión.
 */
public class EurovisionDB extends AccessDB {
    private static final String DB_LOCATION = "db/EUROVISION_22.db";

    private static final String TABLE_NAME = "PARTICIPANTES";

    public static final String PAIS = "PAIS";
    public static final String INTERPRETE = "INTERPRETE";
    public static final String CANCION = "CANCION";
    public static final String PTS_JURADO = "PUNTOS_JURADO";
    public static final String PTS_PUBLICO = "PUNTOS_PUBLICO";
    public static final String PTS = "PUNTOS";

    private static final String COLUMN_ID = PAIS;

    public static final String[] TABLE_ATRIBUTES = {
        "TOP", "CANCIÓN", "INTÉRPRETE", "PAÍS", "PTOS"
    };

    public static final int[] POINTS = {
        1, 2, 3, 4, 5, 6, 7, 8, 10, 12
    };

    public EurovisionDB() {
        super(DB_LOCATION);
    }

    /**
     * Obtiene los resultados actuales de los participantes ordenados por puntuación. Si tienen la misma, por país.
     * @return Arraylist de instancias Participante con los datos actuales de la base de datos o null si no hay datos.
     * @throws SQLiteQueryException
     * @throws InvalidDataException
     */
    public ArrayList<Participante> getResultados() throws SQLiteQueryException, InvalidDataException {
        String query = String.format(
            "SELECT %s, %s, %s, %s, %s, %s FROM %s ORDER BY %s DESC;",
            PAIS,
            INTERPRETE,
            CANCION,
            PTS_JURADO,
            PTS_PUBLICO,
            PTS,
            TABLE_NAME,
            PTS
        );

        ArrayList<Object[]> data = SQLiteQuery.get(
                this,
                6, // Los 6 campos pedidos
                query
        );

        if (data.size() == 0)
            return null;
        return sqlite2model(data);
    }

    /**
     * Obtiene el nombre de todos los países en la base de datos.
     * @return Arraylist con los nombres de los países.
     * @throws SQLiteQueryException
     * @throws InvalidDataException
     */
    public ArrayList<String> getPaises() throws SQLiteQueryException, InvalidDataException {
        String query = String.format(
                "SELECT %s FROM %s;",
                PAIS,
                TABLE_NAME
        );

        ArrayList<Object[]> data = SQLiteQuery.get(
                this,
                1,
                query
        );

        if (data.size() == 0)
            return null;
        ArrayList<String> resultado = new ArrayList<>();
        for (Object[] r : data)
            resultado.add((String) r[0]);
        return resultado;
    }

    /**
     * Añade puntos a los países dados desde el jurado de manera que el primero recibe 1pto y el último 12ptos.
     * @param paises Array con los nombres de los 10 países.
     * @return Resultado de realizar la última actualización en la base de datos.
     * @throws SQLiteQueryException
     * @throws InvalidDataException
     */
    public int addPts(String[] paises) throws SQLiteQueryException, InvalidDataException {
        final String[] fieldsQuery = {
                String.format( // Query para obtener los puntos del jurado de un país.
                        GET_VALUE,
                        PTS_JURADO,
                        TABLE_NAME,
                        COLUMN_ID
                ),
                String.format( // Query para obtener los puntos totales de un país.
                        GET_VALUE,
                        PTS,
                        TABLE_NAME,
                        COLUMN_ID
                )
        };
        final String updateQuery = String.format( // Query genérica para realizar el update.
                "UPDATE %s SET %%s = (%%s) + ? WHERE %%s = ?;",
                TABLE_NAME
        );

        final String[] FIELDS = { // Campos en los que añadir la puntuación en cada país.
            PTS_JURADO,
            PTS
        };

        int result = -1;

        String query;

        for (int i = 0, j; i < paises.length; i++) {
            // Nota: Se podría haber hecho con menos updates. Sin embargo, creo que juntar sentencias va
            // dar a la sentencia una complejidad innecesaria en este caso.
            for (j = 0; j < FIELDS.length; j++) {
                query = String.format( // Query específica para un campo.
                        updateQuery,
                        FIELDS[j],
                        fieldsQuery[j],
                        COLUMN_ID
                );
                System.out.println(query); // UPDATE T SET XX = (SELECT XX FORM T WHERE PAIS = ?) + ? WHERE PAIS = ?;
                result = SQLiteQuery.execute(
                    this,
                    query,
                    paises[i],
                    POINTS[i],
                    paises[i]
                );
            }
        }
        return result;
    }

    /**
     * Transforma los datos obtenidos de la base de datos a una lista de objetos de tipo Participante.
     * @param data Datos obtenidos de la base de datos.
     * @return ArrayList con los datos transformados.
     */
    private static ArrayList<Participante> sqlite2model(ArrayList<Object[]> data) {
        ArrayList<Participante> participantes = new ArrayList<>();
        Participante p;
        for (Object[] row : data) {
            p = new Participante(
                    (String) row[0],
                    (String) row[1],
                    (String) row[2],
                    (int) row[3],
                    (int) row[4],
                    (int) row[5]
            );
            participantes.add(p);
        }
        return participantes;
    }
}
