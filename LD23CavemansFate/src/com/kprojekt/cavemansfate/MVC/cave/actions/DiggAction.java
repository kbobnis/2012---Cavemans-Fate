package com.kprojekt.cavemansfate.MVC.cave.actions;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.cavemansfate.MVC.cave.triggersAndEvents.Trigger.ACTIVATE_ACTION;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * 
 */
public class DiggAction extends CavemanAction
{
	public static String name = "digg";

	/**
	 * Digging a hole underneath the caveman
	 */
	@Override
	public boolean doAction( CavemanModel caveman, MyTiledMap map, SIDES side )
	{
		int tileId;
		Vector3 pos = SIDES.add( side, caveman.pos, true );
		try
		{
			tileId = map.getId( (int)pos.x, (int)pos.y, MyTiledMap.BACKGROUND_LAYER_NAME );
			if( caveman.pickUpTile( tileId ) )
			{
				map.removeTile( pos.x, pos.y, MyTiledMap.BACKGROUND_LAYER_NAME );
				caveman.fall();
				caveman.getEvents().updateAll( ACTIVATE_ACTION.PICKUP_TILE, (int)pos.x, (int)pos.y );
				return true;
			}
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}

		return false;
	}

	@Override
	public String getName()
	{
		return DiggAction.name;
	}

}
