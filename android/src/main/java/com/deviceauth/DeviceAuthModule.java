package com.deviceauth;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import android.app.KeyguardManager;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;

@ReactModule(name = DeviceAuthModule.NAME)
public class DeviceAuthModule extends ReactContextBaseJavaModule {
  public static final String NAME = "DeviceAuth";

  private static final int AUTH_REQUEST = 18864;
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private static final String E_AUTH_CANCELLED = "LAErrorUserCancel";
  private static final String E_FAILED_TO_SHOW_AUTH = "E_FAILED_TO_SHOW_AUTH";
  private static final String E_ONE_REQ_AT_A_TIME = "E_ONE_REQ_AT_A_TIME";

  private KeyguardManager mKeyguardManager;
  private final ReactApplicationContext reactContext;
  private Promise authPromise;

  private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      if (requestCode != AUTH_REQUEST || authPromise == null) return;

      if (resultCode == Activity.RESULT_CANCELED) {
        authPromise.reject(E_AUTH_CANCELLED, "User canceled");
      } else if (resultCode == Activity.RESULT_OK) {
        authPromise.resolve(true);
      }

      authPromise = null;
    }
  };

  public DeviceAuthModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(mActivityEventListener);
    mKeyguardManager = (KeyguardManager) this.reactContext.getSystemService(Context.KEYGUARD_SERVICE);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void isDeviceSecure(final Promise promise) {
   promise.resolve(mKeyguardManager.isDeviceSecure());
  }

  @ReactMethod
  public void authenticate(String reason, String description, final Promise promise) {
    // Create the Confirm Credentials screen. You can customize the title and description. Or
    // we will provide a generic one for you if you leave it null
    Activity currentActivity = getCurrentActivity();

    if (authPromise != null) {
      promise.reject(E_ONE_REQ_AT_A_TIME, "Activity doesn't exist");
      return;
    }

    if (currentActivity == null) {
      promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "One auth request at a time");
      return;
    }

    // Store the promise to resolve/reject when picker returns data
    authPromise = promise;
    try {
      final Intent authIntent = mKeyguardManager.createConfirmDeviceCredentialIntent(reason, " ");
      currentActivity.startActivityForResult(authIntent, AUTH_REQUEST);
    } catch (Exception e) {
      authPromise.reject(E_FAILED_TO_SHOW_AUTH, e);
      authPromise = null;
    }
  }
}
