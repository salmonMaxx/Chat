package Internet;

import java.io.*;
import java.util.ArrayList;

public class ReadWriter {
    FileReader fileReader;
    BufferedReader buffReader;
    FileWriter fileWriter;
    String dir;
    ArrayList<String> theList;
    ArrayList<Contact> contactList;
    
    ReadWriter(String theDir){
        dir = theDir;
        theList = new ArrayList<>();
        contactList = loadContact();
    }
    public ArrayList<Contact> loadContact(){
        try {
            fileReader = new FileReader(dir);
            buffReader = new BufferedReader(fileReader);
            String line;
            while((line = buffReader.readLine()) != null){
                theList.add(line);
            }
            ArrayList<Contact> contactList = new ArrayList<>();
            contactList.add(new Contact("Choose","0",0));
            contactList.add(new Contact("New contact","0",0));
            for(int index = 0; index < theList.size(); index++){
                contactList.add(createContact(theList.get(index)));
            }
            //System.out.println("i loadcontact contactlist size: "+contactList.size());
            fileReader.close();
            return contactList;
        } 
        catch (IOException ex) {
            ex.getMessage();
            return null;
        }
    }
    public void saveContact(Contact theContact){
        try{
            fileWriter = new FileWriter(dir, true);
            fileWriter.write("\n" + theContact.name + " " + theContact.IP + " " + theContact.port +"\n");
            System.out.println("contact saved!");
            fileWriter.close();
        }
        catch(IOException e){
            e.getMessage();
        }
    }
    Contact createContact(String theString){
        String[] contactArray = theString.split(" ");
        System.out.println(contactArray.length);
        System.out.println(theString);
        String name = contactArray[0];
        String IP = contactArray[1];
        int port = Integer.parseInt(contactArray[2]);
        Contact theContact = new Contact(name, IP, port);
        return theContact;
    }
    
    public Contact findName(String name){
       // System.out.println("inuti find contactList size: " + contactList.size());
       
        Contact goal = null;
        for(Contact contact:contactList){
            if(contact.name == name){
                goal = contact;
            }
        }
        return goal;
    }
}