/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localgeoidcreator1;

import java.util.Scanner;

/**
 *
 * @author Omer Faruk
 */
public class modeldenHesap extends functions{
    static double jeoitYuksekligi (double[][] a,double[][] b,double y,double x){
        double n=0,yo,xo,mo,y1,x1;
        double [] katsayi = new double [10];
        yo= a[0][0];
        xo= a[1][0];
        mo= a[2][0];
        y1=(y-yo)/1000;
        x1=(x-xo)/1000;
        for(int i=0; i<b.length; i++){
            int q = (int) b[i][1];
            switch (q) {
                case 0:
                    katsayi[0]=b[i][0];
                    break;
                case 1:
                    katsayi[1]=b[i][0]*y1;
                    break;
                case 2:
                    katsayi[2]=b[i][0]*x1;
                    break;
                case 3:
                    katsayi[3]=b[i][0]*Math.pow(y1,2);
                    break;
                case 4:
                    katsayi[4]=b[i][0]*x1*y1;
                    break;
                case 5:
                    katsayi[5]=b[i][0]*Math.pow(x1,2);
                    break;
                case 6:
                    katsayi[6]=b[i][0]*Math.pow(y1,3);
                    break;
                case 7:
                    katsayi[7]=b[i][0]*Math.pow(y1,2)*x1;
                    break;
                case 8:
                    katsayi[8]=b[i][0]*Math.pow(x1,2)*y1;
                    break;
                case 9:
                    katsayi[9]=b[i][0]*Math.pow(x1,3);
                    break;
                default:
                    break;
            }
        }
        for(int i=0; i<10; i++){
            System.out.println("\n"+katsayi[i]);
            n=n+katsayi[i];
        }

        return n;
    }
    
    public static void modeldenHesap () throws Exception {
        Scanner s =new Scanner(System.in);
        System.out.println("Kullanmak istediğiniz proje ismini yazın.");
        String projeAdi = s.nextLine();
        String dosyaYolu = "C:\\Users\\Omer Faruk\\Documents\\NetBeansProjects\\LocalGeoidCreator1\\"+projeAdi;
        String [][] strinputA = readCSV(dosyaYolu+"_A.csv");
        String [][] strinputB = readCSV(dosyaYolu+"_B.csv");
        double [][] inputA;
        double [][] inputB;
        double y,x;
        inputA = strToDouble(strinputA);
        inputB = strToDouble(strinputB);
               
        System.out.println("İNPUT  A");
        printMatrix(inputA);
        System.out.println("İNPUT  B");
        printMatrix(inputB);
        
        int exit=0;
        do{
            System.out.println("Y DEĞERİ =");
            y = s.nextDouble();
            System.out.println("X DEĞERİ =");
            x = s.nextDouble();
            double n = jeoitYuksekligi (inputA,inputB,y,x);
            System.out.println("JEOİT YÜKSEKLİĞİ ="+n);
            System.out.println("Devam etmek için '1' çıkmak için '0' yazın.");
            exit =  s.nextInt();
        }while(exit>0);

}
   
}
