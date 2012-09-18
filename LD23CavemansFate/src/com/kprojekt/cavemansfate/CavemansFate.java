package com.kprojekt.cavemansfate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectManager;
import com.kprojekt.cavemansfate.core.Core;
import com.kprojekt.cavemansfate.gui.ButtonEvent;
import com.kprojekt.cavemansfate.gui.Container;
import com.kprojekt.cavemansfate.gui.NewButton;
import com.kprojekt.utils.fixes.MyFont;

/**
 * @author Philon 
 */
public class CavemansFate extends Game implements Screen
{
	public static SpriteBatch spriteBatch;

	public static MyFont font;

	public static float fontScale;

	private static float tilesPerWidth = 6f;
	private static float lettersPerWidth = 25f;
	private static float tileWidth = 20f;

	public static float tileScale;

	@Override
	public void create()
	{
		tileScale = Gdx.graphics.getWidth() / tileWidth / tilesPerWidth;
		System.out.println( "Create" );
		CavemansFate.font = new MyFont( Gdx.files.internal( "arial_polish.fnt" ), false );

		String testText = "";
		for( int i = 0; i < CavemansFate.lettersPerWidth; i++ )
		{
			testText += "a";
		}
		float width = CavemansFate.font.getBounds( testText ).width;
		CavemansFate.fontScale = Gdx.graphics.getWidth() / width;

		CavemansFate.spriteBatch = new SpriteBatch();

		//shiiiiit i spent 2 hours looking why this shit is breaking the whole application! it was because i executed before font scale was set!
		Core.init( "model.xml", "pl" );

		try
		{
			CaveManager[] all = Core.levels.getAll().toArray( new CaveManager[] {} );
			final LevelSelectManager levelSelectManager = new LevelSelectManager( all );
			for( CaveManager caveManager : all )
			{
				caveManager.addCaveManagerListener( levelSelectManager.getController() );
			}

			Core.levels.getGotoMenuAction().addListener( levelSelectManager.getController() );
			Core.levels.getResetLevelAction().addListener( levelSelectManager.getController() );

			final Container mainMenu = new Container( Gdx.graphics.getHeight() );
			NewButton newGameButton = new NewButton( Core.lang.get( "menuOptionPlay" ) );
			newGameButton.addEvent( new ButtonEvent()
			{
				public void doAction()
				{
					Core.actualManager = levelSelectManager;
				}
			} );

			final Container options = new Container( Gdx.graphics.getHeight() );
			NewButton language = new NewButton( "Language" );
			NewButton polish = new NewButton( "Polish" );
			polish.addEvent( new ButtonEvent()
			{
				public void doAction()
				{
					Core.lang.changeLocale( "pl" );
					Core.actualManager = mainMenu;
				}
			} );
			NewButton english = new NewButton( "English" );
			english.addEvent( new ButtonEvent()
			{
				public void doAction()
				{
					Core.lang.changeLocale( "en" );
					Core.actualManager = mainMenu;
				}
			} );

			options.add( language );
			options.add( polish );
			options.add( english );

			NewButton optionsButton = new NewButton( Core.lang.get( "menuOptionPreferences" ) );
			optionsButton.addEvent( new ButtonEvent()
			{
				public void doAction()
				{
					Core.actualManager = options;
				}
			} );

			mainMenu.add( newGameButton );
			mainMenu.add( optionsButton );
			Core.actualManager = mainMenu;

		}
		catch( Exception e )
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
		setScreen( this );
	}

	public void render( float delta )
	{
		Core.render( delta );
	}

	@Override
	public void show()
	{
		System.out.println( "Show" );
	}

	@Override
	public void hide()
	{
		System.out.println( "Hide" );
	}

	@Override
	public void resize( int width, int height )
	{
		System.out.println( "Resize" );
	}

	@Override
	public void pause()
	{
		System.out.println( "Pause" );
		this.dispose();
	}

	@Override
	public void resume()
	{
		System.out.println( "Resume" );
	}

}
