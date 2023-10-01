package dalosto.pdfmanager.service;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import dalosto.pdfmanager.model.PdfModel;


public class PdfService {


    public PdfModel getPdf(Path pdfFile) throws IOException {
        String nome = pdfFile.getFileName().toString();
        String texto = getStringInPdf(pdfFile);
        return new PdfModel(nome, texto);
    }



    private String getStringInPdf(Path pdfFile) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }


}
