
package chemicalReactions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Inorganic {           //Abstraction

	protected MysqlCon connect;
	protected int score;
	protected String condition;
	protected boolean checkEl = false;
	protected boolean checkCond = false;
	protected Map<String, Integer> map = new LinkedHashMap<>(); //store user input
	protected String eqn;
    protected String productsNames;
	protected Map<String, Integer> Rmap = new LinkedHashMap<>(); //store reactants from db
	protected Map<String, Integer> Pmap = new LinkedHashMap<>();//store products from the db
	protected String reactants;
	protected String products;
	protected final String plus = "\\+";
	
	public void element(String s) {

		if (map.containsKey(s)) {
			int count = map.get(s);
			System.out.println(count + s);
			map.remove(s);
			map.put(s, count + 1);
		} else {
			map.put(s, 1);
		}
	}
	
	public void setMaps() {
		Rmap.clear();
		Pmap.clear();
	}
	public void getCheck() {
		if (checkEl & checkCond) {
			System.out.println("right");
		} else {
			System.out.println("wrong");
		}
	}
	public Map<String,Integer> getUserMap(){
		return map;
	}

	public void displayUserMap() {
		System.out.println(map);
	}
	public String showEquation() {
        String result = Rmap.entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .collect(Collectors.joining("+"));

        eqn = result;
        eqn += "--->";

        result = Pmap.entrySet()
                .stream()
                .map(entry -> entry.getKey())
                .collect(Collectors.joining("+"));
        eqn += result;
            return eqn;
    }

  
	public void checkCondition(String c) {
		if (c.equals(condition)) {
			checkCond = true;
		}
		System.out.println(checkCond);
	}
	public String getProductsNames() {
		return productsNames;
	}


	abstract public void getData();

	abstract public boolean checkAnswer();

    abstract public boolean checkAnswer(String[] Name,int []balance);    //Overloading

	
}
