package chemicalReactions;

import java.sql.SQLException;
import java.util.Vector;

public class Level2 extends Inorganic {  //inheritance
	
     private Vector<Integer> vec=new Vector<Integer>();  //this vector is used to guarantee that equations appear randomly one time only
     private  int min = 4;
  private  int max = 7;
  private int random_int ;
	
    @Override
    public boolean checkAnswer() {
    	return  map.equals(Rmap);
    }

    @Override
    public void getData() {
       
      
        random_int = (int) (Math.random() * (max - min + 1) + min);
        while(vec.contains(random_int)) {
        	random_int=(int) (Math.random() * (max - min + 1) + min);
        }
               try {
            //reactants 
            MysqlCon.st = MysqlCon.con.createStatement();
            MysqlCon.rs = MysqlCon.st.executeQuery("select reactants from equations where number=" + random_int);
            vec.add(random_int);

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
            MysqlCon.rs=MysqlCon.st.executeQuery("select names from equations where number=" + random_int);
            while (MysqlCon.rs.next()) {
                productsNames = MysqlCon.rs.getString("names");
            }
            //products
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
            //condition
            MysqlCon.rs = MysqlCon.st.executeQuery("select conditions from equations where number=" + random_int);
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
