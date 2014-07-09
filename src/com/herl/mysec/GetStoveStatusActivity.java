package com.herl.mysec;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class GetStoveStatusActivity extends Activity {
	public String strtime=null,str1=null,str2=null,stronoff=null,strsafe=null,str5 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_stove_status);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		} // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		
        Intent intent = getIntent();
        String message = intent.getStringExtra(SecMainActivity.EXTRA_MESSAGE);
        
        String[] aa = message.split(",");
		 for (int i = 0 ; i <aa.length ; i++ ) {
		      System.out.println("--"+aa[i]); 
		    }
		 if (aa[0]!=null)
			 strtime= aa[0].substring(0);
		 
		 if (aa[1]!=null&&aa[1].contains("-")) {
			 
			 str1=aa[1].substring(0);
			 
		 }
		 if (aa[2]!=null&&aa[2].contains("-")) {
			 str2= aa[2].substring(0);
			 
		 }
		 if (aa[3]!=null&&aa[3].contains("off")) {
			 stronoff = aa[3].substring(0);
			 
		 }
		 if (aa[4]!=null) {
			 strsafe = aa[4].substring(0);
			 
		 }
		 if (aa[5]!=null) {
			 str5 = aa[5].substring(0);
			 
		 }

		 TextView tv1 = (TextView) findViewById(R.id.textView5);
		 tv1.setText(strtime);
        
		 TextView tv2 = (TextView) findViewById(R.id.textView6);
		 tv2.setText(strsafe);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_stove_status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_get_stove_status, container, false);
			return rootView;
		}
	}

}
