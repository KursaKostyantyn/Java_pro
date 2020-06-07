package ua.kiev.prog;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Login  {
    private String login;
    private String password;
    private boolean check = false;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login() {
    }



    private void inputLoginAndPassword (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your login: ");
        login = scanner.nextLine();
        System.out.println("Enter your password: ");
        password = scanner.nextLine();
    }

    public String autorization (){
        inputLoginAndPassword();
        try {
            URL url = new URL(Utils.getURL() + "/login?login=" +login + "&password="+ password);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            if(http.getResponseCode() != 400) {
                check = true;
                System.out.println("Login and password is correct");
            } else  if (http.getResponseCode() == 400){
                System.out.println("incorrect login or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        checkin();
        return login;

    }

    private void checkin () {
        while (!check){
            autorization();
        }
    }

//    @Override
//    public void run() {
//        try {
//            URL url = new URL(Utils.getURL() + "/login?login=" +login + "&password="+ password);
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            if(http.getResponseCode() != 400) {
//                check = true;
//                System.out.println("Login and password is correct");
//            } else  if (http.getResponseCode() == 400){
//                System.out.println("incorrect login or password");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
