package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Game.GameDemo;
import Game.Timer;
import Login_Register.LoginRegisterConnection;
import chemicalReactions.Level3;

public class PlayState3 extends State {

	private SpriteBatch sb;
    private	BitmapFont font;
	private String eqn;
	private Texture bg, reactant1, reactant2,balance1,balance2;
    private	boolean[] isSelected = new boolean[] { false, false,false,false};
	private String[] check = new String[] { "", "" };
	private int[] balance = new int[] { 0, 0 };
    private	boolean x;
	private BitmapFont timePassed;
	private final static long timeOfGame=50000;
	private String str;
	private static Timer timer;


	public PlayState3(GameStateManager gsm, SpriteBatch sb) {
		super(gsm);
		x = false;
		bg = new Texture("Level3Bg.jpeg");
		game = new Level3();
		game.getData();
		eqn = game.showEquation();
		timePassed=new BitmapFont();
       startTimer();
	}
	public static void startTimer() {
		timer = new Timer(timeOfGame);
		timer.start();
	}
	@Override
	protected void handleInput()

	{
		if (Gdx.input.justTouched()) {
			// System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
			String temp = getClickedTexture();

			if (!temp.isEmpty()) {
				sound.play(0.7f);
				if (temp == "1.png") {
					if (balance[0] == 0) {

						balance[0] = 1;
					} else if (balance[1] == 0 && balance[0] != 0) {

						balance[1] = 1;
					}

				}
				if (temp == "2.png") {
					if (balance[0] == 0) {
						balance[0] = 2;
					} else if (balance[1] == 0 && balance[0] != 0) {

						balance[1] = 2;
					}

				}
				if (temp == "3.png") {
					if (balance[0] == 0) {
						balance[0] = 3;
					} else if (balance[1] == 0 && balance[0] != 0) {

						balance[1] = 3;
					}

				}

				else if (temp == "Exit") {
					gsm.pop();// play
					gsm.pop();// loading
				} else if (temp == "Reset") {
					game.getUserMap().clear();
					x=false;
					isSelected[0] = isSelected[1] = isSelected[2] = isSelected[3]= false;
				}
				if (temp == "CheckBtn") {

					    game.checkAnswer(check, balance);
					    if (game.checkAnswer(check, balance)==true) {
						System.out.println("this is the chek " + game.checkAnswer(check, balance));
						x = true ;
						game.setMaps();

					}
					    else
					    {
					    	game.getUserMap().clear();
							x=false;
							isSelected[0] = isSelected[1] = isSelected[2] = isSelected[3]= false;
					    }
				}
				if ((!isSelected[0]) && ((temp=="1.png" )||(temp=="2.png") ||(temp=="3.png"))) {
					balance1 = new Texture(temp);
					isSelected[0] = true;
				} else if (isSelected[0] && !isSelected[1]&& ((temp!="1.png") &&(temp!="2.png") &&(temp!="3.png") )) {
					reactant1 = new Texture(temp);
					isSelected[1] = true;

				}
				else if ((isSelected[0] && isSelected[1] && !isSelected[2] )&& ((temp=="1.png") ||(temp=="2.png") ||(temp=="3.png") )) {
					balance2 = new Texture(temp);
					isSelected[2] = true;
				} else if (isSelected[0] && isSelected[1] && isSelected[2] && !isSelected[3]&& ((temp!="1.png") &&(temp!="2.png") &&(temp!="3.png") )) {
					reactant2 = new Texture(temp);
					isSelected[3] = true;

				}
				if (check[0].isEmpty()&& ((temp!="1.png") &&(temp!="2.png") &&(temp!="3.png"))) {

					temp = temp.replace(".png", "");
					System.out.println(temp);
					check[0] = temp;
				} else if (!check[0].isEmpty() && check[1].isEmpty()&& ((temp!="1.png") &&(temp!="2.png") &&(temp!="3.png"))) {
					temp = temp.replace(".png", "");
					check[1] = temp;

					System.out.println(temp);
					temp = "";
				}
			}

		}

	}


	@Override
	public void update(float dt) {
		try {
			handleInput();
			
		} catch (Exception ex) {

			System.out.println(ex.getMessage());
		}

	}

	public void render(SpriteBatch sb) {
		// displayedEquation = game.showEquation();
		sb.begin();
		sb.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA,
				GL20.GL_DST_ALPHA);
		sb.draw(bg, 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);

		font = new BitmapFont();
		font.getData().setScale(2, 2);
		font.draw(sb, "Choose the balance then choose \n" + "the reactants to make this product :\n" + eqn, 45, 500);
		font.draw(sb, "Score : " + LoginRegisterConnection.getScore(), 750, 540);

