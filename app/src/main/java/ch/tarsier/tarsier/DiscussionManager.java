package ch.tarsier.tarsier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gluthier on 27.10.2014.
 */
public class DiscussionManager {

    private static DiscussionSummary[] discussionsArray = {
        new DiscussionSummary("placeholder", "1", "SwEng", "A fond, mais je bosse dur, aussi!",
                "Just now", "37", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
        new DiscussionSummary("placeholder", "3", "Romain Ruetschi", "Typing...",
                "13:10", "1",  DiscussionSummary.TypeConversation.PUBLIC_ROOM),
        new DiscussionSummary("placeholder", "0", "Yann Mahmoudi", "That's because C just has no class!",
                "Yesterday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
        new DiscussionSummary("placeholder", "0", "Marin-Jerry Nicolini", "Ouais, pas de problème pour vendredi.",
                "Sunday", "1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
        new DiscussionSummary("placeholder", "0", "Hong Kong's umbrella movement", "Everybody to Civic Square! Take umb...",
                "Friday", "1254", DiscussionSummary.TypeConversation.PRIVATE_CHAT),
        new DiscussionSummary("placeholder", "0", "Benjamin Paccaud", "Oui, tous les tests passent sans problème.",
                "Friday","1", DiscussionSummary.TypeConversation.PUBLIC_ROOM),
        new DiscussionSummary("placeholder", "0", "TA meeting 1", "Non, Romain n'a toujours pas fiat le git work...",
                "Wednesday", "8", DiscussionSummary.TypeConversation.PRIVATE_CHAT)
    };

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

            for (DiscussionSummary discussionSummary : discussionsArray) {
                mDiscussions.add(discussionSummary);
            }
        }

        return mDiscussions;
    }
}
