package com.kprojekt.cavemansfate.MVC.cave.view;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.MVC.Packfiles;
import com.kprojekt.cavemansfate.MVC.cave.actions.CavemanAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.DiggAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.PutTileAction;
import com.kprojekt.cavemansfate.MVC.cave.controller.CavemanController;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.cavemansfate.MVC.cave.model.Weapon;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.utils.fixes.MyTextureAtlas;
import com.kprojekt.utils.fixes.MyTiledMap;
import com.kprojekt.utils.wrappers.CameraWrapper;
import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * @author Philon 
 */
public class CavemanView extends InputWrapper
{

	private final int tileW;
	private final int tileH;

	private final AtlasRegion circleRegion;
	private int circleW;
	private int circleH;

	private final AtlasRegion cavemanRegion;

	private final CavemanController controller;
	private CavemanModel model;
	private TextureAtlas mapAtlas;
	private Camera camera;
	private HashMap<String, TextureRegion> actionTextures;
	private HashMap<SIDES, TextureRegion> arrowTextures;

	public CavemanView( CavemanController cavemanController, MyTextureAtlas mapAtlas, MyTextureAtlas spritesAtlas,
			int tileW, int tileH, CameraWrapper camera )
	{
		this.mapAtlas = mapAtlas;
		this.tileW = tileW;
		this.tileH = tileH;
		this.circleRegion = spritesAtlas.findRegion( Packfiles.Sprites.circle );
		this.circleW = (int)(this.circleRegion.getRegionWidth() * MVCsManager.tileScale);
		this.circleH = (int)(this.circleRegion.getRegionHeight() * MVCsManager.tileScale);

		this.cavemanRegion = mapAtlas.findRegion( MyTiledMap.PLAYER_NAME );
		this.controller = cavemanController;
		this.model = this.controller.getModel();
		this.camera = camera;

		this.actionTextures = new HashMap<String, TextureRegion>();
		this.actionTextures.put( PutTileAction.name, spritesAtlas.findRegion( PutTileAction.name ) );
		this.actionTextures.put( DiggAction.name, spritesAtlas.findRegion( DiggAction.name ) );

		this.arrowTextures = new HashMap<SIDES, TextureRegion>();
		AtlasRegion arrowUp = spritesAtlas.findRegion( "arrowUp" );

		this.arrowTextures.put( SIDES.UP, arrowUp );
	}

	public void renderCircleAround()
	{
		Vector3 cavemanXY = this.getCavemansCenterInPixels( true );
		MVCsManager.spriteBatch.draw( this.circleRegion, cavemanXY.x - circleW / 2, cavemanXY.y - circleH / 2, circleW,
				circleH );
		MVCsManager.font.setColor( Color.WHITE );
	}

	/**
	 * Caveman itself is a part of the map, therefore there is no need to render him explicitly. 
	 */
	public void render()
	{

		Vector3 pos = this.getCavemansCenterInPixels( true );

		int cavemanW = (int)(this.cavemanRegion.getRegionWidth() * MVCsManager.tileScale);
		int cavemanH = (int)(this.cavemanRegion.getRegionHeight() * MVCsManager.tileScale);
		MVCsManager.spriteBatch.draw( this.cavemanRegion, pos.x - cavemanW / 2, pos.y - cavemanH / 2, cavemanW,
				cavemanH );

		if( this.controller.getState().cavemanSelected )
		{
			this.renderCavemanShadow();
		}
		else
		{
			this.renderCircleAround();
			this.renderActionMenu();
		}

		MVCsManager.font.setColor( Color.WHITE );

		this.renderPickedUpTile();
		this.renderAxes();
		//this.renderNavigationArrows();
	}

	private void renderCavemanShadow()
	{

		Vector3 pos = this.getCavemansCenterInPixels( true );

		int cavemanW = (int)(this.cavemanRegion.getRegionWidth() * MVCsManager.tileScale);
		int cavemanH = (int)(this.cavemanRegion.getRegionHeight() * MVCsManager.tileScale);

		Color color = MVCsManager.spriteBatch.getColor();
		MVCsManager.spriteBatch.setColor( 0.7f, 0.7f, 0.7f, 0.9f );
		MVCsManager.spriteBatch.draw( this.cavemanRegion, pos.x - cavemanW / 2
				+ this.controller.getState().shadowOffsetX, pos.y - cavemanH / 2
				+ this.controller.getState().shadowOffsetY, cavemanW, cavemanH );
		MVCsManager.spriteBatch.setColor( color );

	}

