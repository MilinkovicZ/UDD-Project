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
            return extractKeyData(text);
        } catch (IOException e) {
            System.out.println("Error while converting document!");
        }
        return  null;
    }

    private PdfContractData extractKeyData(String content) {
        PdfContractData data = new PdfContractData();
        int govIndex = content.indexOf("Uprava za");

        String governmentName = null;
        String governmentLevel = null;
        String governmentAddress = null;
        String signatoryFirstName = null;
        String signatoryLastName = null;

        if (govIndex != -1) {
            int count = 0;

            for (int i = govIndex; i < content.length(); i++) {
                if (content.charAt(i) == '<') {
                    int endIndex = content.indexOf('>', i);
                    if (endIndex != -1) {
                        String dataBetweenTags = content.substring(i + 1, endIndex);
                        count++;
                        switch (count) {
                            case 1:
                                governmentName = dataBetweenTags;
                                break;
                            case 2:
                                governmentLevel = dataBetweenTags;
                                break;
                            case 3:
                                governmentAddress = dataBetweenTags;
                                break;
                        }
                        i = endIndex;
                    }
                }
                if (count == 3) break;
            }
        }

        govIndex = content.indexOf("Potpisnik ugovora za klijenta");

        if (govIndex != -1) {
            int endIndex = govIndex - 4;
            boolean foundLastName = false;

            for (int i = govIndex; i >= 0; i--) {
                if (content.charAt(i) == '<') {
                    if (!foundLastName) {
                        signatoryLastName = content.substring(i + 1, endIndex);
                        endIndex = i - 2;
                        foundLastName = true;
                    } else {
                        signatoryFirstName = content.substring(i + 1, endIndex);
                        break;
                    }
                }
            }
        }

        return new PdfContractData(signatoryFirstName, signatoryLastName, governmentName, governmentLevel, governmentAddress, content);
    }
}
