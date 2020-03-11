
/**
 * SocketTjener.java  - "Programmering i Java", 4.utgave - 2009-07-01
 *
 * Programmet åpner en socket og venter på at en klient skal ta kontakt.
 * Programmet leser tekster som klienten sender over, og returnerer disse.
 */

import java.io.*;
import java.net.*;

class SocketTjener {
  public static void main(String[] args) throws IOException, InterruptedException {
    final int PORTNR = 1250;

    ServerSocket tjener = new ServerSocket(PORTNR);
    System.out.println("Logg for tjenersiden. Nå venter vi...");
    while (true) {
      Socket forbindelse = tjener.accept(); // venter inntil noen tar kontakt
      Thread klientThread = new TraadKlientWeb(forbindelse);
      // Thread klientThread = new TraadKlientKalkulator(forbindelse);
      klientThread.start();
    }
  }
}

/*
 * Utskrift på tjenersiden: Logg for tjenersiden. Nå venter vi... En klient
 * skrev: Hallo, dette er en prøve. En klient skrev: Og det fungerer utmerket.
 */