package chemicalReactions;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class Level3 extends Inorganic {


	@Override
	public void getData() {
		int min = 1;
		int max = 6;
		int random_int = (int) (Math.random() * (max - min + 1) + min);
		try {
			// reactants
			MysqlCon.st = MysqlCon.con.createStatement();
			MysqlCon.rs = MysqlCon.st.executeQuery("select reactants from equations where number=" + random_int);

			while (MysqlCon.rs.next()) {
				reactants = MysqlCon.rs.getString("reactants");

				String[] r = reactants.split(plus);
				int size = r.length;
				int cnt;
				for (int i = 0; i < size; i++) {
					String s;
					cnt = Character.getNumericValue(r[i].charAt(0));
					if (cnt == -1) {
						cnt = 1;
					}
					String STR = r[i];
					s = STR.substring(1, STR.length());
					Rmap.put(s, cnt);
				}
			}
			// products
			MysqlCon.rs = MysqlCon.st.executeQuery("select products from equations where number=" + random_int);

			while (MysqlCon.rs.next()) {
				products = MysqlCon.rs.getString("products");

				String r[] = products.split(plus);
				int size = r.length;
				int cnt;
				for (int i = 0; i < size; i++) {
					String s;
					cnt = Character.getNumericValue(r[i].charAt(0));
					if (cnt == -1) {
						cnt = 1;
					}
					String STR = r[i];
					s = STR.substring(1, STR.length());
					Pmap.put(s, cnt);
				}
			}
			// condition
			MysqlCon.rs = MysqlCon.st.executeQuery("select conditions from equations where number=" + random_int);
			while (MysqlCon.rs.next()) {
				condition = MysqlCon.rs.getString("conditions");
			}
		} catch (SQLException e) {
			System.out.println("error " + e);
		}

	}

	@Override
	public String showEquation() {

		String result = Pmap.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.joining("+"));
		return result;
	}

	public boolean checkAnswer(String[] Name, int[] balance) {

		boolean x = false;
		boolean y = false;
		boolean a = false;
		boolean b = false;
		boolean z = false;

		if (Rmap.containsKey(Name[0])) {
			x = true;

		}

		if (Rmap.containsKey(Name[1])) {
			y = true;

		}
		if (Rmap.containsValue(balance[0])) {
			a = true;

		}

		if (Rmap.containsValue(balance[1])) {
			b = true;

		}

		if (y && x && a && b) {

			z = true;

		}

		return z;
	}



	@Override
	public boolean checkAnswer() {
		return false;
	}

}
