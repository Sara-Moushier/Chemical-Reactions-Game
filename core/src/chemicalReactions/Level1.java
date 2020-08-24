package chemicalReactions;

import java.sql.SQLException;
/**
 *
 * @author Nermin
 */
public class Level1 extends Inorganic {       

    public static int equationCounter=1;

    @Override
    public boolean checkAnswer() {
    	 checkEl=map.keySet().equals( Rmap.keySet() );
    	 
        map.clear();
        return checkEl;
    }

    @Override
    //randomly select an equation from database according to their number boundaries
    //start with reactants column
    //split at +
    //convert the number of molecules to int
    //put each reactant at the map with its no. of molecules
    // do the same with products.
    //get the condition from db as well
    public void getData() {

    	
        // System.out.println(random_int);
        try {
            //reactants 
        	MysqlCon.st = MysqlCon.con.createStatement();
        	MysqlCon.rs = MysqlCon.st.executeQuery("select reactants from equations where number=" + equationCounter);

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
          //names
            MysqlCon.rs=MysqlCon.st.executeQuery("select names from equations where number=" + equationCounter);
            while (MysqlCon.rs.next()) {
                productsNames = MysqlCon.rs.getString("names");
            }
            //products
            MysqlCon.rs = MysqlCon.st.executeQuery("select products from equations where number=" + equationCounter);

            while (MysqlCon.rs.next()) 
            {
                products = MysqlCon.rs.getString("products");

                String r[] = products.split(plus);
                int size = r.length;
                int cnt;
                for (int i = 0; i < size; i++) 
                {
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
            //condition
            MysqlCon.rs = MysqlCon.st.executeQuery("select conditions from equations where number=" + equationCounter);
            while (MysqlCon.rs.next()) {
                condition = MysqlCon.rs.getString("conditions");
            }
            //System.out.println("cond"+condition);
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
        

    }

	@Override
	public boolean checkAnswer(String[] Name, int[] balance) {
		// TODO Auto-generated method stub
		return false;
	}
    

}
