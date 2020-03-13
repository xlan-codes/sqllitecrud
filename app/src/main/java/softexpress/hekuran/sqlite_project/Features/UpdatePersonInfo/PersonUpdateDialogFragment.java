package softexpress.hekuran.sqlite_project.Features.UpdatePersonInfo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import softexpress.hekuran.sqlite_project.Database.DatabaseQueryClass;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.Person;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.PersonCreateDialogFragment;
import softexpress.hekuran.sqlite_project.R;
import softexpress.hekuran.sqlite_project.Util.Config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PersonUpdateDialogFragment extends DialogFragment {

    private static int id;
    private static int personItemPosition;
    private static PersonUpdateListener personUpdateListener;

    private Person mPerson;

    private EditText first_name;
    private EditText last_name;
    private EditText birthdate;
    private EditText vendlindja;
    private EditText phoneEditText;
    private CheckBox employed;
    private Spinner martial_status;
    private RadioGroup sex;
    private RadioButton male;
    private RadioButton female;
    private Button updateButton;
    private Button cancelButton;

    String sexType;

    private DatabaseQueryClass databaseQueryClass;

    public PersonUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static PersonUpdateDialogFragment newInstance(int personId, int position, PersonUpdateListener listener){
        id = personId;
        personItemPosition = position;
        personUpdateListener = listener;
        PersonUpdateDialogFragment studentUpdateDialogFragment = new PersonUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update person information");
        studentUpdateDialogFragment.setArguments(args);

        studentUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return studentUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        birthdate = view.findViewById(R.id.birthdate);
        vendlindja = view.findViewById(R.id.vendlindja);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        employed = view.findViewById(R.id.employed);
        martial_status = view.findViewById(R.id.martial_status);
        sex = view.findViewById(R.id.sex);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);

        updateButton = view.findViewById(R.id.updateButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mPerson = databaseQueryClass.getPeopleByRegNum(id);

        if(mPerson !=null){

            String[] mTestArray = getResources().getStringArray(R.array.martial_status_arrays);
            first_name.setText(mPerson.getEmri());
            last_name.setText(mPerson.getMbiemri());
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String formatDate = sdf.format(mPerson.getDatelindja());
            birthdate.setText(formatDate);
            vendlindja.setText(mPerson.getVendlindja());
            phoneEditText.setText(mPerson.getTelefon());
            boolean emploedTemp = mPerson.isIpunesuar()==1 ? true:false;
            employed.setChecked(emploedTemp);

            if(mPerson.getGjinia().equals("M")) {
                male.setChecked(true);
            } else {
                if(mPerson.getGjinia().equals("F")) {
                    female.setChecked(true);
                }
            }

            if(mPerson.getDatelindja() != null) {

            }


            int a = Arrays.asList(mTestArray).indexOf(mPerson.getGjendjamartesore());
            martial_status.setSelection(a);
            sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.male:
                            sexType = "M";
                            break;
                        case R.id.female:
                            sexType = "F";
                            break;
                    }
                }
            });

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mPerson.setEmri(first_name.getText().toString());
                    mPerson.setMbiemri(last_name.getText().toString());
                    mPerson.setGjendjamartesore((String) martial_status.getSelectedItem());
                    mPerson.setTelefon(phoneEditText.getText().toString());
                    mPerson.setVendlindja(vendlindja.getText().toString());
                    mPerson.setGjinia(sexType);
                    mPerson.setDatelindja(new Date(birthdate.getText().toString()));
                    int ispunesuar = employed.isChecked() ? 1 : 0;
                    mPerson.setIpunesuar(ispunesuar);


                    long id = databaseQueryClass.updatePersonInfo(mPerson);

                    if(id>0){
                        personUpdateListener.onPersonInfoUpdated(mPerson, personItemPosition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

        }



        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        birthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PersonUpdateDialogFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdate.setText(sdf.format(myCalendar.getTime()));
    }





    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
