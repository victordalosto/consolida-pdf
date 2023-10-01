package dalosto.pdfmanager;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import dalosto.pdfmanager.helper.ArquivoProxy;
import dalosto.pdfmanager.helper.ArquivoService;
import dalosto.pdfmanager.helper.PropertiesService;
import dalosto.pdfmanager.model.PdfModel;
import dalosto.pdfmanager.service.PdfService;



public class App {

    private static String dirEntrada;
    private static String dirSaida;
    private static PropertiesService props;
    private static ArquivoProxy arquivoProxy;
    private static ArquivoService arquivoService;
    private static PdfService pdfService;


    public static void main( String[] args ) throws Exception {
        System.out.println("-- CONSOLIDADOR PDFs V1.0 --");

        System.out.print("\n Bootstrap.. ");
        bootStrap();
        System.out.println("ok");

        System.out.println(" Iniciando processo..");
        System.out.println("  - Buscando PDFs em: " + dirEntrada);

        List<Path> arquivosPDF = arquivoService.getInDirectoryListArquivosQueTemNoNome(dirEntrada, ".pdf");
        System.out.println("  - Arquivos PDF encontrados: " + arquivosPDF.size());


        List<PdfModel> pdfs = new ArrayList<>();
        for (Path arquivoPDF : arquivosPDF) {
            System.out.print("  - Processando arquivo: " + arquivoPDF.getFileName() + "  .. ");
            PdfModel pdf = pdfService.getPdf(arquivoPDF);
            pdfs.add(pdf);
            System.out.println("ok");
        }


        StringBuffer sb = new StringBuffer();
        sb.append(PdfModel.getHeader()+"\n");
        for (PdfModel pdf : pdfs) {
            sb.append(pdf.toString()+"\n");
        }

        System.out.println("  - Gerando arquivo consolidado.. ");
        arquivoService.salvaArquivo("Consolidado-pdfs", sb);


        System.out.println("\n ************ FIM ********** ");


    }





    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        dirEntrada = props.getStringParam("diretorio_entrada");
        dirSaida = props.getStringParam("diretorio_saida");
        arquivoProxy = new ArquivoProxy();
        arquivoService = new ArquivoService(dirSaida, arquivoProxy);
        pdfService = new PdfService();
    }
}
