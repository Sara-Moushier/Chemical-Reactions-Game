package chemicalReactions;
import java.sql.*;

public class MysqlCon {
	

	    public static Connection con;
	    public static Statement st;
	    public static ResultSet rs;
	    public static PreparedStatement ps;

	    public MysqlCon() {
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb", "root", "");
	            System.out.println("connected");
	        } catch (Exception e) {
	            System.out.println("error connectiong to data base" + e);
	        }
	    }
	

}
