/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Classes;

import static LimitedSolution.Utilities.MathHelper.PrimeNumber;
import java.util.Scanner;

/**
 *
 * @author Limited
 */
public class Matrix {
    private int n;
    private int m;
    private int[][] Cells;  /// Giới hạn giá trị [1..99]

    public Matrix() {
        this.n = 0;
        this.m = 0;
        this.Cells = new int[0][0];
    }
    
    public Matrix(int n, int m) {
        if (n <= 0) n = 0;
        if (m <= 0) m = 0;
        
        this.n = n;
        this.m = m;
        this.Cells = new int[n][m];
        
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) 
                this.Cells[i][j] = 0;
    }
    
    public Matrix(int n, int m, int[][] Cells) {
        if (n <= 0) n = 0;
        if (m <= 0) m = 0;
        
        this.n = n;
        this.m = m;
        this.Cells = new int[n][m];
        
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) 
                this.Cells[i][j] = Cells[i][j];
    }
    
    public Matrix(Matrix matrix) {
        this.n = matrix.n;
        this.m = matrix.m;
        
        this.Cells = new int[matrix.n][matrix.m];

        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) 
                this.Cells[i][j] = matrix.Cells[i][j];
    }
    
    public void setN(int n) {
        if (n <= 0) n = 0;
        this.n = n;
    }

    public void setM(int m) {
        if (m <= 0) m = 0;
        this.m = m;
    }

    public void setCell(int x, int y, int k) {
        if ((0 <= x) && (x < this.n) && (0 <= y) && (y < this.m)) {
            if (k <= 0) k = 1;
            if (k >= 100) k = 99;
        
            this.Cells[x][y] = k;
        }
        else
            System.out.println("x, y không hợp lệ!");
    }
    
    public void Read()
    {
        Scanner jin = new Scanner(System.in);
        
        System.out.print("Nhập vào, n = "); this.setN(jin.nextInt());
        System.out.print("          m = "); this.setM(jin.nextInt());
        
        this.Cells = new int[n][m];
        
        System.out.println("");
        System.out.println("Nhập vào Cells: ");
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                System.out.print("A[" + i + "][" + j + "] = " ); 
                this.setCell(i, j, jin.nextInt());
            }
            
        }
        System.out.println("");
    }
    
    public void Show()
    {
        System.out.println("Ma trận: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) 
                System.out.print(this.Cells[i][j] + " ");
            System.out.println("");
            
        }
        System.out.println("");
    }
    
    /**
     * Inner class Max
     */
    public class Max
    {
        public int Max = Integer.MIN_VALUE;
        public int iMax = -1;
        public int jMax = -1;
        
        public Max() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (Cells[i][j] > Max) {
                        Max = Cells[i][j];
                        iMax = i;
                        jMax = j;
                    }   
                }
            }
        }
        
        public void Show()
        {
            System.out.println("Max cell: " + this.Max + " at " + (this.iMax+1) + ", " + (this.jMax+1) + ".");
            System.out.println("");
        }
 
    }
    
    public Matrix MatrixofPrimeNumber()
    {
        Matrix MaX = new Matrix(this);
        
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) 
                if (!PrimeNumber(MaX.Cells[i][j])) 
                    MaX.Cells[i][j] = 0;
        }
        
        return MaX;
    }
    
    public Matrix MatrixOrderByColumns() {
        Matrix MaX = new Matrix(this);
        
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n-1; i++) {
                for (int k = i+1; k < n; k++) {
                    if (MaX.Cells[i][j] > MaX.Cells[k][j])
                    {
                        int temp = MaX.Cells[i][j];
                        MaX.Cells[i][j] = MaX.Cells[k][j];
                        MaX.Cells[k][j] = temp;
                    }
                    
                }
                
            }
            
        }
        
        return MaX;
    }
    
}
