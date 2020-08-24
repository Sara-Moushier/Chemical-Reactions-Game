package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Game.GameDemo;
import Login_Register.LoginRegisterConnection;

public class MenuState extends State {
	private Texture background;
	private SpriteBatch sb;
	public Music music = Gdx.audio.newMusic(Gdx.files.internal("playTime.mp3"));

	public MenuState(GameStateManager gsm, SpriteBatch sb) {
		super(gsm);

		cam.setToOrtho(false, GameDemo.WIDTH / 2, GameDemo.HEIGHT / 2);
		background = new Texture("MenuBg.jpeg");
		this.sb = sb;
		music.setLooping(true);
		music.setVolume(0.2f);
		music.play();

	}

	@Override
	public void handleInput() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		if (Gdx.input.justTouched()) {
			System.out.println("x: " + Gdx.input.getX() + " y: " + Gdx.input.getY());
			if (x >= 142 && x <= 345 && y >= 70 && y <= 200) {
				gsm.push(new LoadingState(gsm, sb, "PlayState1", 4000));
				sound.play(0.7f);
			} else if (x >= 375 && x <= 580 && y >= 70 && y <= 200) {
				gsm.push(new LoadingState(gsm, sb, "PlayState2", 4000));
				sound.play(0.7f);
			} else if (x >= 600 && x <= 810 && y >= 70 && y <= 200) {
				gsm.push(new LoadingState(gsm, sb, "PlayState3", 4000));
				sound.play(0.7f);
			} else if (x >= 45 && x <= 190 && y >= 480 && y <= 520) {
				sound.play(0.7f);
				gsm.pop(); // Menu
			}

		}

	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
		BitmapFont font = new BitmapFont();
		font.getData().setScale(2, 2);
		font.draw(sb, "Score : " + LoginRegisterConnection.getScore(), 780, 530);
		sb.end();
	}

	@Override
	public void dispose() {
		background.dispose();
		music.dispose(); // music.setLooping(true(;
		System.out.println("Menu State Disposed");

	}
}