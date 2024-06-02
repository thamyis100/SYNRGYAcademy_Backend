package synrgy7thapmoch1.utils.itext;

import com.example.challenge6.entity.UserOld;
import com.example.challenge6.repository.UserRepositoryOld;
import com.example.challenge6.service.UserServiceOld;
import com.example.challenge6.service.impl.ServiceImplUser;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AddingTableUser {
    @Autowired
    private UserServiceOld userService;

    @Autowired
    private static UserRepositoryOld userRepository;

    public static void generatePdfWithUserData(String dest, UserServiceOld userService, Specification<UserOld> spec, Pageable pageable) throws Exception {
        // Creating a PdfDocument object
        PdfWriter writer = new PdfWriter(dest);

        // Creating a PdfDocument object
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document object
        Document doc = new Document(pdf);

        // Creating a table with appropriate column widths
        float[] pointColumnWidths = {50F, 150F, 200F, 100F};
        Table table = new Table(pointColumnWidths);

        // Adding header cells to the table
        table.addCell(new Cell().add("ID"));
        table.addCell(new Cell().add("Username"));
        table.addCell(new Cell().add("Email Address"));
        table.addCell(new Cell().add("Password"));

        // Fetching user data from the UserService using Specification and Pageable
        Page<UserOld> userList = userRepository.findAll(spec, pageable);
        List<UserOld> users = userList.getContent();

        // Adding user data to the table
        for (UserOld user : users) {
            table.addCell(new Cell().add(user.getId().toString()));
            table.addCell(new Cell().add(user.getUsername()));
            table.addCell(new Cell().add(user.getEmail_address()));
            table.addCell(new Cell().add(user.getPassword()));
        }

        // Adding Table to document
        doc.add(table);

        // Closing the document
        doc.close();
        System.out.println("Table created successfully.");
    }

    public static void main(String[] args) {
        // Define the destination file path
        String dest = "./cdn/addingTableUser.pdf";

        // Create a UserService instance
        UserServiceOld userService = new ServiceImplUser();

        // Define Specification and Pageable (example)
        Specification<UserOld> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        Pageable pageable = Pageable.ofSize(10);

        try {
            // Generate the PDF with user data using Specification and Pageable
            generatePdfWithUserData(dest, userService, spec, pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
