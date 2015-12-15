# hadoop-test
Repository ini dapat digunakan untuk melakukan perhitungan terhadap file dblp-short.xml. <br>

## Cara menjalankan
Untuk menjalankannya, cukup download releasenya, extract, dan jalankan 
```
./runall.sh
```
Daftar author dari yang publikasinya paling banyak akan muncul.<br>
Berikut contoh hasil eksekusi untuk 2 file dblp-short.xml.
```
36      Christoph Meinel
16      Harald Sack
16      E. F. Codd
14      Klaus Jansen
14      Anna Slobodov&aacute;
14      Rainer Tichatschke
12      Alexander Kaplan
10      Nguyen V. Thoai
10      Dieter Baum
8       Ekkehard W. Sachs
8       Helmut Seidl
8       Lothar Breuer
8       Reiner Horst
6       Carsten Damm
6       Stasys Jukna
6       Friedemann Leibfritz
6       Michael Ley
6       Christian Stangier
6       Peter Gritzmann
4       Vladimir V. Kalashnikov
4       Martin Mundhenk
4       Elena Dubrova
4       Martin Gugat
4       Jochen Bern
4       Volker Schillings
4       Oliver Stein
4       Markus Casper
4       Hubertus Th. Jongen
4       Christoph W. Ke&szlig;ler
4       Ulrich Hertrampf
4       Rita Ley
4       Arno Wagner
4       Gerhard J. Woeginger
4       Manfred Laumen
2       Werner John
2       Victor Klee
2       Tim Voetmann
2       Thorsten Theobald
2       Thomas Schwentick
2       Tankred Rautert
2       Stefano Quer
2       Siegfried Graf
2       Richard R&ouml;dler
2       Ralf Merz
2       R. J. Gardner
2       Petra Scheffler
2       Peter Willems
2       Peter Sturm
2       Peter Dierolf
2       Patrick Justen
2       Patrick A. V. Hall
2       Oliver Gutjahr
2       Oliver Gronz
2       Norbert Th. M&uuml;ller
2       N. Lehdili
2       Mikail Gevantmakher
2       Martin Luckow
2       Markus Wiegelmann
2       Markus Tresch
2       Markus R. Schmidt
2       M. Davidson
2       M. B&ouml;hm
2       Lars Abbe
2       L. D. Muu
2       Klaus W. Wagner
2       Klaus Lux
2       Judy Goldsmith
2       Jordan Gergov
2       John Lions
2       Johannes K. Lehnert
2       Joachim M&uuml;ller
2       Jens Hofmann
2       Jan-J. R&uuml;ckmann
2       J&uuml;rgen Huschens
2       Hugo Hellebrand
2       Heribert Vollmer
2       Harald Luschgy
2       Hans L. Bodlaender
2       Hannes Frey
2       Gianpiero Cabodi
2       Gayane Grigoryan
2       G&uuml;nther Heinemann
2       Florian Jarre
2       Flemming Nielson
2       Eric Allender
2       El-Sayed M. E. Mostafa
2       E. Levitin
2       E. Arian
2       Dominik Ley
2       Daniel G&ouml;rgen
2       D. Kleis
2       Craig Smith
2       Clemens Lautemann
2       Christopher Lusena
2       Christian Fecht
2       C. T. Kelley
2       C. Ould Ahmed Salem
2       C. J. Date
2       Bernd Sturmfels
2       Belinda B. King
2       Attahiru Sule Alfa
2       Andreas Rock
2       A. Battermann
```

## Cara menghitung waktu
Untuk menghitung waktu, jalankan 
```
time ./runall.sh
```
Contoh waktu eksekusi yang dihasilkan sebagai berikut
```
real    0m48.075s
user    0m21.419s
sys     0m1.210s
```
## Algoritma yang dipakai
```
Hal pertama yang dilakukan adalah melakukan parse terhadap nama Author.
Setelah nama author didapatkan, maka dibuatlah sebuah kelas mapper untuk membuat pasangan key value <nama-author,1>
Dibuatlah kelas reducer untuk menghitung jumlah nama-author yang sama. 
  Misal: <nama-author,[1,1,1,1,1]>  =>  <nama-author,5>
Setelah semua nama-author diketahui nilainya, kemudian dilakukan shorting 
  dari nama-author yang paling banyak muncul
```

## Masalah
Masalah yang muncul ketika membuat program ini adalah tidak dapat melakukan 2 job sekaligus, dan masih belum diketahui dimana permasalahannya. Oleh karena itu, dibuatlah 2 buah file hadoop yang masing - masing file menjalankan job yang berbeda.<br>
File pertama, AuthorCounter.java hanya berfungsi untuk melakukan counting pada file input.<br>
File kedua, Order.java hanya berfungsi untuk melakukan ordering dari author yang memiliki publikasi paling banyak.<br>
Hasil kompilasi kedua file tersebut dijalankan dengan script runall.sh

## Known bug
Entah kenapa ketika digunakan untuk melakukan parse dblp.xml, masih terjadi eror. 
Padahal seharusnya jika memang dblp-short.xml adalah potongan dari dblp.xml, maka seharusnya berjalan dengan baik.

#### Catatan
Hanya dapat dijalankan di lingkungan LABTEK V
