package serverside;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServerApp {

        public static void main(String[] args) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = null ;
            try (ServerSocket serverSocket = new ServerSocket( 8189 )) {
                System.out.println( "Сервер запущен, ожидаем подключения..." );
                socket = serverSocket.accept();
                System.out.println( "Клиент подключился" );
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());


                Thread clientMessage = new Thread(()-> {
                    while ( true ) {
                        String str = null;
                        try {
                            str = dis.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (str.equals( "/q" )) {
                            break ;
                        }
                        try {
                            dos.writeUTF(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                clientMessage.start();

                while(true){
                    dos.writeUTF(reader.readLine());
                }





            } catch (IOException e) {
                e.printStackTrace();
            }




        }
    }


