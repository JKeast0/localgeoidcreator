/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localgeoidcreator1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Stream;
import static localgeoidcreator1.modeldenHesap.jeoitYuksekligi;


/**
 *
 * @author Omer Faruk
 */
public class functions {
    
static double [][] inputMatrix(int nokta_sayisi){
    Scanner s =new Scanner(System.in);  
            System.out.println("Her bir nokta için sırayla X,Y,h,H değerlerini giriniz...");
            double [][] mat_input=new double[nokta_sayisi][4];
            for(int i=0; i<nokta_sayisi; i++){
                for(int j=0; j<4; j++){
                 mat_input[i][j] = s.nextDouble();
                }
            }
            return mat_input;
}
static double [][] strToDouble(String [][] strinputA){
    double [][] outMat = new double [strinputA.length][strinputA[0].length];     //STRİNGİN BOYUTLARINDA DOUBLE MATRİS TANIMLAMA
    for(int i=0; i<strinputA.length; i++){
        for(int j=0; j<strinputA[0].length; j++){                                 //STRİNG DİZİYİ DOUBLE DİZİYE ÇEVİRME
            outMat[i][j]=Double.parseDouble(strinputA[i][j]);
        }
    }
    return outMat;
}

static void printSMatrix(String[][] mat) {
  System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
       int rows = mat.length;
       int columns = mat[0].length;
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               System.out.print("   " + mat[i][j]);
           }
           System.out.println();
       }
 //  System.out.println();
  }

static void writeCSV(double [][] input, String name) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File(name+".csv"));
        StringBuilder sb = new StringBuilder();
             
        for(int i=0; i<input.length; i++){
            for(int j=0; j<input[0].length; j++){                              
                sb.append(input[i][j]);
                sb.append(';');
            }
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
    }


static String[][] readCSV(String path) throws FileNotFoundException, IOException {
    try (FileReader fr = new FileReader(path);
    BufferedReader br = new BufferedReader(fr)) {
        Collection<String[]> lines = new ArrayList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lines.add(line.split(";"));
        }
        return lines.toArray(new String[lines.size()][]);
    }
}

static double [][] Normalization(double[][] input , int derece){
        int row = input.length;
        int col=1;
    switch (derece) {
        case 1:
            col=3;
            break;
        case 2:
            col=6;
            break;
        case 3:
            col=10;
            break;
        default:
            break;
    }
        double Yort;
        double Xort;
        double Xtop=0;
        double Ytop=0;
        double [][] mat_a = new double[row][col];
        for(int i=0 ; i<row ; i++){ //yler toplamı
            Ytop=Ytop + input[i][0];
        }
        Yort=Ytop/row;
        for(int i=0 ; i<row ; i++){ //xler toplamı
            Xtop=Xtop + input[i][1];
        }
        Xort=Xtop/row;
        System.out.println("Y ortalama = " + Yort);
        System.out.println("X ortalama = " + Xort);

        for(int i=0; i<col; i++){
            for(int j=0; j<row; j++){
                switch (i) {
                    case 0:
                        mat_a[j][0]=1;
                        break;
                    case 1:
                        mat_a[j][i]=(input[j][0]-Yort)/1000;        //y
                        break;
                    case 2:
                        mat_a[j][i]=(input[j][1]-Xort)/1000;        //x
                        break;
                    case 3:
                        mat_a[j][i]=Math.pow(((input[j][0]-Yort)/1000), 2);     //y2
                        break;
                    case 4:
                        mat_a[j][i]=((input[j][0]-Yort)/1000)*((input[j][1]-Xort)/1000); //xy
                        break;
                    case 5:
                        mat_a[j][i]=Math.pow(((input[j][1]-Xort)/1000), 2);     //x2
                        break;
                    case 6:
                        mat_a[j][i]=Math.pow(((input[j][0]-Yort)/1000), 3);     //y3
                        break;
                    case 7:
                        mat_a[j][i]=(Math.pow(((input[j][0]-Yort)/1000), 2))*((input[j][1]-Xort)/1000);     //y2x
                        break;
                    case 8:
                        mat_a[j][i]=((input[j][0]-Yort)/1000)*(Math.pow(((input[j][1]-Xort)/1000), 2));     //yx2
                        break;
                    case 9:
                        mat_a[j][i]=Math.pow(((input[j][1]-Xort)/1000),3);      //x3
                        break;
                    default:
                        break;
                }
                
            }
        }
        return mat_a;
    }

