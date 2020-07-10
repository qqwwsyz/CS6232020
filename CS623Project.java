package cs623project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CS623Project {

	public static void main(String args[]) throws SQLException, ClassNotFoundException {

		// Load the MySQL driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Connect to the database
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS387", "root", "cs387623");

		// For atomicity
		conn.setAutoCommit(false);

		// For isolation
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		Statement stmt = null;
		try {
			// create statement object
			stmt = conn.createStatement();

			// atomicity.
			// Change 'd1' to 'ddq' in Depot
			stmt.executeUpdate("UPDATE `CS387`.`Depot` SET `dep_id` = 'dd1' WHERE (`dep_id` = 'd1')");
			// Change 'd1' to 'dd1' in Stock
			stmt.executeUpdate("UPDATE `CS387`.`Stock` SET `dep_id` = 'dd1' WHERE (`dep_id` = 'd1')");
		} catch (SQLException e) {
			System.out.println("catch Exception");
			// For atomicity
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		}
		conn.commit();
		stmt.close();
		conn.close();
	}
}