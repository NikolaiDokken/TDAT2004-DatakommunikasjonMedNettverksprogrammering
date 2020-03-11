import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TraadKlientKalkulator extends Thread {

    Socket forbindelse;

    public TraadKlientKalkulator(Socket forbindelse) {
        this.forbindelse = forbindelse;
    }

    public void run() {
        /* Åpner strømmer for kommunikasjon med klientprogrammet */
        InputStreamReader leseforbindelse = null;
        PrintWriter skriveren = null;
        try {
            leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
            skriveren = new PrintWriter(forbindelse.getOutputStream(), true);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        BufferedReader leseren = new BufferedReader(leseforbindelse);

        /* Mottar data fra klienten */
        String enLinje = null;
        try {
            enLinje = leseren.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } // mottar en linje med tekst
        while (enLinje != null) { // forbindelsen på klientsiden er lukket
            System.out.println("Mottok: " + enLinje);
            String[] tegn = enLinje.split(" ");
            skriveren.println(
                    "Svaret er: " + (tegn[1].equals("+") ? Integer.parseInt(tegn[0]) + Integer.parseInt(tegn[2])
                            : Integer.parseInt(tegn[0]) - Integer.parseInt(tegn[2])));
            try {
                enLinje = leseren.readLine();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } // mottar en linje med tekst
        }

        try {
            leseren.close();
            skriveren.close();
            forbindelse.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}