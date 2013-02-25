package com.kprojekt.cavemansfate.MVC.cave.actions;

import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.cavemansfate.core.Core;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public class PutTileAction extends CavemanAction
{

	public static String name = "leaveTile";

	@Override
	public boolean doAction( CavemanModel caveman, MyTiledMap map, SIDES side )
	{
		int pickupedTileId = caveman.getPickupedTileId();
		if( pickupedTileId == 0 )
		{
			return false;
		}
		caveman.unpackTile();
		TiledLayer layer = map.getLayer( MyTiledMap.BACKGROUND_LAYER_NAME );
		Vector3 tilePos = SIDES.add( side, caveman.pos, true );
		//lets check if there is already something on the way where we want to put a tile
		try
		{
			int actualId = map.getId( (int)tilePos.x, (int)tilePos.y, MyTiledMap.BACKGROUND_LAYER_NAME );
			if( actualId != 0 )
			{
				return false;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
		layer.tiles[(int)tilePos.y][(int)tilePos.x] = pickupedTileId;
		Core.sounds.getPutMud().play();
		map.changed();
		return true;
	}

	@Override
	public String getName()
	{
		return PutTileAction.name;
	}

}