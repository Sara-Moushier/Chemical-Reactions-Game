package Game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import States.GameStateManager;
import States.SplashScreenState;
import chemicalReactions.MysqlCon;

public class GameDemo extends ApplicationAdapter {

	public static final int WIDTH = 950;
	public static final int HEIGHT = 550;

	public static final String TITLE = "Chemical Reactions Game";
	public static Object cam;
	private GameStateManager gsm;
	private SpriteBatch batch;
	Texture img;
	MysqlCon connect = new MysqlCon();// make database connection

	@Override
	public void create() {

		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new SplashScreenState(gsm, batch));


	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		width = WIDTH;
		height = HEIGHT;

	}
}
