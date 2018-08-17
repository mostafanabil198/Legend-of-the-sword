package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GamePlay;

public class GameMain extends Game {
    SpriteBatch batch;

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GamePlay(this));

    }

    @Override
    public void render() {
        super.render();
    }


}
