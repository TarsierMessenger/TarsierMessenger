package ch.tarsier.tarsier.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;
import ch.tarsier.tarsier.domain.model.Chat;
import ch.tarsier.tarsier.domain.model.NearbyChatroomSummary;
import ch.tarsier.tarsier.event.ReceivedNearbyChatListEvent;
import ch.tarsier.tarsier.event.RequestNearbyChatListEvent;
import ch.tarsier.tarsier.ui.activity.ChatActivity;
import ch.tarsier.tarsier.ui.adapter.NearbyChatroomAdapter;

/**
 * @author benpac
 * @author marinnicolini
 */
public class NearbyChatListFragment extends Fragment {

    private Activity mActivity;
    private NearbyChatroomAdapter mNearbyChatroomAdapter;
    private static final String CHAT = "NearbyChat";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNearbyChatroomAdapter = new NearbyChatroomAdapter(mActivity, R.layout.row_nearby_chat_list);
        Tarsier.app().getEventBus().register(this);
        Tarsier.app().getEventBus().post(new RequestNearbyChatListEvent());

        //Bundle bundle = getArguments();
    }

    @Subscribe
    public void onReceivedChatList(ReceivedNearbyChatListEvent event) {
        mNearbyChatroomAdapter.setChatList(event.getChats());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rowView = inflater.inflate(R.layout.fragment_nearby_chat_list, container, false);
        ListView listView = (ListView) rowView.findViewById(R.id.nearby_chat_list);
        listView.setAdapter(mNearbyChatroomAdapter);

        //set click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent chatIntent = new Intent(mActivity, ChatActivity.class);
                chatIntent.putExtra(CHAT, (Chat) adapterView.getItemAtPosition(position));

                Toast.makeText(mActivity,
                               "chat id is " +((Chat) adapterView.getItemAtPosition(position)).getId(),
                                Toast.LENGTH_SHORT).show();
                // TODO decomment when it is ok
                //startActivity(chatIntent);
            }
        });

        return rowView;
    }

    //TO be removed
    public NearbyChatroomSummary[] getChatroomNearby() {
        NearbyChatroomSummary[] chatroomNearby = {
            new NearbyChatroomSummary(7, "SwEng", "23:02", 55),
            new NearbyChatroomSummary(6, "Sat Rocks", "Yesterday", 163),
            new NearbyChatroomSummary(5, "CO2", "Friday", 158),
            new NearbyChatroomSummary(4, "Grillades Préverenges", "Wednesday", 18),
            new NearbyChatroomSummary(3, "Hong Kong's umbrella movement", "Tuesday", 88954),
            new NearbyChatroomSummary(2, "M2 EPFL", "16.10.14", 32),
            new NearbyChatroomSummary(1, "TA meeting 1", "14.10.14", 8)
        };
        return chatroomNearby;
    }
}
