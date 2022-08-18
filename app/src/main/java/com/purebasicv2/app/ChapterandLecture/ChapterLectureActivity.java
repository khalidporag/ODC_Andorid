package com.purebasicv2.app.ChapterandLecture;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.ViewInWebViewActivity;
import com.purebasicv2.app.activity.base.BaseActivity;
import com.purebasicv2.app.activity.youtubevideo.YoutubePlayerView;
import com.purebasicv2.app.app.CustomHeaderInt;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.BatchRoot;
import com.purebasicv2.app.model.CategoryItems;
import com.purebasicv2.app.model.Lecture;
import com.purebasicv2.app.model.LectureRoot;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChapterLectureActivity extends BaseActivity implements ExpandableListAdapter.LectureItemClickListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<ExpGroup> listDataHeader = new ArrayList<ExpGroup>();
    HashMap<String, List<Lecture>> listDataChild = new HashMap<String, List<Lecture>>();

    private final int REQ_CODE_SEARCH_INVITEE = 3345;
    private String batch_id = "";
    private String subject_id = "",student_id,api_token="";
    private BatchRoot.Batch batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_lecture);
//        initToolBar();
//        initStatusBar();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        listAdapter.setLectureItemClickListener(this);

//        // Listview Group click listener
//        expListView.setOnGroupClickListener(new OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                try {
//                    if (listDataChild.get(listDataHeader.get(groupPosition).getGroupName()).size() == 0) {
//                        Intent intentInvitee = new Intent(MainActivity.this, SearchInviteeActivity.class);
//                        intentInvitee.putExtra("invitees", (Serializable) inviteeList);
//                        intentInvitee.putExtra("fromTaskEdit", true);
//                        intentInvitee.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivityForResult(intentInvitee, REQ_CODE_SEARCH_INVITEE);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                try {
                    if (listDataChild.get(listDataHeader.get(groupPosition).getGroupName()).size() > 0) {
                        listDataHeader.get(groupPosition).setExpanded(true);
                        listAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                try {
                    if (listDataChild.get(listDataHeader.get(groupPosition).getGroupName()).size() > 0) {
                        listDataHeader.get(groupPosition).setExpanded(false);
                        listAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                try {
//                    List<Lecture> list = listDataChild.get(listDataHeader.get(groupPosition).getGroupName());
//                    String groupName = "";
//                    String groupValue = "";
//                    for (int i = 0; i < list.size(); i++) {
//                        if (i == childPosition) {
//                            if (list.get(i).isSelected()) {
//                                list.get(i).setSelected(false);
//                            } else {
//                                list.get(i).setSelected(true);
//                                groupName = list.get(i).getChileName();
//                                groupValue = list.get(i).getChildSelectedName();
//                            }
//                        } else {
//                            list.get(i).setSelected(false);
//                        }
//                    }
//                    if (groupPosition == 0) {
//                        task_statusTitle = groupName;
//                        task_status = groupValue;
//                    } else if (groupPosition == 1) {
//                        due_dateTitle = groupName;
//                        due_date = groupValue;
//                    }
//                    listDataHeader.get(groupPosition).setGroupSelectedName(groupName);
//                    listDataChild.put(listDataHeader.get(groupPosition).getGroupName(), list);
//                    listAdapter.notifyDataSetChanged();
//                    isChanged = true;
//                    notifyFilterChange();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        student_id = String.valueOf(getSharedPrefManager().getUserInfo().getStudentInfo().getStudentId());
        api_token=getSharedPrefManager().getUserInfo().getApiToken();

        getIntentData();


    }

    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ResourcesCompat.getColor(getResources(), android.R.color.transparent, null));
        }
    }

    private void initToolBar(CategoryItems subject) {
        new CustomHeaderInt(this, subject.getName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentData() {
        try {
            if (getIntent().hasExtra("batch_id")) {
                batch_id = getIntent().getExtras().getString("batch_id");
            }
            if (getIntent().hasExtra("subject")) {
                CategoryItems subject = (CategoryItems) getIntent().getExtras().getSerializable("subject");
                initToolBar(subject);
            }
            if (getIntent().hasExtra("subject_id")) {
                subject_id = getIntent().getExtras().getString("subject_id");
            }
            getLecture(batch_id, subject_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData(LectureRoot lectureRoot) {
        try {
            listDataHeader.clear();
            listDataChild.clear();
            Iterator it = lectureRoot.getLectureHashMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                listDataHeader.add(new ExpGroup((String) pair.getKey(), false));
                listDataChild.put((String) pair.getKey(), (List<Lecture>) pair.getValue()); // Header, Child data
            }
            listAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("DATA12 " + listDataChild.get(0).size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getLecture(String batchId, String subjectID) {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.LECTURES+"&api_token="+api_token
                + "&" + "batch_id=" + batchId + "&" + "subject_id=" + subjectID+"&student_id="+student_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        try {
                            Log.d("VollySuccess", response);
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                JSONObject jsonObject = obj.getJSONObject("data");
                                Gson gson = new Gson();
                                Type type = new TypeToken<LectureRoot>() {
                                }.getType();
                                LectureRoot lectureRoot = gson.fromJson(obj.toString(), type);
                                prepareListData(lectureRoot);
//                                batchAdapter.addAll(batchRoot.getData());
                            } else {
                                showError(obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                new ErrorMe(ChapterLectureActivity.this, "Something Went Wrong!" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onLectureItemClickListener(Lecture lecture, int type) {
        switch (type) {
            case AppConstants
                    .PDF_CLICK:

                if (lecture.getLinks() != null) {
                    Intent intent = new Intent(this, ViewInWebViewActivity.class);
                    intent.putExtra("nType", "pdf");
                    intent.putExtra("nTitle", lecture.getTitle() + "-" + lecture.getCategory() + "-Note");
                    intent.putExtra("nPath", lecture.getLinks());
                    startActivity(intent);
                } else {
                    showError("Not Available");
                }

                break;
            case AppConstants
                    .NOTE_CLICK:
                if (lecture.getV_link() != null) {
                    Intent intent = new Intent(this, ViewInWebViewActivity.class);
                    intent.putExtra("nType", "pdf");
                    intent.putExtra("nTitle", lecture.getTitle() + "-" + lecture.getCategory() + "-Pdf");
                    intent.putExtra("nPath", lecture.getV_link());
                    startActivity(intent);
                } else {
                    showError("Not Available");
                }
                break;
            case AppConstants
                    .VIDEO_CLICK:
                if (lecture.getYoutube_video_id() != null) {
                    Intent intent = new Intent(this, YoutubePlayerView.class);
                    intent.putExtra("LECTURE_TITLE", lecture.getTitle() + "-" + lecture.getCategory() + "-Video");
                    intent.putExtra("videoid", lecture.getYoutube_video_id());
                    startActivity(intent);
                }
//                if (lecture.getVideo() != null) {
//                    Intent intent = new Intent(this, ViewInWebViewActivity.class);
//                    intent.putExtra("nType", "video");
//                    intent.putExtra("nTitle", lecture.getTitle() + "-" + lecture.getCategory() + "-Video");
//                    intent.putExtra("nPath", lecture.getVideo());
//                   // intent.putExtra("nPath", lecture.getVideo());
//                    startActivity(intent);
//                }
                else {
                    showError("Not Available");
                }
                break;
        }
    }
}
