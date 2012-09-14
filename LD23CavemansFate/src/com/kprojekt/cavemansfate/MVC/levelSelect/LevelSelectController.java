package com.kprojekt.cavemansfate.MVC.levelSelect;

import com.kprojekt.cavemansfate.MVC.cave.CaveManagerListener;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.MenuActionListener;
import com.kprojekt.cavemansfate.MVC.levelSelect.view.LevelSelectView;

/**
 * @author Philon
 */
public class LevelSelectController implements CaveManagerListener, MenuActionListener
{

	private LevelSelectModel model;

	public LevelSelectController( LevelSelectModel model, LevelSelectView view )
	{
		this.model = model;
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
