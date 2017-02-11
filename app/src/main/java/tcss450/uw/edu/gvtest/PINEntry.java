package tcss450.uw.edu.gvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Provides a pin entry for users to sign in rather than with a email and password.
 * @author MA450 Team 11
 * @version Winter 2017
 */
public class PINEntry extends AppCompatActivity {

    /**
     * Creates the activity and sets the state if savedInstanceState is passed.
     * @param savedInstanceState Saves the previous state of the activiy if previously
     *                           created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinentry);
    }
}
