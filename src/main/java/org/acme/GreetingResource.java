package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {
        // Load a sample PDF document
        URL url = new URL(
                "https://github.com/siddharth1009maker/assignments/files/9063609/AccountStatment4720221059957.pdf");
        PdfDocument pdf = new PdfDocument(url.openStream());

        // Create a StringBuilder instance
        StringBuilder builder = new StringBuilder();
        // Create a PdfTableExtractor instance
        PdfTableExtractor extractor = new PdfTableExtractor(pdf);

        // Loop through the pages in the PDF
        for (int pageIndex = 0; pageIndex < pdf.getPages().getCount(); pageIndex++) {
            // Extract tables from the current page into a PdfTable array
            PdfTable[] tableLists = extractor.extractTable(pageIndex);

            // If any tables are found
            if (tableLists != null && tableLists.length > 0) {
                // Loop through the tables in the array
                for (PdfTable table : tableLists) {
                    // Loop through the rows in the current table
                    for (int i = 0; i < table.getRowCount(); i++) {
                        // Loop through the columns in the current table
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            // Extract data from the current table cell and append to the StringBuilder
                            String text = table.getText(i, j);
                            text.replace(',', '+');
                            if (j == table.getColumnCount() - 1)
                                builder.append(text);
                            else
                                builder.append(text + ",");
                        }
                        builder.append("\r\n");
                    }
                }
            }
        }
        // System.out.println(builder);
        // Write data into a .txt document
        // FileWriter fw = new
        // FileWriter("C:\\Users\\Sohil_Khattar\\OneDrive\\Desktop\\ExtractTable.csv");
        // fw.write(builder.toString());
        // fw.flush();
        // fw.close();
        return builder.toString();
    }
}