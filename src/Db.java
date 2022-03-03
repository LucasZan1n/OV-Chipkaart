import java.sql.*;

public class Db {
    private Connection connection;

    public Db() {
        this.connect();
    }

    private void connect() {
        String connectionUrl = "jdbc:postgresql://localhost/ovchip?user=postgres&password=password";

        try {
            this.connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet execute(String query) {
        try {
            this.connection.prepareStatement(query);
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException exception) {
            System.out.println(exception);
            return null;
        }

    }

    public void disconnect() {
        try {
            connection.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}
