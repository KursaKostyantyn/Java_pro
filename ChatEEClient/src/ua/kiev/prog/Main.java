package ua.kiev.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Login auth = new Login();
			String login =auth.autorization();


			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

			System.out.println("Enter your message: ");

			while (true) {

				String text = scanner.nextLine();
				SpecialCommands.setForUser("all");
				if (text.isEmpty()) break;

				if(text.startsWith("/")){
					SpecialCommands.start();
					continue;
				}

				String forUser = SpecialCommands.getForUser();

				Message m = new Message(login, text, forUser);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occured: " + res);
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
