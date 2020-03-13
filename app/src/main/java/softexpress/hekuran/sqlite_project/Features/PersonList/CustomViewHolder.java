package softexpress.hekuran.sqlite_project.Features.PersonList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import softexpress.hekuran.sqlite_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView fullname;
    TextView vendlindja;
    TextView datelindja;
    TextView phoneTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        fullname = itemView.findViewById(R.id.fullname);
        vendlindja = itemView.findViewById(R.id.vendlindjaTextView);
        datelindja = itemView.findViewById(R.id.birthdateTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
