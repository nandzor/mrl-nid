/*==============================================================================
            
@file 
    FrameMarkersRenderer.java

@brief
    Sample for FrameMarkers

==============================================================================*/

package com.nid.mrl.eventTrigger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.qualcomm.QCAR.QCAR;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


/** The renderer class for the FrameMarkers sample. */
public class FrameMarkersRenderer implements GLSurfaceView.Renderer  
{
    public boolean mIsActive = false;
    int flag=0;
    /** Native function for initializing the renderer. */
    public native void initRendering();
    
    
    /** Native function to update the renderer. */
    public native void updateRendering(int width, int height);

    
    /** Called when the surface is created or recreated. */
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        DebugLog.LOGD("GLRenderer::onSurfaceCreated");

        // Call native function to initialize rendering:
        initRendering();
        
        // Call QCAR function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        QCAR.onSurfaceCreated();
    }
    
    
    /** Called when the surface changed size. */
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        DebugLog.LOGD("GLRenderer::onSurfaceChanged");
        
        // Call native function to update rendering when render surface parameters have changed:
        updateRendering(width, height);

        // Call QCAR function to handle render surface size changes:
        QCAR.onSurfaceChanged(width, height);
       // Log.i("------Flag inside another function----- ", " "+flag);  this implies that only actionTrigger method is called in loop
    }
    
    //Intent intent = null;
    //Intent intent=new Intent();
    Intent intent=null;
    
    public void actionTrigger(int idMarker)
    {
    	//if(flag==0)
    	//{
    	Log.i("value of MarkerID : ", " " + idMarker);
    	    	
    	switch(idMarker)
    	{
    	
    	case 0:
    		//System.out.print("Nikhil Agrawal..1");
    		intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
    		startActivity(intent);
    	//	Log.i("Nikhil Case 0", " "); 
    		
    			//opening browser and website
    			break;
    	case 1:
    				
    		intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
    		startActivity(intent);
    			// opening contacts 
    				
    			break;
    	case 2:
    		
    		intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
    		startActivity(intent);
    			// opening Gallery
    	
    			break;
    	case 3:
    		
    		intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:(+49)12345789"));
			startActivity(intent);
    			//  Dialing number
			
 				break;
 				
    	default:
					break;		
    	}	
    	//}
    	//flag++;
    	//Log.i("Value of Flag: ", " "+ flag);
    }
    
    private void startActivity(Intent intent2) {
		// TODO Auto-generated method stub
		
	}


	/** The native render function. */    
    public native void renderFrame();
    
    
    /** Called to draw the current frame. */
    public void onDrawFrame(GL10 gl)
    {
        if (!mIsActive)
            return;

        // Call our native function to render content
        renderFrame();
    }
   
   
  
}