	private void renderNavigationArrows()
	{

		TextureRegion upArrow = this.arrowTextures.get( SIDES.UP );
		Vector2 upArrowPos = this.getArrowPosMiddle( SIDES.UP );
		MVCsManager.spriteBatch.draw( upArrow, upArrowPos.x - tileW / 2, upArrowPos.y - tileH / 2, 0, 0, tileW, tileH,
				1, 1, 0 );
		MVCsManager.font.draw( MVCsManager.spriteBatch, "gorna strzałka", upArrowPos.x, upArrowPos.y );

		Vector2 leftArrowPos = this.getArrowPosMiddle( SIDES.LEFT );
		MVCsManager.spriteBatch.draw( upArrow, leftArrowPos.x, leftArrowPos.y, 0, 0, tileW, tileH, 1, 1, 90 );
		MVCsManager.font.draw( MVCsManager.spriteBatch, "lewa strzałka", leftArrowPos.x, leftArrowPos.y );

		Vector2 rightArrowPos = this.getArrowPosMiddle( SIDES.RIGHT );
		MVCsManager.spriteBatch.draw( upArrow, rightArrowPos.x, rightArrowPos.y, 0, 0, tileW, tileH, 1, 1, 270 );
		MVCsManager.font.draw( MVCsManager.spriteBatch, "prawa strzałka", rightArrowPos.x, rightArrowPos.y );

		Vector2 downArrowPos = this.getArrowPosMiddle( SIDES.DOWN );
		MVCsManager.spriteBatch.draw( upArrow, downArrowPos.x, downArrowPos.y, 0, 0, tileW, tileH, 1, 1, 180 );
		MVCsManager.font.draw( MVCsManager.spriteBatch, "dolna strzałka", downArrowPos.x, downArrowPos.y );

	}

	private Vector2 getArrowPosMiddle( SIDES up )
	{
		Vector3 caveman = this.getCavemansCenterInPixels( true );
		Vector2 oneUnderCaveman = new Vector2( caveman.x, caveman.y - 2 * tileH );
		switch( up )
		{
			case UP:
				return new Vector2( oneUnderCaveman.x, oneUnderCaveman.y );
			case LEFT:
				return new Vector2( oneUnderCaveman.x - tileW / 2, oneUnderCaveman.y - tileH );
			case RIGHT:
				return new Vector2( oneUnderCaveman.x + tileW, oneUnderCaveman.y );
			case DOWN:
				return new Vector2( oneUnderCaveman.x + tileW, oneUnderCaveman.y - tileH );
			default:
				throw new RuntimeException( "Position can not be found. " );
		}
	}

	private void renderAxes()
	{
		Vector3 pos = this.getCavemanBottomXY( true );

		//finding best axe
		int max = 0;
		Weapon bestWeapon = null;
		for( Weapon weapon : this.model.getWeapons() )
		{
			if( weapon.power() >= max )
			{
				max = weapon.power();
				bestWeapon = weapon;
			}
		}
		if( bestWeapon != null )
		{
			AtlasRegion imageRegion = this.mapAtlas.findRegion( bestWeapon.getName() );
			if( imageRegion == null )
			{
				throw new RuntimeException( "There has to be a tile in the map called (" + bestWeapon.getName() + ")." );
			}
			int axeW = (int)(imageRegion.getRegionWidth() * MVCsManager.tileScale / 2);
			int axeH = (int)(imageRegion.getRegionHeight() * MVCsManager.tileScale / 2);
			int cavemanW = (int)(20 * MVCsManager.tileScale);

			MVCsManager.spriteBatch.draw( imageRegion, pos.x + cavemanW / 2, pos.y, axeW, axeH );
		}

	}

	private void renderPickedUpTile()
	{
		if( this.controller.getModel().hasTilePickedUp() )
		{
			int pickupedTileId = this.model.getPickupedTileId();
			String tileName = this.model.getMap().getTileProperty( pickupedTileId, MyTiledMap.KEY_NAME );
			AtlasRegion tileRegion = this.mapAtlas.findRegion( tileName );
			int w = (int)(tileRegion.getRegionWidth() * MVCsManager.tileScale / 2);
			int h = (int)(tileRegion.getRegionHeight() * MVCsManager.tileScale / 2);

			//draw it on the caveman
			Vector3 pos = this.getCavemansCenterInPixels( true );
			MVCsManager.spriteBatch.draw( tileRegion, pos.x - w, pos.y + h / 2, w, h );
		}
	}

	private void renderActionMenu()
	{
		HashMap<SIDES, CavemanAction> actions = this.controller.getAvailableActions();

		Set<SIDES> keySet = actions.keySet();
		for( SIDES side : keySet )
		{
			CavemanAction action = null;
			if( (action = actions.get( side )) != null )
			{
				if( action != null )
				{
					Vector3 actionPos = this.getActionCenter( side, true );
					Color color = MVCsManager.spriteBatch.getColor();

					color.a = 0.3f;
					MVCsManager.spriteBatch.setColor( color );
					MVCsManager.spriteBatch.draw( this.actionTextures.get( action.getName() ), actionPos.x - this.tileW
							/ 2, actionPos.y - this.tileH / 2, this.tileW, this.tileH );

					color.a = 1f;
					MVCsManager.spriteBatch.setColor( color );
				}

			}
		}

	}