		if (isSelected[0]) {
			sb.draw(balance1, 50, 253, 40, 50);
			if (isSelected[1]) {
				sb.draw(reactant1, 90, 253);
				if (isSelected[2]) {
					sb.draw(balance2, 225, 253, 40, 50);
					if (isSelected[3]) {
						sb.draw(reactant2, 260, 253);

					}
				}
			}

		}
		str = "" + ((timer.getRunTime() / 1000000000) / 60) + ":" + ((timer.getRunTime() / 1000000000) % 60);
		if (timer.finished())
			str = "Time Finished!!";
		timePassed.draw(sb, str, 43, 534);
		if (str.equals("Time Finished!!"))
			gsm.push(new LoseState(gsm, sb,"PlayState3"));

		else if (x == true) {
			reactant1 = new Texture("right.png");
			reactant2 = new Texture("right.png");
			balance1 = new Texture("right.png");
			balance2 = new Texture("right.png");

			sb.draw(reactant1, 90, 253);
			sb.draw(reactant2, 260, 253);
			sb.draw(balance1, 50, 253, 10, 50);
			sb.draw(balance2, 225, 253, 10, 50);
			LoginRegisterConnection.setScore(LoginRegisterConnection.getScore() + 100);
			gsm.push(new WinState(gsm, sb, "PlayState3"));

		}

		sb.end();

	}
	private String getClickedTexture() {

		String temp = "";
		int inputX = Gdx.input.getX();
		int inputY = Gdx.input.getY();
		System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
		
		 if (inputX >= 488 && inputX <= 582) 
		{
			 sound.play(0.7f);
			if (inputY >= 274 && inputY <= 346)
				temp = "1.png";
			else if (inputY >= 370 && inputY <= 443)
				temp = "2.png";
			else if (inputY >= 460 && inputY <= 530)
				temp = "3.png";
		} 
		  if (inputX >= 510 && inputX <= 540) 
			{
			 sound.play(0.7f);
				if (inputY >= 45 && inputY <= 79)
					temp = "Al.png";
				else if (inputY >= 124 && inputY <= 154)
					temp = "Na.png";
				else if (inputY >= 197 && inputY <= 230)
					temp = "C.png";
			} 
		else if (inputX >= 591 && inputX <= 658) 
		{
			sound.play(0.7f);
			if (inputY >= 44 && inputY <= 72)
				temp = "Ne.png";
			else if (inputY >= 110 && inputY <= 138)
				temp = "N2.png";
			else if (inputY >= 180 && inputY <= 209)
				temp = "H2.png";
			else if (inputY >= 251 && inputY <= 283)
				temp = "He.png";
			else if (inputY >= 317 && inputY <= 346)
				temp = "O2.png";
			else if (inputY >= 380 && inputY <= 408)
				temp = "Cl2.png";
		}

		else if (inputX >= 712 && inputX <= 726) {
			sound.play(0.7f);
			if (inputY >= 43 && inputY <= 106)
				temp = "CuSO4.png";
			else if (inputY >= 169 && inputY <= 237)
				temp = "CH3OH.png";
			else if (inputY >= 294 && inputY <= 368)
				temp = "H2O.png";
			else if (inputY >= 417 && inputY <= 491)
				temp = "H2SO4.png";
		}

		else if (inputX >= 794 && inputX <= 809) 
		{    sound.play(0.7f);
			if (inputY >= 43 && inputY <= 116)
				temp = "NaCl.png";
			else if (inputY >= 178 && inputY <= 242)
				temp = "HCl.png";
			else if (inputY >= 292 && inputY <= 367)
				temp = "CO2.png";
			else if (inputY >= 419 && inputY <= 489)
				temp = "NaNO3.png";
			
		}

		else if (inputX >= 867 && inputX <= 881) 
		{
			sound.play(0.7f);
			if (inputY >= 40 && inputY <= 108)
				temp = "CH4.png";
			else if (inputY >= 168 && inputY <= 242)
				temp = "NaOH.png";
			else if (inputY >= 296 && inputY <= 366)
				temp = "Na2O.png";
			else if (inputY >= 418 && inputY <= 492)
				temp = "CuO.png";
			
		}

		else if (inputX >= 370 && inputX <= 480 && inputY >= 475 && inputY <= 525) {
			sound.play(0.7f);
			temp = "CheckBtn";
		} else if (inputX >= 195 && inputX <= 300 && inputY >= 475 && inputY <= 525) {
			sound.play(0.7f);
			temp = "Reset";
		} else if (inputX >= 35 && inputX <= 125 && inputY >= 475 && inputY <= 530) {
			sound.play(0.7f);
			temp = "Exit";
		}
		System.out.println(temp);
		return temp;
	}

	@Override
	public void dispose() {
		LoginRegisterConnection.updateUserScore();
		bg.dispose();
	}
	public SpriteBatch getSb() {
		return sb;
	}
	public void setSb(SpriteBatch sb) {
		this.sb = sb;
	}

}