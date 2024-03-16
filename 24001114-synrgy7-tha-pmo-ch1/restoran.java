package ch1.pertemuan2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class restoran {
    private static final Map<String, Integer> menu = new HashMap<>();
    static {
        menu.put("Nasi Goreng", 15000);
        menu.put("Mie Goreng", 13000);
        menu.put("Nasi + Ayam", 18000);
        menu.put("Es Teh Manis", 3000);
        menu.put("Es Jeruk", 5000);
    }
    public static void main(String[] args) {
    // buat variabel
        Scanner scanner = new Scanner(System.in);
        int choice;
        Map<String, Integer> order = new HashMap<>();

        System.out.println("==========================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("==========================");
        do {
        choice = -1;
        //loop menu makanan
        System.out.println("\nSilahkan pilih makanan :");
        int count = 1;
        //looping manggil menu
        for (Map.Entry<String, Integer> entry : menu.entrySet()) {
            System.out.println(count + ". " + entry.getKey() + "\t" + "\t| " + entry.getValue());
            count++;
        }

        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print("\nPilihan Anda: ");

        //masukkan input
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else { // debugging
            System.out.println("Input tidak valid. Mohon masukkan angka.");
            if (scanner.hasNext()) {
                String nonIntValue = scanner.next();
                System.out.println("Input is not an integer: " + nonIntValue);
            } else {
                System.out.println("No more input available.");
            }
            scanner.nextLine();
        }

        //logika pilihan
        if (choice >= 1 && choice <= menu.size()) {
            String chosenItem = (String) menu.keySet().toArray()[choice - 1];
            int price = menu.get(chosenItem);
            order.put(chosenItem, price);
            System.out.println("\n==========================");
            System.out.println("Berapa pesanan anda");
            System.out.println("==========================\n");
            System.out.println(chosenItem + "\t| " + price);
            System.out.print("(input 0 untuk kembali)\n\nqty => ");
            try {
                int qty = Integer.parseInt(scanner.next());
                if (qty == 0) {
                    order.remove(chosenItem);
                } else {
                    order.put(chosenItem, price * qty);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Mohon masukkan angka.");
            }
        } else if (choice == 99) {
            confirmOrder(order);
        }


        }while (choice!=0);
        System.out.println("Terima kasih telah menggunakan aplikasi.");
        System.exit(0);
    }
// melakukan konfirmasi pembelian
    public static void confirmOrder(Map<String, Integer> order) {
        //variabel
        int total = 0;


        System.out.println("\n==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================\n");
        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            System.out.println(
                    entry.getKey() + "\t" + "\t" + (entry.getValue() / menu.get(entry.getKey())) + "\t" + entry.getValue());
            total += entry.getValue();
        }
        System.out.println("------------------------------ +");
        System.out.println("Total\t\t\t" + total);
        System.out.println("\n1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");

//input
        Scanner scanner = new Scanner(System.in);
        int choice2 = scanner.nextInt();
//logika input
        switch (choice2) {
            case 1:
                //receipt.txt
                generatePaymentReceipt(order, total);
                //text show receipt
                System.out.println("\n==========================");
                System.out.println("BinarFud");
                System.out.println("==========================\n");
                System.out.println("Terima kasih sudah memesan di BinarFud\n");
                System.out.println("Dibawah ini adalah pesanan anda\n");
                for (Map.Entry<String, Integer> entry : order.entrySet()) {
                    System.out.println(entry.getKey() + "\t" + (entry.getValue() / menu.get(entry.getKey())) + "\t"
                            + entry.getValue());
                }
                System.out.println("------------------------------ +");
                System.out.println("Total\t\t\t" + total);
                System.out.println("\nPembayaran: BinarCash");
                System.out.println("\n==========================");
                System.out.println("Simpan struk ini sebagai\nbukti pembayaran");
                System.out.println("==========================\n");

                System.out.println("Terima kasih telah menggunakan aplikasi.");
                System.exit(0);
                break;
            case 2:
                //do nothing to go back
                break;
            case 0:
                System.out.println("Terima kasih telah menggunakan aplikasi.");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private static void generatePaymentReceipt(Map<String, Integer> order, int total) {
        try {
            FileWriter fileWriter = new FileWriter("payment_receipt.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("==========================");
            printWriter.println("BinarFud");
            printWriter.println("==========================");
            printWriter.println("Terima kasih sudah memesan di BinarFud\n");
            printWriter.println("Dibawah ini adalah pesanan anda\n");
            for (Map.Entry<String, Integer> entry : order.entrySet()) {
                printWriter.println(entry.getKey() + "\t" + (entry.getValue() / menu.get(entry.getKey())) + "\t"
                        + entry.getValue());
            }
            printWriter.println("------------------------------ +");
            printWriter.println("Total\t\t\t" + total);
            printWriter.println("\nPembayaran: BinarCash");
            printWriter.println("\n==========================");
            printWriter.println("Simpan struk ini sebagai\nbukti pembayaran");
            printWriter.println("==========================");

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencetak struk pembayaran: " + e.getMessage());
        }
    }


}
