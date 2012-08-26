package com.kprojekt.cavemansfate.MVC.cave.actions.menu;

/**
 * @author Philon
 */
public class ResetLevelAction extends MenuAction
{

	public ResetLevelAction()
	{
		super( "Reset level" );
	}

	@Override
	public void doAction()
	{
		for( MenuActionListener listener : this.listeners )
		{
			listener.resetLevelRequested();
		}
	}
}
