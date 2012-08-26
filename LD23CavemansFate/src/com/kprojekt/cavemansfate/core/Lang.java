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
	private HashMap<String, String> dict = new HashMap<String, String>();

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

		Element locale = parse.getChildByName( "locale" );

		for( int i = 0; i < locale.getChildCount(); i++ )
		{
			Element sentence = locale.getChild( i );
			Element sentenceInLang = sentence.getChildByName( localeName );
			dict.put( sentence.getAttribute( "name" ), sentenceInLang.getAttribute( "value" ) );
		}
	}

	public String get( String string )
	{
		String result = this.dict.get( string );
		if( result == null )
		{
			throw new RuntimeException( "There is no translation for key (" + string + ")." );
		}
		return result;
	}

}
