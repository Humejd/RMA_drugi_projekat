e-IPI

e-IPI je Android aplikacija razvijena u Android Studiju koristeći Java programski jezik. Aplikacija je namijenjena studentima IPI Akademije i omogućava pregled osnovnih studentskih informacija, vijesti, rasporeda nastave, ispitnih termina, zadataka i profila. Aplikacija koristi Firebase za autentifikaciju i korisničke podatke, dok se Room baza koristi za lokalno čuvanje ispitnih termina i statusa prijave ispita.
Funkcionalnosti

Aplikacija sadrži sljedeće funkcionalnosti:

• splash ekran pri pokretanju aplikacije

• registraciju korisnika putem Firebase Authentication

• prijavu korisnika putem email adrese i lozinke

• čuvanje korisničkih podataka u Firebase Realtime Database

• prikaz početne stranice sa podacima studenta

• prikaz najbližih nastavnih aktivnosti na početnoj stranici

• prikaz najnovijih vijesti na početnoj stranici

• pregled svih vijesti

• detaljni pregled vijesti

• pregled rasporeda nastave

• prikaz predavanja i vježbi po predmetima

• pregled ispitnih termina

• prijavu i poništavanje prijave ispita

• lokalno čuvanje prijavljenih ispita u Room bazi

• odvojene prijave ispita za svakog korisnika

• pregled zadataka

• detaljni pregled zadatka

• predaju fajla za projekat i seminarski rad

• poseban prikaz za kvizove bez predaje fajla

• profil korisnika

• statusne informacije studenta

• mogućnost slanja pitanja profesoru

• prikaz ocjena po predmetima

• prikaz prisustva na predavanjima i vježbama

• odjavu korisnika iz aplikacije

• donju navigaciju između glavnih odjeljaka aplikacije

Tehnologije

Projekat je razvijen koristeći:

• Java

• Android Studio

• Firebase Authentication

• Firebase Realtime Database

• Room lokalnu bazu podataka

• MVVM arhitekturu za ispitne termine

• LiveData

• ViewModel

• Repository sloj

• XML layout fajlove

• AppCompat

• Gradle

Struktura projekta

Glavni paketi projekta su:

• auth

• database

• home

• model

• nastava

• news

• profile

• splash

• tasks

• terms

auth

Paket auth sadrži aktivnosti koje su zadužene za prijavu i registraciju korisnika.

• LoginActivity.java

• RegisterActivity.java

LoginActivity.java omogućava korisniku prijavu putem email adrese i lozinke. RegisterActivity.java omogućava kreiranje novog korisničkog naloga i upis korisničkih podataka u Firebase Realtime Database.
database

Paket database sadrži Room bazu podataka.

• AppDatabase.java

AppDatabase.java predstavlja glavnu lokalnu bazu aplikacije. U njoj je registrovana tabela za ispitne termine.
home

Paket home sadrži početnu stranicu aplikacije.

• HomeActivity.java

HomeActivity.java prikazuje osnovne podatke o prijavljenom studentu, najbliže termine nastave i najnovije vijesti. Sa početne stranice korisnik može otvoriti druge glavne odjeljke aplikacije.
model

Paket model sadrži model klase koje se koriste za podatke aplikacije.

• UserProfile.java

UserProfile.java predstavlja korisnički profil i sadrži podatke kao što su ime i prezime, email, broj indeksa, studijski program i godina studija.
nastava

Paket nastava sadrži prikaz rasporeda nastave.

• NastavaActivity.java

NastavaActivity.java prikazuje predavanja i vježbe za predmete koji se nalaze u aplikaciji. Za svaki termin prikazuje se naziv predmeta, profesor, vrijeme i učionica.
news

Paket news sadrži prikaz vijesti.

• NewsActivity.java

• NewsDetailActivity.java

NewsActivity.java prikazuje listu vijesti i obavijesti. NewsDetailActivity.java prikazuje detaljan sadržaj odabrane vijesti.
profile

Paket profile sadrži profil korisnika i dodatne stranice koje se otvaraju iz profila.

• ProfileActivity.java

• StatusInfoActivity.java

• QuestionsActivity.java

• GradesActivity.java

• AttendanceActivity.java

ProfileActivity.java prikazuje glavni profil korisnika sa karticama za statusne informacije, pitanja, ocjene, prisustvo i odjavu. StatusInfoActivity.java prikazuje podatke o studentu. QuestionsActivity.java omogućava pisanje pitanja profesoru. GradesActivity.java prikazuje ocjene po predmetima. AttendanceActivity.java prikazuje prisustvo na predavanjima i vježbama.
splash

Paket splash sadrži početni ekran aplikacije.

• SplashActivity.java

SplashActivity.java se prikazuje pri pokretanju aplikacije. Nakon splash ekrana korisnik se usmjerava na login ili početnu stranicu, zavisno od toga da li je već prijavljen.
tasks

Paket tasks sadrži funkcionalnosti za studentske zadatke.

• TasksActivity.java

• TaskDetailActivity.java

• TaskItem.java

