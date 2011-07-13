/*==============================================================================
            Copyright (c) 2010-2011 QUALCOMM Incorporated.
            All Rights Reserved.
            Qualcomm Confidential and Proprietary
            
@file 
    Renderer3D.java

@brief
    
S
==============================================================================*/


package com.nid.mrl.Spell3D;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import android.database.Cursor;
import android.opengl.GLSurfaceView;

import com.qualcomm.QCAR.QCAR;


/** The renderer class for the Spell3D sample. */
public class Renderer3D implements GLSurfaceView.Renderer
{
    public boolean mIsActive = false;
    
    
    /**Will contain the concatenated tracked markerIds**/
    StringBuilder markerid= new StringBuilder(5);

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
    
    /**Called to concatenate the tracked MarkerIds */
    public void setMarkerId(int temp,int index)
    {
    	
    	if(index==0)
    	{
    		int len=markerid.length();
    		markerid.delete(0, len);
    		markerid.append(temp);
    		DebugLog.LOGD("Id:"+markerid.toString());
    	}
    	else
    	{
    		markerid.append(temp);
    		DebugLog.LOGD("Id:"+markerid.toString());
    	}
    	return;
    }
    
    /**Called to get the objectName corresponding to tracked id's**/
    public int getObjectID()
    {
    		
    		String markeridStr=markerid.toString();
    		int objectID=4;
    		String objectidArray[]=new String[7];
    		objectidArray[0]="7019";		//Hat
    		objectidArray[1]="2019";		//Cat
    		objectidArray[2]="2017";		//Car
    		for(int i=0;i<3;i++)
    		{		
    			if(markeridStr.equals(objectidArray[i]))
    			{
    				objectID=i;
    			}
    		}
    		
    			
    	
    		return objectID;

    }
    
    
}
