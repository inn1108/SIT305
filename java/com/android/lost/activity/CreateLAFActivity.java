package com.android.lost.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.lost.BuildConfig;
import com.android.lost.R;
import com.android.lost.entity.LostAndFound;
import com.android.lost.sqlite.DAOService;
import com.android.lost.utils.ToastUtils;
import com.android.lost.utils.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class CreateLAFActivity extends MyBaseActivity {

    // The entry point to the Places API.
    private PlacesClient placesClient;
    private TextView locationTv;
    private LatLng mLatLng = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_botany);

        // [START_EXCLUDE silent]
        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
        placesClient = Places.createClient(this);

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = ((EditText) findViewById(R.id.edit_1)).getText().toString().trim();
                String str2 = ((EditText) findViewById(R.id.edit_2)).getText().toString().trim();
                String str3 = ((EditText) findViewById(R.id.edit_3)).getText().toString().trim();
                String str4 = ((EditText) findViewById(R.id.edit_4)).getText().toString().trim();
                String str5 = ((TextView) findViewById(R.id.edit_5)).getText().toString().trim();
                if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)
                        || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
                    ToastUtils.showShortToast(getApplicationContext(), "info is empty");
                } else {
                    // 插入失物信息
                    LostAndFound botany = new LostAndFound();
                    botany.setName(str1);
                    botany.setPhone(str2);
                    botany.setDesc(str3);
                    botany.setDate(str4);
                    botany.setLocation(str5);
                    if(null != mLatLng) {
                        botany.setLatitude(mLatLng.latitude);
                        botany.setLongitude(mLatLng.longitude);
                    }
                    DAOService.getInstance().insertHealthInfo(botany);
                    // 社团关系表里加入一条人员 就是创建者
                    ToastUtils.showShortToast(getApplicationContext(), "insert success");
                    setResult(-1);
                    finish();
                }
            }
        });

        locationTv = findViewById(R.id.edit_5);
        locationTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到搜索页面
                startActivityForResult(new Intent(getApplicationContext(),SearchActivity.class),100);
            }
        });

        findViewById(R.id.my_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrentPlace();
            }
        });
    }

    /**
     * 获取当前定位位置
     */
    private void showCurrentPlace() {
        ProgressDialog progressDialog = ProgressDialog.show(CreateLAFActivity.this,"","正在获取当前位置信息",true);
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);

        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.newInstance(placeFields);

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
                placesClient.findCurrentPlace(request);
        placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    FindCurrentPlaceResponse likelyPlaces = task.getResult();
                    // 获取到了位置
                    if (Utils.isNotEmpty(likelyPlaces.getPlaceLikelihoods())) {
                        // 取第一个可用的位置
                        Place place = null;
                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                            place = placeLikelihood.getPlace();
                        }
                        if (null == place) {
                            ToastUtils.showShortToast(getApplicationContext(), "获取不到可用位置");
                            return;
                        }
                        // 只取第一个位置
                        String address = place.getAddress();
                        mLatLng = place.getLatLng();
                        locationTv.setText(address);
                        ToastUtils.showShortToast(getApplicationContext(), "获取位置信息成功");

                    } else {
                        ToastUtils.showShortToast(getApplicationContext(), "获取不到可用位置");
                    }
                } else {
                    ToastUtils.showShortToast(getApplicationContext(), "获取不到可用位置");
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        locationTv.setText(DAOService.getInstance().getAddress());
        mLatLng = DAOService.getInstance().getLatLng();
    }
}
