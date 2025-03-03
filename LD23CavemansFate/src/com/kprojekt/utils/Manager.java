package com.kprojekt.utils;

/**
 * @author Philon 
 */
public abstract class Manager
{
	public abstract void render( float delta, int x, int y );

	public void touchDown( int x, int y ){}

	public void touchUp( int x, int y ){}

	public void dragged( int x, int y, int howX, int howY ){}

	public void backPressed(){}

}
