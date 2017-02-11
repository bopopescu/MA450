package tcss450.uw.edu.gvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Initial screen upon app startup. Provides user capability to login
 * or navigate to another screen to register.
 */
public class MainActivity extends AppCompatActivity {
    private static final String PARTIAL_URL = "http://cssgate.insttech.washington.edu/~ekoval/";

    EditText email;
    EditText password;

    /**
     * Initializes activity
     *
     * @param savedInstanceState the instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Navigates to the "sign up" activity
     *
     * @param view The View passed in when this method is called.
     */
    public void registerUser(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Directs the email to the overview activity after logging in.
     *
     * @param view The View passed in when this method is called.
     */
    public void logInUser(View view) {
        email = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText3);

        if (email.getText().toString().length() >= 1 && password.getText().toString().length() >= 1) {
            AsyncTask<String, Void, String> task = null;
            String message = ((EditText) findViewById(R.id.editText2)).getText().toString();
            String message2 = ((EditText) findViewById(R.id.editText3)).getText().toString();

            task = new GetWebServiceTask();
            task.execute(PARTIAL_URL, message, message2);

        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_LONG).show();

        }

    }

    /**
     * Asynchronously verifies login credentials
     */
    private class GetWebServiceTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "login.php";

        /**
         * Executes async tasks on a separate thread.
         *
         * @param strings The parameters to be passed in.
         * @return The result.
         */
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "?username=" + strings[1] + "&password=" + strings[2];
            try {
                URL urlObject = new URL(url + SERVICE + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }

        /**
         * Begins the "overview" activity
         *
         * @param result the result.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else if (result.contains("Error")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            } else {
                startActivity(new Intent(getApplicationContext(), OverviewActivity.class));
            }

        }
    }

}