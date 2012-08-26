package com.kprojekt.utils.fixes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * 
 */
public class MyFont extends BitmapFont
{

	public MyFont( FileHandle internal, boolean b )
	{
		super( internal, b );
	}

	@Override
	public void setScale( float scaleX, float scaleY )
	{
		if( Math.abs( scaleX ) < 0.1 || Math.abs( scaleY ) < 0.1 )
			throw new RuntimeException( "THis scale is too low!: (" + scaleX + ", " + scaleY + ")" );
		super.setScale( scaleX, scaleY );
	}

	@Override
	public void setScale( float scaleXY )
	{
		if( Math.abs( scaleXY ) < 0.1 )
			throw new RuntimeException( "this scale is too low!: (" + scaleXY + ")" );
		super.setScale( scaleXY );
	}

}
