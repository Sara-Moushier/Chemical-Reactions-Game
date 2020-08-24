
package chemicalReactions;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Capsule {

	private String CapsuleName;
	public void setCapsuleName(String CapsuleName) {  //encapsulation
		this.CapsuleName = CapsuleName;
	}

	private boolean checkCapsuleName(String capsuleName) {
		try {
			System.out.println("name");
			MysqlCon.st = MysqlCon.con.createStatement();
			MysqlCon.rs = MysqlCon.st.executeQuery("select CompoundName from capsules");
			while (MysqlCon.rs.next()) {
				if (CapsuleName.equals(MysqlCon.rs.getString("CompoundName"))) {
					//System.out.println("capsule exist");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void makeCapsule() {

		if (checkCapsuleName(CapsuleName)) {
			//If exists add number
			try {

				MysqlCon.st =  MysqlCon.con.createStatement();
				MysqlCon.rs = MysqlCon.st.executeQuery("SELECT NumberOfCapsules FROM capsules WHERE CompoundName = '" + CapsuleName + "'");
				int num = 0;
				if (MysqlCon.rs.next()) {
					num = MysqlCon.rs.getInt("NumberOfCapsules");
					num++;
				}
				Statement st2 = MysqlCon.con.createStatement();
				String q = "update capsules set NumberOfCapsules= '" + num + "' where CompoundName = '" + CapsuleName+ "'";
				//System.out.println(num);
				st2.executeUpdate(q);
				//System.out.println("Updated successfully......");
			}

			catch (Exception e) {
				System.out.println("fail to connect");
				e.printStackTrace();
			}

		} else {
			//If dosen't exist create new 
			String CapsuleQuery = "INSERT INTO `capsules`(`CompoundName`, `NumberOfCapsules`) VALUES (?,?)";  
			try {

				MysqlCon.ps =  MysqlCon.con.prepareStatement(CapsuleQuery);
				MysqlCon.ps.setString(1, CapsuleName);
				MysqlCon.ps.setString(2, "1");

				if (MysqlCon.ps.executeUpdate() != 0) {
					System.out.println("Your Capsule Has Been Created");
				}
			} catch (SQLException ex) {
				Logger.getLogger(Capsule.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

}
