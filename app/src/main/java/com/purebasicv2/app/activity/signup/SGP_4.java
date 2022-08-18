package com.purebasicv2.app.activity.signup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.purebasicv2.app.LoginActivity;
import com.purebasicv2.app.R;
import com.purebasicv2.app.activity.user.PaymentMethodActivity;
import com.purebasicv2.app.constant.Constants;
import com.purebasicv2.app.utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SGP_4 extends Fragment   {

    private Calendar myCalendar;
    private TextView tvRegisterBioDate;
    private TextInputEditText etrRegisterFullName,etrRegisterEmail,etrRegisterDcotorBMDC,etrRegisterMedicalorCollege;
    private TextInputEditText etrRegisterMedicalorCollegeBatch,etrRegisterSession,etrRegisterFacebookId,etrRegisterAddress;
    private String bioDate,gender,position,admissionBatch,studentGraduateLevel="",doctorQualification="";
    private int batchId;
    private LinearLayout lytRegStudentPosition,lytRegDoctorPosition;
    private ProgressDialog progressDialog;
    private String getPhone, getPassword, country="Bangladesh";
    private Spinner spinnerCountryList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.sgp_4, container, false);

        Bundle arguments = getArguments();
        getPhone = arguments.getString("R_PHONE");
        getPassword = arguments.getString("R_PASSWORD");
        batchId = arguments.getInt("R_BATCH_ID");
        admissionBatch = arguments.getString("R_BATCH_NAME");

        TextView tvBatchName = root_view.findViewById(R.id.tvBatchName);
        tvBatchName.setText(admissionBatch);

        spinnerCountryList = root_view.findViewById(R.id.spinnerCountryList);
        etrRegisterFullName = root_view.findViewById(R.id.etrRegisterFullName);
        etrRegisterEmail = root_view.findViewById(R.id.etrRegisterEmail);
        etrRegisterDcotorBMDC = root_view.findViewById(R.id.etrRegisterDcotorBMDC);
        etrRegisterMedicalorCollege = root_view.findViewById(R.id.etrRegisterMedicalorCollege);
        etrRegisterMedicalorCollegeBatch = root_view.findViewById(R.id.etrRegisterMedicalorCollegeBatch);
        etrRegisterSession = root_view.findViewById(R.id.etrRegisterSession);
        etrRegisterFacebookId = root_view.findViewById(R.id.etrRegisterFacebookId);
        etrRegisterAddress = root_view.findViewById(R.id.etrRegisterAddress);

        RadioGroup rgRegisterGender = root_view.findViewById(R.id.rgRegisterGender);
        RadioGroup rgPosition = root_view.findViewById(R.id.rgPosition);
        RadioGroup rgGraduateLevelStudent = root_view.findViewById(R.id.rgGraduateLevelStudent);
        RadioGroup rgQualificationDoctor = root_view.findViewById(R.id.rgQualificationDoctor);

        //PositionParentLayout
        lytRegStudentPosition = root_view.findViewById(R.id.lytRegStudentPosition);
        lytRegDoctorPosition = root_view.findViewById(R.id.lytRegDoctorPosition);

        Button btnCreateAccount = root_view.findViewById(R.id.btnCreateAccount);
        tvRegisterBioDate = root_view.findViewById(R.id.tvRegisterBioDate);
        ImageView ibtnCalneder = root_view.findViewById(R.id.ibtnCalneder);


        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBioLabel();
            }
        };
        ibtnCalneder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        rgRegisterGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.genderMale) {
                    gender = "male";
                } else if(checkedId == R.id.genderFemale) {
                    gender = "female";
                }
            }
        });

        rgPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.positionStudent) {
                    position = "Student";
                    lytRegDoctorPosition.setVisibility(View.GONE);
                    lytRegStudentPosition.setVisibility(View.VISIBLE);
                } else if(checkedId == R.id.positionDoctor) {
                    position = "Doctor";
                    lytRegStudentPosition.setVisibility(View.GONE);
                    lytRegDoctorPosition.setVisibility(View.VISIBLE);
                }
            }
        });

        //if position student
        rgGraduateLevelStudent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = root_view.findViewById(checkedId);
                studentGraduateLevel = radioButton.getText().toString();
            }
        });

        //doctor doctorQualification
        rgQualificationDoctor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = root_view.findViewById(checkedId);
                doctorQualification = radioButton.getText().toString();
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creating Account..");

        btnCreateAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullName =  etrRegisterFullName.getText().toString().trim();
                final String email =  etrRegisterEmail.getText().toString().trim();
                final String medicaldental =  etrRegisterMedicalorCollege.getText().toString().trim();
                final String medicaldentalBatch =  etrRegisterMedicalorCollegeBatch.getText().toString().trim();
                final String session =  etrRegisterSession.getText().toString().trim();
                final String facebook =  etrRegisterFacebookId.getText().toString().trim();
                final String address =  etrRegisterAddress.getText().toString().trim();
                final String doctorBMDC =  etrRegisterDcotorBMDC.getText().toString().trim();

                if (vaFullName() && vaEmail() && vaCountry() && vaBio() && vaGender() && vaPosition() && vaMedicalCollege() && vaMedicalCollegeBatch() && vaSession() && vaAdmissionBatch() && vaFacebook() && vaAddress()){

                    if (position.contains("Student") && !vaGraduateLevel()){
                        Toast.makeText(getContext(), "Select Graduate Level", Toast.LENGTH_SHORT).show();
                    } else if (position.contains("Doctor") && !vaDoctorQualification()){
                        Toast.makeText(getContext(), "Select Qualification Level", Toast.LENGTH_SHORT).show();
                    } else if (position.contains("Doctor") && !vaDoctorBMDC()){
                        etrRegisterDcotorBMDC.setError("Empty!");
                    } else {
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_API, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (!jsonObject.getBoolean("error")) {
/*                                        if (SharedPrefManager.getInstance(getContext()).userLoginAdd(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("mobile"),
                                                jsonObject.getString("hash"),
                                                jsonObject.getBoolean("login")
                                        )){
                                            welcome();
                                        }*/
                                        welcome();

                                    } else {
                                        showError(jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        },new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("mobile", getPhone);
                                params.put("password", getPassword);
                                params.put("full_name", fullName);
                                params.put("email", email);
                                params.put("bio", bioDate);
                                params.put("gender", gender);
                                params.put("position", position);
                                params.put("studentGraduateLevel", studentGraduateLevel);
                                params.put("doctorBMDC", doctorBMDC);
                                params.put("doctorQualification", doctorQualification);
                                params.put("medicalOrDental", medicaldental);
                                params.put("medicalOrDentalBatch", medicaldentalBatch);
                                params.put("session", session);
                                params.put("admissionBatchID", String.valueOf(batchId));
                                params.put("facebook", facebook);
                                params.put("address", address);
                                params.put("country", country);
                                return params;
                            }
                        };
                        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
                    }
                }
            }
        });


        final ArrayList<String> countries = new ArrayList<String>();
        countries.add("Bangladesh");
        countries.add("Other");
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryList.setAdapter(countryAdapter);
        spinnerCountryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                country = countries.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
        spinnerCountryList.setSelection(0);

        return root_view;
    }

    public void showError(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .show();
    }

