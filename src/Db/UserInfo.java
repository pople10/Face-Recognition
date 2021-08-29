package Db;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserInfo {
	private static Connection con;
	private static Statement stm;
	private static ResultSet res;

	public static Connection connectionDataBase(String url,String user,String Pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,Pass);
			
			return con;
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;		
		}
	}
	 
	public static void InsertUserInfo(int id,String CIN,String fname,String lname,String job,String Nationality,String Adress,String Gender,int age,double h) throws SQLException, IOException {
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");
		stm=con.createStatement();
		String req="insert into usersinf values('"+id+"','"+CIN+"','"+fname+"','"+lname+"','"+Adress+"','"+job+"','"+Gender+"','"+Nationality+"','"+age+"','"+h+"')";
		stm.executeUpdate(req);
		stm.close();
	}

	public static void Update(String CIN,String fname,String lname,String job,String Nationality,String Adress,int age,double h) throws SQLException {
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");	
		stm=con.createStatement();
			String req="update usersinf set fname='"+fname+"',lname='"+lname+"',adress='"+Adress+"',job='"+job+"',	nationality	='"+Nationality+"',age='"+age+"',Height='"+h+"' where cin='"+CIN+"'";
			stm.executeUpdate(req);			
			stm.close();

	}

	public static String getCIN(int id) throws SQLException {
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");
		
		String req="SELECT cin FROM usersinf  where id='"+id+"'";
		stm=con.prepareStatement(req);
		res=stm.executeQuery(req);
		ArrayList<String> a=new ArrayList<String>();
		while(res.next()) {
			
			return res.getString("cin");
		}
		
		stm.close();
		return null;
	}
	
	public static ArrayList getIn(String CIN) throws SQLException {
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");

		String req="SELECT * FROM usersinf  where cin='"+CIN+"'";
		stm=con.prepareStatement(req);
		res=stm.executeQuery(req);
		ArrayList<String> a=new ArrayList<String>();
		while(res.next()) {
			a.add(res.getString("fname"));
			a.add(res.getString("lname"));
			a.add(res.getString("Adress"));
			a.add(res.getString("job"));
			a.add(res.getString("nationality"));
			a.add(res.getString("age"));
			a.add(res.getString("Height"));	
			a.add(res.getString("geneder"));	
		}
		
		stm.close();
		return a;
	}

}
