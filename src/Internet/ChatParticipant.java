package Internet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ChatParticipant implements ActionListener, ItemListener, ObjectStreamListener {
    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream out;
    InputStream inputStream;
    ObjectInputStream in;
    JFrame frame;
    JTextArea textArea;
    JTextField textField;
    //String name;
    JButton send;
    JButton logOff;
    JComboBox myFriends;
    ReadWriter readWriter;
    ArrayList<Contact> contactArray;
    
    ChatParticipant(Socket theSocket) throws IOException{
        //name = this.toString();
        socket = theSocket;
        try{
            readWriter = new ReadWriter("C:\\Users\\maxxw\\OneDrive\\Dokument\\NetBeansProjects\\theText.txt");
            createTextStreams(socket);
            contactArray = readWriter.loadContact();
            createWindow();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
    
        public void createTextStreams(Socket socket) throws IOException{
            outputStream = socket.getOutputStream();
            out = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            in = new ObjectInputStream(inputStream);
            ObjectStreamManager manager = new ObjectStreamManager(1, in, this);
        }
        @Override
        public void objectReceived(int number, Object object, Exception exception) {
            String message = (String) object;
            if(message == null){}
            if(message != null){
                if(message.equals("iHaveLoggedOut")){
                    createDialog();
                }  
            textArea.append("Golf says:\n" + message + "\n");
            }
        }
        public void createClientWindow(){
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //EXIT_ON_CLOSE
            frame.setPreferredSize(new Dimension(600,400));

            JPanel primary = new JPanel();
            primary.setLayout(new BorderLayout());
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(200,400));
            buttonPanel.setLayout(new BorderLayout());
            send = new JButton("send");
            send.addActionListener(this);
            logOff = new JButton("log-off");
            logOff.addActionListener(this);
            buttonPanel.add(send, BorderLayout.SOUTH);
            buttonPanel.add(logOff,BorderLayout.NORTH);
            
            JPanel comboPanel = new JPanel();
            //String[] testArray = {"Kalle", "Pelle", "Golf", "Rolf"};//ta bort sen!
            String[] nameArray = new String[contactArray.size()];
            for (int index = 0; index < contactArray.size(); index++){
                nameArray[index] = contactArray.get(index).name;
            }
            myFriends = new JComboBox(nameArray);
            myFriends.addItemListener(this);
            comboPanel.add(myFriends);
            buttonPanel.add(comboPanel,  BorderLayout.CENTER);
            
            JPanel chatPanel = new JPanel();
            textArea = new JTextArea(5,20);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(5,20));
            textField = new JTextField(20);
            chatPanel.setLayout(new BorderLayout());
            chatPanel.add(textField, BorderLayout.SOUTH);
            chatPanel.add(scrollPane, BorderLayout.CENTER);
            
            primary.add(chatPanel, BorderLayout.CENTER);
            primary.add(buttonPanel, BorderLayout.EAST);
            frame.add(primary);
            frame.pack();
            frame.setVisible(true);
        }
        
        public void createWindow(){
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //EXIT_ON_CLOSE
            frame.setPreferredSize(new Dimension(600,400));

            JPanel primary = new JPanel();
            primary.setLayout(new BorderLayout());
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(200,400));
            buttonPanel.setLayout(new BorderLayout());
            send = new JButton("send");
            send.addActionListener(this);
            logOff = new JButton("log-off");
            logOff.addActionListener(this);
            buttonPanel.add(send, BorderLayout.SOUTH);
            buttonPanel.add(logOff,BorderLayout.NORTH);
            
            JPanel chatPanel = new JPanel();
            textArea = new JTextArea(5,20);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(5,20));
            textField = new JTextField(20);
            chatPanel.setLayout(new BorderLayout());
            chatPanel.add(textField, BorderLayout.SOUTH);
            chatPanel.add(scrollPane, BorderLayout.CENTER);
            
            primary.add(chatPanel, BorderLayout.CENTER);
            primary.add(buttonPanel, BorderLayout.EAST);
            frame.add(primary);
            frame.pack();
            frame.setVisible(true);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == send){
                String text = textField.getText();
                textArea.append("Rolf says:\n" +text + "\n");
                try {
                    out.writeObject(text);
                } 
                catch (IOException ex) {
                    ex.getMessage();
                }
                    textField.setText("");
            }
            if(e.getSource() == logOff){
                try {
                    out.writeObject("iHaveLoggedOut");
                    closeStreamsAndSockets();
                    frame.dispose();
                } 
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        
        
        public void closeStreamsAndSockets() throws IOException{
            outputStream.close();
            socket.close();
            //lägg till socket
        }
        
        public void createDialog(){
            JDialog theBox = new JDialog(frame, "Other part left!!!!!", false);
            theBox.setSize(new Dimension(20,20));
            JButton close = new JButton("Close chat.");
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    theBox.dispose();
                    frame.dispose();
                }
                 });
            theBox.getContentPane().add(close, BorderLayout.SOUTH);
            try{
                closeStreamsAndSockets();
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
            theBox.pack();
            theBox.setVisible(true);
            theBox.getDefaultCloseOperation();  
        }

    @Override
    public void itemStateChanged(ItemEvent e) { 
        //Lägg till "select" så den kan förändras
    }
}