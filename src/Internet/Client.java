package Internet;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Client implements ItemListener, ActionListener{
    Socket socket;
    ReadWriter readWriter;
    JFrame theFrame;
    JComboBox selection;
    JTextField contactName;
    JTextField contactIP;
    JTextField contactPort;
    JDialog newContact;
    
    Client(){
        theFrame = new JFrame();
        readWriter = new ReadWriter("C:\\Users\\maxxw\\OneDrive\\Dokument\\NetBeansProjects\\theText.txt");
        JDialog chooseContact = new JDialog(theFrame, "Choose contact", false);
        chooseContact.setSize(new Dimension(30,10));
        
        
        String[] nameArray = new String[readWriter.contactList.size()];
        for(int index = 0; index < readWriter.contactList.size(); index++){
            nameArray[index] = readWriter.contactList.get(index).name;
        }
        selection = new JComboBox(nameArray);
        theFrame.setPreferredSize(new Dimension(100,20));
        selection.setPreferredSize(new Dimension(100,20));
        chooseContact.add(selection);
        selection.addItemListener(this);
        theFrame.setVisible(false);
        chooseContact.setVisible(true);
        // Här ska jD skapas och allt ska matas in i socket

        
    }
        
        public Socket getSocket(){
            return socket;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            JComboBox combo = (JComboBox)e.getSource();
            String theName = (String)combo.getSelectedItem();
            if(theName == "New contact"){
                newContact = new JDialog(theFrame, "new contact", false);
                JButton addButton = new JButton("Add");
                addButton.addActionListener(this);
                contactName = new JTextField(15);
                contactIP = new JTextField(15);
                contactPort = new JTextField(15);
                newContact.setLayout(new GridLayout(4,1));
                newContact.add("Name: ", contactName);
                newContact.add("IP adress: ",contactIP);
                newContact.add("Port number: ",contactPort);
                newContact.add(addButton);
                newContact.setVisible(true);
            }
            else if(theName == "Choose"){}
            else{
                    
                    Contact theContact = readWriter.findName(theName);
                    System.out.println("name found " +theContact.name);
                try {
                    socket = new Socket(theContact.IP, theContact.port);
                    System.out.println("socketcreated");
                    ChatParticipant Golf = new ChatParticipant(socket);
                    System.out.println("golfe created");
                } 
                catch (IOException ex) {System.out.println(ex.getMessage());}

        //            socket = new Socket(IP, theSocket);
        //            ChatParticipant Golf = new ChatParticipant(socket);
            }
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        Contact newCreatedContact = new Contact(contactName.getText(), contactIP.getText(), Integer.parseInt(contactPort.getText()));
        readWriter.saveContact(newCreatedContact);
        newContact.dispose(); //lägg till så att nya kontakten laddas in i lådan!! 
        
    }
}