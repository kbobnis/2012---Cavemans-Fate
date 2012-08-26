package com.kprojekt.cavemansfate.MVC.levelSelect;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.view.LevelSelectView;
import com.kprojekt.utils.Manager;

/**
 * @author PHilon
 */
public class LevelSelectManager extends Manager
{
	private LevelSelectView levelSelectView;
	private LevelSelectModel model;
	private LevelSelectController levelSelectController;

	public LevelSelectManager( CaveManager[] levels )
	{
		this.model = new LevelSelectModel( levels );
		this.levelSelectView = new LevelSelectView( this.model );
		this.levelSelectController = new LevelSelectController( this.model, this.levelSelectView );
	}

	@Override
	public void render( float delta )
	{
		MenuItemModel activeLevel = this.model.getActiveLevel();
		if( activeLevel == null )
		{
			Gdx.input.setInputProcessor( this.levelSelectController );
			this.levelSelectView.render( delta );
		}
		else
		{
			Gdx.input.setInputProcessor( activeLevel.getCaveManager().getMainInputHandler() );
			activeLevel.getCaveManager().render( delta );
		}
	}

	public LevelSelectController getController()
	{
		return this.levelSelectController;
	}
}
