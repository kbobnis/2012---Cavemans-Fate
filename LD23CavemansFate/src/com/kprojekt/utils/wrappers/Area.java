package com.kprojekt.utils.wrappers;

/**
 * @author Philon 
 */
public class Area
{

	public Area( int minX, int minY, int maxX, int maxY )
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public int minX;
	public int maxX;
	public int minY;
	public int maxY;

}
