import java.util.Scanner;

public class Main {   // đổi "SapXepMang" thành "Main"
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[10];

        // Nhập mảng
        System.out.println("Nhap 10 so nguyen:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("Phan tu thu " + (i + 1) + ": ");
            arr[i] = sc.nextInt();
        }

        // Sắp xếp tăng dần (Bubble Sort)
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // In kết quả
        System.out.println("Mang sau khi sap xep tang dan:");
        for (int x : arr) {
            System.out.print(x + " ");
        }

        sc.close();
    }
}