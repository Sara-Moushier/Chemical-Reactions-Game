package Login_Register;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import Game.GameDemo;
import States.GameStateManager;
import States.MenuState;
import States.State;

public class LoginState extends State {
	
	private TextField textField;
	private LoginRegisterConnection loginDbConnection = new LoginRegisterConnection();
	private Stage stage;
	private Skin skin;
	private BitmapFont font;
	
	public LoginState(GameStateManager gsm, SpriteBatch sb) {
		super(gsm);
		bg = new Texture("LoginBg.jpeg");
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Gdx.input.setInputProcessor(stage);
		createTextField();		
		this.sb = sb;
	}


	public void createTextField() {
		textField = new TextField("", skin);
		textField.setX(590);
		textField.setY(250);
		textField.setWidth(200);
		textField.setHeight(30);
		

		stage.addActor(textField);
	}

	@Override
	public void handleInput() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		if (Gdx.input.justTouched()) {
			//System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
			if (x >= 561 && x <= 793 && y >= 339 && y <= 382) {
				if (loginDbConnection.LoginCheck(textField.getText())) //Correct login
				{
					sound.play(0.7f);
					gsm.push(new MenuState(gsm, sb));
					
				}
				else {	//Incorrect login
					sound.play(0.7f);
					showDialogBox("Please go to register page");
				}
			}
			if (x >= 619 && x <= 727 && y >= 437 && y <= 456) { // Register button
				sound.play(0.7f);
				gsm.push(new RegisterState(gsm, sb));
			}
		}
		
		
	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void render(SpriteBatch sb) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.getBatch().begin();
		stage.getBatch().draw(bg, 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
		font = new BitmapFont();
		font.getData().setScale(2, 2);
		stage.getBatch().end();
		stage.draw();

	}
    private void showDialogBox(String dialogText) {       
        Dialog dialog = new Dialog("", skin) ;
        dialog.text(dialogText);
        dialog.button("OK", true);
        dialog.key(Keys.ENTER, true);
        dialog.show(stage);
        dialog.setSize(300.0f, 200.0f);
        dialog.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
    }

	@Override
	public void dispose() {
		stage.dispose();
		System.out.println("Login Disposed");
	}
	

}
