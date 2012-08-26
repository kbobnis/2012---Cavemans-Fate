package com.kprojekt.cavemansfate.MVC.cave.model;

/**
 * @author Philon
 */
public class CaveState
{
	private boolean backgroundSelected;
	private int touchedX;
	private int touchedY;

	public void backgroundSelected( int x, int y )
	{
		this.backgroundSelected = true;
		this.touchedX = x;
		this.touchedY = y;
	}

	public void backgroundUnselected()
	{
		this.backgroundSelected = false;
	}

	public boolean isBackgroundSelected()
	{
		return this.backgroundSelected;
	}

	public float getTouchedX()
	{
		return this.touchedX;
	}

	public float getTouchedY()
	{
		return this.touchedY;
	}

}
