package com.herl.mysec;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SecMainActivity extends Activity {
	
	public String strtime=null,stroven=null,strmicrowave=null,strstove=null,strfaucet=null,hh = null; 
	private static final String TEMP_FILENAME = "applianceData.txt";
	
	public String status2= "";

	private static final String TAG = null;

	public final static String EXTRA_MESSAGE = "com.herl.mysec.MESSAGE";
	
	MyFTPClient ftpclient = null;

	public void getstovestatus(View view) {
	    Intent intent = new Intent(this, GetovenstatusActivity.class);
	    String message = strtime+','+strstove;
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	

	public void getmicrowavestatus(View view) {
		 Intent intent = new Intent(this, MicrowaveMainActivity.class);
		    String message = strtime+','+strmicrowave;
		    intent.putExtra(EXTRA_MESSAGE, message);
		    startActivity(intent);
		}
		

	public void getfaucetstatus(View view)
	{
	    Intent intent = new Intent(this, FaucetMainActivity.class);
	    String message = strtime+','+strfaucet;
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	public void getovenstatus(View view)
	{
	    Intent intent = new Intent(this, GetovenstatusActivity.class);
	    String message = strtime+','+stroven;
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_main);
        ftpclient = new MyFTPClient();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
             new FtpTask().execute();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sec_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
    	int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        */
    	   switch (item.getItemId()) {
      //     case R.id.action_search:
      //         openSearch();
      //         return true;
           case R.id.action_settings:
               openSettings();
               return true;
           default:
               return super.onOptionsItemSelected(item);
    }
    }

    private void openSettings() {
		// TODO Auto-generated method stub
		
	}



	public static String getTempFilename() {
		return TEMP_FILENAME;
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
            View rootView = inflater.inflate(R.layout.fragment_sec_main, container, false);
            return rootView;
        }
    }

  //读SD中的文件  
    public String readFileSdcardFile(String fileName) throws IOException{   
      String res="";   
      try{   
             FileInputStream fin = new FileInputStream(fileName);   
      
             int length = fin.available();   
      
             byte [] buffer = new byte[length];   
             fin.read(buffer);       
      
             res = EncodingUtils.getString(buffer, "UTF-8");   
      
             fin.close();       
            }   
      
            catch(Exception e){   
             e.printStackTrace();   
            }   
            return res;   
    }  

	
    
 
    class FtpTask extends AsyncTask<Void, Void, MyFTPClient> {
    	protected MyFTPClient doInBackground(Void...connstr) {
    		
    		final String TAG = null;
    		MyFTPClient ftpconnect =new MyFTPClient();
    		
    		boolean status = false;
    		status = ftpconnect.ftpConnect("002.3vftp.com", "smartcoach", "Q!w2e3r4", 21);
    		if (status == true) {
    			Log.d(TAG, "Connection Success");
    		}
    		else 
    			{
    			
    					Log.d(TAG, "Connection failed");
    				}
    		
    		String sdpath1 = Environment.getExternalStorageDirectory().getPath();
			sdpath1=sdpath1+"/download";
			status = ftpconnect.ftpDownload(sdpath1, TEMP_FILENAME);					
			if (status == true) {
				Log.d(TAG, "Download Success");
				
			} else {
				Log.d(TAG, "Download failed");
			}
			
			try {
				String tt =sdpath1+"/"+TEMP_FILENAME;
				 status2 = readFileSdcardFile(tt);
				 String[] aa = status2.split("&");
				 for (int i = 0 ; i <aa.length ; i++ ) {
				      System.out.println("--"+aa[i]); 
				    }
				 if (aa[1]!=null&&aa[1].contains("update")) {
					 
					 strtime=aa[1].substring(7);
					 
				 }
				 if (aa[2]!=null&&aa[2].contains("oven")) {
					 stroven = aa[2].substring(5);
					 
				 }
				 if (aa[3]!=null&&aa[3].contains("microwave")) {
					 strmicrowave = aa[3].substring(10);
					 
				 }
				 if (aa[4]!=null&&aa[4].contains("stove")) {
					 strstove = aa[4].substring(6);
					 
				 }
				 if (aa[5]!=null&&aa[5].contains("faucet")) {
					 strfaucet = aa[5].substring(7);
					 
				 }
				 hh = strtime+stroven+strmicrowave+strstove+strfaucet;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
    		
            return ftpconnect;
         }

         protected void onPostExecute(MyFTPClient result) {
         //    Log.v("FTPTask","FTP connection complete");
     //        ftpclient = result;
        	 ftpclient.ftpDisconnect();
             //Where ftpClient is a instance variable in the main activity
         }
   
		
    }
}




