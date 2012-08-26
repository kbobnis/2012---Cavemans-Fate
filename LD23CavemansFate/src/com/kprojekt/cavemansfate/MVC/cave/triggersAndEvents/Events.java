package com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents;

import java.util.ArrayList;
import java.util.List;

import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.cavemansfate.MVC.screens.ScreensManager;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.StringElement;

/**
 * @author Philon
 */
public class Events extends ArrayList<Event>
{
	private static final long serialVersionUID = 1L;

	public Events()
	{

	}

	/**
	 * Creating event with just one string element showing at the start of the level 
	 */
	public Events( String string )
	{
		List<ScreenElement> elements = new ArrayList<ScreenElement>();
		elements.add( new StringElement( string ) );
		ScreensManager beforeManager = new ScreensManager( elements );

		this.add( new Event( beforeManager, new Trigger( ACTIVATE_ACTION.JUST_ON ) ) );
	}

	public void updateAll( ACTIVATE_ACTION action, int x, int y )
	{
		for( Event event : this )
		{
			event.update( action, x, y );
		}
	}

	public Event getFirstActive()
	{
		for( Event event : this )
		{
			if( event.isActive() )
			{
				return event;
			}
		}
		return null;
	}

	public void resetAll()
	{
		for( Event event : this )
		{
			event.reset();
		}
	}

	public boolean isAnythingToRender()
	{
		return getFirstActive() != null;
	}

	public void renderNext( float delta )
	{
		Event firstActive = getFirstActive();
		if( firstActive != null )
		{
			firstActive.render( delta );
		}
	}

}
