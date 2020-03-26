import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	private String Host;
	private String Database;
	private String Username;
	private String Password;
	private String console_prefix = "MyPlugin"
	private Connection connection;

	/**
	 * @param host     The IP or domain of the MySQL Server
	 * @param database The database you would like to use
	 * @param username The login username
	 * @param password The login password
	 */
	public MySQL(String host, String database, String username, String password) {
		this.Host = host;
		this.Database = database;
		this.Username = username;
		this.Password = password;

	}

	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException error) {
			System.out.println(console_prefix + "Database connected !");
			error.printStackTrace();
		}
		String url = "jdbc:mysql://" + this.Host + ":3306/" + this.Database
				+ "?verifyServerCertificate=false&useSSL=true";
		try {
			this.connection = DriverManager.getConnection(url, this.Username, this.Password);
		} catch (SQLException error) {
			System.out.println(console_prefix + "Error when connecting to database :");
			error.printStackTrace();
		}
	}

	public void Disconnect() {
		try {
			if (!this.connection.isClosed() && this.connection != null) {
				this.connection.close();
			} else {
			}
		} catch (SQLException error) {
			System.out.println(console_prefix + "Error when disabling MySQL :");
			error.printStackTrace();
		}
	}

	public boolean isConnected() {
		try {
			if (this.connection.isClosed()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException error) {
			System.out.println(console_prefix + "MySQL Error :");
			error.printStackTrace();
		}
		return false;
	}

	/**
	 * @param command The query you would like to execute
	 */
	public ResultSet GetResult(String command) {
		try {
			if (this.connection.isClosed()) {
				this.Connect();
			}

			Statement st = this.connection.createStatement();
			st.executeQuery(command);
			ResultSet rs = st.getResultSet();
			return rs;

		} catch (SQLException error) {
			System.out.println(console_prefix + "MySQL database error:");
			error.printStackTrace();
		}
		return null;
	}

	/**
	 * @param command The query you would like to execute
	 */
	public void ExecuteCommand(String command) {
		try {
			if (this.connection.isClosed()) {
				this.Connect();
			}
			Statement st = this.connection.createStatement();
			st.executeUpdate(command);
		} catch (SQLException error) {
			System.out.println(console_prefix + "MySQL database error:");
			error.printStackTrace();
		}

	}
}
