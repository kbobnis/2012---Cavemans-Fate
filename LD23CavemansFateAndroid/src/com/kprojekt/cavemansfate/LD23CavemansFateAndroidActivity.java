package com.kprojekt.cavemansfate;

import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class LD23CavemansFateAndroidActivity extends AndroidApplication
{
	private AdView adView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		// Create the layout
		RelativeLayout layout = new RelativeLayout( this );

		// Do the stuff that initialize() would do for you
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;
		config.useCompass = false;
		config.useWakelock = false;
		config.useGL20 = false;
		String displayLanguage = Locale.getDefault().getISO3Language();
		View gameView = initializeForView( new CavemansFate( displayLanguage ), config );

		// Create and setup the AdMob view
		//AdManager.setTestDevices( new String[] {AdManager.TEST_EMULATOR});

		//		adView = new AdView(this, AdSize.SMART_BANNER, "k278436533b84c7b1bfeb8b77383e36b");
		//		AdRequest adRequest = new AdRequest();
		//		adRequest.addTestDevice("8214C563377ADF24D0E2754B98775413");
		//		adView.loadAd(adRequest);
		//		
		//
		//		// Add the AdMob view
		//		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
		//				RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		//		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		//		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//
		//		layout.addView(adView, adParams);

		// Add the libgdx view
		layout.addView( gameView );
		// Hook it all up
		setContentView( layout );

	}
}