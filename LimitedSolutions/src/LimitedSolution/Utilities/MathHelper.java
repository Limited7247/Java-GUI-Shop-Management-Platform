/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Utilities;

/**
 *
 * @author Limited
 */
public class MathHelper {

    /**
     * Ước chung lớn nhất của hai số x, y
     *
     * @param x
     * @param y
     * @return UCLN(x, y)
     */
    public static int UCLN(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);

        if ((x == 0) || (y == 0)) {
            return x + y;
        }

        if (x % y == 0) {
            return y;
        } else {
            return UCLN(y, x % y);
        }
    }

    /**
     * Bội chung nhỏ nhất của hai số x, y
     *
     * @param x
     * @param y
     * @return BCNN(x, y)
     */
    public static int BCNN(int x, int y) {
        return x / UCLN(x, y) * y;
    }

    /**
     * Tổng các chữ số của một số Nguyên
     *
     * @param x Một số Nguyên
     * @return Tổng các chữ số
     */
    public static int TongCacChuSo(int x) {
        x = Math.abs(x);
        int T = 0;

        do {
            T += x % 10;
            x /= 10;
        } while ((x % 10 != 0) || (x / 10 != 0));

        ///T += x % 10;
        return T;
    }

    /**
     * Kiểm tra Số X có là Số nguyên tố không
     *
     * @param X
     * @return True or False
     */
    public static boolean PrimeNumber(int X) {
        if (X < 2) {
            return false;
        }

        for (int i = 2; i < X; i++) {
            if (X % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Giá trị của Số nguyên tố thứ N
     *
     * @param N
     * @return Số nguyên tố thứ N
     */
    public static int PrimeNumberN(int N) {
        if (N <= 0) {
            return 0;
        }

        int index = PrimeNumberN(N - 1) + 1;

        while (!PrimeNumber(index)) {
            index++;
        }

        return index;
    }

    public static boolean PerfectNumber(int X) {
        if (X < 6) {
            return false;
        }

        int T = 0;
        for (int i = 1; i < X; i++) {
            if (X % i == 0) {
                T += i;
            }
            if (T > X) {
                return false;
            }
        }

        if (T == X) {
            return true;
        } else {
            return false;
        }
    }

    public static int Fibonacci(int n) {
        if (n < 0) {
            return 0;
        }

        if ((n == 0) || (n == 1)) {
            return 1;
        } else {
            return Fibonacci(n - 1) + Fibonacci(n - 2);
        }
    }

    public static String Factorization(int x) {
        if (x <= 1) {
            return "Không có";
        }

        if (PrimeNumber(x)) {
            return Integer.toString(x);
        } else {
            String factors = "";

            for (int i = x / 2; i >= 2; i--) {
                while (PrimeNumber(i) && (x % i == 0)) {
                    factors = Integer.toString(i) + "*" + factors;
                    x /= i;
                }

            }

            factors = factors.substring(0, factors.length() - 1);

            return factors;
        }
    }
}
