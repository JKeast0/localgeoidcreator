/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localgeoidcreator1;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Omer Faruk
 */
public class Main extends yuzeyHesapla {
    public static void main(String[] args) throws Exception {
        double [][] mat_input;
        
        System.out.println("atiz yerel jeoit hesaplayıcıya hoşgeldiniz...");
        System.out.println("© Created by atiz");
        System.out.println("® All rights reserved.");
        
        Scanner s=new Scanner(System.in);
        System.out.println("Lütfen projeye bir isim verin..");
        String projeAdi = s.nextLine();
        System.out.println("Dosya yolunu giriniz:");
        String dosyaYolu = s.nextLine();c:
        //    String dosyaYolu = "C:/nokta15.csv";
        System.out.println("Dosya Yolu= "+dosyaYolu);       ///DOSYADAN ALIRKEN İF İLE 4 SÜTUN OLDUĞUNU KONTROL ETTİR!!
            String [][] input = readCSV(dosyaYolu);     //.CSV DEN 2D STRİNGE ALMA      \\-->>NOKTALARI PROJEYE ALMA<<--//
            mat_input = strToDouble(input);             // 2d string diziden 2d double dizi çevirme

        int boyut = mat_input.length;
        double [] ortHata = new double [3];
        ortHata[0] = 999;ortHata[1] = 999;ortHata[2] = 999;     //ilk değer sıfır olunca sıralarken sıkıntı oluyor
        
            if(boyut>10){
                System.out.println("Nokta sayısı "+boyut+" olduğundan 1,2 ve 3.dereceden polinomlarla çözülecek.");
                ortHata[0] = yuzeyHesapla(dosyaYolu,projeAdi,1,false);
                ortHata[1] = yuzeyHesapla(dosyaYolu,projeAdi,2,false);
                ortHata[2] = yuzeyHesapla(dosyaYolu,projeAdi,3,false);
            }
            else if (boyut>6){
                System.out.println("Nokta sayısı "+boyut+" olduğundan 1 ve 2.dereceden polinomlarla çözülecek.");            
                ortHata[0] = yuzeyHesapla(dosyaYolu,projeAdi,1,false);
                ortHata[1] = yuzeyHesapla(dosyaYolu,projeAdi,2,false);            
            }
            else if (boyut>3){
                System.out.println("Nokta sayısı "+boyut+" olduğundan yalnız 1.dereceden polinomla çözülecek.");            
                ortHata[0] = yuzeyHesapla(dosyaYolu,projeAdi,1,false);
            }
        System.out.println("ORTALAMA HATALAR");
        System.out.printf(Arrays.toString(ortHata));
       
        double [] ortHata2 = ortHata.clone();
        int a;
        {
            Arrays.sort(ortHata2);
            a = linearSearch(ortHata,ortHata2[0]);
            a++;
        }
        
        System.out.println("\nEn uygun model " + a+ ".dereceden polinomla oluşturuldu.");
        yuzeyHesapla(dosyaYolu,projeAdi,a,true);
        System.out.println(a+".dereceden polinom modeli dosyaya yazıldı.");
        System.out.println("İşlem başarıyla tamamlandı...");
        
        System.out.println("Modelden Jeoit Yüksekliği hesaplamak için '1' yazın.");
        int c = s.nextInt();
        if(c!=0){
            yuzeydenHesap();
        }
        
    }
}