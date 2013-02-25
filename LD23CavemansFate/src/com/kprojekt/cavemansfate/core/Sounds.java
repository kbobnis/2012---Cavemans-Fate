package com.kprojekt.cavemansfate.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * 
 */
public class Sounds
{

	private Sound walk;
	private Sound diggMud;
	private Sound putMud;
	private Sound swim;
	private Sound intoLevel;
	private Sound exitLevel;

	public Sounds()
	{
		this.walk = Gdx.audio.newSound( Gdx.files.internal( "sounds/step_on_all.mp3" ) );
		this.diggMud = Gdx.audio.newSound( Gdx.files.internal( "sounds/mud_take.mp3" ) );
		this.putMud = Gdx.audio.newSound( Gdx.files.internal( "sounds/mud_leave.mp3" ) );
		this.swim = Gdx.audio.newSound( Gdx.files.internal( "sounds/water_move.mp3" ) );
		this.intoLevel = Gdx.audio.newSound( Gdx.files.internal( "sounds/into_level.mp3" ) );
		this.exitLevel = Gdx.audio.newSound( Gdx.files.internal( "sounds/exit_level_piano.mp3" ) );
	}

	public Sound getWalk()
	{
		return this.walk;
	}

	public Sound getDigg()
	{
		return this.diggMud;
	}

	public Sound getPutMud()
	{
		return this.putMud;
	}

	public Sound getSwim()
	{
		return this.swim;
	}

	public Sound getIntoLevel()
	{
		return this.intoLevel;
	}

	public Sound getExitLevel()
	{
		return this.exitLevel;
	}
}
