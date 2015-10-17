package mekagem.game;

import com.badlogic.gdx.Game;
import mekagem.game.screen.GameScreen;

public class GdxGame extends Game {
    @Override
    public void create () {
        setScreen(new GameScreen(this));
    }
}
