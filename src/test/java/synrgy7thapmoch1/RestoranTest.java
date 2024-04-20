package synrgy7thapmoch1;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestoranTest {
    private static final Map<String, Integer> menu = new LinkedHashMap<>();
    private List<Order> orders = new ArrayList<>();

    static {
        menu.put("Nasi Goreng", 15000);
        menu.put("Mie Goreng", 13000);
        menu.put("Nasi + Ayam", 18000);
        menu.put("Es Teh Manis", 3000);
        menu.put("Es Jeruk", 5000);
        menu.put("Menu A", 0); // Added menu with value 0
        menu.put("Menu B", 0); // Added menu with value 0
    }
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = out;
    private final InputStream originalIn = System.in;

    private Scanner mockScanner;

    @BeforeEach
    public void setUpStreams() {
        mockScanner = Mockito.mock(Scanner.class);
        orders = new ArrayList<>();

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testMain_ValidInput() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("1\n99\n".getBytes());
        System.setIn(in);

        // Mock System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Execute the main method
        Restoran.main(null);

        // Verify output
        String output = out.toString();
        assertTrue(output.contains("Selamat datang di BinarFud"));
        assertTrue(output.contains("Pilihan Anda:"));
        assertTrue(output.contains("Terima kasih"));

//        // Verify exit call
//        verifyStatic(Restoran.class, times(1));
//        Restoran.exit();
    }

    @Test
    public void testMain_NonIntegerInput() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("abc\n1\n99\n".getBytes());
        System.setIn(in);

        // Mock System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Execute the main method
        Restoran.main(null);

        // Verify output
        String output = out.toString();
        assertTrue(output.contains("Input tidak valid"));
        assertTrue(output.contains("Mohon masukkan angka"));

//        // Verify exit call
//        verifyStatic(Restoran.class, times(1));
//        Restoran.exit();
    }

    @Test
    public void testMain_NoMoreInput() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(in);

        // Mock System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Execute the main method
        Restoran.main(null);

        // Verify output
        String output = out.toString();
        assertTrue(output.contains("Input tidak valid"));
        assertTrue(output.contains("No more input available"));

        // Verify exit call
//        verifyStatic(Restoran.class, times(1));
//        Restoran.exit();
    }

    @Test
    public void testShowMenu() {
        Restoran.showMenu();
        assertTrue(outContent.toString().contains("1. Nasi Goreng\t\t| 15000"));
        assertTrue(outContent.toString().contains("2. Mie Goreng\t\t| 13000"));
        assertTrue(outContent.toString().contains("3. Nasi + Ayam\t\t| 18000"));
        assertTrue(outContent.toString().contains("4. Es Teh Manis\t\t| 3000"));
        assertTrue(outContent.toString().contains("5. Es Jeruk\t\t| 5000"));
//        String expected = "1. Nasi Goreng\t\t| 15000\n" +
//                "2. Mie Goreng\t\t| 13000\n" +
//                "3. Nasi + Ayam\t\t| 18000\n" +
//                "4. Es Teh Manis\t\t| 3000\n" +
//                "5. Es Jeruk\t\t| 5000";
//        String actual = outContent.toString().trim();  // Trim any leading/trailing whitespace
//        assertEquals(expected, actual);
//        assertTrue(outContent.toString().contains("Menu A"));
//        assertTrue(outContent.toString().contains("Menu B"));
    }

    @Test
    public void testprint(){
        Restoran.print("test");
        assertTrue(outContent.toString().contains("test"));
    }

    @Test
    public void testGetMenuByKeyIndexValidChoice(){
        // Given
        String[] expectedMenuItems = {"Nasi Goreng", "Mie Goreng", "Nasi + Ayam", "Es Teh Manis", "Es Jeruk", "Menu A", "Menu B"};

        // When & Then
        for (int i = 1; i <= expectedMenuItems.length; i++) {
            String result = Restoran.getMenuByKeyIndex(i);
            assertEquals(expectedMenuItems[i - 1], result);
        }
    }

    @Test
    public void testHandleZeroValueForAllMenuItems() {
        // Given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Map<String, Integer> menu = new LinkedHashMap<>();
        menu.put("Nasi Goreng", 15000);
        menu.put("Mie Goreng", 13000);
        menu.put("Nasi + Ayam", 18000);
        menu.put("Es Teh Manis", 3000);
        menu.put("Es Jeruk", 5000);
        menu.put("Menu A", 0); // Added menu with value 0
        menu.put("Menu B", 0); // Added menu with value 0
        Restoran restoran = new Restoran(); // Assuming Restoran is the class containing the ZeroValueHandler

        // When
        menu.keySet().forEach(chosenItem -> {
            new Restoran.ZeroValueHandler().handleZeroValue(chosenItem);

            // Then
            assertTrue(outContent.toString().contains("Menu " + chosenItem + " is not available."));
            outContent.reset(); // Reset the output stream for the next iteration
        });
    }

    @Test
    public void testThanks() {
        Restoran.thanks();
        String expected = "Terima kasih telah menggunakan aplikasi.";
        String actual = outContent.toString().trim();  // Trim any leading/trailing whitespace
        assertEquals(expected, actual);
    }

    @Test
    public void testTotal() {
        Integer total = 100;
        Restoran.total(total);
        String expected = "Total\t\t\t\t\t" + total;
        String actual = outContent.toString().trim();  // Trim any leading/trailing whitespace
        assertEquals(expected, actual);
    }

