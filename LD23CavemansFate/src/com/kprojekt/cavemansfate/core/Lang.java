package com.kprojekt.cavemansfate.core;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * @author Philon
 */
public class Lang
{
	private HashMap<String, String> actualDict = new HashMap<String, String>();
	private HashMap<String, HashMap<String, String>> allDicts = new HashMap<String, HashMap<String, String>>();

	public Lang( String filePath, String localeName )
	{
		XmlReader reader = new XmlReader();
		Element parse;
		try
		{
			parse = reader.parse( new InputStreamReader( Gdx.files.internal( filePath ).read(), "UTF-8" ) );
		}
		catch( IOException e )
		{
			throw new RuntimeException( e );
		}

		HashMap<String, String> plDIct = new HashMap<String, String>();
		HashMap<String, String> enDIct = new HashMap<String, String>();

		this.allDicts.put( "pl", plDIct );
		this.allDicts.put( "en", enDIct );

		Element locale = parse.getChildByName( "locale" );

		for( int i = 0; i < locale.getChildCount(); i++ )
		{
			Element sentence = locale.getChild( i );
			String id = sentence.getAttribute( "name" );
			for( int j = 0; j < sentence.getChildCount(); j++ )
			{
				Element sentenceInLang = sentence.getChild( j );
				String langName = sentenceInLang.getName();
				String value = sentenceInLang.getAttribute( "value" );
				allDicts.get( langName ).put( id, value );
			}
		}
		this.actualDict = this.allDicts.get( localeName );
	}

	public String get( String string )
	{
		String result = this.actualDict.get( string );
		if( result == null )
		{
			throw new RuntimeException( "There is no translation for key (" + string + ")." );
		}
		return result;
	}

	public void changeLocale( String string )
	{
		this.actualDict = this.allDicts.get( string );
		Core.levels.reload();
	}

}
