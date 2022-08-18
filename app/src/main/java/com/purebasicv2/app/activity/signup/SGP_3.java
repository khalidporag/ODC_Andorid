package com.purebasicv2.app.activity.signup;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.purebasicv2.app.R;
import com.purebasicv2.app.adapter.MembershipAdapter;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.model.MembershipItem;
import com.purebasicv2.app.utils.RequestHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SGP_3 extends Fragment implements MembershipAdapter.OnItemClickListener {

    private RecyclerView rvMemberShipSG3;
    private ProgressBar proMembership;
    private ArrayList<MembershipItem> membershipItems;
    private MembershipAdapter membershipAdapter;
    private String getPhone, getPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.sgp_3, container, false);

        Bundle arguments = getArguments();
        getPhone = arguments.getString("R_PHONE");
        getPassword = arguments.getString("R_PASSWORD");
        //final String getOtp = arguments.getString("R_OTP");

        rvMemberShipSG3 = root_view.findViewById(R.id.rvMemberShipSG3);
        proMembership = root_view.findViewById(R.id.proMembership);


        rvMemberShipSG3.setLayoutManager(new LinearLayoutManager(getContext()));
        membershipItems = new ArrayList<>();
        parseMembership();

        return root_view;
    }


    private void parseMembership(){
        proMembership.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.MEMBERSHIP_ITEMS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                int getId = hit.getInt("id");
                                String name = hit.getString("plan_name");
                                String amount = hit.getString("amount");
                                String type = hit.getString("type");
                                String graduation = hit.getString("graduation");
                                String duration = hit.getString("duration");
                                membershipItems.add(new MembershipItem(getId,name,amount,type,graduation,duration));
                            }
                            membershipAdapter = new MembershipAdapter(getActivity(), membershipItems);
                            rvMemberShipSG3.setAdapter(membershipAdapter);
                            membershipAdapter.setOnItemClickListener(SGP_3.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        proMembership.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                new ErrorMe(getActivity(), "Something Went Wrong!"+error);
            }
        });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(request);
    }


    @Override
    public void onItemClick(int position) {
        if (membershipItems.size() > 0){
            MembershipItem items = membershipItems.get(position);

            final SGP_4 fragment = new SGP_4();
            Bundle arguments = new Bundle();
            arguments.putString( "R_PHONE", getPhone);
            arguments.putString( "R_PASSWORD", getPassword);
            arguments.putInt( "R_BATCH_ID", items.getId());
            arguments.putString( "R_BATCH_NAME", items.getName());
            fragment.setArguments(arguments);

            FragmentTransaction fr_left = getFragmentManager().beginTransaction();
            fr_left.setCustomAnimations(R.animator.fr_left, R.animator.fr_right, R.animator.fr_left, R.animator.fr_right);
            fr_left.replace(R.id.fragment_container_sgp, fragment, "GONext");
            fr_left.addToBackStack(null);
            fr_left.commit();
        }
    }

}
