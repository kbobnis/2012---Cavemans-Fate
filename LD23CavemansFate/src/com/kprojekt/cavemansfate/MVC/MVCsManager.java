package com.kprojekt.cavemansfate.MVC;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.GotoMenuAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.MenuAction;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.ResetLevelAction;
import com.kprojekt.cavemansfate.MVC.cave.menu.Button;
import com.kprojekt.cavemansfate.MVC.cave.menu.Menu;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Event;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Events;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.TriggerOnTile;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectManager;
import com.kprojekt.cavemansfate.MVC.screens.ScreensManager;
import com.kprojekt.cavemansfate.MVC.screens.elements.ImageElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.StringElement;
import com.kprojekt.cavemansfate.core.Core;
import com.kprojekt.utils.Manager;
import com.kprojekt.utils.fixes.MyFont;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public class MVCsManager
{
	public static SpriteBatch spriteBatch;

	public static MyFont font;

	public static float fontScale;

	private static float tilesPerWidth = 6f;
	private static float lettersPerWidth = 25f;
	private static float tileWidth = 20f;

	public static float tileScale = Gdx.graphics.getWidth() / tileWidth / tilesPerWidth;
	private Manager actualManager;

	public MVCsManager()
	{
		MVCsManager.font = new MyFont( Gdx.files.internal( "arial_polish.fnt" ), false );

		String testText = "";
		for( int i = 0; i < MVCsManager.lettersPerWidth; i++ )
		{
			testText += "a";
		}
		float width = MVCsManager.font.getBounds( testText ).width;
		MVCsManager.fontScale = Gdx.graphics.getWidth() / width;

		MVCsManager.spriteBatch = new SpriteBatch();

		//shiiiiit i spent 2 hours looking why this shit is breaking the whole application! it was because i executed before font scale was set!
		Core.init( "model.xml", "pl" );

		try
		{
			this.actualManager = preparePrepareNewManager();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
	}

	private Manager preparePrepareNewManager() throws Exception
	{
		CaveManager[] all = Core.levels.getAll().toArray( new CaveManager[] {} );

		LevelSelectManager levelSelectManager = new LevelSelectManager( all );
		for( CaveManager caveManager : all )
		{
			caveManager.addCaveManagerListener( levelSelectManager.getController() );
		}

		Core.levels.getGotoMenuAction().addListener( levelSelectManager.getController() );
		Core.levels.getResetLevelAction().addListener( levelSelectManager.getController() );

		return levelSelectManager;
	}

	public void render( float delta )
	{
		Gdx.gl.glClearColor( 0.2f, 0f, 0f, 0 );
		Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

		this.actualManager.render( delta );
	}

}