//    @Test
//    public void testQtyWithValidInput() {
//        Scanner mockScanner = mock(Scanner.class);
//        PrintStream mockPrintStream = mock(PrintStream.class);
//
//        int selectedMenu = 1;
//
//// Mock scanner inputs
//        when(mockScanner.hasNextInt())
//                .thenReturn(true); // only need to mock once
//        when(mockScanner.nextInt())
//                .thenReturn(selectedMenu)
//                .thenReturn(0)
//                .thenReturn(0);
//
//// Set up System.out to mockPrintStream
//        System.setOut(mockPrintStream);
//
//// Call method under test
//        Restoran.qty(selectedMenu, mockScanner);
//
//// Verify method calls
//        verify(mockScanner, times(3)).hasNextInt(); // should be called three times
//        verify(mockScanner, times(2)).nextInt(); // should be called twice
//        verify(mockPrintStream).println("Berapa pesanan anda");
//        verify(mockPrintStream).println("Nasi Goreng\t| 15000");
//        verify(mockPrintStream).println("(input 0 untuk kembali)\n\nqty => ");
//        verify(mockPrintStream).println("==========================");
//
//// Reset System.out
//        System.setOut(System.out);
//    }

    @Test
    public void testQtyWithInvalidInput() {
        // Define input containing both valid and invalid inputs
        String input = "abc\n1";


        // Redirect System.in to ByteArrayInputStream
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture output from System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Execute the qty method with the mocked input
        Restoran.qty(1, new Scanner(System.in));

        // Assert that both valid and invalid input prompts are present in the output
        assertTrue(out.toString().contains("Input tidak valid. Mohon masukkan angka."));
    }

    @Test
    public void testConfirmLeaveWithValidInputY() {
        // Prepare input for the scanner
        // Define input containing both valid and invalid inputs
        String input = "y\n";


        // Redirect System.in to ByteArrayInputStream
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Capture output from System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Call the method under test
        Restoran.confirmLeave();

        // Verify the output
        assertTrue(out.toString().contains("Mohon masukkan input"));
        assertTrue(out.toString().contains("pillihan anda"));
        assertTrue(out.toString().contains("=========================="));
        assertTrue(out.toString().contains("y) untuk lanjut"));
        assertTrue(out.toString().contains("(n) untuk keluar"));
        assertTrue(out.toString().contains("=> "));
    }

    @SneakyThrows
    @Test
    public void testGeneratePaymentReceipt_Success() {
        // Create a list of orders for testing
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Nasi Goreng", 2));
        orders.add(new Order("Mie Goreng", 1));

        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method under test
        Restoran.generatePaymentReceipt(orders, 43000);

        // Check if the receipt file is created
        File receiptFile = new File("payment_receipt.txt");
        assertTrue(receiptFile.exists());

        // Check if the console output contains the expected message
        assertTrue(outContent.toString().contains("Receipt created successfully."));

        // Clean up: delete the created receipt file
        receiptFile.delete();
    }

//    @Test
//    void testGeneratePaymentReceipt_IOException() throws IOException {
//        // Create a mock FileWriter
//        FileWriter fileWriterMock = Mockito.mock(FileWriter.class);
//
//        // Stub the behavior of the FileWriter to throw an IOException when write method is called
//        doThrow(new IOException()).when(fileWriterMock).write(anyString());
//
//        // Create an empty list of orders and a total amount
//        List<Order> orders = new ArrayList<>();
//        int total = 0;
//
//        // Call the method under test with the mocked FileWriter
//        Restoran.generatePaymentReceipt(orders, total, fileWriterMock);
//
//        // Verify that the IOException is thrown
//        assertTrue(true); // Placeholder assertion to ensure the test fails if the exception is not thrown
//    }


    @Test
    void confirmOrderTest_inputvalid() {
        // Mock input containing choice 1
        String input = "2";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mock System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Create a list of orders for testing
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Nasi Goreng", 30000));

        // Call the method under test
        Restoran.confirmOrder(orders);

        // Verify that the correct output is printed
        String expectedOutput = "==========================\n" +
                "Konfirmasi & Pembayaran\n" +
                "==========================\n" +
                "\n" +
                "Nasi Goreng  \t\t2\t30000\n" +
                "------------------------------ +\n" +
                "Total\t\t\t\t\t30000\n" +
                "\n" +
                "1. Konfirmasi dan Bayar\n" +
                "2. Kembali ke menu utama\n" +
                "0. Keluar aplikasi\n" +
                "=>\n";
        assertEquals(expectedOutput.replaceAll("\\s", ""), outContent.toString().replaceAll("\\s", ""));

    }

    @Test
    void confirmOrderTest_inputinvalid() {
        // Mock input containing choice 1
        String input = "5";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Mock System.out to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Create a list of orders for testing
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Nasi Goreng", 30000));

        // Call the method under test
        Restoran.confirmOrder(orders);

        // Verify that the correct output is printed
        String expectedOutput = "==========================\n" +
                "Konfirmasi & Pembayaran\n" +
                "==========================\n" +
                "\n" +
                "Nasi Goreng  \t\t2\t30000\n" +
                "------------------------------ +\n" +
                "Total\t\t\t\t\t30000\n" +
                "\n" +
                "1. Konfirmasi dan Bayar\n" +
                "2. Kembali ke menu utama\n" +
                "0. Keluar aplikasi\n" +
                "=>\n" +
                "Pilihan tidak valid.\n";
//        assertEquals(expectedOutput, outContent.toString());
        assertEquals(expectedOutput.replaceAll("\\s", ""), outContent.toString().replaceAll("\\s", ""));
    }
    // Add more test cases as needed

}
