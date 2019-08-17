package com.flex.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class SlidingTable extends Table implements GestureDetector.GestureListener {
    private Texture naviPassive, naviActive;

    // Контейнер для секций
    private float sectionWidth;
    private float sectionHeight;

    // Смещение контейнера sections
    private float amountX      = 0;
    // Направление движения секций
    private int transmission   = 0;
    private float stopSection  = 0;
    private float speed        = 2500;

    private int currentSection = 1;
    // Скорость пиксель/секунда после которой считаем, что пользователь хочет перейти к следующей секции
    private float flingSpeed   = 100;
    private float overscrollDistance = 50;
    private int ImageHeight;

    private boolean isPanning;
    //    private Rectangle cullingArea = new Rectangle();
//    private Actor touchFocusedChild;
//    private ActorGestureListener actorGestureListener;
    private Array<Image> helpImages;
    private Array<ImageButton> imageButton;

    private int LINE_MENU_ITEM_COUNT = 6;

    public SlidingTable() {
        Gdx.app.log("SlidingTable::SlidingTable()", "-- ");

        helpImages = new Array<Image>();
        FileHandle imagesDir = Gdx.files.internal("hui");
        FileHandle[] fileHandles = imagesDir.list();
        for (FileHandle fileHandle : fileHandles) {
            if (fileHandle.extension().equals("png")) {
                Image image = new Image(new Texture(fileHandle));
//                image.setFillParent(true);
                helpImages.add(image);
            }
        }

        imageButton = new Array<ImageButton>();
        FileHandle imagesButtonDir = Gdx.files.internal("button");
        FileHandle[] fileButtonHandles = imagesButtonDir.list();
        for (FileHandle fileHandle1 : fileButtonHandles) {
            if (fileHandle1.extension().equals("png")) {
                ImageButton imagebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(fileHandle1))));
//                image.setFillParent(true);
                imageButton.add(imagebutton);
            }
        }





        naviPassive = new Texture(Gdx.files.internal("naviPassive.png"));
        naviActive  = new Texture(Gdx.files.internal("naviActive.png"));

        for(int section = 0; section < helpImages.size; section++) {
            Image image = helpImages.get(section);
            Table sectionTable = new Table();
            sectionTable.add(image).center().expand();
            addActor(sectionTable);
        }
        for(int section = 0; section < imageButton.size; section++) {
            ImageButton imagebutton = imageButton.get(section);
            Table sectionTable = new Table();
            sectionTable.add(imagebutton).center().expand();
            addActor(sectionTable);
        }

    }
    boolean huinua = false;
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log("SlidingTable::isPanning()", "-- x:" + x + " y:" + y + " deltaX:" + deltaX + " deltaY:" + deltaY);
        float border = Gdx.graphics.getHeight()/3;
        Gdx.app.log("SlidingTable::isPanning()", "-- border:" + border + " getHeight:" + Gdx.graphics.getHeight() + " (Gdx.graphics.getHeight() - border):" + (Gdx.graphics.getHeight() - border));
        if (y > border && y < (Gdx.graphics.getHeight() - border)) {
            if (amountX < -overscrollDistance) {
                return false;
            }
            if (amountX > (getChildren().size - 1) * sectionWidth + overscrollDistance) {
                return false;
            }
            huinua = true;
            isPanning = true;
            amountX -= deltaX;
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("SlidingTable::panStop()", "-- x:" + x + " y:" + y);
        isPanning = false;
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log("SlidingTable::fling()", "-- velocityX:" + velocityX + " velocityY:" + velocityY);
        if (huinua == true) {
            if (Math.abs(velocityX) > flingSpeed) {
                if (velocityX > 0) {
                    setStopSection(currentSection - 2);
                } else {
                    setStopSection(currentSection);
                }
            }
            huinua = false;
        }
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
//        if ( event.getTarget().getClass() == LevelIcon.class ) {
//            touchFocusedChild = event.getTarget();
//        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public int getSectionsCount() {
        return getChildren().size;
    }

    // Вычисление текущей секции на основании смещения контейнера sections
    public int calculateCurrentSection() {
        // Текущая секция = (Текущее смещение / длинну секции) + 1, т.к наши секции нумеруются с 1
        int section = Math.round( amountX / sectionWidth ) + 1;
        //Проверяем адекватность полученного значения, вхождение в интервал [1, количество секций]
        if ( section > getChildren().size ) return getChildren().size;
        if ( section < 1 ) return 1;
        return section;
    }


    public void setStopSection(int stoplineSection) {
        if ( stoplineSection < 0 ) {
            stoplineSection = 0;
        }
        if ( stoplineSection > this.getSectionsCount() - 1 ) {
            stoplineSection = this.getSectionsCount() - 1;
        }

        stopSection = stoplineSection * sectionWidth;

        // Определяем направление движения
        // transmission ==  1 - вправо
        // transmission == -1 - влево
        if ( amountX < stopSection) {
            transmission = 1;
        } else {
            transmission = -1;
        }
    }

    private void move(float delta) {
        // Определяем направление смещения
        if ( amountX < stopSection) {
            // Двигаемся вправо
            // Если попали сюда, а при этом должны были двигаться влево
            // значит пора остановиться
            if ( transmission == -1 ) {
                amountX = stopSection;
                // Фиксируем номер текущей секции
                currentSection = calculateCurrentSection();
                return;
            }
            // Смещаем
            amountX += speed * delta;
        } else if( amountX > stopSection) {
            if ( transmission == 1 ) {
                amountX = stopSection;
                currentSection = calculateCurrentSection();
                return;
            }
            amountX -= speed * delta;
        }
    }

    @Override
    public void act (float delta) {
        // Смещаем контейнер с секциями
        setX( -amountX );

//        cullingArea.set( -sections.getX() + 50, sections.getY(), sectionWidth - 100, sections.getHeight() );
//        sections.setCullingArea(cullingArea);

        // Если водим пальцем по экрану
        if (this.isPanning) {
//        if ( actorGestureListener.getGestureDetector().isPanning() ) {
            // Устанавливаем границу, к которой будем анимировать движение
//             граница = номер предыдущей секции
            setStopSection(calculateCurrentSection() - 1);
        } else {
            // Если палец далеко от экрана - анимируем движение в заданную точку
            move( delta );
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (int i=1; i<= getSectionsCount(); i++) {
            if ( i == calculateCurrentSection() ) {
                batch.draw( naviActive, Gdx.app.getGraphics().getWidth()/2 - getSectionsCount()*20/2 + i*20 , 50);
            } else {
                batch.draw( naviPassive, Gdx.app.getGraphics().getWidth()/2 - getSectionsCount()*20/2 + i*20 , 50);
            }
        }
    }

//    void cancelTouchFocusedChild () {
//        if (touchFocusedChild == null) {
//            return;
//        }
//        try {
//            this.getStage().cancelTouchFocus(touchFocusedChild);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        touchFocusedChild = null;
//    }

//    public void addWidget(Actor widget) {
//        widget.setX( this.sections.getChildren().size * sectionWidth);
//        widget.setY( 0 );
//        widget.setWidth( sectionWidth );
//        widget.setHeight( Gdx.graphics.getHeight() );
//        sections.addActor( widget );
//    }

    //    @Override
    public void resize(float width, float height) {
        sectionWidth = width;
        sectionHeight = height;
        Array<Actor> array = getChildren();
        for (int a = 0; a < array.size; a++) {
            Actor actor = array.get(a);
            actor.setWidth(sectionWidth);
            actor.setHeight(sectionHeight);
            actor.setX(a * sectionWidth);
            if (actor instanceof Table) {
                Table table = (Table)actor;
                Array<Actor> children = table.getChildren();
                for (Actor child : children) {
                    if (child instanceof Table) {
                        child.setWidth(sectionWidth);
                        child.setHeight(sectionHeight);
                    }
                }
            }
        }
        setStopSection(calculateCurrentSection() - 1);
    }
}
