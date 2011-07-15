package com.nid.mrl.Spell3D;

import android.util.Log;

/** DebugLog is a support class for the QCAR samples applications.
 * 
 *  Exposes functionality for logging.
 *  
 * */

public class DebugLog
{
    private static final String LOGTAG = "QCAR";

    /** Logging functions to generate ADB logcat messages. */

    public static final void LOGE(String nMessage)
    {
        Log.e(LOGTAG, nMessage);			//error
    }
    
    public static final void LOGW(String nMessage)
    {
        Log.w(LOGTAG, nMessage);			//warn
    }
    
    public static final void LOGD(String nMessage)
    {
        Log.d(LOGTAG, nMessage);			//debug
    }
    
    public static final void LOGI(String nMessage)
    {
        Log.i(LOGTAG, nMessage);			//info
    }
}