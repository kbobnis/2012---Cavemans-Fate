package com.kprojekt.utils.fixes;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileSet;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

/**
 * @author Philon
 */
public class MyTileAtlas extends TileAtlas
{

	public MyTileAtlas( TiledMap map, TextureAtlas textureAtlas )
	{
		for( TileSet set : map.tileSets )
		{
			List<AtlasRegion> atlasRegions = textureAtlas.getRegions();
			for( AtlasRegion reg : atlasRegions )
			{
				regionsMap.put( reg.index + set.firstgid, reg );
				if( !textures.contains( reg.getTexture() ) )
				{
					textures.add( reg.getTexture() );
				}
			}
		}
	}
}
