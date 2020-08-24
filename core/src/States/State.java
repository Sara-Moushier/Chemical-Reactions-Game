package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import chemicalReactions.Inorganic;
public abstract class State {        // Abstraction
	public OrthographicCamera cam;
	public Vector3 mouse;
	public GameStateManager gsm;
	public Sound sound = Gdx.audio.newSound(Gdx.files.internal("click.ogg"));
	protected SpriteBatch sb;
	protected Texture bg;
	protected Inorganic game;
	
	public State(GameStateManager gsm ) {
		this.gsm = gsm;
		cam = new OrthographicCamera();
		mouse = new Vector3();
	}



	protected abstract void handleInput();

	public abstract void update(float dt);

	public abstract void render(SpriteBatch sb);

	public abstract void dispose();

	public SpriteBatch getSb() {
		return sb;
	}

	public void setSb(SpriteBatch sb) {
		this.sb = sb;
	}

	public Texture getBg() {
		return bg;
	}

	public void setBg(Texture bg) {
		this.bg = bg;
	}
}