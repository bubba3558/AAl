Martyna Kania
Problem: rysowanie spirali.

Program ma trzy parametry aktywacji:
-algorytm: Jarvisa ('J'), Grahama ('G'), Naivny ('N')
-tryb uruchomienia: benchmark ('B') normalny ('N') lub z pliku ('F')
-liczba punktow spirali (w przypadku trybu B = ilo�� w pirwszym kroku benchmarku, dla N = liczba wylosowanych punkt�w)
LUB dla trybu F nazwa pliku z punktami spirali znajduj�cego si� w folderze coordinatesFile.

Aby uruchomi� projekt wystarczy u�y� polecenia mvn package
a nast�pnie przejsc do folderu target i poda� argumenty programu zgodnie z powy�szym opisem.
np. java -jar aal-1.0.jar G F test3.txt

Pliki testowe powinny si� znajdowa� w folderze coordinatesFiles, kt�ry za� powinien znajdowa� si� w tej samej lokalizacji co folder z programem aal_1.0.jar


Pliki:
-Main - g��wny plik programu, wybiera algorytm, wczytuje punkty oraz mierzy benchmarki.
-PointSet - klasa przechowuj�ca uk�ad punkt�w i wykonuj�ca operacje na nich.
-JervisAlgorithm, Graham, NaiveAlgorithm - klasy odpowiadaj�ce za utworzenie listy punkt�w w kolejnosci tworz�cej spirale.
-View Controller - wy�wietla spirale na ekranie.

Wynik trybu N i F prezentowany jest na ekranie w formie punkt�w po��czonych lini� prost�.
Tryb benchmarku za� wy�wietla w terminalu tabelke, zgod� z zaleceniami projektu.

Ograniczenia rozmiarowe:
Algorytm Grahama i Jarvisa pozwala w u�amku sekundy wygenerowa� spiral� o wielko�ci kilkdziesi�ciu tysi�cy punkt�w.
w przypadku algorytmu naiwnego program jest w stanie w kilka sekund wygenerowa� spiral� wielko�ci 12-14 punkt�w.