package com.kprojekt.cavemansfate.MVC.cave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.kprojekt.cavemansfate.MVC.cave.controller.CaveController;
import com.kprojekt.cavemansfate.MVC.cave.controller.MainInputHandler;
import com.kprojekt.cavemansfate.MVC.cave.menu.Menu;
import com.kprojekt.cavemansfate.MVC.cave.model.CaveModel;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Events;
import com.kprojekt.cavemansfate.MVC.cave.view.CaveView;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectController;
import com.kprojekt.utils.Manager;
import com.kprojekt.utils.fixes.MyTextureAtlas;

/**
 * @author Philon
 */
public class CaveManager extends Manager
{
	private CaveView caveView;
	private CaveModel caveModel;
	private MainInputHandler mainInputHandler;

	private List<CaveManagerListener> listeners = new ArrayList<CaveManagerListener>();
	private String path;

	public CaveManager( String tmxFile, Events events, Menu menu, HashMap<String, MyTextureAtlas> atlases )
	{
		this.caveModel = new CaveModel( tmxFile, events );
		this.caveView = new CaveView( new CaveController( this.caveModel ), atlases, menu );

		this.mainInputHandler = new MainInputHandler();
		this.mainInputHandler.addInputHandler( this.caveView );
		this.mainInputHandler.addInputHandler( menu );
	}

	@Override
	public void render( float delta, int x, int y )
	{
		Gdx.input.setInputProcessor( this.getMainInputHandler() );
		if( this.caveModel.getEvents().isAnythingToRender() )
		{
			this.caveModel.getEvents().renderNext( delta );
		}
		else if( !this.caveModel.getCaveman().isEnteredTeleport() )
		{
			Gdx.input.setInputProcessor( this.mainInputHandler );
			Gdx.input.setCatchBackKey( true );
			Gdx.input.setCatchMenuKey( true );
			this.caveView.render( delta );
		}
		else
		{
			//reseting because we want to go into this level another time is necessary
			this.caveModel.reset();
			for( CaveManagerListener listener : this.listeners )
			{
				listener.iFinished();
			}
		}

	}

	public CaveModel getModel()
	{
		return this.caveModel;
	}

	public InputProcessor getMainInputHandler()
	{
		return this.mainInputHandler;
	}

	public CaveView getView()
	{
		return this.caveView;
	}

	public void addCaveManagerListener( LevelSelectController controller )
	{
		this.listeners.add( controller );
	}

	public String getPath()
	{
		return this.path;
	}

	public void reset()
	{
		this.caveModel.getEvents().resetAll();
		getModel().reset();
	}

	public void addBackPressedManager(LevelSelectController controller)
	{
		this.caveView.addBackPressedListener(controller);
		
	}


}
