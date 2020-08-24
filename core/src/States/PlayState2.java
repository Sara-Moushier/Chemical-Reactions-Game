package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import Game.GameDemo;
import Game.Timer;
import Login_Register.LoginRegisterConnection;
import chemicalReactions.Level2;

public class PlayState2 extends State {
	private BitmapFont font; // Set font
	private String displayedEquation;
	private Texture bg, reactant1, reactant2, product;
	private int locR1x = 193, locR1y = 260, locR2x = 367, locR2y = 260, locPx = 200, locPy = 150;
	private int R1num = 0, R2num = 0, correctEqns = 0, maxN = 4;
	private BitmapFont timePassed;
	private static Timer timer;
	private final static long timeOfGame = 40000, timeForFlask = 2000;
	private String str;
	private Animation<TextureRegion> fog;
	private float stateTime;
	private boolean[] selectedReactants; // To check if the reactants are chosen or not
	private boolean[] correctEqnsArr;
	private boolean TimerTicking = true;
	private Timer timeForWining;

	public PlayState2(GameStateManager gsm, SpriteBatch sb) {
		super(gsm);
		bg = new Texture("Level2BG.jpeg");
		game = new Level2();
		game.getData();
		font = new BitmapFont();
		font.getData().setScale(1.8f, 1.8f);
		startTimer();
		timePassed = new BitmapFont();
		timeForWining = new Timer(timeForFlask);
		selectedReactants = new boolean[] { false, false }; // To check if the reactants are chosen or not
		correctEqnsArr = new boolean[] { false, false, false, false ,false};

		Array<TextureRegion> textureRegions = new Array<TextureRegion>();
		for (int i = 0; i <= 57; ++i) {
			String str = "fog-" + i + ".png";
			textureRegions.add(new TextureRegion(new Texture("animations/fog/" + str)));
		}
		fog = new Animation<TextureRegion>(0.0033f, textureRegions);
		stateTime = 0.0f;
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.justTouched()) {
			System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
			String texture = getClickedTexture();
			String temp = texture.replace(".png", "");
			if (!texture.isEmpty()) {
				if (texture.equals("CheckBtn")) {
					if (game.checkAnswer()) {
						correctEqns++;
						correctEqnsArr[correctEqns] = true;// to display the product flask
						game.getUserMap().clear();                       //encapsulation
						selectedReactants[0] = selectedReactants[1] = false;
						game.setMaps();                                  //encapsulation
						TimerTicking = true;
						timeForWining = null;
						timeForWining = new Timer(timeForFlask);
						timeForWining.start();

						if (correctEqns != maxN) {
							game.getData();

						}
					} else {
						game.getUserMap().clear();                   // encapsulation
						R1num = R2num = 0;
						selectedReactants[0] = selectedReactants[1] = false;

					}
				} else if (texture.equals("ResetBtn")) {
					game.getUserMap().clear();
					selectedReactants[0] = selectedReactants[1] = false;
				} else if (texture.equals("ExitBtn")) {
					game.setMaps();                                  //encapsulation
					game.getUserMap().clear();                       //encapsulation
					selectedReactants[0] = selectedReactants[1] = false;
					gsm.pop();// play
					gsm.pop();// loading

				}

				else if (!selectedReactants[0]) {
					reactant1 = new Texture(texture);
					if (temp.equals("Capsule"))
						game.element("NaCl");
					else
						game.element(temp);
					selectedReactants[0] = true;
					R1num = 1;

				} else if (selectedReactants[0] && texture.toString().equals(reactant1.toString())) {
					if (temp.equals("Capsule"))
						game.element("NaCl");
					else
						game.element(temp);
					R1num++;

				} else if (!selectedReactants[1]) {
					reactant2 = new Texture(texture);
					if (temp.equals("Capsule"))
						game.element("NaCl");
					else
						game.element(temp);
					selectedReactants[1] = true;
					R2num = 1;

				} else if (selectedReactants[1] && texture.toString().equals(reactant2.toString())) {

					if (temp.equals("Capsule"))
						game.element("NaCl");
					else
						game.element(temp);
					R2num++;
				}

			}

			game.displayUserMap();
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		if (TimerTicking)
			stateTime += Gdx.graphics.getDeltaTime();
		else
			stateTime = 0.0f;
	}

	public static void startTimer() {
		timer = new Timer(timeOfGame);
		timer.start();
	}

