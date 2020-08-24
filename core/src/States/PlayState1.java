package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Game.GameDemo;
import Game.Timer;
import Login_Register.LoginRegisterConnection;
import chemicalReactions.Capsule;
import chemicalReactions.Level1;

public class PlayState1 extends State {
	private Sound sound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
	private BitmapFont font; // Set font
	private String displayedEquation;
	private Texture bg,  reactant1, reactant2;
	private int locR1x = 60, locR1y = 290, locR2x = 240, locR2y = 290;
	private boolean[] selectedReactants; // To check if the reactants are chosen or not
	private boolean[] correctEquations ;// To check if the equations are done
	private BitmapFont timePassed;
	private static Timer timer;
	private final static long timeOfGame = 20000; //25 sec
	private String str;

	public PlayState1(GameStateManager gsm, SpriteBatch sb) {
		super(gsm);
		bg = new Texture("Level1BG.jpeg");
		selectedReactants = new boolean[] { false, false }; // To check if the reactants are chosen or not
		correctEquations = new boolean[] { false, false, false };// To check if the equations are done
		game = new Level1();
		game.getData();
		startTimer();
		font = new BitmapFont();
		timePassed = new BitmapFont();
	}

	public static void startTimer() {
		timer = new Timer(timeOfGame);
		timer.start();
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
			String temp = getClickedTexture();
			if (!temp.isEmpty()) {

				if (temp == "CheckBtn") {
					if (game.checkAnswer()) {
						correctEquations[Level1.equationCounter - 1] = true;
						Level1.equationCounter++;
				     	game.setMaps();                                 //encapsulation
						if (Level1.equationCounter == 4) {
							Capsule cap = new Capsule();
							cap.setCapsuleName("NaCl");                 //encapsulation
							cap.makeCapsule();
						}

					}
					selectedReactants[1] = selectedReactants[0] = false;
					
				} else if (temp == "Exit") {
					gsm.pop();// play
					gsm.pop();// loading
				} else if (temp == "Reset") {
					game.getUserMap().clear();                             //encapsulation
					selectedReactants[1] = selectedReactants[0] = false;
					
				} else if (!selectedReactants[0]) {
					reactant1 = new Texture(temp);
					temp = temp.replace(".png", "");
					game.element(temp);
					selectedReactants[0] = true;
					temp = "";
				} else if (selectedReactants[0] && !selectedReactants[1]) {
					reactant2 = new Texture(temp);
					temp = temp.replace(".png", "");
					game.element(temp);
					selectedReactants[1] = true;
					temp = "";
				}
			}

		}
	}


	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getData();
		displayedEquation = game.showEquation();
		// Start drawing
		sb.begin();
		sb.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA,
				GL20.GL_DST_ALPHA);
		sb.draw(bg, 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
		font = new BitmapFont();
		font.getData().setScale(2, 2);
		
		font.draw(sb, "Make the drug of this equation :\n" +game.getProductsNames() +"\n" + displayedEquation, 50, 500);
		font.draw(sb, "Score : " + LoginRegisterConnection.getScore(), 750, 530);     //encapsulation

		// Draw reactants
		if (selectedReactants[0]) {
			sb.draw(reactant1, locR1x, locR1y);
			if (selectedReactants[1]) {
				sb.draw(reactant2, locR2x, locR2y);
			}
		}
		// Draw products
				if (correctEquations[0]) {
					sb.draw(new Texture("H2O.png"), 712, 310);
					if (correctEquations[1]) {
						sb.draw(new Texture("HCl.png"), 713, 150);
						if (correctEquations[2]) {
							sb.draw(new Texture("Capsule.png"), 800, 220);

				}
			}
		}
		str = "" + ((timer.getRunTime() / 1000000000) / 60) + ":" + ((timer.getRunTime() / 1000000000) % 60);
		if (timer.finished())
			str = "Time Finished!!";
		timePassed.draw(sb, str, 20, 530);
		if (str.equals("Time Finished!!"))
			gsm.push(new LoseState(gsm, sb,"PlayState1"));
		else if (Level1.equationCounter == 4) {
			LoginRegisterConnection.setScore(LoginRegisterConnection.getScore() + 100);   //encapsulation
			gsm.push(new WinState(gsm, sb,"PlayState1"));
		}
		

	
		sb.end();
	}

	// Determine which texture is selected
		private String getClickedTexture() {
			String temp = "";
			int inputX = Gdx.input.getX();
			int inputY = Gdx.input.getY();
			System.out.println("(" + inputX + ", " + inputY + ")");
			if (inputX >= 572 && inputX <= 636 && inputY >= 115 && inputY <= 155) {
				temp = "H2.png";
				sound.play(0.7f);
			} else if (inputX >= 572 && inputX <= 636 && inputY >= 210 && inputY <= 239) {
				temp = "O2.png";
				sound.play(0.7f);
			} else if (inputX >= 570 && inputX <= 630 && inputY >= 285 && inputY <= 325) {
				temp = "Cl2.png";
				sound.play(0.7f);
			} else if (inputX >= 590 && inputX <= 620 && inputY >= 380 && inputY <= 415) {
				temp = "Na.png";
				sound.play(0.7f);
			} else if (inputX >= 370 && inputX <= 480 && inputY >= 475 && inputY <= 525) {
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
		Level1.equationCounter = 1;
     	game.setMaps();                  //encapsulation
		game.getUserMap().clear();       //encapsulation
		System.out.println("Play1 Disposed");
	}

}