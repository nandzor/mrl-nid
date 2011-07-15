package org.hi2.toast;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class toast extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.btnTakeScreenshot);
        final View content = findViewById(R.id.layoutroot);
        content.setDrawingCacheEnabled(true);
        button.setOnClickListener(new View.OnClickListener()
        
        {
        //@Override
        public void onClick(View v) {
         Bitmap bitmap = content.getDrawingCache(true);
         File file = new File("/sdcard/screeshot.png");
         Context context = getApplicationContext();
         CharSequence text = "file created";
         int duration = Toast.LENGTH_SHORT;

         Toast toast = Toast.makeText(context, text, duration);
         toast.show();
         try 
         {
        	 text = "file created2";
             duration = Toast.LENGTH_SHORT;

             toast = Toast.makeText(context, text, duration);
                      
           	 
        	 file.createNewFile();
        	 
             FileOutputStream ostream = new FileOutputStream(file);           
             
             bitmap.compress(CompressFormat.PNG, 100, ostream);
            
             ostream.close();
           
         } 
         catch (Exception e) 
         {
          System.out.print("error");
             e.printStackTrace();
         }
  }
  });         
       
    }
}