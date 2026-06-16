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

## Technologie & Narzędzia

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
- Maven 
- Apache Tomcat 10+ 
- PostgreSQL

## Przed uruchomieniem
Przed uruchomieniem należy utworzyć bazę danych PostgreSQL. Następnie trzeba ustawić zmienne środowiskowe wymagane przez aplikację.
