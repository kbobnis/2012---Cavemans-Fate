package com.kprojekt.utils;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public class TiledMapVerificator
{
	/**
	 * This method will check map parameters necessesary for this game
	 * @param tmxFile 
	 * @return 
	 */
	public static boolean verificate( MyTiledMap tiledMap, String tmxFile )
	{
		try
		{
			checkLayersExistence( tiledMap );
			checkPlayerAndTeleportOnLayers( tiledMap );
			checkPlayerParameters( tiledMap );
			everythingHasName( tiledMap );
			everythingOnHas( tiledMap, MyTiledMap.BACKGROUND_LAYER_NAME, MyTiledMap.HARDNESS_NAME );

			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new RuntimeException( e );
		}
	}

	private static void everythingOnHas( MyTiledMap tiledMap, String backgroundLayerName, String value )
			throws Exception
	{
		TiledLayer backgroundLayer = tiledMap.getLayer( backgroundLayerName );
		for( int y = 0; y < backgroundLayer.tiles.length; y++ )
		{
			for( int x = 0; x < backgroundLayer.tiles[y].length; x++ )
			{
				int id = backgroundLayer.tiles[y][x];
				String valueString = tiledMap.getTileProperty( id, value );
				String name = tiledMap.getTileProperty( id, MyTiledMap.KEY_NAME );
				if( id != 0 && valueString == null )
				{
					throw new Exception( "Everything on (" + backgroundLayerName + ") layer should have " + value
							+ " paramter. Found tile with name (" + name + ") and id (" + id + ") that has no " + value
							+ ". You should remove it from this layer or add " + value + " parameter to it." );
				}
			}
		}
	}

	private static void everythingHasName( MyTiledMap tiledMap ) throws Exception
	{
		ArrayList<TiledLayer> layers = tiledMap.layers;
		for( TiledLayer tiledLayer : layers )
		{
			for( int y = 0; y < tiledLayer.tiles.length; y++ )
			{
				for( int x = 0; x < tiledLayer.tiles[y].length; x++ )
				{
					int id = tiledLayer.tiles[y][x];
					String name = tiledMap.getTileProperty( id, MyTiledMap.KEY_NAME );
					if( id != 0 && name == null )
					{
						throw new Exception( "Everything on background should have name paramter. Found tile with id ("
								+ id + ") and tile (" + x + "," + y + ") that has no name. " );
					}
				}
			}

		}

	}

	private static void checkPlayerParameters( MyTiledMap tiledMap ) throws Exception
	{
		int cavemanId;
		cavemanId = tiledMap.getId( MyTiledMap.KEY_NAME, MyTiledMap.PLAYER_NAME, MyTiledMap.PLAYER_LAYER_NAME );
		String cavemansDiggPowerString = tiledMap.getTileProperty( cavemanId, MyTiledMap.POWER_NAME );
		Integer.parseInt( cavemansDiggPowerString );
	}

	private static void checkPlayerAndTeleportOnLayers( MyTiledMap tiledMap ) throws Exception
	{
		//getting player id
		int playerId = tiledMap.getId( MyTiledMap.KEY_NAME, MyTiledMap.PLAYER_NAME, MyTiledMap.PLAYER_LAYER_NAME );
		//getting teleport id
		int teleportId = tiledMap.getId( MyTiledMap.KEY_NAME, MyTiledMap.TELEPORT_NAME, MyTiledMap.TELEPORT_LAYER_NAME );

		//checking if player is on playerLayer 
		tiledMap.getFirstPos( playerId, MyTiledMap.PLAYER_LAYER_NAME );
		//checking if teleport is on playerLayer 
		tiledMap.getFirstPos( teleportId, MyTiledMap.TELEPORT_LAYER_NAME );
	}

	private static void checkLayersExistence( MyTiledMap tiledMap )
	{
		String[] layersNames = { MyTiledMap.BACKGROUND_LAYER_NAME, MyTiledMap.PLAYER_LAYER_NAME };

		for( String layerName : layersNames )
		{
			tiledMap.getLayer( layerName );
		}
	}
}