	public Vector3 getCircleCenterInPixels( Vector3 cavemansCenterInPixels, boolean yUp )
	{
		Vector3 caveman = cavemansCenterInPixels;
		return new Vector3( caveman.x - circleW / 2, caveman.y + (yUp ? -1 : 1) * circleH, 0 );
	}

	public Vector3 getCavemanBottomXY( boolean yUp )
	{
		Vector3 cavemanPos = new Vector3( 0, 0, 0 );

		cavemanPos.x = this.controller.getModel().pos.x * tileW - (camera.position.x - camera.viewportWidth / 2);
		float cameraPos = this.model.getMap().height - this.camera.position.y / tileH;
		float viewportPos = camera.viewportHeight / 2 / tileH;
		cavemanPos.y = (this.controller.getModel().pos.y + 1 - (cameraPos - viewportPos)) * tileH;
		if( yUp )
		{
			cavemanPos.y = this.camera.viewportHeight - cavemanPos.y;
		}
		return cavemanPos;
	}

	public Vector3 getCavemansCenterInPixels( boolean yUp )
	{
		Vector3 cavemanPos = this.getCavemanBottomXY( yUp );
		cavemanPos.x += tileW / 2;

		if( yUp )
		{
			cavemanPos.y += tileH / 2;
		}
		else
		{
			cavemanPos.y -= tileH / 2;
		}

		return cavemanPos;
	}

	public boolean touchDown( int x, int y )
	{
		//if touched in the cavemans circle area, you will be able to move the caveman
		Vector3 cavemanPos = this.getCavemansCenterInPixels( false );
		int cavx = (int)cavemanPos.x;
		int cavy = (int)cavemanPos.y;
		this.model.getEvents().updateAll( ACTIVATE_ACTION.FINGER_DOWN_ANYWHERE, cavx, cavy );

		double dist = Math.sqrt( Math.pow( x - cavemanPos.x, 2 ) + Math.pow( y - cavemanPos.y, 2 ) );

		if( dist < tileW * 2 / 3f )
		{
			this.controller.getState().cavemanSelected( true );
		}
		else
		{
			//check if one on the action buttons wasn't pressed
			this.model.getEvents().updateAll( ACTIVATE_ACTION.FINGER_DOWN_NOT_ON_CAVEMAN, cavx, cavy );
			this.checkAction( x, y );
		}
		return true;
	}

	private boolean checkAction( int x, int y )
	{
		SIDES sides[] = { SIDES.LEFT, SIDES.UP, SIDES.DOWN, SIDES.RIGHT };

		for( SIDES side : sides )
		{

			Vector3 sidePos = this.getActionCenter( side, false );
			double dist = Math.sqrt( Math.pow( sidePos.x - x, 2 ) + Math.pow( sidePos.y - y, 2 ) );
			if( dist < tileW )
			{
				this.controller.actionPressed( side, this.model.getAvailableAction( side ) );
				return true;
			}
		}
		return false;
	}

	private Vector3 getActionCenter( SIDES left, boolean yUp )
	{
		Vector3 pos = this.getCavemansCenterInPixels( yUp );
		switch( left )
		{
			case UP:
				pos.y += yUp ? tileH : -tileH;
				break;
			case DOWN:
				pos.y += yUp ? -tileH : tileH;
				break;
			case LEFT:
				pos.x -= tileW;
				break;
			case RIGHT:
				pos.x += tileW;
				break;
			default:
				throw new RuntimeException( "Unknown side: (" + left + ")" );
		}

		return pos;
	}

	public boolean touchUp( int x, int y )
	{
		return this.controller.cavemanReleased();

	}

	@Override
	public boolean dragged( int x, int y, int howX, int howY )
	{
		if( this.controller.getState().cavemanSelected )
		{
			this.controller.getState().shadowOffsetX = Math.abs( howX ) < tileW ? howX : Math.signum( howX ) * tileW;
			this.controller.getState().shadowOffsetY = Math.abs( howY ) < tileH ? -howY : Math.signum( -howY ) * tileH;

			if( Math.abs( howX ) > tileW )
			{
				if( howX < -tileW )
				{
					this.controller.getState().sideSelected = SIDES.RIGHT;
				}
				if( howX > tileW )
				{
					this.controller.getState().sideSelected = SIDES.LEFT;
				}
			}
			else
			{
				if( howY < -tileH )
				{
					this.controller.getState().sideSelected = SIDES.DOWN;
				}
				else if( howY > tileH )
				{
					this.controller.getState().sideSelected = SIDES.UP;
				}
			}
		}
		return true;
	}

}
