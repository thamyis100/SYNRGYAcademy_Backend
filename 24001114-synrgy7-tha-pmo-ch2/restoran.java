package Ch2.Challange; // Noncompliant

import lombok.Getter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Getter
class Order {
    private String menu;
    private Integer qty;

    public Order(String menu, Integer qty) {
        this.menu = menu;
        this.qty = qty;
    }
}

public class Restoran {
    private static final Map<String, Integer> menu = new HashMap<>();
    private static final List<Order> orders = new ArrayList<>();
    static {
        menu.put("Nasi Goreng", 15000);
        menu.put("Mie Goreng", 13000);
        menu.put("Nasi + Ayam", 18000);
        menu.put("Es Teh Manis", 3000);
        menu.put("Es Jeruk", 5000);
    }

    public static void main(String[] args) {
        // configure
        // buat variabel
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            print("""
                    ==========================
                    Selamat datang di BinarFud
                    ==========================

                    Silahkan pilih makanan : """);
            choice = -1;
            int count = 1;
            // looping manggil menu
            for (Map.Entry<String, Integer> entry : menu.entrySet()) {
                print(count + ". " + entry.getKey() + "\t" + "\t| " + entry.getValue());
                count++;
            }

            print("""
                    99. Pesan dan Bayar
                    0. Keluar aplikasi
                    Pilihan Anda: """);
            // masukkan input
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else { // debugging
                print("Input tidak valid. Mohon masukkan angka.");
                if (scanner.hasNext()) {
                    String nonIntValue = scanner.next();
                    print("Input is not an integer: " + nonIntValue);
                } else {
                    print("No more input available.");
                }
                scanner.nextLine();
            }

            // logika pilihan
            qty(choice);

        } while (choice != 0);
        thanks();
        System.exit(0);
    }

    // function
    public static void print(String teks) {
        System.out.println(teks);
    }

    public static void thanks() {
        print("Terima kasih telah menggunakan aplikasi.");
    }

    public static void total(Integer total) {
        print("Total\t\t\t" + total);
    }

    // page
    public static void confirmOrder(List<Order> orders) {
        // variabel
        int total = 0;

        print("""
                ==========================
                Konfirmasi & Pembayaran
                ==========================
                """);

        for (Order order : orders) {
            print(order.getMenu() + "\t" + "\t" + (order.getQty() / menu.get(order.getMenu())) + "\t" + order.getQty());
            total += order.getQty();
        }
        print("------------------------------ +");
        total(total);
        print("""

                1. Konfirmasi dan Bayar
                2. Kembali ke menu utama
                0. Keluar aplikasi
                =>""");

        // input
        Scanner scanner = new Scanner(System.in);
        int choice2 = scanner.nextInt();
        // logika input
        switch (choice2) {
            case 1:
                // receipt.txt
                generatePaymentReceipt(orders, total);
                // text show receipt
                print("""
                        ==========================
                        BinarFud
                        ==========================

                        Terima kasih sudah memesan
                        di BinarFud

                        Dibawah ini adalah pesanan anda
                        """);

                for (Order order1 : orders) {
                    print(order1.getMenu() + "\t" + "\t" + (order1.getQty() / menu.get(order1.getMenu())) + "\t"
                            + order1.getQty());
                }
                print("------------------------------ +");
                total(total);
                print("""

                        Pembayaran: BinarCash

                        ==========================
                        Simpan struk ini sebagai
                        bukti pembayaran
                        ==========================""");

                confirmLeave();
                break;
            case 2:
                // do nothing to go back
                break;
            case 0:
                thanks();
                System.exit(0);
                break;
            default:
                print("Pilihan tidak valid.");
        }
    }

    private static void qty(int choice) {
        // buat variabel
        Scanner scanner = new Scanner(System.in);

        if (choice >= 1 && choice <= menu.size()) {
            int qty = 0;
            do {
                String chosenItem = (String) menu.keySet().toArray()[choice - 1];
                int price = menu.get(chosenItem);
                print("""
                        ==========================
                        Berapa pesanan anda
                        ==========================
                        """);
                print(chosenItem + "\t| " + price);
                print("(input 0 untuk kembali)\n\nqty => ");
                try {
                    qty = Integer.parseInt(scanner.next());
                    if (qty == 0) {
                        print("""
                                ==========================
                                Minimal 1 jumlah pesanan
                                ==========================""");

                        scanner = new Scanner(System.in);
                        scanner.nextLine(); // Wait for the user to press enter
                    } else {
                        Order order1 = new Order(chosenItem, price * qty);
                        orders.add(order1);
                    }
                } catch (NumberFormatException e) {
                    print("Input tidak valid. Mohon masukkan angka.");
                }
            } while (qty == 0);

        } else if (choice == 99) {
            confirmOrder(orders);
        } else {
            print("pilih yg ada di menu");
        }
    }

    private static void confirmLeave() {
        print("\n==========================");
        print("Mohon masukkan input");
        print("pillihan anda");
        print("==========================\n");
        print("(y) untuk lanjut");
        print("(n) untuk keluar");
        print("=> ");
        // input
        Scanner scanner = new Scanner(System.in);
        String choice3 = scanner.next();
        if (choice3.equals("n")) {
            thanks();
            System.exit(0);
        }

    }

    // receipt
    private static void generatePaymentReceipt(List<Order> orders, int total) {
        // Prepare the receipt content using StringBuilder
        StringBuilder receiptContent = new StringBuilder();
        String garis = "==========================\n";
        receiptContent.append(garis);
        receiptContent.append("BinarFud\n");
        receiptContent.append(garis);
        receiptContent.append("Terima kasih sudah memesan di BinarFud\n\n");
        receiptContent.append("Dibawah ini adalah pesanan anda\n\n");
        for (Order order : orders) {
            receiptContent.append(
                    order.getMenu() + "\t" + "\t" + (order.getQty() / menu.get(order.getMenu())) + "\t"
                            + order.getQty());
            total += order.getQty();
        }
        receiptContent.append("------------------------------ +\n");
        receiptContent.append("Total\t\t\t").append(total).append("\n\n");
        receiptContent.append("Pembayaran: BinarCash\n\n");
        receiptContent.append(garis);
        receiptContent.append("Simpan struk ini sebagai\nbukti pembayaran\n");
        receiptContent.append(garis);

        // Write the receipt content to file
        try (FileWriter fileWriter = new FileWriter("payment_receipt.txt")) {
            fileWriter.write(receiptContent.toString());
            print("Receipt created successfully.");
        } catch (IOException e) {
            print("An error occurred while creating the receipt.");
            e.printStackTrace();
        }
    }
}
