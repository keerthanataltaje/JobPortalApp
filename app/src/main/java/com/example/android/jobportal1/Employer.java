package com.example.android.jobportal1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Employer extends AppCompatActivity {
    private  Button Button_to_login;
    private EditText companyname,companyloc,contact;
    private Spinner type;
    //create a firebase firestore reference
    private FirebaseFirestore fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        fire = FirebaseFirestore.getInstance();
        companyname = (EditText) findViewById(R.id.editTextCompany);
        companyloc = (EditText) findViewById(R.id.editTextCompanyLoc);
        contact = (EditText) findViewById(R.id.contact);
        type = (Spinner) findViewById(R.id.spinnercompany);
        Button_to_login = (Button) findViewById(R.id.buttonforhaveacc);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinnercompany);

        //Container to hold the values
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.companytype));
        // To make it a drop down list
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

//If we forget this line then the data in drop down will not be shown in the spinner*/
        Button_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openActivityHaveAcc();
                String comname = companyname.getText().toString();
                String comloc = companyloc.getText().toString();
                String cno = contact.getText().toString();
                String cotype = type.getSelectedItem().toString();
                if (!validateinputs(comname, comloc, cno, cotype)) {
                    Map<String, Object> companymap = new HashMap<>();
                    companymap.put("Comapny name", comname);
                    companymap.put("Comapny location", comloc);
                    companymap.put("Contact number", cno);
                    companymap.put("Company type", cotype);
                    fire.collection("Company").add(companymap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Employer.this, "Company details stored", Toast.LENGTH_SHORT);
                            Intent intentacc = new Intent(Employer.this, HaveAnAccount.class);
                            startActivity(intentacc);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Employer.this, e.getMessage(), Toast.LENGTH_SHORT);
                        }
                    });

                }

            }
            private boolean validateinputs(String name,String loc,String no,String comtype){
                if(name.isEmpty())
                {
                    companyname.setError("Enter your name");
                    companyname.requestFocus();
                    return true;
                }
                else if(loc.isEmpty())
                {
                    companyloc.setError("Enter your email");
                    companyloc.requestFocus();
                    return true;
                }
                else if(no.isEmpty())
                {
                    contact.setError("Please select your date of birth");
                    contact.requestFocus();
                    return true;
                }

                return false;

            }

        });


    }

    }


