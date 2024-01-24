import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqLite implements AutoCloseable {
    private final Connection _dbConnection;

    public SqLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        _dbConnection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
//        System.out.println("DB connected");
        try {
            ExecuteWithoutResult("select ID from Calculations");
        } catch (Exception ex) {
            ExecuteWithoutResult("CREATE TABLE IF NOT EXISTS Calculations ('ID' INTEGER  PRIMARY KEY AUTOINCREMENT , 'first' INTEGER, 'second' INTEGER, 'sign' INTEGER, result text);");
            ExecuteWithoutResult("CREATE TABLE IF NOT EXISTS Comments ('ID' INTEGER PRIMARY KEY AUTOINCREMENT, 'ID_Calculations' INTEGER, comment  text);");
        }
    }

    private void ExecuteWithoutResult(String sqlQuery) throws SQLException {
        var statement = _dbConnection.createStatement();
        statement.execute(sqlQuery);

//        System.out.println("SQL command executed");
    }

    public void WriteDBCalculations(int first, int sing, int second, String result) throws SQLException {

        String sqlQuery = "INSERT INTO 'Calculations' ('first', 'second' , 'sign', 'Result') VALUES (" + first + "," +
                second + ", " + sing + ", '" + result + "'); ";
        ExecuteWithoutResult(sqlQuery);
    }

    public void WriteDBComments(int ID_Calculations, String textComment) throws SQLException {

        String sqlQuery = "INSERT INTO 'Comments' ('ID_Calculations', 'comment') VALUES (" + ID_Calculations +
                ", '" + textComment + "'); ";
        ExecuteWithoutResult(sqlQuery);
    }

    private String getSqlQuery(int ID, int kodQuery) {
        return switch (kodQuery) {
            case 0 -> "select * from Calculations  WHERE ID = " + ID;
            case 1 -> "select comment from Comments  WHERE ID_Calculations = " + ID;
            case 2 -> "select ID from Calculations";
            default -> "";
        };
    }

    public ResultSet ReadDB(int ID, int KodQuery) throws SQLException {
        var statement = _dbConnection.createStatement();
        return statement.executeQuery(getSqlQuery(ID, KodQuery));
    }

    @Override
    public void close() throws Exception {
        _dbConnection.close();
//        System.out.println("DB disconnected");
    }
}