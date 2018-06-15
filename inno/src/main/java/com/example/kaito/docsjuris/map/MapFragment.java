/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kaito.docsjuris.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kaito.docsjuris.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This shows how to close the info window when the currently selected marker is re-tapped.
 */
public class MapFragment extends Fragment implements
        OnMarkerClickListener,
        OnMapClickListener ,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng L1 = new LatLng(32.88380103993505, -6.882699877023697);
    private static final LatLng L2 = new LatLng(32.87992449251782, -6.93782027810812);
    private static final LatLng L3 = new LatLng(32.865680779546864, -6.89093291759491);
    private static final LatLng L4 = new LatLng(32.915481120573865, -6.93310160189867);
    private static final LatLng L5 = new LatLng(32.91716589584254, -6.877378709614278);


    private GoogleMap mMap = null;


    /**
     * Keeps track of the selected marker.
     */
    private Marker mSelectedMarker;
    private View rootView;
    private FragmentActivity myContext;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //mMapFragment.getMapAsync(this);
        new OnMapAndViewReadyListener(mMapFragment, this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

            } else {
            }
        }

        // Hide the zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Set listener for marker click event.  See the bottom of this class for its behavior.
        mMap.setOnMarkerClickListener(this);

        // Set listener for map click event.  See the bottom of this class for its behavior.
        mMap.setOnMapClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localized.
        map.setContentDescription("Demo showing how to close the info window when the currently"
            + " selected marker is re-tapped.");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(PERTH)
                .include(SYDNEY)
                .include(ADELAIDE)
                .include(BRISBANE)
                .include(MELBOURNE)
                .build();
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        /*mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18);
                mMap.animateCamera(cu);
            }
        });*/


    }

    private void addMarkersToMap() {

        mMap.addMarker(new MarkerOptions()
                .position(L1)
                .title("RedOne")
                .snippet("Lawyer Expert")
                .icon(BitmapDescriptorFactory.fromBitmap(fun(R.drawable.pic1)))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));

        mMap.addMarker(new MarkerOptions()
                .position(L2)
                .title("RedOne")
                .snippet("Lawyer Expert")
                .icon(BitmapDescriptorFactory.fromBitmap(fun(R.drawable.appartments)))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));

        mMap.addMarker(new MarkerOptions()
                .position(L3)
                .title("RedOne")
                .snippet("Lawyer Expert")
                .icon(BitmapDescriptorFactory.fromBitmap(fun(R.drawable.balance)))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));

        mMap.addMarker(new MarkerOptions()
                .position(L4)
                .title("RedOne")
                .snippet("Lawyer Expert")
                .icon(BitmapDescriptorFactory.fromBitmap(fun(R.drawable.hospital)))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));

        mMap.addMarker(new MarkerOptions()
                .position(L5)
                .title("RedOne")
                .snippet("Lawyer Expert")
                .icon(BitmapDescriptorFactory.fromBitmap(fun(R.drawable.shield)))
                // Specifies the anchor to be at a particular point in the marker image.
                .anchor(0.5f, 1));



    }

    @Override
    public void onMapClick(final LatLng point) {
        // Any showing info window closes when the map is clicked.
        // Clear the currently selected marker.
        mSelectedMarker = null;
        Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // The user has re-tapped on the marker which was already showing an info window.
        if (marker.equals(mSelectedMarker)) {
            // The showing info window has already been closed - that's the first thing to happen
            // when any marker is clicked.
            // Return true to indicate we have consumed the event and that we do not want the
            // the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            mSelectedMarker = null;
            return true;
        }

        mSelectedMarker = marker;

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public Bitmap fun(int id){

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),id);
        Bitmap resized = Bitmap.createScaledBitmap(bmp, 96, 96, true);

        return resized;
    }


}
