package softexpress.hekuran.sqlite_project.Features.CreatePerson;

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

import softexpress.hekuran.sqlite_project.R;
import softexpress.hekuran.sqlite_project.Util.Config;
import softexpress.hekuran.sqlite_project.Database.DatabaseQueryClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PersonCreateDialogFragment extends DialogFragment {

    private static PersonCreateListener personCreateListener;

    private EditText first_name;
    private EditText last_name;
    private EditText birthdate;
    private EditText vendlindja;
    private EditText phoneEditText;
    private CheckBox employed;
    private Spinner martial_status;
    private Button createButton;
    private Button cancelButton;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;

    String gwnderType;

    public PersonCreateDialogFragment() {
        // Required empty public constructor
    }

    public static PersonCreateDialogFragment newInstance(String title, PersonCreateListener listener){
        personCreateListener = listener;
        PersonCreateDialogFragment personCreateDialogFragment = new PersonCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        personCreateDialogFragment.setArguments(args);

        personCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return personCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person_create_dialog, container, false);

        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        birthdate = view.findViewById(R.id.birthdate);
        vendlindja = view.findViewById(R.id.vendlindja);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        employed = view.findViewById(R.id.employed);
        martial_status = view.findViewById(R.id.martial_status);
        gender = view.findViewById(R.id.sex);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        gwnderType = "M";
                        break;
                    case R.id.female:
                        gwnderType = "F";
                        break;
                }
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Person person = new Person(-1, first_name.getText().toString(), last_name.getText().toString(), new Date(birthdate.getText().toString()), phoneEditText.getText().toString(), gwnderType, 1, String.valueOf(martial_status.getSelectedItem()), vendlindja.getText().toString());

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                int id = (int)databaseQueryClass.insertPeople(person);

                if(id>0){
                    person.setId(id);
                    personCreateListener.onPersonCreated(person);
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
                new DatePickerDialog(PersonCreateDialogFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "MM/dd/yyyy";
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
