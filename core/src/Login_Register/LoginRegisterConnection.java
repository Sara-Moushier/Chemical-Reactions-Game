package Login_Register;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import chemicalReactions.MysqlCon;

public class LoginRegisterConnection {

	protected static String user;
	protected Stage stage;
	protected Skin skin;
	private static int score;

	public LoginRegisterConnection() {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.input.setInputProcessor(stage);
	}

	public boolean LoginCheck(String inpName) {


		try {
			MysqlCon.st = MysqlCon.con.createStatement();
			MysqlCon.rs = MysqlCon.st.executeQuery("select username from users");

			while (MysqlCon.rs.next()) {
				LoginRegisterConnection.user = MysqlCon.rs.getString("username");
				if (inpName.equals(user)) {
					setScoreInDb(inpName);
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("error " + e);
		}
		return false;// go to registerState
	}

	public boolean RegisterUser(String inpName) {
		if (LoginCheck(inpName))
			return false;
		else {
			LoginRegisterConnection.user=inpName;
			String registerUserQuery = "INSERT INTO `users`( `username`) VALUES (?)";

			try {
				MysqlCon.ps = MysqlCon.con.prepareStatement(registerUserQuery);
				MysqlCon.ps.setString(1, inpName);
				if (MysqlCon.ps.executeUpdate() != 0) {
					System.out.println("Added username");
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
			return true;
		}
	}

	public static void updateUserScore() {
		try {

			MysqlCon.st = MysqlCon.con.createStatement();
			String updateStatment = "update users set score= '" + LoginRegisterConnection.getScore() + "' where username = '" + LoginRegisterConnection.user + "'";
			MysqlCon.st.executeUpdate(updateStatment);
			System.out.println("Updated successfully......");
		}
		catch (SQLException e) {
			System.out.println("error " + e);
		}
	}
	public void setScoreInDb(String inpName)
	{
		
		try {
			Statement st2=MysqlCon.con.createStatement();
			ResultSet rs2 = st2.executeQuery("SELECT score FROM users WHERE username = '" + inpName + "'");
			if (rs2.next()) {
				LoginRegisterConnection.score =rs2.getInt("score");
			}
				
				System.out.println(score);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		LoginRegisterConnection.score = score;
	}

}
