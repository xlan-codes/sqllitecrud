package softexpress.hekuran.sqlite_project.Features.UpdatePersonInfo;

import softexpress.hekuran.sqlite_project.Features.CreatePerson.Person;

public interface PersonUpdateListener {
    void onPersonInfoUpdated(Person person, int position);
}
