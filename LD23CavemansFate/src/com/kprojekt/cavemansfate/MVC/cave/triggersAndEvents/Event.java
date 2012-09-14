package com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents;

import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.cavemansfate.MVC.screens.ScreensManager;

/**
 * @author Philon
 */
public class Event
{

	private Trigger trigger;
	private final ScreensManager screensManager;
	private boolean isCompleted;

	public Event( ScreensManager screensManager, Trigger triggerOn )
	{
		this.screensManager = screensManager;
		this.trigger = triggerOn;
	}

	public boolean isActive()
	{
		return this.trigger.isActivated() && !this.isCompleted;
	}

	public void update( ACTIVATE_ACTION triggerAction, int newX, int newY )
	{
		if( !this.isCompleted )
		{
			this.trigger.tryToActivate( triggerAction, newX, newY );
		}
	}

	public void render( float delta )
	{
		if( !this.screensManager.isDismissed() )
		{
			this.screensManager.render( delta, 0, 0 );
		}
		else
		{
			this.isCompleted = true;
		}
	}

	public void reset()
	{
		this.isCompleted = false;
		this.screensManager.dismiss( false );
		this.trigger.active = false;
	}
}
