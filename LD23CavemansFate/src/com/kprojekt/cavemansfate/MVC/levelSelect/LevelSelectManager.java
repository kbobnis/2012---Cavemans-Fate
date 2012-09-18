package com.kprojekt.cavemansfate.MVC.levelSelect;

import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.view.LevelSelectView;
import com.kprojekt.utils.Manager;

/**
 * @author PHilon
 */
public class LevelSelectManager extends Manager
{
	private LevelSelectModel model;
	private LevelSelectController levelSelectController;

	private LevelSelectView view;
	private MenuItemModel selected;
	private int movedSinceSelected;
	private int touchedDownY;

	public LevelSelectManager( CaveManager[] levels )
	{
		this.model = new LevelSelectModel( levels );
		this.view = new LevelSelectView( this.model );
		this.levelSelectController = new LevelSelectController( this.model, this.view );
	}

	@Override
	public void render( float delta, int x, int y )
	{
		MenuItemModel activeLevel = this.model.getActiveLevel();
		if( activeLevel == null )
		{
			this.view.render( delta );
		}
		else
		{
			activeLevel.getCaveManager().render( delta, 0, 0 );
		}
	}

	public LevelSelectController getController()
	{
		return this.levelSelectController;
	}

	@Override
	public void touchDown( int x, int y )
	{
		this.touchedDownY = y;
		MenuItemModel caveManager = null;
		if( (caveManager = view.getMenuItem( x, y )) != null )
		{
			this.selected = caveManager;
		}
	}

	@Override
	public void touchUp( int x, int y )
	{
		if( this.selected != null )
		{
			if( selected.isUnlocked() )
			{
				model.setActiveLevel( this.selected );
			}
		}
	}

	@Override
	public void dragged( int x, int y, int howX, int howY )
	{
		this.movedSinceSelected += Math.abs( howY );
		if( Math.abs( this.movedSinceSelected ) > 20 )
		{
			this.movedSinceSelected = 0;
			this.selected = null;
		}

		view.dragMenuItems( y - this.touchedDownY );
		this.touchedDownY = y;
	}

}
