/*==============================================================================
            
@file 
    Spell3D.cpp

@brief
    Sample for Spell3Ds

==============================================================================*/


#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

#include <QCAR/QCAR.h>
#include <QCAR/CameraDevice.h>
#include <QCAR/Renderer.h>
#include <QCAR/VideoBackgroundConfig.h>
#include <QCAR/Trackable.h>
#include <QCAR/Tool.h>
#include <QCAR/Tracker.h>
#include <QCAR/CameraCalibration.h>
#include <QCAR/Marker.h>

#include "SampleUtils.h"
#include "Texture.h"
#include "CubeShaders.h"
#ifdef __cplusplus
#include "rCar.h"
#include "HatNew.h"
#include "Cat.h"
extern "C"
{
#endif

// Textures:
int textureCount                = 0;
Texture** textures              = 0;

// OpenGL ES 2.0 specific:
unsigned int shaderProgramID    = 0;
GLint vertexHandle              = 0;
GLint normalHandle              = 0;
GLint textureCoordHandle        = 0;
GLint mvpMatrixHandle           = 0;

// Screen dimensions:
unsigned int screenWidth        = 0;
unsigned int screenHeight       = 0;

// Indicates whether screen is in portrait (true) or landscape (false) mode
bool isActivityInPortraitMode   = false;

// The projection matrix used for rendering virtual objects:
QCAR::Matrix44F projectionMatrix;
QCAR::Matrix44F modelViewRender;
// Constants:
static const float kLetterScale        = 100.0f;
static const float kLetterTranslate    = 0.0f;


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_setActivityPortraitMode(JNIEnv *, jobject, jboolean isPortrait)
{
    isActivityInPortraitMode = isPortrait;
}
void addMatrix(float *matrixA, float *matrixB, float *matrixC)
{
    for (int i = 0; i < 16; i++)
        matrixC[i] = matrixA[i]+matrixB[i];
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Renderer3D_renderFrame(JNIEnv *env, jobject obj)
{
    //LOG("Java_com_nid_mrl_Spell3D_Renderer3D_GLRenderer_renderFrame");
    jclass activityClass = env->GetObjectClass(obj);
    jmethodID setMarkerIdMethodId = env->GetMethodID(activityClass,
                                                    "setMarkerId", "(II)V");
    jmethodID getObjectIDMethodID = env->GetMethodID(activityClass,
                                                    "getObjectID", "()I");
    // Clear color and depth buffer 
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Render video background:
    QCAR::State state = QCAR::Renderer::getInstance().begin();

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    int flag_tracking=2;        //flag signifying whether it is first iteration of for loop (=0)or not
    int objID=3; 
	// Did we find any trackables this frame?
    for(int tIdx = 0; tIdx < state.getNumActiveTrackables(); tIdx++)
    {
        LOG("The Number of Tracked Alphabets:%d",state.getNumActiveTrackables());
        QCAR::Matrix44F result_sum_modelViewMatrices;
        // Get the trackable:
        const QCAR::Trackable* trackable = state.getActiveTrackable(tIdx);
        QCAR::Matrix44F modelViewMatrix = QCAR::Tool::convertPose2GLMatrix(trackable->getPose()); 
        SampleUtils::scalePoseMatrix(kLetterScale, kLetterScale, kLetterScale, &modelViewMatrix.data[0]);    
        //SampleUtils::rotatePoseMatrix(45.f, 0.f, 0.f, 0.f,
        //                      &modelViewMatrix.data[0]);
        if((state.getNumActiveTrackables()==3)&&(tIdx==1)){
                modelViewRender=modelViewMatrix;
        }
        //Choose the texture based on the target name:
        int textureIndex = 0;
        
        // Check the type of the trackable:
        assert(trackable->getType() == QCAR::Trackable::MARKER);
        const QCAR::Marker* marker = static_cast<const QCAR::Marker*>(trackable);
        textureIndex = marker->getMarkerId();
        
        //Setting Flag and calling setMarkerIdMethodId of Renderer3D.java
        if(tIdx==0)
            flag_tracking=0;
        else
            flag_tracking=2;
            
        env->CallVoidMethod(obj, setMarkerIdMethodId,textureIndex,flag_tracking);
       
                        
        if(tIdx==(state.getNumActiveTrackables()-1)){        //last iteration of for loop
                objID=(env->CallIntMethod(obj, getObjectIDMethodID));
           }
        //assert(textureIndex < textureCount);
        const Texture* const thisTexture = textures[objID];
		
        // Select which model to draw:
        GLvoid* vertices = 0;
        GLvoid* normals = 0;
        GLvoid* indices = 0;
        GLvoid* texCoords = 0;
        int numIndices = 0;
        
        switch (objID)              
        {
            case 0:
            	LOG("The objectName:Hat");
                vertices = &HatNewVerts[0];
                normals = &HatNewNormals[0];
                texCoords = &HatNewTexCoords[0];
                numIndices =HatNewNumVerts;
                break;
            case 1:
                LOG("The objectName:Cat");
                vertices = &CatVerts[0];
                normals = &CatNormals[0];
                texCoords = &CatTexCoords[0];
                numIndices =CatNumVerts;
                break;
            case 2:
            	LOG("The objectName:Car");
               vertices = &rCarVerts[0];
                normals = &rCarNormals[0];
                texCoords = &rCarTexCoords[0];
                numIndices =rCarNumVerts;
                break;
            default:
                float vVertices[] = {0.0f, 0.4f, 0.0f,-0.4f, -0.4f, 0.0f,0.4f, -0.4f, 0.0f};
				glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, GL_FALSE, 0, vVertices);
				glEnableVertexAttribArray(vertexHandle);
				numIndices =3;
                break;
        }
       
       if((state.getNumActiveTrackables()==3)&&(tIdx==2)){
                if(numIndices!=3){
               glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, GL_FALSE, 0, vertices);
               glVertexAttribPointer(normalHandle, 3, GL_FLOAT, GL_FALSE, 0, normals);
               glVertexAttribPointer(textureCoordHandle, 2, GL_FLOAT, GL_FALSE,0, texCoords);
               glEnableVertexAttribArray(vertexHandle);
               glEnableVertexAttribArray(normalHandle);
               glEnableVertexAttribArray(textureCoordHandle);}
                //***Mutiplying modelviewMatrix and projectionMatrix to find modeViewProjection matrix
                QCAR::Matrix44F modelViewProjection;
                SampleUtils::multiplyMatrix(&projectionMatrix.data[0],
                                            &modelViewRender.data[0],
                                            &modelViewProjection.data[0]);
                glUseProgram(shaderProgramID);
                glActiveTexture(GL_TEXTURE0);
                glBindTexture(GL_TEXTURE_2D, thisTexture->mTextureID);
                glUniformMatrix4fv(mvpMatrixHandle, 1, GL_FALSE,(GLfloat*)
                                                    &modelViewProjection.data[0]);
                glDrawArrays(GL_TRIANGLES, 0, numIndices);
                SampleUtils::checkGlError("Spell3Ds render frame");
             
        }
    }
     

    glDisable(GL_DEPTH_TEST);
    glDisableVertexAttribArray(vertexHandle);
    glDisableVertexAttribArray(normalHandle);
    glDisableVertexAttribArray(textureCoordHandle);

    QCAR::Renderer::getInstance().end();
}

