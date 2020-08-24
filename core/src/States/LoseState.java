package States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Game.GameDemo;
import Login_Register.LoginRegisterConnection;

public class LoseState extends State {

	private Sound loseSound=Gdx.audio.newSound(Gdx.files.internal("loser.ogg"));
	private String prevState;
    private State tempState;
	public LoseState(GameStateManager gsm,  SpriteBatch sb, String prevState) {
		super(gsm);
		this.setSb(sb);
		this.prevState=prevState;
		setBg(new Texture("LoseBg.jpeg"));
		loseSound.play(0.7f);
	}

	@Override
	public void handleInput() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		if (Gdx.input.justTouched()) {
			System.out.println("(" + x + ", " + y + ")");
			if (x >= 595 && x <= 745 && y >= 485 && y <= 520) {
				sound.play(0.7f);
				gsm.pop(); // win
				gsm.pop();//play
				if (prevState.equals("PlayState1"))
					tempState = new PlayState1(gsm, sb);
				else if (prevState.equals("PlayState2"))
					tempState = new PlayState2(gsm, sb);
				else if (prevState.equals("PlayState3"))
					tempState = new PlayState3(gsm, sb);
				gsm.push(tempState);
				}
			else if (x >= 775 && x <= 920 && y >= 485 && y <= 520) {
				sound.play(0.7f);
				gsm.pop(); // win
				gsm.pop();//play
				gsm.pop();//loading
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
		sb.draw(getBg(), 0, 0, GameDemo.WIDTH, GameDemo.HEIGHT);
		BitmapFont font= new BitmapFont();;
		font.getData().setScale(2, 2);
        font.draw(sb, "Score : "+LoginRegisterConnection.getScore() , 780, 530);     
		sb.end();
	}
	@Override
	public void dispose() {
		getBg().dispose();
		loseSound.dispose();
	}

	public SpriteBatch getSb() {
		return sb;
	}



}