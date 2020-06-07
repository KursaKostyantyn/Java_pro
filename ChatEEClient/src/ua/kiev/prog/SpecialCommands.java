package ua.kiev.prog;

import java.util.*;

public class SpecialCommands {
    private static HashMap<Integer,String> listOfCommands= new HashMap();
    private static String forUser = "all";
    static {
        listOfCommands.put(0, "exit");
        listOfCommands.put(1, "private message to user");
        listOfCommands.put(2, "get user list");
        listOfCommands.put(3, "user is active?");
    }

    public static String getForUser() {
        return forUser;
    }

    public static void setForUser(String forUser) {
        SpecialCommands.forUser = forUser;
    }

    public static void start(){
        Scanner sc= new Scanner(System.in);
        System.out.println("List of special commands:");
        for(Map.Entry<Integer,String> me: listOfCommands.entrySet()){
            System.out.println(me.getKey() + " = " + me.getValue());
        }
        System.out.println("Select command - input number");
        int number = -1;
        while (number <0 || number >3) {
            try {
                number = sc.nextInt();
            } catch (InputMismatchException  ex ) {
                System.out.println("incorect input");
                System.out.println("input / for to come in to special commands");
                return;

            }
            if (number<0 || number>3 ){
                System.out.println("There is no such number command");
                number = -1;
            }
        }

        selectCommand(number);
    }

    private static void selectCommand (int number){
        if (number ==1 ){
            privateMessage();
        } else if (number ==2) {
            getUserList();
        } else if (number ==3) {
            activeUser();
        }
    }


    private static void privateMessage (){
        Scanner sc = new Scanner(System.in);
        String text;
        System.out.println("Input user login:");
        String user = sc.nextLine();
        forUser = user;
        System.out.println("Enter your message for: " + user);
        text = sc.nextLine();
        try {
            Message m = new Message(user, text, forUser);
            int res = m.send(Utils.getURL() + "/add");

            if (res != 200) { // 200 OK
                System.out.println("HTTP error occured: " + res);
                return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void getUserList(){
        try{
            GetUserList.getUserList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void activeUser (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Input user login:");
        String user = sc.nextLine();
        System.out.println("User: " + user + "active = " + ActiveUser.isActiveUser(user));
    }

}
