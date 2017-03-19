package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.oreid.virtualkitchen.StorageArea.stringValues;
import static java.lang.Integer.parseInt;

public class AddItem extends AppCompatActivity implements OnFocusChangeListener {

    private static final String TAG = "AddItem";

    private int contentViewId = R.layout.activity_add_item;
    // do i pass the same instance from where-ever or new instance
    private EditText textName;
    private EditText textQuan;
    private EditText textExpiry;
    private Button btnAddFav;
    private Button btnSaveItem;
    private Button btnCancel;
    private Spinner spinnerCat;
    private Spinner spinnerStoragearea;
    private EditText textDate;
    private Button btnChangeDate;
    private Button btnScanBarcode;

    private boolean textExpiryFocus = false;
    private boolean textDateFocus = false;
    private boolean fav = false;
    private static final int BARCODE_REQUEST_CODE = 3;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");



    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);
        initialiseComponents();
        setStorageArea();

    }

    private void setStorageArea() {
        String storageArea = getIntent().getStringExtra("STORAGEAREA");
        if (storageArea != null) {
            String[] areas = stringValues();
            for (int i = 0; i < areas.length; i++) {
                if (storageArea.equals(areas[i])) {
                    spinnerStoragearea.setSelection(i);
                    break;
                }
            }
        }
    }

    private boolean validate() {

        boolean passed = true;

        if (textName.getText().toString().length() == 0) {
            textName.setError("Please provide a name for your item.");
            passed = false;
        }

        if (textQuan.getText().toString().length() == 0) {
            textQuan.setError("Please provide a quantity. ");
            passed = false;
        } else {
            try {
                int qty = Integer.parseInt(textQuan.getText().toString());
                if (qty <= 0) {
                    textQuan.setError("Please provide a positive quantity - you can only add one or more items to the kitchen. ");
                    passed = false;
                }
            } catch (NumberFormatException e) {
                textQuan.setError("Please enter a number for item quantity. ");
                passed = false;
            }
        }

        if (textExpiry.getText().toString().length() == 0) {
            textExpiry.setError("Please provide a shelf life, or select an expiry date.");
            passed = false;
        } else {
            try {
                int exp = Integer.parseInt(textExpiry.getText().toString());
                if (exp <= 0) {
                    textExpiry.setError("Please provide a positive shelf life - you can't keep out of date food in the kitchen.");
                    passed = false;
                }
            } catch (NumberFormatException e) {
                textExpiry.setError("Please enter a number for item quantity.");
                passed = false;
            }
        }

        return passed;
    }

    public void  initialiseComponents(){

        btnScanBarcode = (Button)findViewById(R.id.scanBarcode);
        btnBarcodeListener(btnScanBarcode);
        textName = (EditText) findViewById(R.id.editTextName);
        textQuan = (EditText) findViewById(R.id.editTextQuan);
        textExpiry = (EditText) findViewById(R.id.editTextExpiry);
        textChangeListener(textExpiry);
        btnAddFav = (Button) findViewById(R.id.addFavourite);
        btnFavListener(btnAddFav);
        //on click add to fav
        btnSaveItem = (Button) findViewById(R.id.doneItem);
        btnSaveItem.setText("Add Item");
        btnSaveListener(btnSaveItem);
        btnCancel = (Button)findViewById(R.id.cancelButton);
        btnCancelListener(btnCancel);
        //add text to button
        //on click save the data
        spinnerCat = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(
                this, R.array.array_category, R.layout.spinner_layout);
        catAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerCat.setAdapter(catAdapter);
        spinnerStoragearea = (Spinner) findViewById(R.id.spinnerStoragearea);
        ArrayAdapter<String> storageAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout, StorageArea.stringValues());
        storageAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerStoragearea.setAdapter(storageAdapter);
        textDate = (EditText) findViewById(R.id.editTextDate);
        dateChangeListener(textDate);
        setCurrentDateOnView();
        addListenerOnButton();
    }

    private void btnBarcodeListener(Button btnScanBarcode) {
        btnScanBarcode.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent scanIntent = new Intent(AddItem.this, ScanBarcodeActivity.class);
                startActivityForResult(scanIntent, BARCODE_REQUEST_CODE);
            }
        });
    }

    private void btnSaveListener(Button btnSaveItem) {
        btnSaveItem.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!validate()) {
                    // validation failed. do nothing so the user can correct their mistakes.
                    return;
                }

                String name = String.valueOf(textName.getText());
                String quantity = String.valueOf(textQuan.getText());
                String expiry = String.valueOf(textExpiry.getText());
                String category = spinnerCat.getSelectedItem().toString();
                String storage = spinnerStoragearea.getSelectedItem().toString();
                String favourite = fav ? "True" : "False";

                Intent returnIntent = new Intent(AddItem.this, MainKitchenActivity.class);
                returnIntent.putExtra("NAME",name);
                returnIntent.putExtra("QUAN",quantity);
                returnIntent.putExtra("EXP",expiry);
                returnIntent.putExtra("CAT", category);
                returnIntent.putExtra("STORAGE", storage);
                returnIntent.putExtra("FAV", favourite);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void btnCancelListener(Button btnCancel) {
        btnCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent cancelIntent = new Intent(AddItem.this, MainKitchenActivity.class);
                setResult(Activity.RESULT_CANCELED,cancelIntent);
                finish();
            }
        });
    }

    private void btnFavListener(Button btnCancel) {
        btnCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String msg = "";
                if (fav) {
                    msg = "This item is no longer a favourite.";
                    fav = false;
                } else {
                    msg = "Adding to favourites...";
                    fav = true;
                }
                Toast.makeText(AddItem.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dateChangeListener(final EditText textDate) {
        textDate.setOnFocusChangeListener(this);
        textDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!textDateFocus) {
                    return; // do not change if no focus.
                }

                String expDateString = textDate.getText().toString();
                Date currExp;
                try {
                    currExp = dateFormat.parse(expDateString);
                } catch (ParseException e) {
                    return;
                }
                long diff = currExp.getTime() - (new Date()).getTime();
                long days = diff / (24 * 60 * 60 * 1000);
                textExpiry.setText(Integer.toString((int)days));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    // Checking if the textviews have focus. Used in the text change listener to only update fields
    // with focus.
    public void onFocusChange(View v, boolean hasFocus)  {

        textExpiryFocus = false;
        textDateFocus = false;

        if (v.equals(textExpiry)) {
            textExpiryFocus = true;
        } else if (v.equals(textDate)) {
            textDateFocus = true;
        }

    }

    private void textChangeListener(final EditText textExpiry) {
        textExpiry.setOnFocusChangeListener(this);
        textExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!textExpiryFocus) {
                    return; // no focus, no change.
                }
                String txtVal = textExpiry.getText().toString();
                int expDays;
                try {
                    expDays = parseInt(txtVal);
                } catch (NumberFormatException e) {
                    return; // do nothing. they entered an invalide value.
                }
                updateDateBasedOnExpiry(expDays);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    // display current date
    public void setCurrentDateOnView() {
        Date now = new Date();
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(now);
        int year = nowCal.get(Calendar.YEAR);
        int month = nowCal.get(Calendar.MONTH);
        int day = nowCal.get(Calendar.DAY_OF_MONTH);
        setDateOnView(day,month,year);
        textExpiry.setText("");
    }

    public void setDateOnView(int day, int month, int year) {
        final Calendar currCal = Calendar.getInstance();

        Calendar expCal = Calendar.getInstance();
        expCal.set(year,month,day);

        Date expDate = expCal.getTime();
        Date currDate = currCal.getTime();

        long diff = expDate.getTime() - currDate.getTime();
        long days = diff / (24 * 60 * 60 * 1000);

        textExpiry.setText(Integer.toString((int)days));

        //set selected date into textview
        textDate.setText(dateFormat.format(expDate));
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            setDateOnView(selectedDay, selectedMonth, selectedYear);

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String barcode = data.getStringExtra("BARCODE");
            if (barcode.equals("1234567891231")) { // sample barcode
                Toast.makeText(this, "Barcode recognised.", Toast.LENGTH_SHORT).show();
                textName.setText("Barcode Food");
                textQuan.setText("2");
                setCurrentDateOnView();

                updateDateBasedOnExpiry(10);
                textExpiry.setText("10");
            } else {
                Toast.makeText(this, "Barcode not recognised, please try another or enter manually.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateDateBasedOnExpiry(int expDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, expDays); // Adding 5 days
        textDate.setText(dateFormat.format(c.getTime()));
    }

}
