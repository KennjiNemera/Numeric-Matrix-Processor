package numeric.matrix.processor;

import java.util.Scanner;
import java.text.DecimalFormat;

public class Matrix {
    
    private final int n, m;
    private double[][] elements;
    private DecimalFormat df = new DecimalFormat("0.00");
    private double determinant;
    
    // Matrix constructor: gets the amount of rows and columns and initializes the matrix
    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        // initialize all the elements with 0
        this.elements = new double[n][m];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                this.elements[i][j] = 0.0;
            }
        }
    }
    
    // Function that will fill in the array with input values
    public void setElements(){
        Scanner scan = new Scanner(System.in);
        double[][] matrix = new double[this.n][this.m];
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                matrix[i][j] = scan.nextDouble();
            } 
        }
        this.elements = matrix;        
    }
    
    // Function that creates a new matrix: Has to be static because no object has been created yet
    public static Matrix getNewMatrix() {
        // Get the size of the matrix & and create an object accordingly
        System.out.println("Enter the size's of the matrix: ");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        // Create object and call the setElements function
        Matrix tempMatrix = new Matrix(n, m);
        System.out.println("Enter matrix elements: ");
        tempMatrix.setElements();
        return tempMatrix;
    }
    
    // Print our matrix in rows and columns
    public String toString() {
        String matrixToString = "";
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++) {
                double tempElement = (double)(Math.round(elements[i][j] * 100) / 100);
                matrixToString += df.format(tempElement) + " ";
            }
            matrixToString += "\n";
        }
        return matrixToString;
    }
    
    // Add two matrices together
    public static Matrix addMatrices(Matrix a, Matrix b) {
        if (a.n != b.n || a.m != b.m) { // edge case
            System.out.println("Can't add these matrices sorry");
            return null;
        }
        double[][] sumArr = new double[a.n][a.m];
        for (int i = 0; i < a.n; i++){
            for (int j = 0; j < a.m; j++) {
                sumArr[i][j] = a.elements[i][j] + b.elements[i][j];
            }
        }
        Matrix sum = new Matrix(a.n, a.m);
        sum.elements = sumArr;
        return sum;
    }
    
    // Mutiply each element of a matrix with a constant
    public Matrix constantMultiplication(Matrix a, double num) {
        Matrix multMatrix = new Matrix(a.n, a.m);
        for (int i = 0; i < a.n; i++){
            for (int j = 0; j < a.m; j++) {
                multMatrix.elements[i][j] = a.elements[i][j] * num;
            }
        }
        return multMatrix;
    }
    
    // Multiply two matrices together
    public static Matrix mutliplyMatrices(Matrix a, Matrix b) {
        if (a.n != a.m){
            return null;
        }
        Matrix productMatrix = new Matrix(a.n, a.m);
        double[][] product = new double[a.n][a.m];
        for (int i = 0; i < a.n; i++){
            for (int j = 0; j < a.m; j++) {
                product[i][j] = a.elements[i][j] * b.elements[j][i];
            }
        }
        productMatrix.elements = product;
        return productMatrix;
    }
    
    // Diagonally tranpose of a matrix
    public Matrix diagTranspose() {
        Matrix tempMatrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                tempMatrix.elements[i][j] = this.elements[j][i];
            }
        }
        return tempMatrix;
    }
    
    // Side diagonal tranpose of a matrix
    public Matrix sideTranspose() {
        Matrix tempMatrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = i; j < this.n; j++) {
                tempMatrix.elements[i][j] = this.elements[this.n - 1 - j][this.n - 1 - i];
                tempMatrix.elements[j][i] = this.elements[this.n - 1 - i][this.n - 1 - j];
            }
        }
        return tempMatrix;
    }
    
    // Horizontal transpose of a matrix
    public Matrix horTranspose() {
        Matrix tempMatrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                tempMatrix.elements[i][j] = this.elements[i][this.m-1-j];
            }
        }
        return tempMatrix;
    }
    
    // The determinant of a matrix
    public double calculateDeterminant() {
        if (this.n == 1) {
            return this.elements[0][0];
        }
        if (this.m == 2) {
            return elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0];
        }
        for (int k = 0; k < n; k++) {
            Matrix smallerMatrix = new Matrix(n-1, m-1);
            int x = 0;
            int y = 0;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (j != k) {
                        smallerMatrix.elements[x][y] = elements[i][j];
                        y++;
                    }
                }
                y = 0;
                x++;
            }
            determinant += elements[0][k] * smallerMatrix.calculateDeterminant() * Math.pow(-1, k+2); // Recursion to complete until matrix of size 2x2
        }
        return determinant;
    }
    
    // Vertical transpose of a matrix
    public Matrix vertTranspose() {
        Matrix tempMatrix = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                tempMatrix.elements[i][j] = this.elements[this.m-1-i][j];
            }
        }
        return tempMatrix;
    }
    
    // Get the user selection of the calculation that they would like to call
    public static void getUserSelection() {
        System.out.println("1. Print matrix");
        System.out.println("2. Add matrices");
        System.out.println("3. Multiply matrix w/constant");
        System.out.println("4. Multiply matrices");
        System.out.println("5. Transpose diagonal");
        System.out.println("6. Transpose side diagonal");
        System.out.println("7. Transpose horizontal");
        System.out.println("8. Transpose vertical");
        System.out.println("9. Calculate the determinant");
        System.out.println("0. Exit");
        
        Scanner scan = new Scanner(System.in);
        String selection = scan.next();
        switch(selection){
            case "1":
                Matrix matrix = Matrix.getNewMatrix();
                System.out.println("Here you go: ");
                System.out.println(matrix.toString());
                getUserSelection();
                break;
            case "2":
                // Get the first matrix
                Matrix matrixAdd1 = Matrix.getNewMatrix();
                // Get the second matrix
                Matrix matrixAdd2 = Matrix.getNewMatrix();
                System.out.println("\n--Here is the addition result--\n");
                System.out.println(Matrix.addMatrices(matrixAdd1, matrixAdd2));
                getUserSelection();
                break;   
            case "3":
                //Get the matrix
                Matrix multMatrix = Matrix.getNewMatrix();
                // Get the constant
                System.out.println("What is the scale factor?");
                double scale = new Scanner(System.in).nextDouble();
                System.out.println("\n--Here is the multiplication result--\n");
                System.out.println(multMatrix.constantMultiplication(multMatrix, scale));
                getUserSelection();
                break;
            case "4":
                // Get the first matrix
                Matrix multMat1 = Matrix.getNewMatrix();
                // Get the second matrix
                Matrix multMat2 = Matrix.getNewMatrix();
                System.out.println("\n--Here is the multiplication result--\n");
                System.out.println(Matrix.mutliplyMatrices(multMat1, multMat2));
                getUserSelection();
                break;
            case "5":
                // Get the matrix
                Matrix diagMatrix = Matrix.getNewMatrix();
                System.out.println("\n--Here is the diagonally transposed matrix--\n");
                System.out.println(diagMatrix.diagTranspose());
                getUserSelection();
                break;
            case "6":
                // Get the matrix
                Matrix sideDiagMatrix = Matrix.getNewMatrix();
                System.out.println("\n--Here is the diagonally transposed matrix--\n");
                System.out.println(sideDiagMatrix.sideTranspose());
                getUserSelection();
                break;
            case "7":
                Matrix horMatrix = Matrix.getNewMatrix();
                System.out.println("\n--Here is the horizontally transposed matrix--\n");
                System.out.println(horMatrix.horTranspose());
                getUserSelection();
                break;
            case "8":
                Matrix vertMatrix = Matrix.getNewMatrix();
                System.out.println("\n--Here is the vertically transposed matrix--\n");
                System.out.println(vertMatrix.vertTranspose());
                getUserSelection();
                break;
            case "9":
                Matrix matrixDet = Matrix.getNewMatrix();
                System.out.println("Determinant : " + matrixDet.calculateDeterminant());
                getUserSelection();
                break;
            case "0":
                System.exit(0);
        }
    }
}
