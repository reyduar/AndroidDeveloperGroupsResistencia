package com.videotutoriales.ae;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions. 
   ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy. 
   RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;


public class ProyectoAEActivity extends BaseGameActivity {
			// ===========================================================
			// Constants
			// ===========================================================

			private static final int CAMERA_WIDTH = 720;
			private static final int CAMERA_HEIGHT = 480;

			// =========================================================== 
			// Fields
			// ===========================================================

			private Camera mCamera;
			private BitmapTextureAtlas mBitmapTextureAtlas;
			private TextureRegion mFaceTextureRegion;

			// ===========================================================
			// Constructors
			// ===========================================================

			// ===========================================================
			// Getter & Setter
			// ===========================================================

			// ===========================================================
			// Methods for/from SuperClass/Interfaces
			// ===========================================================
			
			/*
			 * -->	Son 4 los metododos necesarios que tenemos que sobrescribir 

			1° onLoadEngine():  	Es llamado cuando el motor del juego es cargado, aqui donde vamos a inicializar la camara y el motor 
			2° onLoadResources():	Es el encargado de cargar los recursos requeridos por nuestro juego.
			3° onLoadScene(): 		Es llamado cuando el Engine esta preparado para la accion, pues aqui se inicia los fotogramas por segundos, tenemos que crear la escena tenemos que centrar la camara en la escena, 
									tenemos que crear los sprites que vayamos a necesitar para incluir los contenidos por ejemplo imagenes, tenemos que enlazarla a la escena y una vez que tengamos creada la escena
									se la devolvemos al Engine para que se reprodusca  
			4° onLoadComplete() :	Este metodo por lo general no es muy utilizado 
			*/
			
			@Override
			public Engine onLoadEngine() {
				//-- Configuramos la orientacion de la pantalla y solicitamos una politica de resolucion
				//-- que es la que hace que AndEngine se ajuste a diferemtes geometrias de pantalla 
				//-- escalando el contenido pero escalando el ratio original del aspecto
				this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
				return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
			}
			
			@Override
			public void onLoadResources() {
				//-- Creamos las texturas que va a utilizar el juego y cargar una imagen que sera la que aparesca 
				//-- esta imagen
				this.mBitmapTextureAtlas = new BitmapTextureAtlas(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
				this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "tripulacion.png", 0, 0);

				this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
			}

			@Override
			public Scene onLoadScene() {
				
				//-- En este ejemplo solo vamos a hacer que aparesca una imagen 
				this.mEngine.registerUpdateHandler(new FPSLogger()); //Aqui indicamos los frame por segundo 

				final Scene scene = new Scene(); //Creamos la escena
				scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f)); //Configuramos un fondo 

				/* Calculate the coordinates for the face, so its centered on the camera. */
				final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2; //Centramos la imagen en la pantalla
				final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion.getHeight()) / 2;

				/* Create the face and add it to the scene. */
				final Sprite face = new Sprite(centerX, centerY, this.mFaceTextureRegion);//Aqui donde creamos la imagen 
				scene.attachChild(face);//Aqui es donde la vinculamos a la escena 

				return scene;//Y posteriormente la devolvemos 
				
			}

			@Override
			public void onLoadComplete() {

			}
    }
