package ua.kiev.prog;

import java.net.HttpURLConnection;
import java.net.URL;

public class ActiveUser {
    private static boolean status = false;

    public static boolean isActiveUser (String user) {
        try{
            URL url = new URL(Utils.getURL() + "/ActiveSessionsServlet?user=" + user);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            if (http.getResponseCode() == 400){
                status = false;
                return status;
            }
            status = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;

    }
}