TasksActivity.java prikazuje listu studentskih zadataka. TaskDetailActivity.java prikazuje detalje odabranog zadatka i omogućava predaju fajla za projekat i seminarski rad. TaskItem.java predstavlja model jednog zadatka.
terms

Paket terms sadrži funkcionalnosti vezane za ispitne termine i Room bazu.

• TermsActivity.java

• ExamTermEntity.java

• ExamTermDao.java

• ExamTermRepository.java

• ExamTermViewModel.java

TermsActivity.java prikazuje ispitne termine i omogućava prijavu ispita. ExamTermEntity.java predstavlja tabelu u Room bazi. ExamTermDao.java sadrži upite prema bazi. ExamTermRepository.java upravlja podacima iz Room baze. ExamTermViewModel.java povezuje podatke iz baze sa korisničkim interfejsom.
Firebase

Aplikacija koristi Firebase Authentication za registraciju i prijavu korisnika. Nakon uspješne registracije, korisnički podaci se čuvaju u Firebase Realtime Database.

Podaci koji se čuvaju za korisnika su:

• ime i prezime

• email adresa

• broj indeksa

• studijski program

• godina studija

Korisnički podaci se u Firebase Realtime Database čuvaju pod čvorom users, gdje svaki korisnik ima svoj jedinstveni uid.

Room baza

Aplikacija koristi Room lokalnu bazu podataka za čuvanje ispitnih termina i statusa prijave ispita.

Naziv baze je:

• eipi_database

Glavna tabela je:

• exam_terms

Tabela exam_terms sadrži sljedeće podatke:

• id

• userId

• subjectName

• dateTime

• registered

Polje userId omogućava da svaki korisnik ima svoje odvojene prijave ispita. Na taj način se prijavljeni ispiti jednog korisnika ne prikazuju drugom korisniku nakon odjave i ponovne prijave.

Vrijednost registered označava status prijave ispita:

• 0 znači da ispit nije prijavljen

• 1 znači da je ispit prijavljen

Način rada aplikacije

Korisnik prvo pokreće aplikaciju i vidi splash ekran. Nakon toga se otvara login ekran ako korisnik nije prijavljen. 

Novi korisnik se može registrovati unosom svojih podataka. Nakon registracije ili prijave korisnik dolazi na početnu stranicu aplikacije.

Na početnoj stranici korisnik vidi svoje ime, broj indeksa, najbliže termine nastave i najnovije vijesti. 

Kroz donju navigaciju korisnik može otvoriti vijesti, ispitne termine, početnu stranicu, zadatke i profil.

U odjeljku Vijesti korisnik može pregledati obavijesti i otvoriti detalje svake vijesti. U odjeljku Termini korisnik može prijaviti ili poništiti prijavu ispita.

U odjeljku Zadaci korisnik može pregledati obaveze, otvoriti detalje zadatka i predati fajl za projekat ili seminarski rad. U odjeljku Profil korisnik može otvoriti statusne informacije, poslati pitanje profesoru, pregledati ocjene, pregledati prisustvo i odjaviti se iz aplikacije.

Ispitni termini

Aplikacija prikazuje ispitne termine za sljedeće predmete:

• Razvoj mobilnih aplikacija

• E-usluge

• Sigurnost elektronskog poslovanja

• Menadžment informatičkih projekata

• Poduzetništvo

Student može prijaviti ispit klikom na odgovarajući termin. Status prijave se čuva lokalno u Room bazi.

Zadaci

Aplikacija prikazuje zadatke iz različitih predmeta. Za projekte i seminarske radove omogućena je predaja fajla. Za kvizove nije omogućena predaja fajla, jer je predviđeno da se kvizovi rade direktno kroz aplikaciju.

Profil

Profil sadrži više kartica:

• Statusne informacije

• Pitanja

• Ocjene

• Prisustvo

• Odjavi se

Statusne informacije prikazuju podatke o studentu. Pitanja omogućavaju izbor profesora i unos pitanja. Ocjene prikazuju pet predmeta sa statusom da ocjena još nije unesena. 

Prisustvo prikazuje broj prisustava na predavanjima i vježbama za svaki predmet u okviru 15 sedmica nastave.

Pokretanje projekta

Za pokretanje projekta potrebno je:
1.	otvoriti projekat u Android Studiju 
2.	sačekati da se učitaju Gradle zavisnosti 
3.	provjeriti da se fajl google-services.json nalazi u app folderu 
4.	provjeriti da je u Firebase konzoli uključena Email/Password autentifikacija 
5.	pokrenuti aplikaciju na emulatoru ili fizičkom Android uređaju 
6.	registrovati korisnika ili se prijaviti postojećim nalogom
   
Cilj projekta

Cilj projekta je izrada Android aplikacije koja demonstrira rad sa korisničkom autentifikacijom, Firebase bazom, lokalnom Room bazom, XML korisničkim interfejsom, navigacijom između ekrana i osnovnim MVVM pristupom.

Aplikacija prikazuje praktičan primjer studentskog sistema u kojem korisnik može pregledati informacije važne za studij, pratiti obavijesti, prijavljivati ispite, pregledati zadatke i koristiti profilne funkcionalnosti.

Autor Humejd Oruč

