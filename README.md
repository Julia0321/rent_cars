# Rent Cars

Aplikacja webowa do zarządzania wypożyczalnią samochodów, przygotowana w technologii Spring Framework. System umożliwia użytkownikom rejestrację, logowanie, wyszukiwanie dostępnych samochodów, składanie rezerwacji oraz obsługę płatności. Administratorzy mają dostęp do panelu zarządzania samochodami, użytkownikami i rezerwacjami.


## Opis projektu

**Rent Car** to aplikacja typu MVC służąca do obsługi wypożyczalni samochodów. Projekt pozwala na zarządzanie flotą pojazdów, użytkownikami oraz procesem rezerwacji.

Aplikacja posiada klasyczny interfejs webowy oparty o szablony Thymeleaf oraz dodatkowe endpointy REST umożliwiające pobieranie danych użytkowników w formacie JSON i XML.

## Funkcjonalności

### Użytkownik

- rejestracja konta,
- aktywacja konta za pomocą tokenu,
- logowanie i wylogowanie,
- ochrona formularza rejestracji przez Google reCAPTCHA,
- edycja profilu użytkownika,
- wyszukiwanie dostępnych samochodów w podanym zakresie dat,
- podgląd szczegółów samochodu,
- tworzenie rezerwacji,
- obsługa płatności za rezerwację,
- podgląd własnych rezerwacji,
- anulowanie rezerwacji.

### Administrator

- panel administratora,
- zarządzanie użytkownikami,
- aktywowanie i dezaktywowanie kont użytkowników,
- nadawanie i odbieranie roli administratora,
- zarządzanie samochodami,
- dodawanie nowych samochodów,
- usuwanie samochodów,
- zmiana dostępności samochodów,
- przeglądanie wszystkich rezerwacji,
- akceptowanie i odrzucanie rezerwacji.

### Dodatkowe funkcje

- wysyłanie wiadomości e-mail,
- generowanie dokumentów PDF,
- obsługa wielu języków,
- walidacja formularzy,
- automatyczne zadania harmonogramu dla statusów samochodów i rezerwacji,
- eksport danych przez REST API w formacie JSON oraz XML.

## Technologie

Projekt wykorzystuje:

- Java 23,
- Spring MVC 6,
- Spring Security 6,
- Spring Data JPA,
- Hibernate ORM,
- PostgreSQL,
- Thymeleaf,
- Thymeleaf Spring Security,
- Lombok,
- Jakarta Servlet API,
- Jakarta Mail,
- Jakarta Validation,
- Jackson JSON/XML,
- iText PDF,
- Google reCAPTCHA.

## Architektura i wzorce

Aplikacja została zbudowana w oparciu o architekturę warstwową oraz wzorzec MVC.

### MVC

Projekt wykorzystuje wzorzec **Model-View-Controller**:

- **Model** — encje domenowe, DTO oraz logika biznesowa,
- **View** — widoki HTML przygotowane w Thymeleaf,
- **Controller** — kontrolery obsługujące żądania użytkownika i zwracające odpowiednie widoki lub dane.

### Architektura warstwowa

Kod aplikacji został podzielony na warstwy odpowiedzialne za konkretne zadania:

- `controller` — obsługa żądań HTTP,
- `service` — logika biznesowa,
- `repository` — dostęp do danych,
- `domain` — encje domenowe,
- `dto` — obiekty transferu danych,
- `mapper` — mapowanie pomiędzy encjami i DTO,
- `validator` — walidacja danych,
- `configuration` — konfiguracja aplikacji.

### Dependency Injection

Aplikacja wykorzystuje mechanizm **Dependency Injection** dostarczany przez Spring Framework. Dzięki temu zależności pomiędzy komponentami są zarządzane przez kontener Springa.

## Narzędzia

Do budowania, uruchamiania i zarządzania projektem wykorzystywane są:

- Maven — budowanie projektu i zarządzanie zależnościami,
- Apache Tomcat 10+ — serwer aplikacyjny do uruchomienia pliku WAR,
- PostgreSQL — system zarządzania relacyjną bazą danych.

## Wymagania

Do uruchomienia aplikacji wymagane są:

- JDK 23,
- Maven,
- PostgreSQL,
- serwer aplikacyjny obsługujący pliki WAR i Jakarta Servlet 6, np. Apache Tomcat 10+,
- konto e-mail do wysyłki wiadomości aktywacyjnych,
- klucze Google reCAPTCHA.


Przed uruchomieniem należy utworzyć bazę danych PostgreSQL. Następnie trzeba ustawić zmienne środowiskowe wymagane przez aplikację.
