package ch.tarsier.tarsier.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.network.MessagingManager;

/**
 * @author amirezza
 */
public class ChatroomFragment extends Fragment {

    private MessagingManager mMessengerDelegate;

    private TextView mChatLine;

    private ChatMessageAdapter mAdapter = null;

    private List<String> mItems = new ArrayList<String>();

    public void setMessengerDelegate(MessagingManager messagingInterface) {
        mMessengerDelegate = messagingInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.chat_room, container, false);
        mChatLine = (TextView) mView.findViewById(R.id.txtChatLine);
        ListView mListView = (ListView) mView.findViewById(android.R.id.list);
        mAdapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                mItems);
        mListView.setAdapter(mAdapter);
        mView.findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (mMessengerDelegate != null) {
                            mMessengerDelegate.broadcastMessage(mChatLine.getText().toString());
                            pushMessage("Me: " + mChatLine.getText().toString());
                            mChatLine.setText("");
                        }
                    }
                });
        return mView;
    }

    public void pushMessage(String readMessage) {
        mAdapter.add(readMessage);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * ArrayAdapter to manage chat messages.
     */
    public class ChatMessageAdapter extends ArrayAdapter<String> {

        public ChatMessageAdapter(Context context, int textViewResourceId,
                List<String> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }
            String message = mItems.get(position);
            if (message != null && !message.isEmpty()) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);
                if (nameText != null) {
                    nameText.setText(message);
                    if (message.startsWith("Me: ")) {
                        nameText.setTextAppearance(getActivity(),
                                R.style.normalText);
                    } else {
                        nameText.setTextAppearance(getActivity(),
                                R.style.boldText);
                    }
                }
            }
            return v;
        }
    }

}
