package ch.tarsier.tarsier.network.old;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.tarsier.tarsier.R;

/**
 * Created by amirreza on 10/27/14.
 */
    public class ChatRoom extends Fragment {


        private MyConnection myConnection;

        private View view;
        private TextView chatLine;
        private ListView listView;
        ChatMessageAdapter adapter = null;
        private List<String> items = new ArrayList<String>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.chat_room, container, false);
            chatLine = (TextView) view.findViewById(R.id.txtChatLine);
            listView = (ListView) view.findViewById(android.R.id.list);
            adapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                    items);
            listView.setAdapter(adapter);
            view.findViewById(R.id.button1).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            if (myConnection != null) {
                                myConnection.write(chatLine.getText().toString()
                                        .getBytes());
                                pushMessage("Me: " + chatLine.getText().toString());
                                chatLine.setText("");
                                chatLine.clearFocus();
                            }
                        }
                    });
            return view;
        }
        public interface MessageTarget {
            public Handler getHandler();
        }
        public void setMyConnection(MyConnection obj) {
            myConnection = obj;
        }
        public void pushMessage(String readMessage) {
            adapter.add(readMessage);
            adapter.notifyDataSetChanged();
        }
        /**
         * ArrayAdapter to manage chat messages.
         */
        public class ChatMessageAdapter extends ArrayAdapter<String> {
            List<String> messages = null;
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
                String message = items.get(position);
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
