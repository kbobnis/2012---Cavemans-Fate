package com.kprojekt.cavemansfate.MVC.cave.view;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.cave.controller.CaveController;
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
	private AtlasRegion backgroundImage;

	private CaveModel caveModel;

	private CavemanView cavemanView;

	public CaveView( CaveController caveController, HashMap<String, MyTextureAtlas> atlases, Menu menu )
	{
		int tileW = (int)(20 * CavemansFate.tileScale);
		int tileH = (int)(20 * CavemansFate.tileScale);
		this.mapHeight = caveController.getModel().getMap().height;

		MyTextureAtlas spritesAtlas = atlases.get( "sprites" );
		MyTextureAtlas mapAtlas = atlases.get( "map" );

		this.arrows = spritesAtlas.findRegion( "arrows" );
		this.backgroundImage = spritesAtlas.findRegion( "background" );

		this.tileW = tileW;
		this.tileH = tileH;
		this.camera = new CameraWrapper( Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		this.camera.setToOrtho( false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

		this.caveModel = caveController.getModel();

		this.tileMapRendererWrapper = new TileMapRendererWrapper( caveController.getModel().getMap(), mapAtlas, tileW,
				tileH, tileW, tileH );

		this.cavemanView = new CavemanView( caveController.getCavemanController(), mapAtlas, spritesAtlas, tileW,
				tileH, this.camera );

		this.menu = menu;

	}

	@Override
	public boolean keyDown( int keycode )
	{
		switch( keycode )
		{
			case Input.Keys.MENU:
				System.out.println( "Menu pressed" );
				Gdx.input.vibrate( 50 );
				break;
			case Input.Keys.BACK:
				System.out.println( "Back pressed" );
				Gdx.input.vibrate( 200 );
				break;
		}
		return false;
	}

	public void render( float delta )
	{
		CavemansFate.spriteBatch.begin();

		CavemansFate.spriteBatch.draw( this.backgroundImage, 0, 0, Gdx.graphics.getWidth() * 2,
				Gdx.graphics.getHeight() * 2 );

		CavemansFate.spriteBatch.end();

		this.centerCameraOnCaveman();
		camera.update();
		tileMapRendererWrapper.updateCamera( camera.combined );
		tileMapRendererWrapper.render( camera );

		CavemansFate.spriteBatch.begin();
		this.cavemanView.render();
		this.renderBackground();
		//draw restart text
		this.menu.render();
		CavemansFate.spriteBatch.end();

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
	public boolean touchDown( int x, int y )
	{
		boolean result = false;
		if( this.cavemanView.touchDown( x, y ) )
		{
			result = true;
		}
		this.centerCameraOnCaveman();
		return result;

	}

	@Override
	public boolean touchUp( int x, int y )
	{
		boolean result = false;
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
		if( this.cavemanView.dragged( x, y, howX, howY ) )
		{
			result = true;
		}
		this.centerCameraOnCaveman();
		return result;
	}

	private void centerCameraOnCaveman()
	{
		int mapHeightInPixels = this.tileH * this.mapHeight;

		//first, center on the caveman.
		this.camera.position.x = this.caveModel.getCaveman().pos.x * this.tileW;
		this.camera.position.y = mapHeightInPixels - this.caveModel.getCaveman().pos.y * this.tileH;
		this.camera.update();
	}

}
