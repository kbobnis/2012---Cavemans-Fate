package com.kprojekt.cavemansfate.MVC.cave.controller;

import java.util.HashMap;
import java.util.List;

import com.kprojekt.cavemansfate.MVC.cave.actions.CavemanAction;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.cavemansfate.core.Core;

/**
 * 
 */
public class CavemanController
{
	private final CavemanModel model;
	private final CavemanState state;

	public CavemanController( CavemanModel model )
	{
		this.model = model;
		this.state = new CavemanState();
	}

	public CavemanModel getModel()
	{
		return this.model;
	}

	public boolean cavemanReleased()
	{
		if( this.state.cavemanSelected && this.state.sideSelected != null )
		{
			this.tryToMove( this.state.sideSelected );
			this.state.sideSelected = null;
		}
		this.state.shadowOffsetX = 0;
		this.state.shadowOffsetY = 0;
		this.state.cavemanSelected( false );

		return true;
	}

	private boolean tryToMove( SIDES side )
	{
		SIDES side2 = null;

		side = SIDES.negate( side );

		if( side == SIDES.RIGHT )
		{
			side2 = SIDES.UP_RIGHT;
		}
		else if( side == SIDES.LEFT )
		{
			side2 = SIDES.UP_LEFT;
		}

		boolean result;
		if( this.model.isSwimming() )
		{
			result = tryToMoveInWater( side, side2 );
			if( result )
			{
				Core.sounds.getSwim().play();
			}
		}
		else
		{
			result = tryToMoveWhenWalking( side, side2 );
			if( result )
			{
				Core.sounds.getWalk().play();
			}
		}

		return result;
	}

	public CavemanState getState()
	{
		return this.state;
	}

	public HashMap<SIDES, List<CavemanAction>> getAvailableActions()
	{
		HashMap<SIDES, List<CavemanAction>> availableActions = new HashMap<CavemanState.SIDES, List<CavemanAction>>();
		availableActions.put( SIDES.RIGHT, this.getModel().getAvailableAction( SIDES.RIGHT ) );
		availableActions.put( SIDES.UP_RIGHT, this.getModel().getAvailableAction( SIDES.UP_RIGHT ) );
		availableActions.put( SIDES.LEFT, this.getModel().getAvailableAction( SIDES.LEFT ) );
		availableActions.put( SIDES.UP_LEFT, this.getModel().getAvailableAction( SIDES.UP_LEFT ) );
		availableActions.put( SIDES.UP, this.getModel().getAvailableAction( SIDES.UP ) );
		availableActions.put( SIDES.DOWN, this.getModel().getAvailableAction( SIDES.DOWN ) );
		availableActions.put( SIDES.DOWN_LEFT, this.getModel().getAvailableAction( SIDES.DOWN_LEFT ) );
		availableActions.put( SIDES.DOWN_RIGHT, this.getModel().getAvailableAction( SIDES.DOWN_RIGHT ) );

		return availableActions;
	}

	private boolean tryToMoveWhenWalking( SIDES side, SIDES side2 )
	{
		//nothing when moving up or down
		if( side == SIDES.DOWN )
		{
			return false;
		}

		CavemanModel caveman = this.model;

		if( caveman.canWalkOrSwim( side ) )
		{
			caveman.move( side );
		}
		else if( caveman.canSwim( side ) )
		{
			caveman.move( side );
			caveman.putTile( SIDES.negate( side ) );

		}
		else if( side2 != null && caveman.canSwim( side2 ) )
		{
			caveman.move( side2 );
			caveman.putTile( SIDES.negate( side2 ) );
		}
		else if( side2 != null && caveman.canWalkOrSwim( side2 ) )
		{
			caveman.move( side2 );
		}
		return true;
	}

	private boolean tryToMoveInWater( SIDES side, SIDES side2 )
	{
		CavemanModel caveman = this.model;

		if( side != null )
		{
			if( caveman.canSwim( side ) || caveman.canWalk( side ) )
			{

				caveman.move( side );
				caveman.leaveTile( SIDES.negate( side ) );
			}
			else if( side2 != null && caveman.canWalk( side2 ) )
			{
				caveman.move( side2 );
			}
			return true;
		}
		return false;
	}

	public void cavemanSelected( boolean b )
	{
		this.getState().cavemanSelected = b;
		if( b )
		{
			this.model.getEvents().updateAll( ACTIVATE_ACTION.FINGER_DOWN_ON_CAVEMAN, 0, 0 );
		}
	}

	public void actionPressed( SIDES side, CavemanAction availableAction )
	{
		if( availableAction != null )
		{
			availableAction.doAction( this.model, this.model.getMap(), side );
		}
	}
}
