package com.kprojekt.cavemansfate.MVC.cave.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Events;
import com.kprojekt.utils.TiledMapVerificator;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon 
 */
public class CaveModel
{
	private CavemanModel caveman;
	private MyTiledMap tiledMap;
	private final Events events;
	private final String tmxFile;
	private CavemanState cavemanState;
	private CaveState caveState;
	private String levelName;

	public CaveModel( String tmxFile, Events events )
	{
		this.tmxFile = tmxFile;

		try
		{
			this.tiledMap = new MyTiledMap( TiledLoader.createMap( Gdx.files.internal( tmxFile ) ) );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}
		//checking the tiledmap for needed things
		TiledMapVerificator.verificate( tiledMap, tmxFile );

		String[] splitName = tmxFile.split( "/" );
		String last = splitName[splitName.length - 1];
		String[] lastElement = last.split( "\\." );
		this.levelName = "Level " + lastElement[0];

		caveman = new CavemanModel( tiledMap, events );

		if( events == null )
		{
			events = new Events();
		}
		this.events = events;
		this.cavemanState = new CavemanState();
		this.caveState = new CaveState();
	}

	public MyTiledMap getMap()
	{
		return this.tiledMap;
	}

	public CavemanModel getCaveman()
	{
		return this.caveman;
	}

	public Events getEvents()
	{
		return this.events;
	}

	public CavemanState getCavemanState()
	{
		return this.cavemanState;
	}

	public void reset()
	{
		this.tiledMap.reset( TiledLoader.createMap( Gdx.files.internal( tmxFile ) ) );
		this.caveman.reset( tiledMap, events );
		this.cavemanState.resetState();

	}

	public CaveState getCaveState()
	{
		return this.caveState;
	}

	public CharSequence getName()
	{
		return levelName;
	}

}
