package com.kprojekt.cavemansfate.core;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
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
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectController;
import com.kprojekt.cavemansfate.MVC.screens.ScreensManager;
import com.kprojekt.cavemansfate.MVC.screens.elements.ImageElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.StringElement;
import com.kprojekt.utils.fixes.MyTextureAtlas;

/**
 * @author PHilon 
 */
public class Levels
{

	private GotoMenuAction gotoMenuAction;
	private ResetLevelAction resetLevelAction;
	private List<CaveManager> caveLevels = new ArrayList<CaveManager>();
	private XmlReader.Element parse;
	private HashMap<String, MyTextureAtlas> atlases;

	/**
	 * Loads levels from the xml file
	 * @throws Exception 
	 */
	public Levels( String xmlPath )
	{
		XmlReader reader = new XmlReader();
		try
		{
			parse = reader.parse( new InputStreamReader( Gdx.files.internal( xmlPath ).read(), "UTF-8" ) );
		}
		catch( IOException e )
		{
			throw new RuntimeException( e );
		}
		//all of this is being loaded from xml
		this.validatePackfile( parse, "map" );
		this.validatePackfile( parse, "sprites" );

		atlases = prepareAtlases( parse );

		this.validateEventElementsForPackfiles( parse, atlases );

		reload();
	}

	public void reload()
	{
		Menu menu = prepareMenu();
		this.caveLevels = prepareLevels( parse, atlases, menu );
	}

	private HashMap<String, MyTextureAtlas> prepareAtlases( Element parse )
	{
		MyTextureAtlas mapAtlas = new MyTextureAtlas(
				parse.getChildByName( "packfiles" ).getChildByName( "map" ).getAttribute( "src" ) );
		MyTextureAtlas spritesAtlas = new MyTextureAtlas(
				parse.getChildByName( "packfiles" ).getChildByName( "sprites" ).getAttribute( "src" ) );
		HashMap<String, MyTextureAtlas> atlases = new HashMap<String, MyTextureAtlas>();
		atlases.put( "map", mapAtlas );
		atlases.put( "sprites", spritesAtlas );
		return atlases;
	}

	private void validateEventElementsForPackfiles( Element parse, HashMap<String, MyTextureAtlas> atlases )
	{
		Element levels = parse.getChildByName( "levels" );
		for( int i = 0; i < levels.getChildCount(); i++ )
		{
			Element level = levels.getChild( i );
			for( int j = 0; j < level.getChildCount(); j++ )
			{
				Element event = level.getChild( j );
				for( int k = 0; k < event.getChildCount(); k++ )
				{
					Element element = event.getChild( k );

					String sentence = element.getAttribute( "sentence", null );
					String packfile = element.getAttribute( "packfile", null );
					if( sentence != null )
					{
						if( Core.lang.get( sentence ) == null )
						{
							throw new RuntimeException( "THere is no sentence in dictionary for key (" + sentence + ")" );
						}
					}
					else if( packfile != null )
					{
						String region = element.getAttribute( "region" );
						TextureAtlas textureAtlas = atlases.get( packfile );
						if( textureAtlas == null )
						{
							throw new RuntimeException( "There is no packfile named (" + packfile + ")" );
						}
						AtlasRegion findRegion = textureAtlas.findRegion( region );
						if( findRegion == null )
						{
							throw new RuntimeException( "There is no region named (" + region + ") in packfile ("
									+ packfile + ")" );
						}
					}
					else
					{
						throw new RuntimeException( "ScreenElement not recognized (" + element + ") " );
					}

				}
			}
		}
	}

	private void validatePackfile( Element root, String name )
	{

		Element map = root.getChildByName( "packfiles" ).getChildByName( name );

		ArrayList<String> regionsFromXML = new ArrayList<String>();
		for( int i = 0; i < map.getChildCount(); i++ )
		{
			regionsFromXML.add( map.getChild( i ).getAttribute( "name" ) );
		}

		TextureAtlas mapAtals = new TextureAtlas( map.getAttribute( "src" ) );
		List<AtlasRegion> regionsFromPackfile = mapAtals.getRegions();
		List<String> regionNamesFromPackFile = new ArrayList<String>();

		for( AtlasRegion region : regionsFromPackfile )
		{
			regionNamesFromPackFile.add( region.name );
		}

		//checking if there are more in region from map
		List<String> tmp = new ArrayList<String>( regionNamesFromPackFile );
		tmp.removeAll( regionsFromXML );

		if( tmp.size() > 0 )
		{
			throw new RuntimeException( "There are " + name + " regions which aren't included in the xml file: "
					+ tmp.toString() );
		}

		//checking if there are more in region from xml
		tmp = new ArrayList<String>( regionsFromXML );

		tmp.removeAll( regionNamesFromPackFile );

		if( tmp.size() > 0 )
		{
			throw new RuntimeException( "There are " + name + " regions in XML that doesn't exist in the packfile: "
					+ tmp.toString() );
		}

	}

