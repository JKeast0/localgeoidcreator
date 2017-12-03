
package localgeoidcreator1;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Omer Faruk
 */
public class yuzeyHesapla extends functions {
    public static double yuzeyHesapla(String inputPath,String dosyaAdi,int drc,boolean fileOutput) throws Exception{
        // TODO code application logic here1
        double [][] mat_a;
        double [][] orj_mat_a;
        double [][] mat_l;
        double [][] transpose_a;
        double [][] mat_N;
        double [][] mat_n;
        double [][] mat_N1;
        double [][] mat_x;
        double [][] mat_sonucX;     //1.sütun x matrisi 2.sütun kaçıncı katsyı oldugu
        double [][] mat_v;
        double [] mat_test;
        double mo;
        double t_tablo;
        int sayac;
        double [][] mat_input;
        int f; //serbestlik derecesi
        int derece=drc;
        String dosya=inputPath;
        int [] silinenParametre;
        int silinen=0;
        //        DOSYA YA YAZMA  log
   /*     if(fileOutput==true){
            SimpleDateFormat bicim2=new SimpleDateFormat("dd-M-yyyy hh-mm-ss");
            Date tarihSaat=new Date();
            System.out.println("Dosya ismi : "+bicim2.format(tarihSaat)); // 24-8-2014 02:17:02
            String str ="Log "+bicim2.format(tarihSaat)+".txt";
            FileOutputStream d = new FileOutputStream(str);
            System.setOut(new PrintStream(d));   
        }*/
        System.out.println("~~~~ "+drc+". DERECE POLİNOMLA ÇÖZÜM ~~~~");

        String [][] input = readCSV(dosya);     //.CSV DEN 2D STRİNGE ALMA
        mat_input = new double [input.length][input[0].length];     //STRİNGİN BOYUTLARINDA DOUBLE MATRİS TANIMLAMA
        for(int i=0; i<input.length; i++){
            for(int j=0; j<4; j++){                                 //STRİNG DİZİYİ DOUBLE DİZİYE ÇEVİRME
                mat_input[i][j]=Double.parseDouble(input[i][j]);
            }
        }
    
        double Yort;
        double Xort;
        double Xtop=0;                                 
        double Ytop=0;
        for(int i=0 ; i<mat_input.length ; i++){ //yler toplamı
            Ytop=Ytop + mat_input[i][0];
        }
        Yort=Ytop/mat_input.length;
        
        for(int i=0 ; i<mat_input.length ; i++){ //xler toplamı
            Xtop=Xtop + mat_input[i][1];
        }
        Xort=Xtop/mat_input.length;

        


            mat_a = Normalization(mat_input,derece);
            
            orj_mat_a = mat_a.clone();
            
            System.out.println("Normalizasyon yapılmış A matrisi :");
            printMatrix(mat_a);
            

            
            mat_l =createMatL(mat_input);
            System.out.println("L matrisi :");
            printMatrix(mat_l);            
            
     do
     {
            f=mat_a.length-mat_a[0].length;
            System.out.println("satır sayisi= "+mat_a.length);
            System.out.println("sütun sayisi= "+mat_a[0].length);
            System.out.println("serbestlik derecesi = "+ f);
          
            System.out.print("A ");
            printMatrix(mat_a);
             
        transpose_a = transposeMatrix(mat_a);
            System.out.print("AT ");
            printMatrix(transpose_a);
        
        mat_N= multMatrix(transpose_a , mat_a);
            System.out.print("ATA=N ");
            printMatrix(mat_N);
        
            System.out.print("L ");
            printMatrix(mat_l);
            
        mat_n= multMatrix(transpose_a,mat_l);
            System.out.print("ATL=n ");
            printMatrix(mat_n);
            
        mat_N1= Invert(mat_N);
            System.out.print("N^-1 ");
            printMatrix(mat_N1);
            
        mat_x= multMatrix(mat_N1,mat_n);
            System.out.print("N^-1*n=X ");
            printMatrix(mat_x);
        mat_v= Subtract((multMatrix(mat_a,mat_x)),mat_l);
            System.out.print("Ax-L=v ");
            printMatrix(mat_v);
            
            mo =0;                              //m0 ın hesaplanması
        for(int i=0; i<mat_a.length; i++){      
            mo+=Math.pow(mat_v[i][0], 2);       //v'lerin kareleri toplamı alınıyor     
        }
        mo = Math.sqrt(mo/f);                   //v'lerin karelerinin karekökü m0'a atanıyor.
        System.out.println("m0= "+ mo);
        
        t_tablo=tTablo(f);                      //t tablo değeri f serbestil derecesine göre fonksiyonundan alınıyor
        System.out.println("t tablo degeri ="+ t_tablo);        
        
            mat_test =new double [mat_x.length];    //t test matrisi tanımlanıyor   
            for(int i=0; i<mat_x.length ; i++){
                mat_test[i] = (Math.abs(mat_x[i][0]))/(mo*Math.sqrt(mat_N1[i][i])); //Ttest = |x|/mx
            }
            
            double [] mat_test2 =new double [mat_x.length];                     //sıralama için t test kopyalanıyor(mat_test2)
            for(int i=0; i<mat_x.length ; i++){                                 // KOPYALAMAYI CLONE İLE DEĞİŞTİRECEM !!!!!!!!!!
                mat_test2[i] = (Math.abs(mat_x[i][0]))/(mo*Math.sqrt(mat_N1[i][i])); //Ttest = |x|/mx
            }
  
            System.out.println("MAT TEST\n");
            System.out.println(Arrays.toString(mat_test));
            System.out.println("SIRALANMIŞ  MAT TEST\n");
            Arrays.sort(mat_test2);             //t testin kopyası sıralanıyor
            System.out.println(Arrays.toString(mat_test2));
            double temp =  mat_test2[0];        //sıralanmış t testin ilk elemanı alınıyor(yani en küçük eleman)
            System.out.println("temp -----<> "+ temp +"\n");
            int enKucuk = linearSearch(mat_test,temp);      //en küçük eleman t test içinde lineersearch methoduyla aranıyor ve 
            
            System.out.println("MAT TESTİN EN KÜÇÜK DEĞERİ " +enKucuk+"\n");
            System.out.println(mat_test[enKucuk]+"\n");
                
            sayac=0;        //Anlamlı olmayan sayısını tutmak için tanımlanıyor
            for(int i=0; i<mat_test.length ;i++){       //T testin anlamlılık kontrolü burada yapılıyor
                    if(mat_test[i] >= t_tablo){
                       System.out.print("t test" + i + " =" + (mat_test[i]));
                       System.out.println(" --> OK");       //anlamlıysa OK değilse Anlamlı değil yazdırıyor

                    }
                    else if(mat_test[i]< t_tablo){
                       System.out.print("t test" + i + " =" + (mat_test[i]));
                       System.out.println(" --> Anlamlı Değil");
                       sayac++;         
                    }
                }
                if(sayac>=1){       //anlamlı olmayan eleman varsa en küçük olanın karşılık geldiği parametre modelden çıkarılıyor
                    mat_a = removeCol(mat_a,enKucuk);

                    silinen++;
                    
                }                        
     }while(sayac>=1);      //Anlamlı olmayan ifade olduğu sürece iterasyon devam ettiriliyor 

            System.out.println("\nModel başarıyla oluşturuldu..");
            printMatrix(mat_x);
            System.out.print("m0 =");
            System.out.printf("%8.4f" , mo*100);
            System.out.println(" cm\n");
            
            double [][] sonucModel=new double[][]{      //Yuzey bilgileri XORT,YORT; ORTALAMA HATA
                {Yort},
                {Xort},
                {mo}
           };
           System.out.println("SON mat a"); 
           printMatrix(mat_a);
           System.out.println("ORJİNAL mat a");
           printMatrix(orj_mat_a);
           
           double [] katsayiId = new double [orj_mat_a[0].length];
           
           for(int i=0; i<orj_mat_a[0].length; i++){
               katsayiId [i] = orj_mat_a[0][i];
           }
           System.out.println("KATSAYI ID");
           System.out.println(Arrays.toString(katsayiId));

           
           int [] polinomKatsayi=new int [mat_a[0].length];
           for (int i=0; i<mat_a[0].length; i++){
                polinomKatsayi[i]= linearSearch(katsayiId,mat_a[0][i]);
           }
           
           System.out.println("POLİNOM KATSAYI");
           System.out.println(Arrays.toString(polinomKatsayi));  
           
           mat_sonucX = new double [mat_x.length][2];
           for (int i=0; i<mat_x.length; i++){
               mat_sonucX[i][0]=mat_x[i][0];
               mat_sonucX[i][1]=polinomKatsayi[i];
           }
           System.out.println("SONUC X MATRİS");
           printMatrix(mat_sonucX);
           
           if(fileOutput==true){
                writeCSV(sonucModel,dosyaAdi+"_A"); //MODEL
                writeCSV(mat_sonucX,dosyaAdi+"_B");    //SONUC KATSAYILAR
                return 0;
           }
      
           
           return sonucModel [2][0];        // ORTALAMA HATAYI GERİ DÖNDÜRÜYOR
   
    }
  
}
