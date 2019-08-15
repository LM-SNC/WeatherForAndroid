package com.flex.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainMenuScreen implements Screen {
    final Drop game;
    SpriteBatch batch;
    OrthographicCamera camera;
    Image startBtnImg;
    Image ric;
    BufferedReader buff;
    ImageButton gold;
    Image backGroundTexture;
    Label coin;
    Stage stage;
    ImageButton storeButton;
    Skin skin;

    String line;
    int Hscore;
    Sprite backGroundSprite;

    public MainMenuScreen(final Drop gam) {
        Gdx.app.log("MainMenuScreen::MainMenuScreen()", "gam:" + gam);
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        // получаем размеры экрана устройства пользователя и записываем их в переменнные высоты и ширины

        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(width, height);
        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false);// временный вектор для "захвата" входных координат
        batch = new SpriteBatch();
        // инициализируем текстуры и спрайты


        // exitButtonSprite = new Sprite(exitButtonTexture);
        // устанавливаем размер и позиции
        //exitButtonSprite.setSize(exitButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), exitButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        //exitButtonSprite.setPosition((width/2f -exitButtonSprite.getWidth()/2) , width/EXIT_VERT_POSITION_FACTOR);
        // устанавливаем прозрачность заднего фон
        stage = new Stage(new ExtendViewport(width, height));

        //stage.setDebugAll(true);
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Table firstTable = new Table();
        firstTable.setFillParent(true);
        Table secondTable = new Table();
        secondTable.setFillParent(true);
        backGroundTexture = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("menubackground.jpg"))));
        backGroundTexture.setFillParent(true);
        stage.addActor(backGroundTexture);
        storeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("storeButton.png"))));
        gold = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("gold.png"))));



        storeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MainMenuScreen:clicked;MainMenuScreen()", "clicked");
                super.clicked(event, x, y);
                game.setScreen(new Shop(game));
                dispose();
            }
        });

        startBtnImg = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("start_button.png"))));
        startBtnImg.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MainMenuScreen:clicked;MainMenuScreen()", "clicked");
                super.clicked(event, x, y);
                game.setScreen(new GameScreen(game));
                dispose();
            }
    });
        coin = new Label("Coins: ", skin);
        firstTable.add(gold).align(Align.topLeft).row();
        secondTable.add(coin).align(Align.topLeft).padLeft(120);
        firstTable.add(storeButton).expand().align(Align.bottomLeft).row();
        firstTable.add(startBtnImg).align(Align.bottomLeft).padTop(10);//.padLeft(30).padRight(40);
        ric = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("ric.png"))));
        secondTable.add(ric).expand().align(Align.bottomRight);

        stage.addActor(firstTable);
        stage.addActor(secondTable);
    }

    @Override
    public void show() {
        Gdx.app.log("MainMenuScreen::show()", "--");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
//        Gdx.app.log("MainMenuScreen::render()", "delta:" + delta);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        stage.act();
        stage.draw();
        coin.setText("Coin: ");

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("MainMenuScreen::resize()", "width:" + width + " height:" + height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        Gdx.app.log("MainMenuScreen::pause()", "--");
    }

    @Override
    public void resume() {
        Gdx.app.log("MainMenuScreen::resume()", "--");
    }

    @Override
    public void hide() {
        Gdx.app.log("MainMenuScreen::hide()", "--");
    }

    @Override
    public void dispose() {
        Gdx.app.log("MainMenuScreen::dispose()", "--");
    }
}
