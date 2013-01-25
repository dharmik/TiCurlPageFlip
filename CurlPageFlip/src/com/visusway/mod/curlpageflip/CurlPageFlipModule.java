/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.visusway.mod.curlpageflip;

//import org.appcelerator.kroll.KrollModule;
//import org.appcelerator.kroll.annotations.Kroll;

//import org.appcelerator.titanium.TiApplication;
//import org.appcelerator.kroll.common.Log;
//import org.appcelerator.kroll.common.TiConfig;

import org.appcelerator.kroll.KrollDict;
//import org.appcelerator.kroll.KrollInvocation;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollObject;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.TiApplication;
//import org.appcelerator.titanium.TiContext;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;

//import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiActivityResultHandler;
import org.appcelerator.titanium.util.TiActivitySupport;
import org.appcelerator.titanium.util.TiIntentWrapper;

import android.app.Activity;
import android.content.Intent;

import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;


@Kroll.module(name="CurlPageFlip", id="com.visusway.mod.curlpageflip")
public class CurlPageFlipModule extends KrollModule
{

	// Standard Debugging variables
	private static final String LCAT = "CurlPageFlipModule";
	private static final boolean DBG = TiConfig.LOGD;
	protected static final int UNKNOWN_ERROR = 0;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;
	
	public CurlPageFlipModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(LCAT, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}

	// Methods
	@Kroll.method
	public String example()
	{
		Log.d(LCAT, "example called");
		return "hello world";
	}
	
	@Kroll.method
	public int findResID(String resName)
	{
		Log.d(LCAT, "findResID called");
		int resid = 0;
		try {
			//Log.d(LCAT,"resName:"+resName);
			resid = TiRHelper.getApplicationResource(resName);
			//Log.d(LCAT,"resName:"+resName+" = "+ resid);
		} catch (ResourceNotFoundException e) {
			//Log.e(LCAT,"resName:"+resName);
			resid = -1;
		}		
		return resid;
	}

	@Kroll.method
	public void openBook(KrollDict options)
	{
		final KrollFunction successCallback = getCallback(options, "success");
		final KrollFunction cancelCallback = getCallback(options, "cancel");
		final KrollFunction errorCallback = getCallback(options, "error");

		logDebug("launchCurlPageFlipActivity() called");


		final Activity activity = TiApplication.getAppCurrentActivity();
		final TiActivitySupport activitySupport = (TiActivitySupport) activity;

		final TiIntentWrapper curlpageflipIntent = new TiIntentWrapper(new Intent(
				activity, CurlPageFlipActivity.class));
		curlpageflipIntent.setWindowId(TiIntentWrapper.createActivityName("CURLPAGEFLIP"));

		CurlPageFlipResultHandler resultHandler = new CurlPageFlipResultHandler();
		resultHandler.successCallback = successCallback;
		resultHandler.cancelCallback = cancelCallback;
		resultHandler.errorCallback = errorCallback;
		resultHandler.activitySupport = activitySupport;
		resultHandler.curlpageflipIntent = curlpageflipIntent.getIntent();
		activity.runOnUiThread(resultHandler);
	}
	
//	private KrollDict getDictForResult(final String result) {
//		final KrollDict dict = new KrollDict();
//		dict.put("curlpageflip", result);
//		return dict;
//	}
	
	private KrollFunction getCallback(final KrollDict options, final String name) {
		if (options.containsKey(name)) {
			return (KrollFunction) options.get(name);
		} else {
			logError("Callback not found: " + name);
			return null;
		}
	}
	
	// Properties
	@Kroll.getProperty
	public String getExampleProp()
	{
		Log.d(LCAT, "get example property");
		return "hello world";
	}
	
	
	@Kroll.setProperty
	public void setExampleProp(String value) {
		Log.d(LCAT, "set example property: " + value);
	}
	
	private void logError(final String msg) {
		Log.e(LCAT, msg);
	}

	private void logDebug(final String msg) {
		if (DBG) {
			Log.d(LCAT, msg);
		}
	}

	protected class CurlPageFlipResultHandler implements TiActivityResultHandler,
			Runnable {

		protected int code;
		protected KrollFunction successCallback, cancelCallback, errorCallback;
		protected TiActivitySupport activitySupport;
		protected Intent curlpageflipIntent;

		public void run() {
			code = activitySupport.getUniqueResultCode();
			activitySupport.launchActivityForResult(curlpageflipIntent, code, this);
		}

		public void onError(Activity activity, int requestCode, Exception e) {
			String msg = "Problem with curlpageflip; " + e.getMessage();
			logError("error: " + msg); 
			if (errorCallback != null) {
				errorCallback
						.callAsync((KrollObject)errorCallback,createErrorResponse(UNKNOWN_ERROR, msg));
			}
		}

		public void onResult(Activity activity, int requestCode,
				int resultCode, Intent data) {
			logDebug("onResult() called");

			if (resultCode == Activity.RESULT_CANCELED) {
				logDebug("call Activity canceled");
				if (cancelCallback != null) {
					cancelCallback.callAsync((KrollObject)cancelCallback,new KrollDict());
				}
			} else {
				logDebug("call Activity successful");
				//String result = data
				//		.getStringExtra(CurlActivity.EXTRA_RESULT);
				//logDebug("scan result: " + result);
				//successCallback.callAsync((KrollObject)successCallback,getDictForResult(result));
				if (cancelCallback != null) {
					cancelCallback.callAsync((KrollObject)successCallback,new KrollDict());
				}
			}
		}
	}
}

