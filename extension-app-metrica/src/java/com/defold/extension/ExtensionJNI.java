package com.defold.extension;

import androidx.annotation.NonNull;
import android.util.Log;
import android.app.Activity;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class ExtensionJNI {

  private static final String TAG = "ExtensionJNI";
  public static native void AddToQueue(int msg, String json);
  private static final int MSG_TYPE_NONE = 1;
  private static final int EVENT_JSON_ERROR = 101;
  private Activity activity;


  public ExtensionJNI(Activity activity) {
    this.activity = activity;
  }

  public void initialize(final String unitId) {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        sendSimpleMessage(MSG_TYPE_NONE, "init", "okay:"+unitId);
      }
    });

  }

  private String getJsonConversionErrorMessage(String messageText) {
    String message = null;
    try {
      JSONObject obj = new JSONObject();
      obj.put("error", messageText);
      obj.put("event", EVENT_JSON_ERROR);
      message = obj.toString();
    } catch (JSONException e) {
      message = "{ \"error\": \"Error while converting simple message to JSON.\", \"event\": " + EVENT_JSON_ERROR
          + " }";
    }
    return message;
  }


  private void sendSimpleMessage(int msg, String key_2, String value_2) {
    String message = null;
    try {
      JSONObject obj = new JSONObject();
      obj.put(key_2, value_2);
      message = obj.toString();
    } catch (JSONException e) {
      message = getJsonConversionErrorMessage(e.getLocalizedMessage());
    }
    AddToQueue(msg, message);
  }


}
