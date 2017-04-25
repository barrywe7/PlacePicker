package com.barryirvine.tide.placepicker.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.barryirvine.tide.placepicker.R;
import com.barryirvine.tide.placepicker.api.BarsService;
import com.barryirvine.tide.placepicker.model.Place;
import com.barryirvine.tide.placepicker.model.PlaceResponse;
import com.barryirvine.tide.placepicker.ui.fragment.BarsPagerFragment;
import com.barryirvine.tide.placepicker.utils.RuntimePermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ResultCallback<LocationSettingsResult> {

    private static final int REQUEST_LOCATION_SETTING = 1234;
    private ArrayList<Place> mPlaces;
    private Location mLocation;
    private GoogleApiClient mLocationClient;
    private LocationRequest mLocationRequest;
    private boolean mInForeground;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        RuntimePermissionUtils.checkForPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                new RuntimePermissionUtils.CheckPermissionListener() {
                    @Override
                    public void onPermissionAlreadyGranted() {
                        checkLocationSettings(MainActivity.this);
                    }

                    @Override
                    public void onPermissionAlreadyDeniedWithDoNotAskAgain() {
                        RuntimePermissionUtils.missingPermissionAlertDialog(MainActivity.this, R.string.missing_location_permission_title, R.string.missing_location_permission_body, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(final DialogInterface dialog) {
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onPermissionPendingRequest() {
                        RuntimePermissionUtils.requestPermissionFromActivity(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, RuntimePermissionUtils.REQUEST_LOCATION);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (RuntimePermissionUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            connectLocationClient();
        }
    }

    @Override
    protected void onStop() {
        disconnectLocationClient();
        super.onStop();
    }

    @Override
    protected void onPause() {
        mInForeground = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInForeground = true;
        if (RuntimePermissionUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) && getSupportFragmentManager().findFragmentById(R.id.fragment_layout) == null) {
            if (mPlaces == null) {
                getBars();
            } else {
                addFragment(R.id.fragment_layout, BarsPagerFragment.newInstance(mPlaces, mLocation));
            }
        }
    }

    private void getBars() {
        if (mLocation != null) {
            BarsService.get().getNearbyBars(mLocation.getLatitude() + "," + mLocation.getLongitude(), 1500, getString(R.string.web_api_key))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Consumer<PlaceResponse>() {
                                @Override
                                public void accept(final PlaceResponse response) throws Exception {
                                    mPlaces = response.getPlaces();
                                    if (mInForeground) {
                                        addFragment(R.id.fragment_layout, BarsPagerFragment.newInstance(mPlaces, mLocation));

                                    }
                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(final Throwable throwable) throws Exception {
                                    Toast.makeText(MainActivity.this, "Failure" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RuntimePermissionUtils.REQUEST_LOCATION:
                RuntimePermissionUtils.onRequestPermissionsResult(grantResults, this,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        new RuntimePermissionUtils.OnResultPermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                setLocationClient();
                                connectLocationClient();
                                getBars();
                            }

                            @Override
                            public void onPermissionDeniedWithDoNotAskAgain() {
                                finish();
                            }

                            @Override
                            public void onPermissionDeniedButShowRequestPermissionRationale() {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage(R.string.missing_location_rationale_body)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                RuntimePermissionUtils.requestPermissionFromActivity(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, RuntimePermissionUtils.REQUEST_LOCATION);

                                            }
                                        })
                                        .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, final int which) {
                                                finish();
                                            }
                                        })
                                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(final DialogInterface dialog) {
                                                finish();
                                            }
                                        });
                                alertDialogBuilder.create().show();
                            }
                        });
                break;
            default:
                break;
        }
    }

    public void addFragment(@IdRes final int containerViewId, final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commitNow();
    }

    public void checkLocationSettings(final ResultCallback<LocationSettingsResult> callback) {
        if (mLocation == null) {
            setLocationClient();
            final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mLocationClient,
                    new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).build());
            result.setResultCallback(callback);
            connectLocationClient();
        }
    }

    @Override
    public void onResult(@NonNull final LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // Noop - will happen elsewhere
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    disconnectLocationClient();
                    mLocationClient = null;
                    status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION_SETTING);
                } catch (final IntentSender.SendIntentException e) {
                    // Ignore the error.
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are not satisfied. However, we have no way to fix the settings so we won't show the dialog.
                break;
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOCATION_SETTING:
                if (resultCode == Activity.RESULT_OK) {
                    if (RuntimePermissionUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        setLocationClient();
                        connectLocationClient();
                    }
                }
        }
    }

    public void disconnectLocationClient() {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            mLocationClient.disconnect();
        }
    }

    public void connectLocationClient() {
        if (!mLocationClient.isConnecting()) {
            mLocationClient.connect();
        }
    }

    public void setLocationClient() {
        if (mLocationClient == null) {
            mLocationClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
            mLocationRequest = new LocationRequest()
                    .setInterval(10 * DateUtils.SECOND_IN_MILLIS)
                    .setFastestInterval(5 * DateUtils.SECOND_IN_MILLIS)
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onConnected(@Nullable final Bundle bundle) {
        if (LocationServices.FusedLocationApi.getLastLocation(mLocationClient) != null) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
            if (RuntimePermissionUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) && getSupportFragmentManager().findFragmentById(R.id.fragment_layout) == null) {
                getBars();
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, this);
                    mLocation = location;
                    if (RuntimePermissionUtils.hasPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) && getSupportFragmentManager().findFragmentById(R.id.fragment_layout) == null) {
                        getBars();
                    }
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(final int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
    }
}
