Martyna Kania
Problem: rysowanie spirali.

Program ma trzy parametry aktywacji:
-algorytm: Jarvisa ('J'), Grahama ('G'), Naivny ('N')
-tryb uruchomienia: benchmark ('B') normalny ('N') lub z pliku ('F')
-liczba punktow spirali (w przypadku trybu B = iloœæ w pirwszym kroku benchmarku, dla N = liczba wylosowanych punktów)
LUB dla trybu F nazwa pliku z punktami spirali znajduj¹cego siê w folderze coordinatesFile.

Aby uruchomiæ projekt wystarczy u¿yæ polecenia mvn package
a nastêpnie przejsc do folderu target i podaæ argumenty programu zgodnie z powy¿szym opisem.
np. java -jar aal-1.0.jar G F test3.txt

Pliki testowe powinny siê znajdowaæ w folderze coordinatesFiles, który zaœ powinien znajdowaæ siê w tej samej lokalizacji co folder z programem aal_1.0.jar


Pliki:
-Main - g³ówny plik programu, wybiera algorytm, wczytuje punkty oraz mierzy benchmarki.
-PointSet - klasa przechowuj¹ca uk³ad punktów i wykonuj¹ca operacje na nich.
-JervisAlgorithm, Graham, NaiveAlgorithm - klasy odpowiadaj¹ce za utworzenie listy punktów w kolejnosci tworz¹cej spirale.
-View Controller - wyœwietla spirale na ekranie.

Wynik trybu N i F prezentowany jest na ekranie w formie punktów po³¹czonych lini¹ prost¹.
Tryb benchmarku zaœ wyœwietla w terminalu tabelke, zgod¹ z zaleceniami projektu.

Ograniczenia rozmiarowe:
Algorytm Grahama i Jarvisa pozwala w u³amku sekundy wygenerowaæ spiralê o wielkoœci kilkdziesiêciu tysiêcy punktów.
w przypadku algorytmu naiwnego program jest w stanie w kilka sekund wygenerowaæ spiralê wielkoœci 12-14 punktów.