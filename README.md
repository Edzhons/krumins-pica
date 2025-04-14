# 🍕 Picērijas Pasūtījumu Sistēma

Šis ir vienkāršs Java projekts ar grafisko lietotāja saskarni (GUI), kas ļauj lietotājiem veidot, apskatīt un saņemt picas pasūtījumus, kā arī izvēlēties piegādi. Pasūtījumu dati tiek saglabāti un nolasīti no faila `pasutijumi.txt`. Projekts arī ietver iespēju izveidot bankas kontus, caur kuriem var iemaksāt un izņemt naudu, kā arī veikt pirkumus, piemēram, pasūtot picas. Šie bankas kontu objekti glabājas teksta failā `bankasKonti.txt`.

## 🔧 Funkcionalitāte

- ✅ Jauna pasūtījuma izveide ar izvēlēm (veids, izmērs, piedevas, mērces)
- 🔐 Paroles pievienošana katram pasūtījumam
- 📄 Esošo pasūtījumu saraksta apskate
- 📦 Pasūtījuma saņemšana ar paroles pārbaudi
- 🚚 Iespēja izvēlēties piegādi ar adreses un telefona numura ievadi
- 💰 Bankas kontu izveide, kas ļauj veikt iemaksas un izņemšanas
- 🍕 Bankas konti ļauj iegādāties picu no esošā atlikuma
- 💾 Pasūtījumu saglabāšana un nolasīšana no faila `pasutijumi.txt`
- 💳 Bankas kontu saglabāšana un ielāde no faila `bankasKonti.txt`

## 📂 Failu struktūra

- `App.java` – Galvenā programma
- `Pica.java` – Picas klase, lai veidotu jaunus pasūtījumus
- `Banka.java` – Bankas klase, lai veidotu jaunus bankas kontus
- `pasutijumi.txt` – Fails, kur tiek glabāti visi pasūtījumi
- `bankasKonti.txt` – Fails, kur tiek glabāti visi bankas konti un to dati (atlikums, konta nosaukums, utt.)

## 🛠️ Prasības

- Java 8+ (vai jebkura Java versija ar `javax.swing` atbalstu)
- Nav nepieciešamas ārējas bibliotēkas

## 🚀 Kā darbināt

1. Lejupielādē vai klonē repozitoriju:
    ```bash
    git clone https://github.com/Edzhons/krumins-pica.git
    ```
2. Atver projektu ar IDE (piemēram, IntelliJ, Eclipse vai VS Code ar Java atbalstu)
3. Kompilē un palaid `Main.java`

## 📌 Piezīmes

- Parole ir obligāta un nepieciešama, lai saņemtu pasūtījumu.
- Sistēma nepieļauj tukšus pasūtījumus vai pārāk īsas paroles.
- Bankas konti ļauj veikt iemaksas un izņemšanas, kā arī veikt pirkumus (picu iegāde no konta atlikuma).
- Bankas konti un pasūtījumi tiek saglabāti atsevišķos failos, kas tiek saglabāti un atjaunināti pēc katras izmaiņas.

## 📞 Saziņai

Ja ir jautājumi vai ieteikumi – droši raksti! ;]  
Autors: **Edzhons**

---
