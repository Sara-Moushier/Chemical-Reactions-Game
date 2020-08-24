package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Game.GameDemo;
import Game.Timer;
import Login_Register.LoginState;

public class SplashScreenState extends State {

	private Timer timer;
	private final long time = 2000;
	private final SpriteBatch sb;
	private Texture logo;

	public SplashScreenState(GameStateManager gsm, final SpriteBatch sb) {
		super(gsm);
		timer = new Timer(time);
		timer.start();
		this.sb = sb;
		logo = new Texture("logo.png");
	}

	@Override
	protected void handleInput() {
		 if(Gdx.input.justTouched()){
	            gsm.push(new LoginState(gsm,sb));
	            sound.play(0.7f);
	            }
	}

	@Override
	public void update(float dt) {
      handleInput();

	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();

		sb.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA,
				GL20.GL_DST_ALPHA);

		Gdx.gl.glClearColor(1f, 1f, 1f, 1);

		sb.draw(logo, (GameDemo.WIDTH / 2) - (logo.getWidth() / 2), GameDemo.HEIGHT / 2 - (logo.getHeight() / 2));

		sb.end();

	}

	@Override
	public void dispose() {
		logo.dispose();

	}

}