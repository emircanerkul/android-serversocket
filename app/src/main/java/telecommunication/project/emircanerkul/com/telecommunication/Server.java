package telecommunication.project.emircanerkul.com.telecommunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 8888;

    MainActivity mainActivity;

    Servant servant;
    Socket socket;

    public Server(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        new Thread(new ClientListener()).start();

        mainActivity.logger.push("İstemci bekleniyor");
    }

    private class ClientListener extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                Thread bridgeThread = null;

                while (true) {
                    if (socket == null) {
                        socket = serverSocket.accept();

                        if (socket.isBound()) {
                            bridgeThread = new Thread(servant = new Servant(socket, mainActivity));
                            bridgeThread.start();
                            connected();
                        }
                    }

                    if (socket.isClosed()) {
                        socket = null;
                        bridgeThread.interrupt();

                        disconnected();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connected() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.txtClientInfo.setText("İstemci : " + servant.getClientInfo());
                mainActivity.logger.push("İstemci bağlandı");
            }
        });
    }

    private void disconnected() {
        mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                mainActivity.txtClientInfo.setText("İstemci : ");
                mainActivity.logger.push("İstemci bağlantısı koptu");
            }
        });
    }
}
