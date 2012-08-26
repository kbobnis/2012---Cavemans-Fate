package com.kprojekt.utils.ui.base;

import java.awt.Dimension;

import com.badlogic.gdx.InputProcessor;

/**
 * @author Philon 
 */
public interface UIElementInterface extends InputProcessor
{
	public void render( float delta );

	public Dimension getCoords();

}
