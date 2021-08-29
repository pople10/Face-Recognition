package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbFaceDet {
	private static Connection con;
	private static Statement stm;
	private static ResultSet res;

	public static Connection connectionDataBase(String url,String user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,"");
			
			System.out.println("connexion est reussite");
			return con;
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;		
		}
	}
	public static boolean Login(String Email,String Password) {
		System.out.print(connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root"));

		return false;
	}
}
