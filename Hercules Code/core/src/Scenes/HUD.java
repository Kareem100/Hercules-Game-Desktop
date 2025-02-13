
package Scenes;

import com.main.Main;
import MovingObjects.Hercules;
import Screens.PlayScreen;
import Sprites.ProtectingShield;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUD implements Disposable{
        public static PlayScreen screen;
        public static int i,lifeCounter;
        public TextureRegion[] t;
        public static Sprite s;
        public static Sprite []s1;
        public static Sprite []s2;
        public static Hercules herucle;
        public Viewport viewport;
        public Stage stage;
        public Skin skin;
        public Label scoreText;
        public Label scoreLabel;
        public static Integer score;
        public static boolean FireDecrease;
        public static boolean DecreasePeriod;
        private static boolean setToRestart;
        private static float restartTimer;
        static double count =0 ;
        public Label swordtimertext;
        public Label swordtimerlabel;
        public Float swordtimer;
        private static Music music;
        /******** KAREEM  *************/
        private static Image image, image2;
        private TextureRegion region, region2;
        private static String healthPath, lifesPath;
        private static int X[], Y[], X2[], Y2[];
        
        private BitmapFont FONT, FONT2;  
        private Label.LabelStyle font, font2;
        /***************************/
    
        public HUD (PlayScreen Screen){
             this.screen =Screen;
               this.herucle=screen.getPlayer();
               this.FireDecrease=setToRestart=false;
               this.DecreasePeriod=true;
               score=0;
               swordtimer=0f;
               lifeCounter=3;
               viewport=new FitViewport(Main.WIDTH,Main.HEIGHT, new OrthographicCamera());
               stage=new Stage(viewport, Main.batch);
               
               Table table=new Table();
         /*********************** KAREEM ***********************************/
        image = new Image(); 
        image2 = new Image();
        i = 1;
        healthPath = "Sprites\\Level 1\\HUD\\Healthbar.png"; lifesPath="Sprites\\Level 1\\HUD\\Lifes.png";
        X = new int [] {1, 1, 361, 1, 361, 361}; Y = new int [] {271, 136, 271, 1, 136, 1};
        X2 = new int [] {307, 205, 103, 1}; Y2 = new int [] {1, 1, 1, 1};
        region=new TextureRegion(new Texture(healthPath),X[0],Y[0],358,133);
        image.setDrawable(new TextureRegionDrawable(region)); 
        region2=new TextureRegion(new Texture(lifesPath),X2[0],Y2[0],100,95);
        image2.setDrawable(new TextureRegionDrawable(region2)); 
        
        FONT= new BitmapFont(Gdx.files.internal("Fonts\\HUD.fnt"));
        font = new Label.LabelStyle(FONT, null);
        FONT2= new BitmapFont(Gdx.files.internal("Fonts\\HUD2.fnt"));
        font2 = new Label.LabelStyle(FONT2, null);
        
        skin = new Skin(Gdx.files.internal("Fonts/uiskin.json"));
        table.setBackground(skin.getDrawable("dialogDim"));
        table.setSize(stage.getWidth(), 150);
        table.setPosition(0, stage.getHeight()-150);
         /******************************************************************/
         
        scoreText = new Label("SCORE", font);
        scoreText.setFontScale(0.7f);
        scoreLabel = new Label(String.format("%3d", score),font);
        scoreLabel.setFontScale(0.7f);
        swordtimertext = new Label("SWORD TIMER", font2);
        swordtimertext.setFontScale(0.7f);
        swordtimerlabel = new Label(String.format("%.0f", swordtimer), font2);
        swordtimerlabel.setFontScale(0.7f);
        
        table.add(image).padTop(10f);
        table.add(image2).padTop(15f).padLeft(-750f);
        table.add(scoreText).expandX().padTop(10).padLeft(-700);
        table.add(swordtimertext).expandX().padTop(10).padLeft(-500);
        table.row();
        table.add().expandX();
        table.add().expandX();
        table.add(scoreLabel).expandX().padTop(-50).padLeft(-730);
        table.add(swordtimerlabel).expandX().padTop(-50).padLeft(-470);
       
        stage.addActor(table);
        }
        
    public void update(float dt){   
        fire_hit();
        ProtectingShield.ShowShieldTimer(swordtimertext, swordtimerlabel, dt);
        if (Hercules.hercules_Drink){
            i=1;
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(healthPath), X[0], Y[0], 358, 133)));
        }
        scoreLabel.setText(String.format("%3d",score));
        restartTimer += dt;
        if(setToRestart && restartTimer > 1.5f){
            screen.restart=true;
            setToRestart=false;
        }
    }
    
    /******************KAREEM*********************/
    public static void hit(){
        if(DecreasePeriod && !setToRestart){
            if(i<6){//KAREEM
                image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(healthPath), X[i], Y[i++], 358, 133)));
                music = Main.manager.get("Audio//Hercules - sounds//Hercules_Atacked.wav",Music.class);
                music.setVolume(Main.vol); 
                music.play();
                if (i==5 && !screen.concentrate.isPlaying()){
                    screen.concentrate.setVolume(Main.vol);
                    screen.concentrate.play();
                }
            }
            
            else { // Loses Life
                herucle.hercules_Die=true;
                music = Main.manager.get("Audio//Hercules - Voices//Hercules/Oh boy.wav",Music.class);
                music.setVolume(Main.vol); 
                music.play();
                if(lifeCounter>0){
                        i=0; lifeCounter--;
                        image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(healthPath), X[i], Y[i++], 358, 133)));
                        image2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(lifesPath), X2[3-lifeCounter], Y2[3-lifeCounter], 100, 95)));
                        if(lifeCounter!=0){restartTimer=0;setToRestart=true; screen.stopHercAction=true;}
                }
                screen.concentrate.stop();
            }
        }
    }
    /*******************************************/
    public static void featherHit() {
          count++;
          if ( count > 10000 ) count=0;
        if (count % 15 ==0)
            hit();
    }
    
    public void fire_hit() {
        if (FireDecrease && DecreasePeriod && !setToRestart) hit();
        FireDecrease=false;
    }
    
    @Override
    public void dispose() {
         stage.dispose();
         skin.dispose();
         FONT.dispose();
         FONT2.dispose();
    }
    
}
