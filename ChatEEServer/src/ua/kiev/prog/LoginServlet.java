package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginServlet extends HttpServlet {
     private static HashMap<String,String> hm = new HashMap<>();


    private void fillHM (){
        hm.put("admin", "admin");
        hm.put("user", "qwerty");
    }

    public static  String getUserList() {
        StringBuilder sb= new StringBuilder();
        System.out.println("vizov prowel");
        if(hm.isEmpty()){
            sb.append("User list is empty");
            System.out.println(sb.toString());
            return sb.toString();
        }
        Set <Map.Entry<String, String>> hms= hm.entrySet();
        for(Map.Entry<String,String> hmse: hms ){
            sb.append(hmse.getKey() +";");
        }
        System.out.println(sb.toString());
        return sb.toString() ;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fillHM();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean check = checking(login,password);
        if (!check){
            resp.setStatus(400);
        }
        if(check){
            HttpSession session = req.getSession(true);
            session.setAttribute("user_login", login);
            ActiveSessionsServlet.httpSessions.put(login, session);
        }


    }
     private boolean checking (String login, String password){
         boolean check = false;
         if(hm.get(login) == null){
             return check;
         }
         if(hm.get(login).equals(password)){
             check =true;
         }

         return check;
     }
}
