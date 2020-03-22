import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Klient {

    public static void main(String[] args) throws Exception {
        // Remember to insert DB credentials in persistence.xml

        oppgave2();
        oppgave3();
        oppgave4();
        cleanUp();
    }

    public static void oppgave2() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        KontoDAO dao = new KontoDAO(entityManagerFactory);
        System.out.println("Oppgave 2");

        // Opprett to kontoer og lagre til databasen
        Konto konto1 = new Konto();
        konto1.setKontonr(1);
        konto1.setEier("Nikolai");
        konto1.setSaldo(1000);

        dao.lagreNyKonto(konto1);

        Konto konto2 = new Konto();
        konto2.setKontonr(2);
        konto2.setEier("Studass");
        konto2.setSaldo(500);

        dao.lagreNyKonto(konto2);

        // List kontoer med saldo over et gitt belop
        double saldo = 700;
        System.out.println("Kontoer med saldo over " + saldo + ":");
        for (Konto konto : dao.getKontoerOverSaldo(saldo)) {
            System.out.println("    " + konto);
        }

        // Endre eier paa en konto og oppdater databasen
        konto2.setEier("Ny Studass");
        dao.oppdaterKonto(konto2);

    }

    public static void oppgave3() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        KontoDAO dao = new KontoDAO(entityManagerFactory);
        System.out.println("\nOppgave 3");

        Thread thread = new Thread(() -> {
            dao.overfor(1, 2, 100);
        });
        thread.start();
        dao.overfor(1, 2, 100);
        thread.join();

        System.out.println("Forventet: Konto 1, saldo 800. Konto 2, saldo 700");
        System.out.println(dao.getKonto(1));
        System.out.println(dao.getKonto(2));

        // Tilbakestill saldoene
        Konto konto = dao.getKonto(1);
        konto.setSaldo(1000);
        dao.oppdaterKonto(konto);
        konto = dao.getKonto(2);
        konto.setSaldo(500);
        dao.oppdaterKonto(konto);
    }

    public static void oppgave4() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        KontoDAO dao = new KontoDAO(entityManagerFactory);
        System.out.println("\nOppgave 4");

        KontoLocked konto1 = new KontoLocked();
        konto1.setKontonr(3);
        konto1.setEier("Niko 2");
        konto1.setSaldo(1000);
        dao.lagreNyKontoLocked(konto1);

        KontoLocked konto2 = new KontoLocked();
        konto2.setKontonr(4);
        konto2.setEier("Studass 3");
        konto2.setSaldo(500);
        dao.lagreNyKontoLocked(konto2);

        Thread thread = new Thread(() -> {
            dao.overforLocked(3, 4, 100);
        });
        thread.start();
        dao.overforLocked(3, 4, 100);
        thread.join();

        System.out.println("Forventet: Konto 3, saldo 800. Konto 4, saldo 700");
        System.out.println(dao.getKontoLocked(3));
        System.out.println(dao.getKontoLocked(4));

        // Tilbakestill saldoene
        KontoLocked konto = dao.getKontoLocked(3);
        konto.setSaldo(1000);
        dao.oppdaterKontoLocked(konto);
        konto = dao.getKontoLocked(4);
        konto.setSaldo(500);
        dao.oppdaterKontoLocked(konto);
    }

    public static void cleanUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        KontoDAO dao = new KontoDAO(entityManagerFactory);

        dao.deleteKonto(1);
        dao.deleteKonto(2);
        dao.deleteKontoLocked(3);
        dao.deleteKontoLocked(4);

    }
}