/*    public void showSuccess(String text) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(" ")
                .setContentText(text)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        //finish();
                    }
                })
                .show();
    }*/


    private void welcome(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.lyt_reg_welcome);
        dialog.show();
        dialog.findViewById(R.id.skip001).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        dialog.findViewById(R.id.gotoPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().finish();
                Intent payIntent = new Intent(getActivity(), PaymentMethodActivity.class);
                payIntent.putExtra("PAY_USER", getPhone);
                payIntent.putExtra("PAY_SOURCE", 1);
                startActivity(payIntent);
            }
        });
    }

    private void updateBioLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        bioDate = sdf.format(myCalendar.getTime());
        tvRegisterBioDate.setText(bioDate);
    }


    private boolean vaFullName() {
        String input = etrRegisterFullName.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterFullName.setError("Full Name is empty!");
            return false;
        }  else {
            etrRegisterFullName.setError(null);
            return true;
        }
    }

    private boolean vaEmail() {
        String input = etrRegisterEmail.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterEmail.setError("Email is empty!");
            return false;
        }  else {
            etrRegisterEmail.setError(null);
            return true;
        }
    }

    private boolean vaBio() {
        if (bioDate.isEmpty()) {
            Toast.makeText(getContext(), "Date of Birth is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            return true;
        }
    }

    private boolean vaGender() {
        String[] genderLocal = {"male", "female"};
        if (!Arrays.asList(genderLocal).contains(gender)) {
            Toast.makeText(getContext(), "Select Your Gender", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            return true;
        }
    }

    private boolean vaPosition() {
        String[] positionLocal = {"Student", "Doctor"};
        if (!Arrays.asList(positionLocal).contains(position)) {
            Toast.makeText(getContext(), "Select Your Position", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            return true;
        }
    }


    private boolean vaMedicalCollege() {
        String input = etrRegisterMedicalorCollege.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterMedicalorCollege.setError("Empty!");
            return false;
        }  else {
            etrRegisterMedicalorCollege.setError(null);
            return true;
        }
    }

    private boolean vaMedicalCollegeBatch() {
        String input = etrRegisterMedicalorCollegeBatch.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterMedicalorCollegeBatch.setError("Empty!");
            return false;
        }  else {
            etrRegisterMedicalorCollegeBatch.setError(null);
            return true;
        }
    }

    private boolean vaSession() {
        String input = etrRegisterSession.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterSession.setError("Session is empty!");
            return false;
        }  else {
            etrRegisterSession.setError(null);
            return true;
        }
    }

    private boolean vaAdmissionBatch() {
        if (batchId <= 0 || admissionBatch.isEmpty()) {
            Toast.makeText(getContext(), "Select Admission Batch", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            return true;
        }
    }

    private boolean vaFacebook() {
        String input = etrRegisterFacebookId.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterFacebookId.setError("Facebook ID is empty!");
            return false;
        }  else {
            etrRegisterFacebookId.setError(null);
            return true;
        }
    }

    private boolean vaAddress() {
        String input = etrRegisterAddress.getText().toString().trim();
        if (input.isEmpty()) {
            etrRegisterAddress.setError("Address is empty!");
            return false;
        }  else {
            etrRegisterAddress.setError(null);
            return true;
        }
    }

    private boolean vaGraduateLevel() {
        return !studentGraduateLevel.isEmpty();
    }

    private boolean vaDoctorBMDC() {
        String input = etrRegisterDcotorBMDC.getText().toString().trim();
        if (input.isEmpty()) {
            return false;
        }  else {
            etrRegisterDcotorBMDC.setError(null);
            return true;
        }
    }

    private boolean vaDoctorQualification() {
        return !doctorQualification.isEmpty();
    }

    private boolean vaCountry() {
        if (country.isEmpty()) {
            Toast.makeText(getContext(), "Select Country", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            return true;
        }
    }

}