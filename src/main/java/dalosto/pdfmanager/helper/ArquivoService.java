package dalosto.pdfmanager.helper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArquivoService {

    private final String diretorioRaiz;
    private final ArquivoProxy proxy;


    public List<Path> getInDirectoryListArquivosQueTemNoNome(String directory, String fileName) throws IOException {
        var list = proxy.getFilesInDirectory(directory);
        if (list == null) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .toList();
    }



    public void salvaArquivo(String nomeArquivo, StringBuffer sb) {
        if (sb == null || sb.length() == 0)
            return;
        salvaStringEmArquivoCSV(nomeArquivo, sb);
    }





    private void salvaStringEmArquivoCSV(String nome, StringBuffer sb) {
        File file = Paths.get(diretorioRaiz, "Resultados", nome).toFile();
        String originalFileName = file.getName();
        if (!originalFileName.endsWith(".csv")) {
            originalFileName += ".csv";
            file = new File(file.getParentFile(), originalFileName);
        }

        int attempts = 0;
        while (attempts < 3) {
            try {
                if (file.exists()) {
                    file.delete();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    System.out.println("..Salvando arquivo: " + file.getAbsolutePath());
                    writer.write(sb.toString());
                }
                break; // Successfully saved the file, exit the loop
            } catch (IOException e) {
                attempts++;
                System.out.println("Nao foi possivel salvar o consolidador (" + attempts + "ª tentativa): " + e.getMessage());
                // Modify the file name for the next attempt
                String fileNameWithoutExtension = originalFileName.replace(".csv", "");
                originalFileName = fileNameWithoutExtension + "_" + attempts + ".csv";
                file = new File(file.getParentFile(), originalFileName);
            }
        }
    }

}