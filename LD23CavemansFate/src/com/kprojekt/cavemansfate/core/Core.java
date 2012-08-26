package com.kprojekt.cavemansfate.core;

/**
 * Singleton class for all singletons, easy to mock
 * @author Philon
 */
public class Core
{
	public static Lang lang;
	public static Levels levels;

	public static void init( String xmlPath, String localeName )
	{
		Core.loadLang( xmlPath, localeName );
		Core.loadLevels( xmlPath );
	}

	private static void loadLevels( String xmlPath )
	{
		Core.levels = new Levels( xmlPath );
	}

	private static void loadLang( String xmlPath, String localeName )
	{
		Core.lang = new Lang( xmlPath, localeName );
	}

}
