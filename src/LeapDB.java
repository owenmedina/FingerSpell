import java.sql.*;
import java.sql.Date;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LeapDB {
    private static Connection con;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
            con = getConnection();
	}

	//constructor
	public LeapDB() throws Exception{
		con = getConnection();
	}
	
	//confirmed
	public static Connection getConnection() throws Exception{ //connect to mysql db
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/fingerspell_db?useLegacyDatetimeCode=false&serverTimezone=UTC"; // "//localhost OR ip add/port/dbname" //where the db is located
			
			//establish connection
			String user = "root" ;
			String pw = "brianowen" ;
			Connection conn = DriverManager.getConnection(url, user, pw);
			System.out.println("Connected successfully."); //tester
			return conn;
		}catch(Exception e) {
			System.out.println("Error in getConnection: " + e); //in case of any errors
		}
		return null;
	}
	
	public static void insertSample(String name, int[] distances) throws Exception{
		
		int tTip_tProx = distances[0];
		int tTip_iTip  = distances[1];
		int tTip_iProx  = distances[2];
		int tTip_mTip = distances[3];
		int tTip_mProx = distances[4];
		int tTip_rTip = distances[5];
		int tTip_rProx = distances[6];
		int tTip_pTip = distances[7];
		int tTip_pProx = distances[8];
		int tTip_pc = distances[9];
		int tProx_iTip = distances[10];
		int tProx_iProx = distances[11];
		int tProx_mTip = distances[12];
		int tProx_mProx = distances[13];
		int tProx_rTip = distances[14];
		int tProx_rProx = distances[15];
		int tProx_pTip = distances[16];
		int tProx_pProx = distances[17];
		int tProx_pc = distances[18];
		int iTip_iProx = distances[19];
		int iTip_mTip = distances[20];
		int iTip_mProx = distances[21];
		int iTip_rTip = distances[22];
		int iTip_rProx = distances[23];
		int iTip_pTip = distances[24];
		int iTip_pProx = distances[25];
		int iTip_pc = distances[26];
		int iProx_mTip = distances[27];
		int iProx_mProx = distances[28];
		int iProx_rTip = distances[29];
		int iProx_rProx = distances[30];
		int iProx_pTip = distances[31];
		int iProx_pProx = distances[32];
		int iProx_pc = distances[33];
		int mTip_mProx = distances[34];
		int mTip_rTip = distances[35];
		int mTip_rProx = distances[36];
		int mTip_pTip = distances[37];
		int mTip_pProx = distances[38];
		int mTip_pc = distances[39];
		int mProx_rTip = distances[40];
		int mProx_rProx = distances[41];
		int mProx_pTip = distances[42];
		int mProx_pProx = distances[43];
		int mProx_pc = distances[44];
		int rTip_rProx = distances[45];
		int rTip_pTip = distances[46];
		int rTip_pProx = distances[47];
		int rTip_pc = distances[48];
		int rProx_pTip = distances[49];
		int rProx_pProx = distances[50];
		int rProx_pc = distances[51];
		int pTip_pProx = distances[52];
		int pTip_pc = distances[53];
		int pProx_pc = distances[54];
		
		
		// inserts a sample of a gesture in the gestures table
		try{
			// first inner array is for the x, y and z of the tip of the finger
			// second inner array is for the x, y and z of the proximal joint of the finger
			
			PreparedStatement newGesture = con.prepareStatement("INSERT INTO gestures VALUES('" + name +
						+ tTip_tProx + "', '" + tTip_iTip + "', '" + tTip_iProx + "', '" + tTip_mTip + "', '" + tTip_mProx + "', '" + tTip_rTip + "', '"
						+ tTip_rProx + "', '" + tTip_pTip + "', '" + tTip_pProx + "', '" + tTip_pc + "', '" + tProx_iTip + "', '" + tProx_iProx + "', '"
						+ tProx_mTip + "', '" + tProx_mProx + "', '" + tProx_rTip + "', '" + tProx_rProx + "', '" + tProx_pTip + "', '" + tProx_pProx + "', '"
						+ tProx_pc + "', '" + iTip_iProx + "', '" + iTip_mTip + "', '" + iTip_mProx + "', '" + iTip_rTip + "', '" + iTip_rProx + "', '"
						+ iTip_pTip + "', '" + iTip_pProx + "', '" + iTip_pc + "', '" + iProx_mTip + "', '" + iProx_mProx + "', '" + iProx_rTip + "', '"
						+ iProx_rProx + "', '" + iProx_pTip + "', '" + iProx_pProx + "', '" + iProx_pc + "', '" + mTip_mProx + "', '" + mTip_rTip + "', '"
						+ mTip_rProx + "', '" + mTip_pTip + "', '" + mTip_pProx + "', '" + mTip_pc + "', '" + mProx_rTip + "', '" + mProx_rProx + "', '"
						+ mProx_pTip + "', '" + mProx_pProx + "', '" + mProx_pc + "', '" + rTip_rProx + "', '" + rTip_pTip + "', '" + rTip_pProx + "', '"
						+ rTip_pc + "', '" + rProx_pTip + "', '" + rProx_pProx + "', '" + rProx_pc + "', '" + pTip_pProx + "', '" + pTip_pc + "', '" + pProx_pc + "')"
					);
			newGesture.executeUpdate(); //execute the insert
		}catch(Exception e) {
			System.out.println("Error in insertSample " + e); //in case of any errors;
		}
		finally{
			System.out.println("Insert to gestures completed"); //tester;
		}
	}
	
        
}
