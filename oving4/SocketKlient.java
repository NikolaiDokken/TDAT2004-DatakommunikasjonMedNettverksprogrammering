/*
 * SocketKlient.java  - "Programmering i Java", 4.utgave - 2009-07-01
 *
 * Programmet kontakter et tjenerprogram som allerede kjører på port 1250.
 * Linjer med tekst sendes til tjenerprogrammet. Det er laget slik at
 * det sender disse tekstene tilbake.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

class SocketKlient {
  public static void main(String[] args) throws IOException {
    final int PORTNR = 1250;

    /* Bruker en scanner til å lese fra kommandovinduet */
    Scanner leserFraKommandovindu = new Scanner(System.in);
    System.out.print("Oppgi navnet på maskinen der tjenerprogrammet kjører: ");
    String tjenermaskin = leserFraKommandovindu.nextLine();

    /* Setter opp forbindelsen til tjenerprogrammet */
    Socket forbindelse = new Socket(tjenermaskin, PORTNR);
    System.out.println("Nå er forbindelsen opprettet.");

    /* Åpner en forbindelse for kommunikasjon med tjenerprogrammet */
    InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
    BufferedReader leseren = new BufferedReader(leseforbindelse);
    PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);

    ///////// NIKO KODE /////////////
    System.out.println("1: Pluss\n2: Minus\n0: avslutt");
    int valg = leserFraKommandovindu.nextInt();

    while (!(valg == 1 || valg == 2 || valg == 0)) {
      System.out.println("Vennligst velg et gyldig valg");
      System.out.println("1: Pluss\n2: Minus\n0: avslutt");
      valg = leserFraKommandovindu.nextInt();
    }
    while (valg != 0) {
      System.out.println("Skriv inn tall nr.1:");
      int tall1 = leserFraKommandovindu.nextInt();
      System.out.println("Skriv inn tall nr.2:");
      int tall2 = leserFraKommandovindu.nextInt();

      if (valg == 1) {
        skriveren.println(tall1 + " + " + tall2); // sender teksten til tjeneren
      }

      if (valg == 2) {
        skriveren.println(tall1 + " - " + tall2); // sender teksten til tjeneren
      }

      String respons = leseren.readLine(); // mottar respons fra tjeneren
      System.out.println(respons);

      System.out.println("1: Pluss\n2: Minus\n0: avslutt");
      valg = leserFraKommandovindu.nextInt();
      while (!(valg == 1 || valg == 2 || valg == 0)) {
        System.out.println("Vennligst velg et gyldig valg");
        System.out.println("1: Pluss\n2: Minus\n0: avslutt");
        valg = leserFraKommandovindu.nextInt();
      }
    }

    /* Lukker forbindelsen */
    leseren.close();
    skriveren.close();
    forbindelse.close();
    leserFraKommandovindu.close();
  }
}