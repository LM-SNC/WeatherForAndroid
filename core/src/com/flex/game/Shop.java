package com.flex.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
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

public class Shop implements Screen {
    final Drop game;
    Stage stage;
    ImageButton cnbutton;
    ImageButton aby;
    Image backGround;
    ImageButton sound;
    Label coinLabel;
    FileHandle score;
    ImageButton exitstore;
    ImageButton back1;
    ImageButton skins;
    ImageButton back;
    Batch batch;
    Skin skin;
    int coin;
    private Table rootTable;
    private SlidingTable slidingTable;


    Shop(final Drop gam) {
        final float height = Gdx.graphics.getHeight();
        final float width = Gdx.graphics.getWidth();
        final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        batch = new SpriteBatch();
        stage = new Stage(new ExtendViewport(width, height));
        this.game = gam;


        Table firstTable = new Table();
        firstTable.setFillParent(true);
        final Table secondTable = new Table();
        secondTable.setFillParent(true);
        Table thirdTable = new Table();
        thirdTable.setFillParent(true);
        Table backForTableBYART = new Table();
        backForTableBYART.setFillParent(true);
        coinLabel = new Label("Coin: " + coin,skin);
        back1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("back1.png"))));
        backGround = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("bstore.jpg"))));
        skins = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("skins.png"))));
        sound = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("sound.png"))));
        exitstore = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("exitstore.png"))));
        aby = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("aby.png"))));
        cnbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("cnbutton.png"))));
        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("back.png"))));
        //stage.setDebugAll(true);
        backGround.setFillParent(true);
        stage.addActor(backGround);
       secondTable.add(coinLabel).expand().align(Align.topLeft).row();
        firstTable.add(skins).align(Align.left).padRight(20).padBottom(20);
        rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        slidingTable = new SlidingTable();
        rootTable.add(slidingTable).expand().fill().row();
        slidingTable.setVisible(false);
        skins.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swithSkins;
                swithSkins = true;
                    Gdx.app.log("Shop::Shop()", "swithSkins);" + swithSkins);
                    sound.setVisible(!swithSkins);
                    aby.setVisible(!swithSkins);
                    cnbutton.setVisible(!swithSkins);
                    exitstore.setVisible(!swithSkins);
                    skins.setVisible(!swithSkins);
                if (height > 480 && width > 800) {
                    back.setVisible(swithSkins);
                    back1.setVisible(false);
                }
                else {
                    back1.setVisible(swithSkins);
                }
                    slidingTable.setVisible(swithSkins);


            }




        });
        firstTable.add(sound).align(Align.left).padBottom(20).padLeft(20).row();
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swithSkins;
                swithSkins = true;
                Gdx.app.log("Shop::Shop()", "swithSkins);" + swithSkins);
                sound.setVisible(!swithSkins);
                aby.setVisible(!swithSkins);
                cnbutton.setVisible(!swithSkins);
                exitstore.setVisible(!swithSkins);
                skins.setVisible(!swithSkins);
                if (height > 480 && width > 800) {
                    back.setVisible(swithSkins);
                    back1.setVisible(false);
                }
                else {
                    back1.setVisible(swithSkins);
                }


            }




        });

        firstTable.add(aby).align(Align.left).padRight(20);
        aby.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swithSkins;
                swithSkins = true;
                Gdx.app.log("Shop::Shop()", "swithSkins);" + swithSkins);
                sound.setVisible(!swithSkins);
                aby.setVisible(!swithSkins);
                cnbutton.setVisible(!swithSkins);
                exitstore.setVisible(!swithSkins);
                skins.setVisible(!swithSkins);
                if (height > 480 && width > 800) {
                    back.setVisible(swithSkins);
                    back1.setVisible(false);
                }
                else {
                    back1.setVisible(swithSkins);
                }


            }




        });
        firstTable.add(cnbutton).align(Align.left).padLeft(20);
        cnbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swithSkins;
                swithSkins = true;
                Gdx.app.log("Shop::Shop()", "swithSkins);" + swithSkins);
                sound.setVisible(!swithSkins);
                aby.setVisible(!swithSkins);
                cnbutton.setVisible(!swithSkins);
                exitstore.setVisible(!swithSkins);
                skins.setVisible(!swithSkins);
                if (height > 480 && width > 800) {
                    back.setVisible(swithSkins);
                    back1.setVisible(false);
                }
                else {
                    back1.setVisible(swithSkins);
                }


            }




        });

        stage.addActor(firstTable);
        stage.addActor(secondTable);
        stage.addActor(thirdTable);
        stage.addActor(backForTableBYART);
//        back.getImage().setSize(50, 100);
        secondTable.add(exitstore).expand().align(Align.bottom);
        thirdTable.add(back).expand().align(Align.bottomRight);
        backForTableBYART.add(back1).expand().align(Align.bottomRight);
       back1.setVisible(false);
//        back.align(Align.right);
        back.setVisible(false);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swith;
               swith = true;
                    back.setVisible(swith);
                    back.setVisible(!swith);
                    aby.setVisible(swith);
                    sound.setVisible(swith);
                    skins.setVisible(swith);
                    cnbutton.setVisible(swith);
                    exitstore.setVisible(swith);
                    slidingTable.setVisible(!swith);


            }

        });
        back1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swith;
                swith = true;
                back1.setVisible(swith);
                back1.setVisible(!swith);
                aby.setVisible(swith);
                sound.setVisible(swith);
                skins.setVisible(swith);
                cnbutton.setVisible(swith);
                exitstore.setVisible(swith);
                slidingTable.setVisible(!swith);


            }

        });
        exitstore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game)); // Переход к экрану игры
            }
        });
        score = Gdx.files.local("score.txt");
        score();



    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GestureDetector(slidingTable));
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        coinLabel.setText("Coin: " + coin);
        stage.act();
        stage.draw();
        game.batch.end();
        inputHandler(delta);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        slidingTable.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public void score() {
        Gdx.app.log("GameScreen::score()", "--");
        String line = score.readString();
        String lines[] = line.split("\n");
        if (lines.length == 2) {
            coin = Integer.parseInt(lines[1]);
        } else {
            Gdx.app.log("GameScreen::score()", "lines:" + lines);
        }
    }
    private void inputHandler(float delta) {
//        Gdx.app.log("HelpMenuScreen::inputHandler(" + delta + ");");
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- isKeyJustPressed(Input.Keys.BACK || Input.Keys.BACKSPACE);");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- isKeyJustPressed(Input.Keys.NUMPAD_1 || Input.Keys.NUM_1);");
//            clickAnalyzer((short)1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- isKeyJustPressed(Input.Keys.NUMPAD_2 || Input.Keys.NUM_2);");
//            clickAnalyzer((short)2);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3) || Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- isKeyJustPressed(Input.Keys.NUMPAD_3 || Input.Keys.NUM_3);");
//            clickAnalyzer((short)3);
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- isKeyJustPressed(Input.Keys.ENTER);");
//            Gdx.app.log("HelpMenuScreen::inputHandler()", "-- Campaign levels:" + widgetController.gameLevelMaps.toString());
//            widgetController.nextGameLevel();
        }
    }
}

