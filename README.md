# Tucil1_13523146

Program untuk menyelesaikan IQ Puzzler Pro dengan Algoritma Brute Force. Program menerima input txt file dengan format:
```
N M P
S
puzzle_1_shape
puzzle_2_shape
…
puzzle_P_shape
```
N & M adalah dimensi board. P adalah banyak blok puzzle. S adalah jenis kasus (DEFAULT/CUSTOM). Puzzle Shape dilambangkan oleh konfigurasi Character berupa huruf A-Z dalam kapital.

Contoh Default:
```
5 5 7
DEFAULT
A
AA
B
BB
C
CC
D
DD
EE
EE
E
FF
FF
F
GGG
```
Contoh Custom:
```
5 7 5
CUSTOM
...X...
.XXXXX.
XXXXXXX
.XXXXX.
...X...
A
AAA
BB
BBB
CCCC
C
D
EEE
E
```
Kasus PYRAMID tidak diimplementasikan.

## PERSYARATAN

- **Java JDK** versi 8 atau yang lebih baru
- Konfigurasi PATH agar dapat menjalankan perintah `javac` dan `java`
- File konfigurasi untuk input yang sesuai dengan format dan berada pada root folder repositori.

## INSTALASI & MENGGUNAKAN PROGRAM

1. Clone repository ini atau download zip
2. Buka terminal
3. Navigasi ke folder repositori ini
4. Jalankan perintah berikut:
    - Windows:
    
    Double click run.bat atau jalankan melalui terminal
    - Linux:
    ```bash
    chmod +x run.sh
    ./run.sh
    ```

## CATATAN TAMBAHAN
- Ketika input file untuk konfigurasi, masukkan nama file lengkap dengan txt (contoh: tes.txt)
- Ketika menyimpan output program, cukup masukkan nama file tanpa ekstensi (contoh: tes)

## Author

<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/V-Kleio"><img style="border-radius: 20%" src="https://avatars.githubusercontent.com/u/101655336?v=4" width="100px;" alt="V-Kleio"/><br /><sub><b>Rafael Marchel Darma Wijaya / 13523146</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
