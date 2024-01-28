package com.udd.lawsearch.shared;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfService {
    public PdfContractData getData(InputStream stream) {
        try (PDDocument pdDocument = PDDocument.load(stream)) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(pdDocument);
            return extractPersonInfo(text);
        } catch (IOException e) {
            System.out.println("Error while converting document!");
        }
        return  null;
    }

    private PdfContractData extractPersonInfo(String content) {
        int index = content.indexOf("Potpisnik ugovora za klijenta");

        if (index != -1) {
            int endIndex = index - 4;
            String ime = null;
            String prezime = null;
            boolean foundLastName = false;

            for (int i = index; i >= 0; i--){
                if (content.charAt(i) == '<') {
                    if (!foundLastName) {
                        prezime = content.substring(i + 1, endIndex);
                        endIndex = i - 2;
                        foundLastName = true;
                    } else {
                        ime = content.substring(i + 1, endIndex);
                        break;
                    }
                }
            }

            if (ime != null) {
                return new PdfContractData(ime, prezime, content);
            }
        }
        return null;
    }
}
