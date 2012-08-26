package com.kprojekt.utils.fixes;

import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Philon
 */
public class MyTiledMap extends TiledMap
{
	public static final String KEY_NAME = "name";

	public static final String TELEPORT_NAME = "teleport";
	public static final String PLAYER_NAME = "player";
	public static final String MUD_NAME = "mud";

	public static String BACKGROUND_LAYER_NAME = "background";
	public static String PLAYER_LAYER_NAME = "player";
	public static String PICKABLEITEMS_LAYER_NAME = "pickableItems";

	public static final String TELEPORT_LAYER_NAME = "teleport";
	public static final String POWER_NAME = "power";
	public static final String HARDNESS_NAME = "hardness";
	public static final String WALKABLE_NAME = "walkable";
	public static final String SWIMMABLE_NAME = "swimmable";
	public static final String WATER_NAME = "water";
	public static final String ARROW_RIGHT_NAME = "arrow_right";
	public static final String PICKAXE_NAME = "woodaxe";
	public static final String MILDROCK_NAME = "mildRock";

	private boolean mapChanged;

	public MyTiledMap( TiledMap createMap ) throws Exception
	{
		this.init( createMap );
	}

	private void init( TiledMap createMap )
	{
		this.height = createMap.height;
		this.layers = createMap.layers;
		this.objectGroups = createMap.objectGroups;
		this.orientation = createMap.orientation;
		this.properties = createMap.properties;
		this.tileHeight = createMap.tileHeight;
		this.tileSets = createMap.tileSets;
		this.tileWidth = createMap.tileWidth;
		this.tmxFile = createMap.tmxFile;
		this.width = createMap.width;

		try
		{
			Field declaredField = createMap.getClass().getDeclaredField( "tileProperties" );
			declaredField.setAccessible( true );
			Object object = declaredField.get( createMap );
			declaredField.set( this, object );
		}
		catch( SecurityException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		catch( NoSuchFieldException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		catch( IllegalArgumentException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
		catch( IllegalAccessException e )
		{
			// TODO @Krzysiek logger
			e.printStackTrace();
		}
	}

	public void changed()
	{
		this.mapChanged = true;
	}

	public int getId( String key, String value, String layerName ) throws Exception
	{
		TiledLayer layer = getLayer( layerName );
		int w = layer.tiles.length;
		for( int x = 0; x < w; x++ )
		{
			int h = layer.tiles[x].length;
			for( int y = 0; y < h; y++ )
			{
				int actualId = layer.tiles[x][y];
				String tmpValue = getTileProperty( actualId, key );
				if( tmpValue != null && tmpValue.equalsIgnoreCase( value ) )
				{
					return actualId;
				}
			}
		}
		throw new Exception( "Key: (" + key + "), value: (" + value + ") wasn't found in the map in layer ("
				+ layerName + ")." );
	}

	public TiledLayer getLayer( String string )
	{
		for( TiledLayer layer : this.layers )
		{
			if( layer.name.equalsIgnoreCase( string ) )
				return layer;
		}
		throw new RuntimeException( "There is no layer named: (" + string + ") in map tmx." );
	}

	public Vector3 getFirstPos( int id, String layerName ) throws Exception
	{
		TiledLayer layer = getLayer( layerName );
		int w = layer.tiles.length;
		for( int x = 0; x < w; x++ )
		{
			int h = layer.tiles[x].length;
			for( int y = 0; y < h; y++ )
			{
				int actualId = layer.tiles[x][y];
				if( actualId == id )
				{
					return new Vector3( y, x, 0 );
				}
			}
		}
		throw new Exception( "Tile with id(" + id + ") wasn't found in the map in layer (" + layerName + ")." );

	}

	public void moveTile( int oldX, int oldY, int newX, int newY, String layerName )
	{
		int tmp = 0;
		TiledLayer layer = getLayer( layerName );
		layer.tiles[newY][newX] = layer.tiles[oldY][oldX];
		layer.tiles[oldY][oldX] = tmp;
		this.mapChanged = true;
	}

	public int getId( int x, int y, String layersName ) throws Exception
	{
		if( x < 0 || y < 0 || y >= getLayer( layersName ).tiles.length || x >= getLayer( layersName ).tiles[y].length )
		{
			throw new Exception( "You can not access this (" + x + ")(" + y + ") coordinate, out of map." );
		}
		return getLayer( layersName ).tiles[y][x];
	}

	/**
	 * @throws Exception if x, y out of bounds
	 */
	public String getValue( int newX, int newY, String key, String layerName )
	{
		//get id of the given position
		int tileId;
		try
		{
			tileId = this.getId( newX, newY, layerName );
			return this.getTileProperty( tileId, key );
		}
		catch( Exception e )
		{
			return null;
		}
	}

	public boolean wasChanged()
	{
		return this.mapChanged;
	}

	public void removeTile( float x, float y, String layerName )
	{
		this.getLayer( layerName ).tiles[(int)y][(int)x] = 0;
		this.changed();
	}

	public void reset( TiledMap createMap )
	{
		this.init( createMap );
	}

}
