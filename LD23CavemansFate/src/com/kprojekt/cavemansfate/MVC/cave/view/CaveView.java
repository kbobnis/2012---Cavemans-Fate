package com.kprojekt.cavemansfate.MVC.cave.view;

import java.io.Console;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.MenuActionListener;
import com.kprojekt.cavemansfate.MVC.cave.controller.CaveController;
import com.kprojekt.cavemansfate.MVC.cave.controller.CavemanController;
import com.kprojekt.cavemansfate.MVC.cave.menu.Menu;
import com.kprojekt.cavemansfate.MVC.cave.model.CaveModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CaveState;
import com.kprojekt.utils.fixes.MyTextureAtlas;
import com.kprojekt.utils.wrappers.CameraWrapper;
import com.kprojekt.utils.wrappers.InputWrapper;
import com.kprojekt.utils.wrappers.TileMapRendererWrapper;

/**
 * @author Philon
 */
public class CaveView extends InputWrapper
{
	public CameraWrapper camera;
	private TileMapRendererWrapper tileMapRendererWrapper;
	private int tileW;
	private int tileH;
	private int mapHeight;

	private Menu menu;
	private AtlasRegion arrows;
	private AtlasRegion backgroundBack;
	private AtlasRegion backgroundFront;

	private CaveModel caveModel;

	private CavemanView cavemanView;
	private MenuActionListener listener;
	private MyTextureAtlas mapAtlas;
	private CavemanController cavemanController;
	private MyTextureAtlas spritesAtlas;
	private final CaveController caveController;
	private float touchedDownCameraPosX;
	private float touchedDownCameraPosY;

	public CaveView( CaveController caveController, HashMap<String, MyTextureAtlas> atlases, Menu menu )
	{
		this.caveController = caveController;
		spritesAtlas = atlases.get( "sprites" );
		this.mapAtlas = atlases.get( "map" );
		this.arrows = spritesAtlas.findRegion( "arrows" );
		this.backgroundBack = spritesAtlas.findRegion( "background_back" );
		this.backgroundFront = spritesAtlas.findRegion( "background_front" );
		this.caveModel = caveController.getModel();
		this.cavemanController = caveController.getCavemanController();
		this.menu = menu;
	}

	public void render( float delta )
	{

		initView();

		CavemansFate.spriteBatch.begin();

		CavemansFate.spriteBatch.draw( this.backgroundBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

		float startX = 0;
		float startY = 0;
		float width = Gdx.graphics.getWidth();
		CavemansFate.spriteBatch.draw( this.backgroundFront, startX, startY, width, Gdx.graphics.getHeight() );

		CavemansFate.spriteBatch.end();

		//this.centerCameraOnCaveman();
		camera.update();
		if( this.tileMapRendererWrapper == null )
		{
			this.tileMapRendererWrapper = new TileMapRendererWrapper( this.caveModel.getMap(), mapAtlas, tileW, tileH,
					tileW, tileH );
		}
		this.tileMapRendererWrapper.updateCamera( camera.combined );
		this.tileMapRendererWrapper.render( camera );

		CavemansFate.spriteBatch.begin();
		this.cavemanView.render();
		this.renderBackground();
		// draw restart text
		this.menu.render();
		CavemansFate.spriteBatch.end();

	}

	private void initView()
	{
		if( this.cavemanView == null )
		{
			this.mapHeight = caveController.getModel().getMap().height;
			int tileW = (int)(20 * CavemansFate.tileScale);
			int tileH = (int)(20 * CavemansFate.tileScale);
			this.tileW = tileW;
			this.tileH = tileH;
			this.camera = new CameraWrapper( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			this.camera.setToOrtho( false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
			this.cavemanView = new CavemanView( cavemanController, mapAtlas, spritesAtlas, tileW, tileH, this.camera );
		}
	}

	private void renderBackground()
	{
		if( this.caveModel.getCaveState().isBackgroundSelected() )
		{
			CaveState caveState = this.caveModel.getCaveState();
			int width = (int)(this.arrows.getRegionWidth() * CavemansFate.tileScale);
			int height = (int)(this.arrows.getRegionHeight() * CavemansFate.tileScale);

			CavemansFate.spriteBatch.draw( this.arrows, caveState.getTouchedX() - width / 2, Gdx.graphics.getHeight()
					- caveState.getTouchedY() - height / 2, width, height );
		}
	}

	@Override
	public boolean backPressed()
	{
		this.listener.resetLevelRequested();
		this.listener.gotoMenuRequested();
		return false;
	}

	public void addBackPressedListener( MenuActionListener listener )
	{
		this.listener = listener;
	}

	@Override
	public boolean touchDown( int x, int y )
	{
		boolean result = false;
		this.initView();
		if( this.cavemanView.touchDown( x, y ) )
		{
			result = true;
			this.centerCameraOnCaveman();
		}
		this.touchedDownCameraPosX = this.camera.position.x;
		this.touchedDownCameraPosY = this.camera.position.y;

		return result;

	}

	@Override
	public boolean touchUp( int x, int y )
	{
		boolean result = false;
		this.initView();
		if( this.cavemanView.touchUp( x, y ) )
		{
			result = true;
		}
		this.centerCameraOnCaveman();
		return result;
	}

	@Override
	public boolean dragged( int x, int y, int howX, int howY )
	{
		boolean result = false;
		this.initView();
		//drag caveman
		if( this.cavemanView.dragged( x, y, howX, howY ) )
		{
			result = true;
			this.centerCameraOnCaveman();
		}
		//move camera
		else
		{
			this.camera.position.x = dontExceedBorderX( touchedDownCameraPosX - 2 * howX );/// CavemansFate.tilesPerWidth;
			this.camera.position.y = dontExceedBorderY( touchedDownCameraPosY + 2 * howY );// CavemansFate.tilesPerWidth;
			this.camera.update();
		}
		return result;
	}

	private float dontExceedBorderY( float y )
	{
		float tilesCountV = Gdx.graphics.getHeight() / (float)this.tileW;
		int tilesToDown = (int)(tilesCountV / 2);
		int tilesToUp = tilesToDown;

		int heightToDown = (int)(tilesToUp * this.tileW + this.tileW / 2f);
		int heightToUp = tilesToDown * this.tileW - this.tileW / 2;

		System.out.println( "y: " + y );
		if( y < heightToDown )
		{
			y = heightToDown;
		}
		if( y > Gdx.graphics.getHeight() - heightToUp )
		{
			y = Gdx.graphics.getHeight() - heightToUp;
		}
		return y;
	}

	private void centerCameraOnCaveman()
	{
		int mapHeightInPixels = this.tileH * this.mapHeight;

		// first, center on the caveman.
		this.camera.position.x = dontExceedBorderX( this.caveModel.getCaveman().pos.x * this.tileW + this.tileW / 2 );
		float posY = this.caveModel.getCaveman().pos.y;
		this.camera.position.y = dontExceedBorderY( mapHeightInPixels - posY * this.tileH + this.tileW / 2 );
		this.camera.update();
	}

	private float dontExceedBorderX( float x )
	{
		int tilesToLeft = (int)(CavemansFate.tilesPerWidth / 2);
		int tilesToRight = tilesToLeft + 1;

		int widthToLeft = tilesToLeft * this.tileW + this.tileW / 2;
		int widthToRight = tilesToRight * this.tileW - this.tileW / 2;

		if( x < widthToLeft )
		{
			x = widthToLeft;
		}
		if( caveController.getModel().getMap().width * this.tileW - x < widthToRight )
		{
			x = caveController.getModel().getMap().width * this.tileW - widthToRight;
		}
		return x;
	}
}
