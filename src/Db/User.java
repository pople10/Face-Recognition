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

public class User { 
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
	 
	public static Boolean Login(String CIN,String Password) throws SQLException, IOException {
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");
		String req="SELECT fname,lname,email FROM users WHERE cin='"+CIN+"' AND password='"+Password+"'";
		stm=con.prepareStatement(req);
		res=stm.executeQuery(req);
        
        String S="";
		while(res.next()) {
			S=CIN+"\n"+res.getString("fname")+" "+res.getString("lname")+"\n"+res.getString("email");
		}
		if(!S.equals("")) {
			File file = new File("src/User.txt");
	        if (!file.isFile()) file.createNewFile();
	        BufferedWriter w=new BufferedWriter(new FileWriter(file));
	        w.write(S);
	        w.close();
	        
			return true;
		}
		return false;
	}
	public static Boolean SignUp(String CIN,String fname,String Lname,String email,String password) throws SQLException, IOException {
		
		connectionDataBase("jdbc:mysql://localhost:3306/kuzulardet","root","");
		stm=con.createStatement();
		String req="insert into users values('"+CIN+"','"+fname+"','"+Lname+"','"+email+"','"+password+"')";
		stm.executeUpdate(req);
		stm.close();
		return Login(CIN,password);	
	}
	
	
	
}
