package com.purebasicv2.app.activity.offline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.purebasicv2.app.R;
import com.purebasicv2.app.adapter.model_test.OfflineVideoAdapter;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.OfflineVideoItems;
import java.io.File;
import java.util.ArrayList;

public class OfflineVideos extends AppCompatActivity implements OfflineVideoAdapter.OnItemClickListener {

    private ArrayList<OfflineVideoItems> offlineVideoItems;
    private RecyclerView rvCategoryLectures;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        new CustomHeaderInt(this, "Offline Video");

        tvNoData = findViewById(R.id.tvNoData);
        rvCategoryLectures = findViewById(R.id.rvCategoryLectures);
        rvCategoryLectures.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        offlineVideoItems = new ArrayList<>();
        tvNoData.setText("No Saved Video Found");

    }

    private void loadVideos(){
        offlineVideoItems.clear();
        String path = Environment.getExternalStorageDirectory().toString()+"/"+Constants.DOWNLOAD_FOLDER;
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null){
            Log.d("Files", "Size: "+ files.length);
            if (files.length > 0){
                for (File file : files) {
                    //Bitmap thumb = ThumbnailUtils.createVideoThumbnail(file.getPath(), MediaStore.Images.Thumbnails.MICRO_KIND);
                    String getTitle = file.getName();
                    offlineVideoItems.add(new OfflineVideoItems(getTitle, file));
                }
                OfflineVideoAdapter offlineVideoAdapter = new OfflineVideoAdapter(OfflineVideos.this, offlineVideoItems);
                rvCategoryLectures.setAdapter(offlineVideoAdapter);
                offlineVideoAdapter.setOnItemClickListener(OfflineVideos.this);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                Log.d("Files", "No file found--111");
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            Log.d("Files", "No file found");
        }
    }

    @Override
    public void onItemClick(int position) {
        if (offlineVideoItems.size() > 0){
            OfflineVideoItems items = offlineVideoItems.get(position);
            Intent intent = new Intent(OfflineVideos.this, VideoPlayerOffline.class);
            intent.putExtra("VIDEO_SOURCE", items.getName());
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    loadVideos();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            loadVideos();
        }
    }
}