	private List<CaveManager> prepareLevels( Element parse, HashMap<String, MyTextureAtlas> atlases, Menu menu2 )
	{

		Element levels = parse.getChildByName( "levels" );
		HashMap<String, Element> levelMap = new HashMap<String, Element>();
		for( int i = 0; i < levels.getChildCount(); i++ )
		{
			Element child = levels.getChild( i );
			levelMap.put( child.getAttribute( "id" ), child );
		}

		Element gameLevels = parse.getChildByName( "gameLevels" );
		List<CaveManager> caveLevels = new ArrayList<CaveManager>();
		for( int i = 0; i < gameLevels.getChildCount(); i++ )
		{
			Element child = gameLevels.getChild( i );
			String gameLevelId = child.getAttribute( "id" );
			caveLevels.add( this.prepareLevel( levelMap.get( gameLevelId ), atlases, menu2 ) );
		}
		return caveLevels;
	}

	private CaveManager prepareLevel( Element levelXML, HashMap<String, MyTextureAtlas> atlases, Menu menu )
	{
		String filePath = levelXML.getAttribute( "src" );

		Events events = this.prepareEvents( levelXML, atlases );
		return new CaveManager( filePath, events, menu, atlases );
	}

	private Events prepareEvents( Element levelXML, HashMap<String, MyTextureAtlas> atlases )
	{
		Events events = new Events();
		for( int i = 0; i < levelXML.getChildCount(); i++ )
		{
			events.add( this.prepareEvent( levelXML.getChild( i ), atlases ) );
		}
		return events;
	}

	private Event prepareEvent( Element event, HashMap<String, MyTextureAtlas> atlases )
	{

		List<ScreenElement> screenElements = new ArrayList<ScreenElement>();
		for( int i = 0; i < event.getChildCount(); i++ )
		{
			screenElements.add( this.prepareScreenElement( event.getChild( i ), atlases ) );
		}

		ScreensManager screensManager = new ScreensManager( screenElements );
		Trigger trigger = this.prepareTrigger( event );

		return new Event( screensManager, trigger );
	}

	private Trigger prepareTrigger( Element event )
	{
		Trigger trigger = null;
		String triggerName = event.getAttribute( "trigger" );
		String triggerAction = event.getAttribute( "action" );
		if( triggerName.equalsIgnoreCase( "regular" ) )
		{
			trigger = new Trigger( ACTIVATE_ACTION.fromString( triggerAction ) );
		}
		else if( triggerName.equalsIgnoreCase( "onTile" ) )
		{
			int x = Integer.parseInt( event.getAttribute( "x" ) );
			int y = Integer.parseInt( event.getAttribute( "y" ) );
			trigger = new TriggerOnTile( ACTIVATE_ACTION.fromString( triggerAction ), x, y );
		}
		else
		{
			throw new RuntimeException( "There is no trigger for name (" + triggerName + ")" );
		}

		return trigger;
	}

	private ScreenElement prepareScreenElement( Element element, HashMap<String, MyTextureAtlas> atlases )
	{
		ScreenElement screenElement = null;
		String sentence;
		String packFile;
		if( (sentence = element.getAttribute( "sentence", null )) != null )
		{
			screenElement = new StringElement( Core.lang.get( sentence ) );
		}
		else if( (packFile = element.getAttribute( "packfile", null )) != null )
		{
			String region = element.getAttribute( "region" );
			screenElement = new ImageElement( atlases.get( packFile ).findRegion( region ) );
		}
		else
		{
			throw new RuntimeException( "There is no proper attribute in the element (" + element.toString() + ")" );
		}

		return screenElement;
	}

	private Menu prepareMenu()
	{
		Menu menu = new Menu();
		this.gotoMenuAction = new GotoMenuAction();
		this.resetLevelAction = new ResetLevelAction();
		menu.addButton( new Button( 0, 10, Core.lang.get( "menuGotoMenu" ), this.gotoMenuAction ) );
		menu.addButton( new Button( Gdx.graphics.getWidth() / 2, 10, Core.lang.get( "menuReset" ),
				this.resetLevelAction ) );
		return menu;
	}

	public List<CaveManager> getAll()
	{
		return this.caveLevels;
	}

	public MenuAction getGotoMenuAction()
	{
		return this.gotoMenuAction;
	}

	public MenuAction getResetLevelAction()
	{
		return this.resetLevelAction;
	}

	public void addBackPressedListener(LevelSelectController controller)
	{
		for (CaveManager cavemanager : this.caveLevels )
		{
			cavemanager.addBackPressedManager(controller);
		}
	}

}
