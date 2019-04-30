package telecommunication.project.emircanerkul.com.telecommunication;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Servant implements Runnable {

    Socket socket;
    MainActivity mainActivity;

    Servant(Socket socket, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.socket = socket;
    }

    public String getClientInfo() {
        return socket.getRemoteSocketAddress().toString().substring(1);
    }

    Scanner in;
    PrintWriter out;

    @Override
    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("           ╔═════════════════════════════════════╗");
            out.println("           ║┌┐░┌┐░┌┐░░┌┐░░┌───┐░░┌───┐░░┌─┐┌─┐░░░║");
            out.println("           ║││░││░│└┐┌┘│░░└┐┌┐│░░│┌─┐│░░││└┘││░░░║");
            out.println("           ║│└─┘├─┴┐││┌┴─┐░│││├┐┌┤└─┘├┐┌┤┌┐┌┐├┐┌┐║");
            out.println("           ║│┌─┐│┌┐│└┘│┌┐│░│││││││┌┐┌┤│││││││││││║");
            out.println("           ║││░││┌┐├┐┌┤┌┐│┌┘└┘│└┘│││└┤└┘││││││└┘│║");
            out.println("           ║└┘░└┴┘└┘└┘└┘└┘└───┴──┴┘└─┴──┴┘└┘└┴──┘║");
            out.println("           ╠═════════════════════════════════════╣");
            out.println("           ║emircanerkul.com/android-serversocket║");
            out.println("           ╚═════════════════════════════════════╝");

            while (in.hasNextLine()) {
                String c = in.nextLine();

                if (c.length() >= 3) {
                    boolean isRaw = false;
                    if (c.substring(0, 1) == "!") {
                        isRaw = true;
                        c = c.substring(1);
                    }

                    history("İstek alındı \"" + c + "\"");
                    history("Veriler alınıyor");

                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + c + "&APPID=2bbffe51d45c67abf0aea3fafa7d0a2f");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        String result = readStream(new BufferedInputStream(urlConnection.getInputStream()));
                        JSONObject data = new JSONObject(result);

                        history("Veriler alındı");

                        if (isRaw) out.println(result);
                        else if (data.getInt("cod") == 200) {
                            int weatherId = data.getJSONArray("weather").getJSONObject(0).getInt("id");

                            String temp = String.format("%.2f", kelvinToCelsius(data.getJSONObject("main").getDouble("temp")));
                            String temp_max = String.format("%.2f", kelvinToCelsius(data.getJSONObject("main").getDouble("temp_max")));
                            String temp_min = String.format("%.2f", kelvinToCelsius(data.getJSONObject("main").getDouble("temp_min")));
                            String pressure = String.valueOf(data.getJSONObject("main").getDouble("pressure"));
                            String humidity = String.valueOf(data.getJSONObject("main").getDouble("humidity"));

                            out.println(c + " " + weather(weatherId) + " " + temp + " derece, en yuksek sicaklik " + temp_max + " derece, en dusuk sicaklik " + temp_min + " derece, basinc: " + pressure + " hPa, nem:  %" + humidity + "\n");
                            history("Veriler istemciye gönderildi");
                        } else {
                            history("Veriler alınamadı!");
                        }

                    } catch (Exception e) {
                        history("Veriler alınamadı!");
                    } finally {
                        urlConnection.disconnect();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + socket);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
        }
    }

    private void history(final String t) {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.logger.push(t);
            }
        });
    }

    private Double kelvinToCelsius(Double d) {
        return d - 273.15;
    }

    private String weather(int c) {
        Map<Integer, String> a = new HashMap<Integer, String>();


        a.put(200, "saganak yagmurlu");
        a.put(201, "saganak yagmurlu");
        a.put(202, "saganak yagmurlu");
        a.put(210, "hafif saganak yagmurlu");
        a.put(211, "saganak yagmurlu");
        a.put(212, "yogun saganak yagmurlu");
        a.put(221, "duzensiz saganak yagmurlu");
        a.put(230, "saganak yagmurlu");
        a.put(231, "saganak yagmurlu");
        a.put(232, "saganak yagmurlu");
        a.put(300, "hafif ciseli");
        a.put(301, "ciseli");
        a.put(302, "yogun ciseli");
        a.put(310, "hafif ciseleyen yagmurlu");
        a.put(311, "ciseleyen yagmurlu");
        a.put(312, "yogun ciseleyen yagmurlu");
        a.put(313, "ciseleyen yagmurlu");
        a.put(314, "ciseleyen yagmurlu");
        a.put(321, "ciseleyen yagmurlu");
        a.put(500, "hafif yagmurlu");
        a.put(501, "hafif yagmurlu");
        a.put(502, "siddetli yagmurlu");
        a.put(503, "cok siddetli yagmurlu");
        a.put(504, "asiri cok siddetli yagmurlu");
        a.put(511, "dondurucu yagmurlu");
        a.put(520, "saganak yagmurlu");
        a.put(521, "saganak yagmurlu");
        a.put(522, "saganak yagmurlu");
        a.put(531, "saganak yagmurlu");
        a.put(600, "hafif karli");
        a.put(601, "karli");
        a.put(602, "yogun karli");
        a.put(611, "sulu karli");
        a.put(612, "hafif sagnak seklinde karli");
        a.put(613, "sagnak karli");
        a.put(615, "karla karisik yagmurlu");
        a.put(616, "karla karisik yagmurlu");
        a.put(620, "karla karisik yagmurlu");
        a.put(621, "sagnak seklinde karli");
        a.put(622, "yogun sagnak seklinde karli");
        a.put(701, "sisli");
        a.put(711, "dumanli");
        a.put(721, "puslu");
        a.put(731, "toz firtinali");
        a.put(741, "sisli");
        a.put(751, "kum firtinali");
        a.put(761, "toz firtinali");
        a.put(762, "volkanik kul yagisli");
        a.put(771, "ani firtinali");
        a.put(781, "kasirgali");
        a.put(800, "gunesli");
        a.put(801, "parcali bulutlu");
        a.put(802, "parcali bulutlu");
        a.put(803, "parcali bulutlu");
        a.put(804, "parcali bulutlu");

        return a.get(c);
    }

    //https://stackoverflow.com/questions/8376072/whats-the-readstream-method-i-just-can-not-find-it-anywhere
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