	@Override
	public void render(SpriteBatch sb) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		displayedEquation = game.showEquation();

		// Start drawing
		sb.begin();
		sb.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA,
				GL20.GL_DST_ALPHA);
		sb.draw(bg, 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
		font.draw(sb, "Score : " + LoginRegisterConnection.getScore(), 750, 530);
		font.draw(sb, "Make the balanced drug of \n" + game.getProductsNames() + "\n" + displayedEquation, 85, 500);
		
		if (selectedReactants[0]) {
			sb.draw(reactant1, locR1x, locR1y);
			if (R1num > 1)
				font.draw(sb, "x" + R1num, 258, 280);
		}
		if (selectedReactants[1]) {
			sb.draw(reactant2, locR2x, locR2y);
			if (R2num > 1)
				font.draw(sb, "x" + R2num, 432, 280);
		}

		if (correctEqnsArr[correctEqns] && TimerTicking && !timeForWining.finished()) {
			product = new Texture("white.png");
			sb.draw(fog.getKeyFrame(stateTime, true),
					locPx - fog.getKeyFrame(stateTime, true).getRegionWidth() / 4 - 20, locPy + product.getHeight());
			sb.draw(product, locPx, locPy);
		} else {
			TimerTicking = false;
		}

		str = "" + ((timer.getRunTime() / 1000000000) / 60) + ":" + ((timer.getRunTime() / 1000000000) % 60);
		if (timer.finished())
			str = "Time Finished!!";
		timePassed.draw(sb, str, 20, 530);
		if (str.equals("Time Finished!!"))
			gsm.push(new LoseState(gsm, sb,"PlayState2"));
		else if (correctEqns >= maxN) {
			LoginRegisterConnection.setScore(LoginRegisterConnection.getScore() + 100);
			gsm.push(new WinState(gsm, sb,"PlayState2"));
		}

		sb.end();
	}

	private String getClickedTexture() {
		String temp = "";
		int inputX = Gdx.input.getX();
		int inputY = Gdx.input.getY();

		if (inputX >= 649 && inputX <= 719 && inputY >= 78 && inputY <= 178) {
			sound.play(0.7f);
			temp = "Na2SO4.png";
		} else if (inputX >= 761 && inputX <= 807 && inputY >= 75 && inputY <= 178) {
			sound.play(0.7f);
			temp = "Na2O.png";
		} else if (inputX >= 872 && inputX <= 904 && inputY >= 75 && inputY <= 178) {
			sound.play(0.7f);
			temp = "Al(OH)3.png";
		}

		else if (inputX >= 653 && inputX <= 717 && inputY >= 210 && inputY <= 312) {
			sound.play(0.7f);
			temp = "H2SO4.png";
		} else if (inputX >= 761 && inputX <= 800 && inputY >= 216 && inputY <= 312) {
			sound.play(0.7f);
			temp = "H2O.png";
		} else if (inputX >= 872 && inputX <= 904 && inputY >= 210 && inputY <= 312) {
			sound.play(0.7f);
			temp = "Capsule.png";
		}

		else if (inputX >= 657 && inputX <= 713 && inputY >= 350 && inputY <= 435) {
			sound.play(0.7f);
			temp = "NaOH.png";
		} else if (inputX >= 766 && inputX <= 796 && inputY >= 350 && inputY <= 442) {
			sound.play(0.7f);
			temp = "HCl.png";
		}

		else if (inputX >= 386 && inputX <= 450 && inputY >= 478 && inputY <= 530) {
			sound.play(0.7f);
			temp = "CheckBtn";
		} else if (inputX >= 240 && inputX <= 345 && inputY >= 475 && inputY <= 530) {
			sound.play(0.7f);
			temp = "ResetBtn";
		} else if (inputX >= 105 && inputX <= 200 && inputY >= 475 && inputY <= 530) {
			sound.play(0.7f);
			temp = "ExitBtn";
		}
		System.out.println(temp);

		return temp;
	}

	@Override
	public void dispose() {
		bg.dispose();
		LoginRegisterConnection.updateUserScore();
		if (reactant1 != null)
			reactant1.dispose();
		if (reactant2 != null)
			reactant2.dispose();
       	game.setMaps();                     //encapsulation
		game.getUserMap().clear();           //encapsulation
		System.out.println("Play2 Disposed");
	}

}