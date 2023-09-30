package dalosto.pdfmanager;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import dalosto.pdfmanager.helper.ArquivoProxy;
import dalosto.pdfmanager.helper.ArquivoService;
import dalosto.pdfmanager.helper.PropertiesService;



public class App {

    private static String diretorioAssets;
    private static PropertiesService props;
    private static ArquivoProxy arquivoProxy;
    private static ArquivoService arquivoService;


    public static void main( String[] args ) throws Exception {
        System.out.println("-- CONSOLIDADOR PDFs V1.0 --");

        System.out.print("\n Bootstrap.. ");
        bootStrap();
        System.out.println("ok");

        System.out.println(" Iniciando processo..");
        System.out.println("  - Buscando PDFs em: " + diretorioAssets);

        List<Path> arquivosPDF = arquivoService.getInDirectoryListArquivosQueTemNoNome(diretorioAssets, ".pdf");
        System.out.println("  - Arquivos PDF encontrados: " + arquivosPDF.size());


    }





    private static void bootStrap() throws IOException {
        props = new PropertiesService("configuracoes.properties");
        diretorioAssets = props.getStringParam("diretorio");
        arquivoProxy = new ArquivoProxy();
        arquivoService = new ArquivoService(diretorioAssets, arquivoProxy);

    }
}
