package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddItem extends AppCompatActivity {

    private static final String TAG = "AddItem";

    private int contentViewId = R.layout.activity_add_item;
    // do i pass the same instance from where-ever or new instance
    private EditText textName;
    private EditText textQuan;
    private EditText textExpiry;
    private Button btnAddFav;
    private Button btnSaveItem;
    private Spinner spinnerCat;
    private  EditText textDate;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);
        initialiseComponents();
    }

    public void  initialiseComponents(){

        textName = (EditText) findViewById(R.id.editTextName);
        textQuan = (EditText) findViewById(R.id.editTextQuan);
        textExpiry = (EditText) findViewById(R.id.editTextExpiry);
        textChangeListener(textExpiry);
        btnAddFav = (Button) findViewById(R.id.addFavourite);
        //on click add to fav
        btnSaveItem = (Button) findViewById(R.id.doneItem);
        btnSaveItem.setText("Add Item");
        btnSaveListener(btnSaveItem);
        //add text to button
        //on click save the data
        spinnerCat = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(
                this, R.array.array_category, R.layout.spinner_layout);
        catAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerCat.setAdapter(catAdapter);
        textDate = (EditText) findViewById(R.id.editTextDate);
        setCurrentDateOnView();
        addListenerOnButton();

    }

    private void btnSaveListener(Button btnSaveItem) {
        btnSaveItem.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String name = String.valueOf(textName.getText());
                String quantity = String.valueOf(textQuan.getText());
                String expiry = String.valueOf(textExpiry.getText());
                Intent returnIntent = new Intent(AddItem.this, MainKitchenActivity.class);
                returnIntent.putExtra("NAME",name);
                returnIntent.putExtra("QUAN",quantity);
                returnIntent.putExtra("EXP",expiry);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void textChangeListener(final EditText textExpiry) {
        textExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int expDays = Integer.parseInt(String.valueOf(textExpiry.getText()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Now use today date.
                c.add(Calendar.DATE, expDays); // Adding 5 days
                textDate.setText(sdf.format(c.getTime()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // display current date
    public void setCurrentDateOnView() {

        //dpResult = (DatePicker) findViewById(R.id.dpResult);

        final Calendar currCal = Calendar.getInstance();
        year = currCal.get(Calendar.YEAR);
        month = currCal.get(Calendar.MONTH);
        day = currCal.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        textDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            final Calendar currCal = Calendar.getInstance();

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            /*
            Calendar expCal = Calendar.getInstance();
            expCal.set(year,month+1,day);

            Date expDate = expCal.getTime();
            Date currDate= currCal.getTime();

            long diff = expDate.getTime() - currDate.getTime();
            long days = diff / (24 * 60 * 60 * 1000);

            textExpiry.setText((int) days);
*/
            // set selected date into textview
            textDate.setText(new StringBuilder().append(day)
                    .append("-").append(month+1).append("-").append(year)
                    .append(" "));
        }
    };


}
