package com.kprojekt.cavemansfate.MVC.cave.actions.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PHilon
 */
public abstract class MenuAction
{
	protected List<MenuActionListener> listeners = new ArrayList<MenuActionListener>();
	private final String name;

	public MenuAction( String name )
	{
		this.name = name;
	}

	public abstract void doAction();

	public void addListener( MenuActionListener controller )
	{
		this.listeners.add( controller );

	}

}
