package com.example.daftarpencarianorang.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.daftarpencarianorang.Model.MissingPersonData;
import com.example.daftarpencarianorang.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class EditActivity extends AppCompatActivity {
    private TextInputEditText name, age, religion, address, contact, missingDate, description;
    private RadioGroup mGroup;
    private Button updateBtn;
    private MissingPersonData mOldData;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_ROOT_KEY);
        //mOldData = (MissingPersonData) getIntent().getSerializableExtra(DATA_TRANSFER_KEY);

        initview();
        setData();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    // update data
    private void updateData() {
        RadioButton gender = findViewById(mGroup.getCheckedRadioButtonId());
//        MissingPersonData mUpdateData = new MissingPersonData(USER_ID,
//                name.getText().toString(),
//                age.getText().toString(),
//                gender.getText().toString(),
//                address.getText().toString(),
//                missingDate.getText().toString(),
//                prize.getText().toString(),
//                contact.getText().toString() + "\n" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),
//                mOldData.getPhoto_urls(),
//                description.getText().toString());

//        if (mOldData.getName().equals(mUpdateData.getName())) {
//            mDatabaseReference.child(USER_ID).child(mOldData.getName()).setValue(mUpdateData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(EditActivity.this, "your data is updated", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//        } else {
//            mDatabaseReference.child(USER_ID).child(mOldData.getName()).removeValue(new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                    mDatabaseReference.child(USER_ID).child(mUpdateData.getName()).setValue(mUpdateData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Toast.makeText(EditActivity.this, "your data is updated", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
//                }
//            });
//        }
    }

    // set data on all views
    private void setData() {
        name.setText(mOldData.getName());
        age.setText(mOldData.getAge());
        religion.setText(mOldData.getReligion());
        address.setText(mOldData.getAddress());
        contact.setText(mOldData.getContacts().replace("\n" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), ""));
        missingDate.setText(mOldData.getMissing_date());
        description.setText(mOldData.getDescription());
        if (mOldData.getGender().equals("Male")) {
            mGroup.check(R.id.male_rdo_btn);
        } else if (mOldData.getGender().equals("Female")) {
            mGroup.check(R.id.female_rdo_btn);
        } else {
//            mGroup.check(R.id.other_rdo_btn);
        }
    }

    // init view with ids
    private void initview() {
        name = findViewById(R.id.msg_text_box);
        age = findViewById(R.id.missing_person_age);
        religion = findViewById(R.id.missing_person_religion);
        address = findViewById(R.id.missing_person_address);
        contact = findViewById(R.id.missing_person_contact_number);
        missingDate = findViewById(R.id.missing_person_missing_date);
        description = findViewById(R.id.description);
        mGroup = findViewById(R.id.radioGroup);
        updateBtn = findViewById(R.id.update_btn);
    }
}