/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentskaaplikacija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Jana
 */
public class KlijentskaAplikacija {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        while(true) {
            System.out.println("Unesite broj zahteva:\n" +
                               "1) Kreiranje grada\n" +
                               "2) Kreiranje korisnika\n" +
                               "3) Promena email adrese za korisnika\n" +
                               "4) Promena mesta za korisnika\n" +
                               "5) Kreiranje kategorije\n" +
                               "6) Kreiranje video snimka\n" +
                               "7) Promena naziva video snimka\n" +
                               "8) Dodavanje kategorije video snimku\n" +
                               "9) Kreiranje paketa\n" +
                               "10) Promena mesecne cene za paket\n" +
                               "11) Kreiranje pretplate korisnika na paket\n" +
                               "12) Kreiranje gledanja video snimka od strane korisnika\n" +
                               "13) Kreiranje ocene korisnika za video snimak\n" +
                               "14) Menjanje ocene korisnika za video snimak\n" +
                               "15) Brisanje ocene korisnika za video snimak\n" +
                               "16) Brisanje video snimka od strane korisnika koji ga je kreirao\n" +
                               "17) Dohvatanje svih mesta\n" +
                               "18) Dohvatanje svih korisnika\n" +
                               "19) Dohvatanje svih kategorija\n" +
                               "20) Dohvatanje svih video snimaka\n" +
                               "21) Dohvatanje kategorija za odredjeni video snimak\n" +
                               "22) Dohvatanje svih paketa\n" +
                               "23) Dohvatanje svih pretplata za korisnika\n" + 
                               "24) Dohvatanje svih gledanja za video snimak\n" + 
                               "25) Dohvatanje svih ocena za video snimak"
                    );
            
            Scanner scanner = new Scanner(System.in);   
            int stavka = Integer.parseInt(scanner.nextLine());
            String adresa = "";
            String metoda = "";
            
            switch(stavka) {
                case 1:
                    System.out.println("Unesite naziv grada:");
                    String naziv = scanner.nextLine();
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/kreirajGrad/" + naziv;
                    metoda = "POST";
                    break;
                case 2:
                    System.out.println("Unesite ime korisnika:");
                    String ime = scanner.nextLine();
                    System.out.println("Unesite email korisnika:");
                    String email = scanner.nextLine();
                    System.out.println("Unesite godiste korisnika:");
                    int godiste = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite pol korisnika:");
                    String pol  = scanner.nextLine();
                    System.out.println("Unesite ID mesta korisnika:");
                    int IDMes = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/kreirajKorisnika/" +
                            ime + '/' + email + '/' + godiste + '/' + pol + '/' + IDMes;
                    metoda = "POST";
                    break;
                case 3:
                    System.out.println("Unesite ID korisnika:");
                    int IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite novi email:");
                    email = scanner.nextLine();
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/promeniEmail/" +
                            IDKor + '/' + email;
                    metoda = "POST";
                    break;
                case 4:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite novi ID mesta:");
                    IDMes = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/promeniMesto/" +
                            IDKor + '/' + IDMes;
                    metoda = "POST";
                    break;
                case 5:
                    System.out.println("Unesite naziv kategorije:");
                    naziv = scanner.nextLine();
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/kreirajKategoriju/" + naziv;
                    metoda = "POST";
                    break;
                case 6:
                    System.out.println("Unesite naziv video snimka:");
                    naziv = scanner.nextLine();
                    System.out.println("Unesite trajanje video snimka:");
                    int trajanje = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID korisnika vlasnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/kreirajVideoSnimak/" +
                            naziv + '/' + trajanje + '/' + IDKor;
                    metoda = "POST";
                    break;
                case 7:
                    System.out.println("Unesite ID video snimka:");
                    int IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite novi naziv:");
                    naziv = scanner.nextLine();
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/promeniNaziv/" +
                            IDVid + '/' + naziv;
                    metoda = "POST";
                    break;
                case 8:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID kategorije:");
                    int IDKat = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/dodajKategoriju/" +
                            IDVid + '/' + IDKat;
                    metoda = "POST";
                    break;
                case 9:
                    System.out.println("Unesite cenu paketa:");
                    int cena = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/kreirajPaket/" + cena;
                    metoda = "POST";
                    break;
                case 10:
                    System.out.println("Unesite ID paketa:");
                    int IDPak = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite novu cenu:");
                    cena = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/promeniCenu/" +
                            IDPak + '/' + cena;
                    metoda = "POST";
                    break;
                case 11:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID paketa:");
                    IDPak = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/kreirajPretplatu/" +
                            IDKor + '/' + IDPak;
                    metoda = "POST";
                    break;
                case 12:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite sekund od kog je zapoceto gledanje:");
                    int sekund = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite broj sekundi koji je odgledan:");
                    int brSekundi = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/kreirajGledanje/" +
                            IDKor + '/' + IDVid + '/' + sekund + '/' + brSekundi;
                    metoda = "POST";
                    break;
                case 13:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ocenu:");
                    int vrednost = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/kreirajOcenu/" +
                            IDKor + '/' + IDVid + '/' + vrednost;
                    metoda = "POST";
                    break;
                case 14:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite novu ocenu:");
                    vrednost = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/promeniOcenu/" +
                            IDKor + '/' + IDVid + '/' + vrednost;
                    metoda = "POST";
                    break;
                case 15:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/obrisiOcenu/" + 
                            IDVid + '/' + IDKor;
                    metoda = "DELETE";
                    break;
                case 16:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/obrisiVideo/" +
                            IDVid + '/' + IDKor;
                    metoda = "DELETE";
                    break;
                case 17:
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/dohvatiMesta/";
                    metoda = "GET";
                    break;
                case 18:
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem1/dohvatiKorisnike/";
                    metoda = "GET";
                    break;
                case 19:
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/dohvatiKategorije/";
                    metoda = "GET";
                    break;
                case 20:
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/dohvatiSnimke/";
                    metoda = "GET";
                    break;
                case 21:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem2/dohvatiKategorijeZaVideo/" + IDVid;
                    metoda = "GET";
                    break;
                case 22:
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/dohvatiPakete/";
                    metoda = "GET";
                    break;
                case 23:
                    System.out.println("Unesite ID korisnika:");
                    IDKor = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/dohvatiPretplateZaKorisnika/" + IDKor;
                    metoda = "GET";
                    break;
                case 24:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/dohvatiGledanjaZaVideo/" + IDVid;
                    metoda = "GET";
                    break;
                case 25:
                    System.out.println("Unesite ID video snimka:");
                    IDVid = Integer.parseInt(scanner.nextLine());
                    adresa = "http://localhost:8080/CentralniServer/resources/podsistem3/dohvatiOceneZaVideo/" + IDVid;
                    metoda = "GET";
                    break;
            }
            ispisiOdgovor(adresa, metoda);
        }
       
    }
    
    private static void ispisiOdgovor(String adresa, String metoda) {
         try {
            URL url = new URL(adresa);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(metoda);

             try (InputStream inputStream = connection.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }
                System.out.println(response.toString());
            } catch (IOException e) {
            }
            connection.disconnect();
            
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }
    
}
