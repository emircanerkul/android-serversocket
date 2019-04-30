package telecommunication.project.emircanerkul.com.telecommunication;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class Logger {
    public static int MAX_STACK = 6;
    Stack<String> logs = new Stack<>();
    TextView txtHistory;

    public Logger(TextView txtHistory) {
        this.txtHistory = txtHistory;
    }

    public void push(String s) {
        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        logs.push("{" + time.format(date) + "} " + s);

        if (logs.size() > MAX_STACK) logs.remove(0);
        txtHistory.setText(implode("\n", logs));
    }

    //https://stackoverflow.com/questions/11248119/java-equivalent-of-phps-implode-array-filter-array
    private static String implode(String separator, Stack<String> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size() - 1; i++) {
            if (!data.get(i).matches(" *")) {
                sb.append(data.get(i));
                sb.append(separator);
            }
        }
        sb.append(data.get(data.size() - 1).trim());
        return sb.toString();
    }
}
