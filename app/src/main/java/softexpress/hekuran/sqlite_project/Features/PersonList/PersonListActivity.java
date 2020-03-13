package softexpress.hekuran.sqlite_project.Features.PersonList;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import softexpress.hekuran.sqlite_project.Database.DatabaseQueryClass;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.Person;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.PersonCreateDialogFragment;
import softexpress.hekuran.sqlite_project.Features.CreatePerson.PersonCreateListener;
import softexpress.hekuran.sqlite_project.R;
import softexpress.hekuran.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class PersonListActivity extends AppCompatActivity implements PersonCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Person> personList = new ArrayList<>();

    private TextView personListEmptyTextView;
    private RecyclerView recyclerView;
    private PersonListRecyclerViewAdapter personListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.personRecyclerView);
        personListEmptyTextView = (TextView) findViewById(R.id.emptyPersonListTextView);

        personList.addAll(databaseQueryClass.getAllPeople());

        personListRecyclerViewAdapter = new PersonListRecyclerViewAdapter(this, personList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(personListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPersonCreateDialog();
            }
        });
        PersonCreateDialogFragment studentCreateDialogFragment = PersonCreateDialogFragment.newInstance("Create Person", this);
        studentCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PERSON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO write your code what you want to perform on search
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                personList.clear();
                personList.addAll(databaseQueryClass.getPeople(query));

                personListRecyclerViewAdapter = new PersonListRecyclerViewAdapter(PersonListActivity.this, personList);
//                recyclerView.
                recyclerView.setAdapter(personListRecyclerViewAdapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all person?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllPeople();
                            if(isAllDeleted){
                                personList.clear();
                                personListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(personList.isEmpty())
            personListEmptyTextView.setVisibility(View.VISIBLE);
        else
            personListEmptyTextView.setVisibility(View.GONE);
    }

    private void openPersonCreateDialog() {
        PersonCreateDialogFragment studentCreateDialogFragment = PersonCreateDialogFragment.newInstance("Create Person", this);
        studentCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PERSON);
    }

    @Override
    public void onPersonCreated(Person person) {
        personList.add(person);
        personListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(person.getEmri());
    }

}
