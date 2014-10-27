package ch.tarsier.tarsier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gluthier on 27.10.2014.
 */
public class DiscussionManager {

    private static String[] nameArray = {"Romain Ruetschi", "Frederic Jacobs",
            "Xavier Willemin", "Amirreza Bahreini", "Marin-Jerry Nicolini",
            "Gabriel Luthier", "Yann Mahmoudi", "Benjamin Paccaud"};
    private static String loremIpsum = "Lorem ipsum dolor sit amet.";

    private static DiscussionManager mInstance;
    private List<DiscussionSummary> mDiscussions;

    public static DiscussionManager getInstance() {
        if (mInstance == null) {
            mInstance = new DiscussionManager();
        }

        return mInstance;
    }

    public List<DiscussionSummary> getDiscussions () {
        if (mDiscussions == null) {
            mDiscussions = new ArrayList<DiscussionSummary>();

            for (String name : nameArray) {
                DiscussionSummary discussion = new DiscussionSummary();
                discussion.peopleName = name;
                discussion.lastMessage = loremIpsum;
                mDiscussions.add(discussion);
            }
        }

        return mDiscussions;
    }
}
