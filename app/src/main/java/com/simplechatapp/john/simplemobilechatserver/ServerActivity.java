package com.simplechatapp.john.simplemobilechatserver;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by John on 7/7/2015.
 */
public class ServerActivity extends Activity {

    private final int SERVERSOCKETPORT = 9090;

    private ServerSocket serverSocket;
    private ArrayList<ChatClient> chatClientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        chatClientList = new ArrayList<ChatClient>();

        super.onCreate(savedInstanceState);
    }

    private class ServerConnection implements Runnable {


        @Override
        public void run() {

            try {
                serverSocket = new ServerSocket(SERVERSOCKETPORT);

                while (true) {
                    Socket socket = serverSocket.accept();
                    ChatClient chatClient = new ChatClient();
                    chatClientList.add(chatClient);

                    Thread thread = new Thread(new ClientConnection(chatClient, socket));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private class ClientConnection implements Runnable {

        private ChatClient chatClient;
        private Socket socket;

        public ClientConnection(ChatClient chatClient, Socket socket) {
            this.chatClient = chatClient;
            this.socket = socket;

            this.chatClient.setSocket(socket);
            this.chatClient.setClientConnection(this);
        }

        @Override
        public void run() {

        }
    }

    private class ChatClient {
        private String username;
        private Socket socket;
        private ClientConnection clientConnection;

        private String getUsername() {
            return this.username;
        }

        private Socket getSocket() {
            return this.socket;
        }

        private ClientConnection getClientConnection() {
            return this.clientConnection;
        }

        private void setUsername(String username) {
            this.username = username;
        }

        private void setSocket(Socket socket) {
            this.socket = socket;
        }

        private void setClientConnection(ClientConnection clientConnection) {
            this.clientConnection = clientConnection;
        }
    }
}
