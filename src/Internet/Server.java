package Internet;

import java.io.*;
import java.net.*;

public class Server {
    ServerSocket server;
    Socket theSocket;
    
    Server(int portNumber){
        try{
            server = new ServerSocket(portNumber);
            theSocket = serverConnect();
            server.close();
            ChatParticipant Rolf = new ChatParticipant(theSocket);
        }
            catch(Exception e){e.getMessage();}
    }
        public Socket serverConnect() throws IOException{
            Socket serverSocket = server.accept();
            if(serverSocket.isConnected()){
                System.out.println("Server is connected!");
            }
        return serverSocket;
        }
        
        public Socket getServerSocket(){
            return theSocket;
        }
        
}