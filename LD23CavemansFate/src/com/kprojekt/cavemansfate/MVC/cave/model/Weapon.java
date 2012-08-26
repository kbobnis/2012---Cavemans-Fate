package com.kprojekt.cavemansfate.MVC.cave.model;

import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * this shouldn't be used now (in the maps)
 * @author Philon
 */
public class Weapon
{
	private final MyTiledMap tiledMap;
	private String name;
	private int power;

	public Weapon( int id, MyTiledMap tiledMap )
	{
		this.tiledMap = tiledMap;
		this.name = this.tiledMap.getTileProperty( id, MyTiledMap.KEY_NAME );
		this.power = Integer.parseInt( this.tiledMap.getTileProperty( id, MyTiledMap.POWER_NAME ) );
	}

	public int power()
	{
		return this.power;
	}

	public String getName()
	{
		return this.name;
	}

}
