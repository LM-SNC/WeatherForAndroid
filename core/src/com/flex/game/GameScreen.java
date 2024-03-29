package com.flex.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class GameScreen implements Screen {
    private float timeSecondsCoin = 0f;
    private float periodCoin = 20f;
    private float timeSecondsRicardo = 0f;
    private float periodRicardo = 1f;


    final Drop game;
    Music mp3;
    OrthographicCamera camera;
    Texture dropImage;
    int coin;
    Texture coinImage;
    Texture bucketImage;
    Rectangle bucket;
    FileHandle score;
    FileHandle settingTXT;
    Vector2 nubesx;

    Texture background1;
    int h1;
    int h2;
    int Hscore;
    Sound mario;
    Sound mario1;
    PrintWriter scorewrite;
    ImageButton pause;
    boolean gamepause;
    BufferedReader buff;
    Vector3 touchPos;
    ImageButton setting;
    ImageButton resumeButton;
    float volumeSound = 0.5f;
    ImageButton exit_bn;
    Array<Float> speedsForNubes;
    Array<Float> speedsForRic;
    Array<Float> speedsForCoin;
    Array<Sound> musicMass;
    Array<Rectangle> raindrops;
    Array<Rectangle> coiondrops;
    Array<Rectangle> nubesdrops;

    Label dropColleted;
    Label hightScore;
    Label coinScoreLabel;
    Label musicVolume;
    Label textProebano;

    boolean gameOver = false;
    Texture over;
    int sp;
    int proebano;
    Stage stage;
    int dropsGatchered;
    Texture nubesImg;
    float height = Gdx.graphics.getHeight();
    float width = Gdx.graphics.getHeight();

    public GameScreen(final Drop gam) {
        Gdx.app.log("GameScreen::GameScreen()", "gam:" + gam);
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        touchPos = new Vector3();
        bucketImage = new Texture("bucket.png");
        dropImage = new Texture("droplet.png");
        coinImage = new Texture("gold.png");
        nubesImg = new Texture("nubes.png");
        background1 = new Texture("background1.png");
        over = new Texture("over.png");
        nubesx = new Vector2();

        score = Gdx.files.local("score.txt");
        if (!score.exists()) {
            score.writeString("0", false);
        }
        settingTXT = Gdx.files.local("setting.txt");
        if (!settingTXT.exists()) {
            settingTXT.writeString("0", false);
        }
        settingRead();
        score();
        scoreWrite();


        musicMass = new Array();
        mario = Gdx.audio.newSound(Gdx.files.internal("mario.mp3"));
        mario1 = Gdx.audio.newSound(Gdx.files.internal("mario1.mp3"));
        mp3 = Gdx.audio.newMusic(Gdx.files.internal("mp3.mp3"));
        mp3.setLooping(true);
        mp3.setVolume(volumeSound);
        mp3.play();
        musicMass.add(mario1);
        musicMass.add(mario);
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 0;
        bucket.height = 32;

        speedsForNubes = new Array<>();
        speedsForRic = new Array<>();
        speedsForCoin = new Array<>();
        coiondrops = new Array<Rectangle>();
        raindrops = new Array<Rectangle>();
        nubesdrops = new Array<Rectangle>();

        stage = new Stage(new ExtendViewport(width,height));
        stage.setDebugAll(true);
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        dropColleted = new Label("Drops Collected: " + dropsGatchered, skin);
        textProebano = new Label("Proebano: " + proebano + "/5", skin);
        hightScore = new Label("Hight score: " + Hscore, skin);
        coinScoreLabel = new Label("Coin: " + coin, skin);
        musicVolume = new Label("Music Volume: ", skin);

        Table firstTable = new Table();
        firstTable.setFillParent(true);
        pause = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("pause.png"))));
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gamepause = true;
                pause.setVisible(false);
                resumeButton.setVisible(true);
                setting.setVisible(true);
                exit_bn.setVisible(true);
            }
        });
        firstTable.add(dropColleted).align(Align.topLeft).row();
        firstTable.add(textProebano).align(Align.topLeft).row();
        firstTable.add(hightScore).align(Align.topLeft).row();
        firstTable.add(coinScoreLabel).align(Align.topLeft).row();
        firstTable.add(pause).expand().align(Align.topLeft).padTop(10);
        stage.addActor(firstTable);

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);

        final Slider slider = new Slider(0f, 1f, 0.01f, false, skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Slider slider = (Slider) actor;
                mp3.setVolume(volumeSound);
                volumeSound = slider.getValue();
                settingWrite();
            }
        });


        resumeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("resume.png"))));
        //  resumeButton = new TextButton("RESUME", skin);
        resumeButton.setVisible(false);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gamepause = false;
                resumeButton.setVisible(false);
                setting.setVisible(false);
                exit_bn.setVisible(false);
                pause.setVisible(true);

            }
        });
        final Slider slider1 = new Slider(0f, 1f, 0.01f, false, skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Slider slider1 = (Slider) actor;
                mp3.setVolume(volumeSound);
                volumeSound = slider1.getValue();
            }
        });
        musicVolume.setVisible(false);
        slider.setVisible(false);
        setting = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("setting.png"))));
        setting.setVisible(false);
        setting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                boolean swith = setting.isChecked();
                resumeButton.setVisible(!swith);
                setting.setVisible(swith || !swith);
                slider.setVisible(swith);
                //  slider1.setVisible(swith);
                musicVolume.setVisible(swith);
                exit_bn.setVisible(!swith);
                pause.setVisible(false);
                System.out.print(swith);
            }
        });
        exit_bn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("exit_bn.png"))));
        exit_bn.setVisible(false);

        exit_bn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game)); // Переход к экрану игры
                dispose();
            }
        });
        slider1.setVisible(false);
        //resumeButton.getImage().getDrawable().setHeight(Gdx.graphics.getHeight()/2);
        resumeButton.setHeight(1);
        pauseTable.add(resumeButton).prefHeight(1).row();
        pauseTable.add(musicVolume).row();
        pauseTable.add(slider).row();
        pauseTable.add(setting).padBottom(40).row();
        pauseTable.add(slider1).row();
        pauseTable.add(exit_bn).padBottom(2);