static double [] [] createMatL(double [][] mat){
        int row = mat.length;
        double [][] mat_l = new double [row][1];
        for(int i=0; i<row ; i++){
            mat_l[i][0]=mat[i][2]-mat[i][3];
        }
        return mat_l;
}
    
static double[][] Add(double[][] a, double[][] b) {
       int rows = a.length;
       int columns = a[0].length;
       double [][] result = new double[rows][columns];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               result[i][j] = a[i][j] + b[i][j];
           }
       }
       return result;
   }

static double [][] Subtract(double[][] a, double[][] b) {
       int rows = a.length;
       int columns = a[0].length;
       double[][] result = new double[rows][columns];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               result[i][j] = a[i][j] - b[i][j];
           }
       }
       return result;
   }
           
static void printMatrix(double[][] mat) {
  System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
       int rows = mat.length;
       int columns = mat[0].length;
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               System.out.printf("%8.4f " , mat[i][j]);
           }
           System.out.println();
       }
   System.out.println();
  }
  
     
static double[][] multMatrix(double a[][], double b[][]){//a[m][n], b[n][p]
   if(a.length == 0) return new double[0][0];
   if(a[0].length != b.length) return null; //invalid dims

   int n = a[0].length;
   int m = a.length;
   int p = b[0].length;

   double ans[][] = new double[m][p];

   for(int i = 0;i < m;i++){
      for(int j = 0;j < p;j++){
         ans[i][j]=0;
         for(int k = 0;k < n;k++){
            ans[i][j] += a[i][k] * b[k][j];
         }
      }
   }
   return ans;
   }
  
static double[][] transposeMatrix(double [][] m){
        double[][] ans = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                ans[j][i] = m[i][j];
        return ans;
    }
   
static double[][] Invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
 // Transform the matrix into an upper triangle
        gaussian(a, index);
 
 // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
 // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
 // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
 // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
 // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
 // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
 // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

static double [][] removeCol(double [][] array, int colRemove)
    {
        int row = array.length;
        int col = array[0].length;

        if(col==2){
            System.out.println("2.kolon silinemez!!!");           
            return array;
        }
        
        double [][] newArray = new double[row][col-1];

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < colRemove; j++)
            {              
                newArray[i][j] = array[i][j];                
            }

            for(int j = colRemove; j < col-1; j++)
            {                          
               newArray[i][j] = array[i][j+1];
            }
        }

        return newArray;
    }

static double tTablo (int f){
    double t_tablo;
    double [][] tablo;
    tablo=new double [][] {
        {1, 3.078, 6.314},
        {2, 1.886, 2.920},
        {3, 1.638, 2.353},
        {4, 1.533, 2.132},
        {5, 1.476, 2.015},
        {6, 1.440, 1.943},
        {7, 1.415, 1.895},
        {8, 1.397, 1.860},
        {9, 1.383, 1.833},
        {10, 1.372, 1.812},
        {11, 1.363, 1.796},
        {12, 1.356, 1.782},
        {13, 1.350, 1.771},
        {14, 1.345, 1.761},
        {15, 1.341, 1.753},
        {16, 1.337, 1.746},
        {17, 1.333, 1.740},
        {18, 1.330, 1.734},
        {19, 1.328, 1.729},
        {20, 1.325, 1.725},
        {21, 1.323, 1.721},
        {22, 1.321, 1.717},
        {23, 1.319, 1.714},
        {24, 1.318, 1.711},
        {25, 1.316, 1.708},
        {26, 1.315, 1.706},
        {27, 1.314, 1.703},
        {28, 1.313, 1.701},
        {29, 1.311, 1.699},
        {30, 1.310, 1.697},
   };
    t_tablo=tablo[f-1][2];
    return t_tablo;
}

static int linearSearch(double [] liste, double arananSayi) {
 
        for (int i = 0; i <liste.length; i++) { 
            if(liste[i] == arananSayi){
                return i;
            }
        }
        return -1;
    }

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

public static void yuzeydenHesap () throws Exception {
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
            System.out.print("Y DEĞERİ =");
            y = s.nextDouble();
            System.out.print("X DEĞERİ =");
            x = s.nextDouble();
            double n = jeoitYuksekligi (inputA,inputB,y,x);
            System.out.println("JEOİT YÜKSEKLİĞİ ="+n);
            System.out.println("Devam etmek için '1' çıkmak için '0' yazın.");
            exit =  s.nextInt();
        }while(exit>0);
}

}