void
configureVideoBackground()
{
    // Get the default video mode:
    QCAR::CameraDevice& cameraDevice = QCAR::CameraDevice::getInstance();
    QCAR::VideoMode videoMode = cameraDevice.
                                getVideoMode(QCAR::CameraDevice::MODE_DEFAULT);

    // Configure the video background
    QCAR::VideoBackgroundConfig config;
    config.mEnabled = true;
    config.mSynchronous = true;
    config.mPosition.data[0] = 0.0f;
    config.mPosition.data[1] = 0.0f;
    
    if (isActivityInPortraitMode)
    {
        //LOG("configureVideoBackground PORTRAIT");
        config.mSize.data[0] = videoMode.mHeight
                                * (screenHeight / (float)videoMode.mWidth);
        config.mSize.data[1] = screenHeight;
    }
    else
    {
        //LOG("configureVideoBackground LANDSCAPE");
        config.mSize.data[0] = screenWidth;
        config.mSize.data[1] = videoMode.mHeight
                            * (screenWidth / (float)videoMode.mWidth);
    }

    // Set the config:
    QCAR::Renderer::getInstance().setVideoBackgroundConfig(config);
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_initApplicationNative(
                            JNIEnv* env, jobject obj, jint width, jint height)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_initApplicationNative");
    
    // Store screen dimensions
    screenWidth = width;
    screenHeight = height;
        
    // Handle to the activity class:
    jclass activityClass = env->GetObjectClass(obj);
    jmethodID getTextureCountMethodID = env->GetMethodID(activityClass,
                                                    "getTextureCount", "()I");
    if (getTextureCountMethodID == 0)
    {
        LOG("Function getTextureCount() not found.");
        return;
    }

    textureCount = env->CallIntMethod(obj, getTextureCountMethodID);    
    if (!textureCount)
    {
        LOG("getTextureCount() returned zero.");
        return;
    }

    textures = new Texture*[textureCount];

    jmethodID getTextureMethodID = env->GetMethodID(activityClass,
        "getTexture", "(I)Lcom/nid/mrl/Spell3D/Texture;");

    if (getTextureMethodID == 0)
    {
        LOG("Function getTexture() not found.");
        return;
    }

    // Register the textures
    for (int i = 0; i < textureCount; ++i)
    {

        jobject textureObject = env->CallObjectMethod(obj, getTextureMethodID, i); 
        if (textureObject == NULL)
        {
            LOG("GetTexture() returned zero pointer");
            return;
        }
        textures[i] = Texture::create(env, textureObject);
    }
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_deinitApplicationNative(
                                                        JNIEnv* env, jobject obj)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_deinitApplicationNative");

    // Release texture resources
    if (textures != 0)
    {    
        for (int i = 0; i < textureCount; ++i)
        {
            delete textures[i];
            textures[i] = NULL;
        }
    
        delete[]textures;
        textures = NULL;
        
        textureCount = 0;
    }
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_startCamera(JNIEnv *,
                                                                         jobject)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_startCamera");

    // Initialize the camera:
    if (!QCAR::CameraDevice::getInstance().init())
        return;

    // Configure the video background
    configureVideoBackground();

    // Select the default mode:
    if (!QCAR::CameraDevice::getInstance().selectVideoMode(
                                QCAR::CameraDevice::MODE_DEFAULT))
        return;

    // Start the camera:
    if (!QCAR::CameraDevice::getInstance().start())
        return;

    // Start the tracker:
    QCAR::Tracker::getInstance().start();
    
    // Cache the projection matrix:
    const QCAR::Tracker& tracker = QCAR::Tracker::getInstance();
    const QCAR::CameraCalibration& cameraCalibration =
                                    tracker.getCameraCalibration();
    projectionMatrix = QCAR::Tool::getProjectionGL(cameraCalibration, 2.0f,
                                            2000.0f);
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_stopCamera(JNIEnv *,
                                                                   jobject)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_stopCamera");

    QCAR::Tracker::getInstance().stop();

    QCAR::CameraDevice::getInstance().stop();
    QCAR::CameraDevice::getInstance().deinit();
}