//        Container<Slider> container = new Container<Slider>(slider);
//        container.setTransform(true);   // for enabling scaling and rotation
//        container.size(100, 60);
//        container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
//        container.setPosition(200, 400);
//        container.setScale(1);  //scale according to your requirement
        slider.setValue(volumeSound);
        stage.addActor(pauseTable);
    }


    private void spawnRaindrop() {
        Gdx.app.log("GameScreen::spawnRaindrop()", "--");
        if (!gamepause) {
            Rectangle raindrop = new Rectangle();
            raindrop.x = MathUtils.random(0, 800 - 64);
            raindrop.y = 480;
            raindrop.width = 64;
            raindrop.height = 64;
            speedsForRic.add(MathUtils.random(150 * Gdx.graphics.getDeltaTime(), 200f * Gdx.graphics.getDeltaTime()));
            raindrops.add(raindrop);


        }
    }

    private void spawnCoindrop() {
        Gdx.app.log("GameScreen::spawnRaindrop()", "--");
        if (!gamepause) {
            Rectangle coindrop = new Rectangle();
            coindrop.x = MathUtils.random(0, 800 - 64);
            coindrop.y = 480;
            coindrop.width = 64;
            coindrop.height = 64;
            speedsForCoin.add(MathUtils.random(150f * Gdx.graphics.getDeltaTime(), 200f * Gdx.graphics.getDeltaTime()));
            coiondrops.add(coindrop);

        }
    }

    private void spawnNubes() {
        Gdx.app.log("GameScreen::spawnNubes()", "--");
        Rectangle nubesdrop = new Rectangle();
        nubesdrop.x = MathUtils.random(0, 500);
        nubesdrop.y = 350;
        sp++;
        speedsForNubes.add(MathUtils.random(0.8f, 1.2f));
        nubesdrops.add(nubesdrop);
    }


    public void inputHandler(float delta) {
        Gdx.app.log("GameScreen::inputHandler()", "delta:" + delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gamepause = !gamepause;
        }
    }

    @Override
    public void render(float delta) {
        timeSecondsCoin += Gdx.graphics.getRawDeltaTime();
        if (timeSecondsCoin > periodCoin) {
            timeSecondsCoin -= periodCoin;
            periodCoin = MathUtils.random(10, 40);
            spawnCoindrop();
        }
        timeSecondsRicardo += Gdx.graphics.getRawDeltaTime();
        if (timeSecondsRicardo > periodRicardo) {
            timeSecondsRicardo -= periodRicardo;
            spawnRaindrop();
        }


//        Gdx.app.log("GameScreen::render()", "delta:" + delta);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!gamepause) {

        }
        if (!gamepause) {

        }
        h1 = MathUtils.random(10000, 20000);
        h2 = MathUtils.random(10000, 20000);
        if (!gamepause) {
            for (int k = 0; k < nubesdrops.size; k++) {
                Rectangle nubesdrop = nubesdrops.get(k);
                float speed = speedsForNubes.get(k);
                nubesdrop.x += speed;
                if (nubesdrop.x > 500 || nubesdrop.x < 0) {
                    speedsForNubes.set(k, -speed);
                }
            }
        }

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background1, 1, 1);

        if (gameOver == false) {
            game.batch.draw(bucketImage, bucket.x, bucket.y);
        } else if (gameOver == true) {
            game.batch.draw(over, 130, 20);
            game.font.draw(game.batch, "Pess f", 400, 100);
        }

        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        for (Rectangle coindrop : coiondrops) {
            game.batch.draw(coinImage, coindrop.x, coindrop.y);
        }
        for (Rectangle nubesdrop : nubesdrops) {
            game.batch.draw(nubesImg, nubesdrop.x, nubesdrop.y);
        }


        if (!gamepause) {
//            lastDropTime -= delta;
//            if (lastDropTime < 0){
//                spawnRaindrop(); // добавление капли
//                lastDropTime = 1;
//            }

            if (sp < MathUtils.random(1, 6)) {
                spawnNubes();
            }
            if (dropsGatchered > Hscore) {
                Hscore = dropsGatchered;
            }
        }

        dropColleted.setText("Drops Collected: " + dropsGatchered);
        textProebano.setText("proebano: " + proebano + "/5");
        hightScore.setText("Hight Score: " + Hscore);
        coinScoreLabel.setText("Coin: " + coin);
        musicVolume.setText("Music Volume: ");

        game.batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x - 64 / 2);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && !gamepause)
            bucket.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !gamepause)
            bucket.x += 400 * Gdx.graphics.getDeltaTime();
        if (bucket.x == 0) {
            bucket.x = 1;
        }
        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }
        if (!gamepause) {
            for (int k = 0; k < raindrops.size; k++) {
                Rectangle raindrop = raindrops.get(k);
                float speed = speedsForRic.get(k);
                raindrop.y -= speed;
            }
            for (int k = 0; k < coiondrops.size; k++) {
                Rectangle coindrop = coiondrops.get(k);
                float speed = speedsForCoin.get(k);
                coindrop.y -= speed;
            }
        }
        Iterator<Rectangle> iter = raindrops.iterator();
        Iterator<Float> iter2 = speedsForRic.iterator();
        if (!gamepause) {
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next();
                Float speedForRic = iter2.next();


                if (raindrop.y + 64 < 0) {
                    proebano++;
                    iter.remove();
                    iter2.remove();
                }
                if (raindrop.overlaps(bucket)) {
                    if (gameOver == false) {
                        ((Sound) musicMass.random()).play();
//                        mario.play(1.0f);
//                        mario1.play();
                        dropsGatchered++; // TODO ХУЙНЯ 1
                        if (dropsGatchered > Hscore) {
                            scoreWrite();
                            score();
                        }
                    }
                    iter.remove();
                    iter2.remove();
                }
            }
        }
        Iterator<Rectangle> iterCoin = coiondrops.iterator();
        if (!gamepause) {
            while (iterCoin.hasNext()) {
                Rectangle coindrop = iterCoin.next();
                if (coindrop.y + 64 < 0) {
                    iterCoin.remove();
                }
                if (coindrop.overlaps(bucket)) {
                    if (gameOver == false) {
                        ((Sound) musicMass.random()).play();
//                        mario.play(1.0f);
//                        mario1.play();
                        coin++;
                        scoreWrite();
                    }
                    iterCoin.remove();
                }
            }
        }
        if (proebano == 5) { // TODO ХУЙНЯ 2
            gameOver = true;
        }
        if (proebano > 5) {
            proebano--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F) && gameOver == true) {
            restart();
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen::resize()", "width:" + width + " height:" + height);
        stage.getViewport().update(width,height);
    }

    public void restart() {
        Gdx.app.log("GameScreen::restart()", "--");
        gameOver = false;
        raindrops.clear();
        proebano = 0;
        dropsGatchered = 0;
    }

    public void score() {
        Gdx.app.log("GameScreen::score()", "--");
        String line = score.readString();
        String lines[] = line.split("\n");
        if (lines.length == 2) {
            Hscore = Integer.parseInt(lines[0]);
            coin = Integer.parseInt(lines[1]);
        } else {
            Gdx.app.log("GameScreen::score()", "lines:" + lines);
        }
    }

    public void scoreWrite() {
        Gdx.app.log("GameScreen::scoreWrite()", "--");
        if (dropsGatchered > Hscore) {
            Hscore = dropsGatchered;
        }
        Gdx.app.log("GameScreen::score()", "Hscore:" + Hscore);
        Gdx.app.log("GameScreen::score()", "coin:" + coin);
        score.writeString(String.valueOf(Hscore), false);
        score.writeString("\n" + coin, true);
    }

    public void settingRead() {

        volumeSound = Float.parseFloat(settingTXT.readString());


    }

    public void settingWrite() {


        settingTXT.writeString(String.valueOf(volumeSound), false);
        // score.writeString("\n" + coin, true);
    }


    @Override
    public void pause() {
        Gdx.app.log("GameScreen::pause()", "--");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen::resume()", "--");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen::hide()", "--");
    }

    @Override
    public void dispose() {
        Gdx.app.log("GameScreen::dispose()", "--");
        dropImage.dispose();
        coinImage.dispose();
        bucketImage.dispose();
        mp3.dispose();
        nubesImg.dispose();
        score();
        scoreWrite();
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen::show()", "--");
        Gdx.input.setInputProcessor(stage);
    }
}
