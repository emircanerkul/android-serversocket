package telecommunication.project.emircanerkul.com.telecommunication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Server server;
    TextView txtClientInfo, txtServerInfo;
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(Locale.US);

        txtClientInfo = findViewById(R.id.txtClientInfo);
        txtServerInfo = findViewById(R.id.txtServerInfo);

        logger = new Logger((TextView) findViewById(R.id.txtLogs));
        server = new Server(this);

        txtServerInfo.setText("Sunucu : 192.168.43.1:" + server.PORT);
        txtClientInfo.setText("İstemci : ");
    }
}
