package softexpress.hekuran.sqlite_project.Features.PersonList;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import softexpress.hekuran.sqlite_project.Database.DatabaseQueryClass;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.Person;
import softexpress.hekuran.sqlite_project.Features.UpdatePersonInfo.PersonUpdateDialogFragment;
import softexpress.hekuran.sqlite_project.Features.UpdatePersonInfo.PersonUpdateListener;
import softexpress.hekuran.sqlite_project.R;
import softexpress.hekuran.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PersonListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Person> personList;
    private DatabaseQueryClass databaseQueryClass;

    public PersonListRecyclerViewAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Person person = personList.get(position);

        holder.fullname.setText(person.getEmri() + " " + person.getMbiemri());
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formatDate = sdf.format(person.getDatelindja());
        holder.vendlindja.setText(String.valueOf(person.getVendlindja()));
        holder.datelindja.setText(formatDate);
        holder.phoneTextView.setText(person.getTelefon());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this person?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deletePerson(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonUpdateDialogFragment studentUpdateDialogFragment = PersonUpdateDialogFragment.newInstance(person.getId(), itemPosition, new PersonUpdateListener() {
                    @Override
                    public void onPersonInfoUpdated(Person student, int position) {
                        personList.set(position, student);
                        notifyDataSetChanged();
                    }
                });
                studentUpdateDialogFragment.show(((PersonListActivity) context).getSupportFragmentManager(), Config.UPDATE_PERSON);
            }
        });
    }

    private void deletePerson(int position) {
        Person person = personList.get(position);
        long count = databaseQueryClass.deletePersonByRegNum(person.getId());

        if(count>0){
            personList.remove(position);
            notifyDataSetChanged();
            ((PersonListActivity) context).viewVisibility();
            Toast.makeText(context, "Person deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Person not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
