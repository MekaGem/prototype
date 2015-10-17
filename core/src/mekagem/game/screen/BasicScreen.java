package mekagem.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import mekagem.game.GdxGame;
import mekagem.game.util.Accumulator;

public abstract class BasicScreen implements Screen, InputProcessor {
    public static final float TIME_STEP = 1.0f / 60;
    public static final float MAX_DELTA = 0.25f;
    protected final GdxGame game;
    protected Stage stage;

    private final Accumulator accumulator = new Accumulator(TIME_STEP);

    public BasicScreen(GdxGame game) {
        this.game = game;
    }

    public abstract void renderScreen();

    public abstract void updateScreen(float delta);

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        stage.getBatch().enableBlending();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, MAX_DELTA);
        accumulator.update(delta);

        while (accumulator.useIfReady()) {
            updateScreen(accumulator.getBound());
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        renderScreen();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
        Gdx.app.log(getClass().getSimpleName(), "Hide");
    }

    @Override
    public void dispose() {
        Gdx.app.log(getClass().getSimpleName(), "Dispose");
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}