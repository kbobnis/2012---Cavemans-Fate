package com.kprojekt.cavemansfate.MVC.cave.menu;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.MVC.cave.actions.menu.MenuAction;

/**
 * @author PHilon 
 */
public class Button
{

	private final int x;
	private final int y;
	private final String name;
	private final MenuAction action;
	private final int maxX;
	private final int maxY;
	private final static float buttonScale = 2;

	public Button( int x, int y, String name, MenuAction action )
	{
		this.x = x;
		this.y = y;
		this.name = name;
		MVCsManager.font.setScale( Button.buttonScale );
		this.maxX = (int)(x + MVCsManager.font.getBounds( name ).width);
		this.maxY = (int)(y + MVCsManager.font.getBounds( name ).height);
		MVCsManager.font.setScale( 1 );
		this.action = action;
	}

	public void render()
	{
		MVCsManager.font.setScale( Button.buttonScale );
		MVCsManager.font.draw( MVCsManager.spriteBatch, this.name, x, Gdx.graphics.getHeight() - y );
		MVCsManager.font.setScale( 1 );
	}

	public boolean touchUp( int x2, int y2 )
	{
		if( x2 > this.x && x2 < this.maxX && this.y < y2 && this.maxY > y2 )
		{
			action.doAction();
			return true;
		}
		return false;
	}

}
