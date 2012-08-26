package com.kprojekt.cavemansfate.MVC.cave.actions.menu;

/**
 * @author Philon
 */
public class GotoMenuAction extends MenuAction
{

	public GotoMenuAction()
	{
		super( "Go to menu" );
	}

	@Override
	public void doAction()
	{
		for( MenuActionListener listener : this.listeners )
		{
			listener.resetLevelRequested();
			listener.gotoMenuRequested();
		}
	}

}
