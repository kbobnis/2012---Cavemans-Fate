package com.kprojekt.utils.wrappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.utils.fixes.MyTileAtlas;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public class TileMapRendererWrapper
{
	private TileMapRenderer tileMapRenderer;

	private final MyTiledMap map;
	private final MyTileAtlas atlas;
	private final int tilesPerBlockY;
	private final int tilesPerBlockX;
	private final float unitsPerTileX;
	private final float unitsPerTileY;

	public TileMapRendererWrapper( MyTiledMap map, TextureAtlas atlas, int tilesPerBlockX, int tilesPerBlockY,
			float unitsPerTileX, float unitsPerTileY )
	{
		this.map = map;
		this.unitsPerTileX = unitsPerTileX;
		this.unitsPerTileY = unitsPerTileY;
		this.tilesPerBlockX = tilesPerBlockX;
		this.tilesPerBlockY = tilesPerBlockY;

		this.atlas = new MyTileAtlas( map, atlas );
		tileMapRenderer = new TileMapRenderer( map, this.atlas, tilesPerBlockX, tilesPerBlockY, unitsPerTileX,
				unitsPerTileY );

	}

	public void updateCamera( Matrix4 combined )
	{
		tileMapRenderer.getProjectionMatrix().set( combined );
	}

	public void render( CameraWrapper camera )
	{
		if( this.map.wasChanged() )
		{
			Matrix4 projectionMatrix = tileMapRenderer.getProjectionMatrix();
			//if the map changed, then this has to be invoked for changes to apply
			tileMapRenderer.dispose();
			tileMapRenderer = new TileMapRenderer( map, atlas, tilesPerBlockX, tilesPerBlockY, unitsPerTileX,
					unitsPerTileY );
			tileMapRenderer.getProjectionMatrix().set( projectionMatrix.getValues() );
		}
		Vector3 vec = new Vector3();
		camera.unproject( vec );

		tileMapRenderer.render( vec.x, vec.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
	}
}
