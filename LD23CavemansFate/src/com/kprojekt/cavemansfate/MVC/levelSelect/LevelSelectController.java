package com.kprojekt.cavemansfate.MVC.levelSelect;

import com.kprojekt.cavemansfate.MVC.cave.CaveManagerListener;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.MenuActionListener;
import com.kprojekt.cavemansfate.MVC.levelSelect.view.LevelSelectView;
import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * @author Philon
 */
public class LevelSelectController extends InputWrapper implements CaveManagerListener, MenuActionListener
{

	private LevelSelectModel model;
	private LevelSelectView view;
	private MenuItemModel selected;
	private int movedSinceSelected;

	public LevelSelectController( LevelSelectModel model, LevelSelectView view )
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public boolean touchDown( int x, int y )
	{
		MenuItemModel caveManager = null;
		if( (caveManager = view.getMenuItem( x, y )) != null )
		{
			this.selected = caveManager;
		}
		return false;
	}

	@Override
	public boolean touchUp( int x, int y )
	{
		if( this.selected != null )
		{
			if( selected.isUnlocked() )
			{
				model.setActiveLevel( this.selected );
			}
		}
		return false;
	}

	@Override
	public boolean dragged( int howX, int howY )
	{
		this.movedSinceSelected += Math.abs( howY );
		if( Math.abs( this.movedSinceSelected ) > 20 )
		{
			this.movedSinceSelected = 0;
			this.selected = null;
		}

		view.dragMenuItems( howY );
		this.resetMoved();
		return false;
	}

	@Override
	public void resetLevelRequested()
	{
		this.model.getActiveLevel().getCaveManager().reset();

	}

	@Override
	public void gotoMenuRequested()
	{
		this.model.setActiveLevel( null );
	}

	@Override
	public void iFinished()
	{
		this.model.unlockNextLevelAndGotoIt();
		this.model.saveStateToDisc();
	}

}
