package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import Game.Timer;

public class LoadingState extends State {

	private String toGoTo;
	private State tempState;
	private Timer timer, l;
	private final long time;
	private BitmapFont loadingTxt;
	private final SpriteBatch sb;
	private int idx = 0;
	private Animation<TextureRegion> walking;
	private float stateTime;
	private String[] loading = { "Loading", "Loading.", "Loading..", "Loading..." };

	public LoadingState(GameStateManager gsm, final SpriteBatch sb, String toGoTo, long time) {
		super(gsm);
		this.toGoTo = toGoTo;
		this.time = time;
		timer = new Timer(time);
		l = new Timer(500);
		l.start();
		timer.start();
		this.sb = sb;
		loadingTxt = new BitmapFont();
		loadingTxt.setColor(Color.BLACK);
		Array<TextureRegion> textureRegions = new Array<TextureRegion>();
		for (int i = 0; i <= 14; ++i) {
			String str = "walking_" + i + ".png";
			textureRegions.add(new TextureRegion(new Texture("animations/walking/" + str)));
		}
		walking = new Animation<TextureRegion>(0.033f, textureRegions);
		stateTime = 0f;

	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {
		if (timer.finished()) {
			if (toGoTo.equals("PlayState1"))
				tempState = new PlayState1(gsm, sb);
			else if (toGoTo.equals("PlayState2"))
				tempState = new PlayState2(gsm, sb);
			else if (toGoTo.equals("PlayState3"))
				tempState = new PlayState3(gsm, sb);
			gsm.push(tempState);
		}

		if (l.finished()) {
			idx = (idx + 1) % 4;
			l = null;
			l = new Timer(500);
			l.start();
		}

		stateTime += Gdx.graphics.getDeltaTime();

	}

	@Override
	public void render(SpriteBatch sb) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();

		sb.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_SRC_ALPHA,
				GL20.GL_DST_ALPHA);

		Gdx.gl.glClearColor(1f, 1f, 1f, 1);

		sb.draw(walking.getKeyFrame(stateTime, true), 50, 50);

		loadingTxt.draw(sb, loading[idx], 570, 300);

		sb.end();

	}

	@Override
	public void dispose() {
		loadingTxt.dispose();
	}

	public long getTime() {
		return time;
	}

}
