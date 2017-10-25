package pcbri1.edappmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import static pcbri1.edappmodule.R.id.list_text_date;
import static pcbri1.edappmodule.R.id.list_text_location;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("Earthquakes");

        recyclerView = (RecyclerView) findViewById(R.id.db_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("Earthquakes");

        FirebaseRecyclerAdapter<Event, EventViewHolder> eventAdapter
                = new FirebaseRecyclerAdapter<Event, EventViewHolder>(
                        Event.class,
                        R.layout.list_item_card,
                        EventViewHolder.class,
                        dbRef
        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                viewHolder.mLocation.setText(model.getLocation());
                viewHolder.mDateTime.setText(model.getDateTime());
            }
        };
        recyclerView.setAdapter(eventAdapter);

    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView mLocation;
        TextView mDateTime;

        public EventViewHolder(View itemView) {
            super(itemView);
            mLocation = (TextView) itemView.findViewById(list_text_location);
            mDateTime = (TextView) itemView.findViewById(list_text_date);
        }
    }
}
