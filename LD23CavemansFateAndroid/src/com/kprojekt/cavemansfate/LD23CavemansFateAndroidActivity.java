package com.kprojekt.cavemansfate;

import java.util.Locale;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class LD23CavemansFateAndroidActivity extends AndroidApplication
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = false;
		config.useWakelock = false;
		config.useGL20 = false;
		String displayLanguage = Locale.getDefault().getISO3Language();

		initialize( new CavemansFate(displayLanguage), config );
	}
}