package com.github.beothorn;

import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintRequestResult;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PrintService {

    @Tool(name = "print_file", description = "Prints a file.")
    public String printFile(
        @ToolParam(description = "The file full path.") final String fileName
    ) {
        // Retrieve environment variables
        String printerIp = System.getenv("PRINTER_IP");
        String printerName = System.getenv("PRINTER_NAME");
        String printFolder = System.getenv("PRINT_FOLDER");

        if (printerIp == null || printerName == null || printFolder == null) {
            return "Error: Missing environment variables. Please set PRINTER_IP, PRINTER_NAME, and PRINT_FOLDER.";
        }

        File file = new File(printFolder, fileName);
        if (!file.exists() || !file.isFile()) {
            return "File not found within the print folder.";
        }

        try {
            Path basePath = Paths.get(printFolder).toRealPath().normalize();
            Path filePath = basePath.resolve(fileName).normalize();

            if (!filePath.startsWith(basePath)) {
                return "File not found within the print folder.";
            }
        } catch (Exception e) {
            return "Error: Exception occurred while printing: " + e.getMessage();
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            CupsClient cupsClient = new CupsClient(printerIp, 631);
            URL printerURL = new URL("http://" + printerIp + ":631/printers/" + printerName);
            CupsPrinter printer = cupsClient.getPrinter(printerURL);

            if (printer == null) {
                return "Error: Could not find printer: " + printerName;
            }

            PrintJob job = new PrintJob.Builder(inputStream).build();
            PrintRequestResult result = printer.print(job);

            if (result.isSuccessfulResult()) {
                return "Success: Print job submitted successfully.";
            } else {
                return "Error: Print job failed: " + result.getResultMessage();
            }
        } catch (Exception e) {
            return "Error: Exception occurred while printing: " + e.getMessage();
        }
    }
}
