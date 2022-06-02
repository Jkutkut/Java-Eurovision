package dam.jkutkut.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase con la lógica básica para establecer la conexión con una base de datos SQLite.
 */
public class AccessDB {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String URL_PREFIX = "jdbc:sqlite:";

    // Basic SQL sentences
    public static final String GET_VALUE = "SELECT %s FROM %s WHERE %s = ?";

    private String url;

    public AccessDB(String db) {
        url = URL_PREFIX + db;
    }

    /**
     * Crea una conexión con la base de datos. Si algún dato no es válido, lanza excepciones con los errores.
     * @return Objeto con la conexión.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(url);
    }
}
