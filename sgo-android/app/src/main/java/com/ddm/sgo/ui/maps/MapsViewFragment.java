package com.ddm.sgo.ui.maps;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ddm.sgo.R;
import com.ddm.sgo.infra.geolocation.GeolocationPort;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.repositories.ProjectRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsViewFragment extends Fragment {
    private GoogleMap map;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            updateLocationUI();
            setProjectsMarkers();
            getDeviceLocation();
        }
    };

    private void setProjectsMarkers() {
        List<Project> projectList = loadProjectsMarkers();
        LatLng firstLatLng = null;
        for (Project project : projectList) {
            LatLng marker = new LatLng(project.latitude, project.longitude);
            if (firstLatLng == null) firstLatLng = marker;

            map.addMarker(new MarkerOptions().position(marker).title(project.name));
        }

        if (firstLatLng != null)
            map.moveCamera(CameraUpdateFactory.newLatLng(firstLatLng));
    }

    private void getDeviceLocation() {
        Location currentLocation = GeolocationPort.getInstance(getContext()).getCurrentLocation();
        if (currentLocation != null) {
            LatLng latLngLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            map.moveCamera(CameraUpdateFactory.newLatLng(latLngLocation));
        }
    }


    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private List<Project> loadProjectsMarkers() {
        List<Project> projectList = ProjectRepository.getInstance(getContext()).getProjectList();

        return projectList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}