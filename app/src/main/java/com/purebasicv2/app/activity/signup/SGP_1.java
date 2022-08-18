package com.purebasicv2.app.activity.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.view.View.*;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.user.LoginVerify;
import com.purebasicv2.app.app.ErrorMe;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.AppConstants;
import com.purebasicv2.app.utils.EmailCheck;
import com.purebasicv2.app.utils.MySpinnerAdapter;
import com.purebasicv2.app.utils.RequestHandler;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SGP_1 extends Fragment {

    private TextInputEditText etrRegisterPhone, etrRegisterPassword, etrFullName, etrREmail, etrCPassword;
    private TextInputLayout tilRegPhone, tilRegPassword, tilFullName, tilREmail, tilCPassword;
    private AppCompatTextView tilCountry;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.sgp_1, container, false);

        String checkBoxText = "By Using the app you agree to our <a href='https://google.com' >Terms & Privacy policy</a>";

        CheckBox cbTerms = (CheckBox) root_view.findViewById(R.id.cbTerms);
        final Button btnNext = (Button) root_view.findViewById(R.id.btnRegisterNext);
        final TextView tvTerms = root_view.findViewById(R.id.tvTerms);
        final AVLoadingIndicatorView loading = root_view.findViewById(R.id.avLoadingRegSG1);
        tvTerms.setText(Html.fromHtml(checkBoxText));
        tvTerms.setMovementMethod(LinkMovementMethod.getInstance());

        btnNext.setEnabled(false);
        cbTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnNext.setEnabled(true);
                } else
                    btnNext.setEnabled(false);
            }
        });

        etrRegisterPhone = root_view.findViewById(R.id.etrRegisterPhone);
        etrRegisterPassword = root_view.findViewById(R.id.etrRegisterPassword);
        etrFullName = root_view.findViewById(R.id.etrName);
        etrREmail = root_view.findViewById(R.id.etrRegisterEmail);
        etrCPassword = root_view.findViewById(R.id.etrRegisterCPassword);
        spinner = root_view.findViewById(R.id.spinner);

        tilRegPhone = root_view.findViewById(R.id.tilRegPhone);
        tilRegPassword = root_view.findViewById(R.id.tilRegPassword);
        tilCPassword = root_view.findViewById(R.id.tilRegCPassword);
        tilFullName = root_view.findViewById(R.id.tilName);
        tilREmail = root_view.findViewById(R.id.tilRegEmail);
        tilCountry = root_view.findViewById(R.id.tilCountry);
        MySpinnerAdapter adapter = new MySpinnerAdapter(
                getContext(),
                R.layout.spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.country))
        );
        spinner.setAdapter(adapter);


        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                if (vaName() && vaPhone()
                        && vaEmail() && vaSpinner() && vaPassword()
                        && vaCPassword()) {
                    final SGP_3 fragment = new SGP_3();
                    Bundle arguments = new Bundle();
//                    arguments.putString("name", name);
//                    arguments.putString("email", email);
//                    arguments.putString("mobile", phone);
//                    arguments.putString("country", country);
//                    arguments.putString("password", password);
                    fragment.setArguments(arguments);

                    loading.setVisibility(View.VISIBLE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("VollySuccess", response);
                                        JSONObject obj = new JSONObject(response);
                                        if (obj.getBoolean("success")) {
                                            JSONObject jsonObject = obj.getJSONObject("data");
                                            Intent intent = new Intent(requireActivity(), LoginVerify.class);
                                            intent.putExtra(AppConstants.USER_ID_TAG, jsonObject.getString("user_id"));
                                            intent.putExtra(AppConstants.FROM_WHERE, AppConstants.FROM_REG);
                                            intent.putExtra(AppConstants.MOBILE, etrRegisterPhone.getText().toString());
                                            startActivity(intent);
//                                            startActivity(new Intent(requireContext(), LoginVerify.class));
                                        } else {
                                            JSONObject errorObj = obj.getJSONObject("errors");
                                            if (errorObj.has("email")){
                                                tilREmail.setError(errorObj.getString("email"));
                                            }
                                            if (errorObj.has("mobile")){
                                                tilRegPhone.setError(errorObj.getString("mobile"));
                                            }
                                            if (errorObj.has("password")){
                                                tilRegPassword.setError(errorObj.getString("password"));
                                            }

//                                            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
//                                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                                            pDialog.setTitleText(" ");
//                                            pDialog.setContentText(obj.getString("message"));
//                                            pDialog.setCancelable(true);
//                                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                                @Override
//                                                public void onClick(SweetAlertDialog sDialog) {
//                                                    sDialog.dismissWithAnimation();
//                                                }
//                                            });
//                                            pDialog.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("VollyError", e.getMessage());
                                    }
                                    loading.setVisibility(View.INVISIBLE);
                                }
                            }, new Response.ErrorListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.setVisibility(View.INVISIBLE);
                            new ErrorMe(getActivity(), "Something Went Wrong!" + error);
                        }
                    }

                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String name = etrFullName.getText().toString().trim();
                            String phone = etrRegisterPhone.getText().toString().trim();
                            String email = etrREmail.getText().toString().trim();
                            String password = etrRegisterPassword.getText().toString().trim();
                            String country = spinner.getSelectedItem().toString();
                            Map<String, String> arguments = new HashMap<String, String>();
                            arguments.put("name", name);
                            arguments.put("email", email);
                            arguments.put("mobile", phone);
                            arguments.put("country", country);
                            arguments.put("password", password);
                            return arguments;
                        }
                    };
                    RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }
            }
        });

        root_view.findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return root_view;
    }

    private boolean vaName() {
        String input = etrFullName.getText().toString().trim();
        if (input.isEmpty()) {
            etrFullName.setError("Full Name is empty!");
            return false;
        } else {
            tilFullName.setError(null);
            return true;
        }
    }

    private boolean vaEmail() {
        String input = etrREmail.getText().toString().trim();
        if (input.isEmpty()) {
            etrREmail.setError("Email is empty!");
            return false;
        } else if (!EmailCheck.emailCheck(input)) {
            tilREmail.setError("Invalid email!");
            return false;
        } else {
            tilREmail.setError(null);
            return true;
        }
    }

    private boolean vaPhone() {
        String input = etrRegisterPhone.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterPhone.setError("Phone No is empty!");
            return false;
        } else if (input.length() < 10) {
            tilRegPhone.setError("Invalid phone number!");
            return false;
        } else {
            tilRegPhone.setError(null);
            return true;
        }
    }

    private boolean vaPassword() {
        String input = etrRegisterPassword.getText().toString().trim();
        if (input.isEmpty()) {
            tilRegPassword.setError("Password is empty!");
            return false;
        } else {
            tilRegPassword.setError(null);
            return true;
        }
    }

    private boolean vaCPassword() {
        String input = etrCPassword.getText().toString().trim();
        if (input.isEmpty()) {
            tilCPassword.setError("Confirm password is empty!");
            return false;
        } else if (!input.contentEquals(etrRegisterPassword.getText().toString().trim())) {
            tilCPassword.setError("Password mismatch!");
            return false;
        } else {
            tilCPassword.setError(null);
            return true;
        }
    }

    private boolean vaSpinner() {
        if (spinner.getSelectedItemPosition() < 1) {
            tilCountry.setText("Country required!");
            tilCountry.setVisibility(View.VISIBLE);
            return false;
        } else {
            tilCountry.setText(null);
            tilCountry.setVisibility(View.GONE);
            return true;
        }
    }

}
