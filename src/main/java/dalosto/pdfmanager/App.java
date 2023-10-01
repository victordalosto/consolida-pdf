package dalosto.pdfmanager;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import dalosto.pdfmanager.helper.ArquivoProxy;
import dalosto.pdfmanager.helper.ArquivoService;
import dalosto.pdfmanager.helper.PropertiesService;
import dalosto.pdfmanager.model.FileBuffer;
import dalosto.pdfmanager.model.PdfModel;
import dalosto.pdfmanager.service.PdfService;



public class App {

    private static String dirEntrada;
    private static String arquivoSaida;
    private static PropertiesService props;
    private static ArquivoProxy arquivoProxy;
    private static ArquivoService arquivoService;
    private static PdfService pdfService;


    public static void main( String[] args ) throws Exception {
        System.out.println("-- CONSOLIDADOR PDFs V1.0 --");

        System.out.print("\n Bootstrap.. ");
        bootStrap();
        System.out.println("ok\n");

        System.out.println(" Iniciando processo..");


        System.out.println("  - Buscando PDFs em: " + dirEntrada);
        List<Path> arquivosPDF = arquivoService.getInDirectoryListArquivosQueTemNoNome(dirEntrada, ".pdf");
        if (arquivosPDF == null || arquivosPDF.isEmpty()) {
            System.out.println("  - Nenhum arquivo PDF encontrado em: " + dirEntrada + "\n");
            return;
        }
        System.out.println("  - Arquivos PDF encontrados: " + arquivosPDF.size() + "\n");


        System.out.println("  - Processando arquivos.. ");
        FileBuffer fileBuffer = new FileBuffer(PdfModel.getHeader());
        for (Path arquivoPDF : arquivosPDF) {
            System.out.print("  - Processando arquivo: " + arquivoPDF.getFileName() + "  .. ");
            try {
                PdfModel pdf = pdfService.getPdf(arquivoPDF);
                fileBuffer.append(pdf);
                System.out.println("ok");
            } catch (Exception e) {
                System.out.println("erro");
                System.out.println("    - " + e.getMessage());
                fileBuffer.add(arquivoPDF.getFileName() + ";" + "ERRO no momento de processar: " + e.getMessage());
            }
        }


        System.out.println(" Gerando arquivo consolidado.. ");
        arquivoService.salvaArquivo(Paths.get(arquivoSaida), fileBuffer.getText());


        System.out.println("\n ************ CONCLUIDO ********** \n\n");


    }





    private static void bootStrap() {
        try {
            props = new PropertiesService("configuracoes.properties");

            dirEntrada = props.getStringParam("diretorio_entrada");
            arquivoSaida = props.getStringParam("arquivo_saida");

            arquivoProxy = new ArquivoProxy();
            arquivoService = new ArquivoService(arquivoProxy);

            pdfService = new PdfService();

        } catch (Exception e) {

        }
    }
}
