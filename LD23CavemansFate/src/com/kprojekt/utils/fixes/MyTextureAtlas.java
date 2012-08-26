package com.kprojekt.utils.fixes;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * @author Philon 
 */
public class MyTextureAtlas extends TextureAtlas
{

	private final String packfilePath;

	public MyTextureAtlas( String packfilePath )
	{
		super( packfilePath );
		this.packfilePath = packfilePath;
	}

	@Override
	public AtlasRegion findRegion( String name )
	{
		AtlasRegion findRegion = super.findRegion( name );
		if( findRegion == null )
		{
			throw new RuntimeException( "Couldn't find region of name (" + name + "). in texture atlas ("
					+ this.packfilePath + ")" );
		}
		return findRegion;
	}
}
