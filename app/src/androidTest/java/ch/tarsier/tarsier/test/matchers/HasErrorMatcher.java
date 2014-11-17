package ch.tarsier.tarsier.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.widget.EditText;

import ch.tarsier.tarsier.Tarsier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author romac
 */
public class HasErrorMatcher extends TypeSafeMatcher<View> {

    private String mError;

    public HasErrorMatcher(final String error) {
        mError = checkNotNull(error);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has an error");
    }

    @Override
    public boolean matchesSafely(View view) {
        EditText editText = (EditText) view;

        return editText.getError() != null
            && editText.getError().toString().equals(mError);
    }

    public static Matcher<View> hasError(final String error) {
        return new HasErrorMatcher(error);
    }

    public static Matcher<View> hasError(final int resource) {
        return hasError(Tarsier.app().getResources().getString(resource));
    }
}