JNIEXPORT jboolean JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_toggleFlash(JNIEnv*, jobject, jboolean flash)
{
    return QCAR::CameraDevice::getInstance().setFlashTorchMode((flash==JNI_TRUE)) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_autofocus(JNIEnv*, jobject)
{
    return QCAR::CameraDevice::getInstance().startAutoFocus()?JNI_TRUE:JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_nid_mrl_Spell3D_Spell3D_setFocusMode(JNIEnv*, jobject, jint mode)
{
    return QCAR::CameraDevice::getInstance().setFocusMode(mode)?JNI_TRUE:JNI_FALSE;
}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Renderer3D_initRendering(
                                                    JNIEnv* env, jobject obj)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_initRendering");

    // Define clear color
    glClearColor(0.0f, 0.0f, 0.0f, QCAR::requiresAlpha() ? 0.0f : 1.0f);
    
    // Now generate the OpenGL texture objects and add settings
    for (int i = 0; i < textureCount; ++i)
    {
        glGenTextures(1, &(textures[i]->mTextureID));
        glBindTexture(GL_TEXTURE_2D, textures[i]->mTextureID);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textures[i]->mWidth,
                textures[i]->mHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                (GLvoid*)  textures[i]->mData);
    }
  
    shaderProgramID     = SampleUtils::createProgramFromBuffer(cubeMeshVertexShader,
                                                            cubeFragmentShader);

    vertexHandle        = glGetAttribLocation(shaderProgramID,
                                                "vertexPosition");
    normalHandle        = glGetAttribLocation(shaderProgramID,
                                                "vertexNormal");
    textureCoordHandle  = glGetAttribLocation(shaderProgramID,
                                                "vertexTexCoord");
    mvpMatrixHandle     = glGetUniformLocation(shaderProgramID,
                                                "modelViewProjectionMatrix");

}


JNIEXPORT void JNICALL
Java_com_nid_mrl_Spell3D_Renderer3D_updateRendering(
                        JNIEnv* env, jobject obj, jint width, jint height)
{
    LOG("Java_com_nid_mrl_Spell3D_Renderer3D_updateRendering");
    
    // Update screen dimensions
    screenWidth = width;
    screenHeight = height;

    // Reconfigure the video background
    configureVideoBackground();
}


#ifdef __cplusplus
}
#endif
