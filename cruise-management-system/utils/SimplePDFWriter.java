package utils;

import java.io.*;

/**
 * Simple PDF writer that creates actual PDF files without external libraries
 * Uses PDF specification to create basic but valid PDF documents
 */
public class SimplePDFWriter {
    
    /**
     * Creates a basic PDF document with text content
     */
    public static void createPDF(String filename, String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        
        // PDF Header
        writeLine(fos, "%PDF-1.4");
        writeLine(fos, "%âãÏÓ"); // PDF binary marker
        
        // Calculate content length
        byte[] contentBytes = content.getBytes("UTF-8");
        
        // Object 1: Catalog
        long obj1Pos = fos.getChannel().position();
        writeLine(fos, "1 0 obj");
        writeLine(fos, "<<");
        writeLine(fos, "/Type /Catalog");
        writeLine(fos, "/Pages 2 0 R");
        writeLine(fos, ">>");
        writeLine(fos, "endobj");
        
        // Object 2: Pages
        long obj2Pos = fos.getChannel().position();
        writeLine(fos, "2 0 obj");
        writeLine(fos, "<<");
        writeLine(fos, "/Type /Pages");
        writeLine(fos, "/Kids [3 0 R]");
        writeLine(fos, "/Count 1");
        writeLine(fos, ">>");
        writeLine(fos, "endobj");
        
        // Object 3: Page
        long obj3Pos = fos.getChannel().position();
        writeLine(fos, "3 0 obj");
        writeLine(fos, "<<");
        writeLine(fos, "/Type /Page");
        writeLine(fos, "/Parent 2 0 R");
        writeLine(fos, "/Resources <<");
        writeLine(fos, "/Font <<");
        writeLine(fos, "/F1 <<");
        writeLine(fos, "/Type /Font");
        writeLine(fos, "/Subtype /Type1");
        writeLine(fos, "/BaseFont /Courier");
        writeLine(fos, ">>");
        writeLine(fos, ">>");
        writeLine(fos, ">>");
        writeLine(fos, "/MediaBox [0 0 612 792]");
        writeLine(fos, "/Contents 4 0 R");
        writeLine(fos, ">>");
        writeLine(fos, "endobj");
        
        // Object 4: Content Stream
        long obj4Pos = fos.getChannel().position();
        
        // Build PDF content stream
        StringBuilder stream = new StringBuilder();
        stream.append("BT\n");
        stream.append("/F1 10 Tf\n");
        stream.append("50 750 Td\n");
        stream.append("12 TL\n");
        
        // Add each line of content
        String[] lines = content.split("\n");
        for (String line : lines) {
            // Escape special characters
            line = line.replace("\\", "\\\\")
                      .replace("(", "\\(")
                      .replace(")", "\\)")
                      .replace("\r", "");
            stream.append("(").append(line).append(") Tj\n");
            stream.append("T*\n"); // Move to next line
        }
        
        stream.append("ET\n");
        
        byte[] streamBytes = stream.toString().getBytes("UTF-8");
        
        writeLine(fos, "4 0 obj");
        writeLine(fos, "<<");
        writeLine(fos, "/Length " + streamBytes.length);
        writeLine(fos, ">>");
        writeLine(fos, "stream");
        fos.write(streamBytes);
        writeLine(fos, "");
        writeLine(fos, "endstream");
        writeLine(fos, "endobj");
        
        // Cross-reference table
        long xrefPos = fos.getChannel().position();
        writeLine(fos, "xref");
        writeLine(fos, "0 5");
        writeLine(fos, "0000000000 65535 f ");
        writeLine(fos, String.format("%010d 00000 n ", obj1Pos));
        writeLine(fos, String.format("%010d 00000 n ", obj2Pos));
        writeLine(fos, String.format("%010d 00000 n ", obj3Pos));
        writeLine(fos, String.format("%010d 00000 n ", obj4Pos));
        
        // Trailer
        writeLine(fos, "trailer");
        writeLine(fos, "<<");
        writeLine(fos, "/Size 5");
        writeLine(fos, "/Root 1 0 R");
        writeLine(fos, ">>");
        writeLine(fos, "startxref");
        writeLine(fos, String.valueOf(xrefPos));
        writeLine(fos, "%%EOF");
        
        fos.close();
    }
    
    private static void writeLine(FileOutputStream fos, String line) throws IOException {
        fos.write((line + "\n").getBytes("UTF-8"));
    }
}
