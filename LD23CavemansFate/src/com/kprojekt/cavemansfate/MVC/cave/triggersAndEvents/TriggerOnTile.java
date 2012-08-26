package com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents;

/**
 * @author Philon 
 */
public class TriggerOnTile extends Trigger
{
	private final int x;
	private final int y;

	public TriggerOnTile( ACTIVATE_ACTION activateAction, int x, int y )
	{
		super( activateAction );
		this.x = x;
		this.y = y;
	}

	@Override
	public void tryToActivate( ACTIVATE_ACTION triggerAction, int newX, int newY )
	{
		if( !this.active && x == newX && y == newY )
		{
			this.active = this.activateAction.willActivate( triggerAction );
		}
	}

}
