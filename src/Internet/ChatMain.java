/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Internet;

import java.io.IOException;
import java.net.*;

/**
 *  KVAR:
 * - gör läsare
 * - göra metod som gör kontaktlista (array av contacts) 
 * - fixa actionlistener till jcombo
 *
 */
public class ChatMain{
    public static void main(String[] args){
//        ReadWriter rw;
//        String dir = "C:\\Users\\maxxw\\OneDrive\\Dokument\\NetBeansProjects\\theText.txt";
//        rw = new ReadWriter(dir);
//        
          boolean isServer = false;
            int port = 8889;
            if(isServer){
                Server server = new Server(port);
                isServer = !isServer;
            }
        try{
            String IP = InetAddress.getLocalHost().getHostAddress();
            Client client = new Client();
        }
        catch(IOException e){System.out.println(e.getMessage());} 
    }
}