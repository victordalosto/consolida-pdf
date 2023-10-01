package dalosto.pdfmanager.helper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArquivoService {

    private final ArquivoProxy proxy;


    public List<Path> getInDirectoryListArquivosQueTemNoNome(String directory, String fileName) {
        List<Path> list = proxy.getFilesInDirectory(directory);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.stream()
                   .filter(path -> path.getFileName().toString().toUpperCase().contains(fileName.toUpperCase()))
                   .toList();
    }



    public void salvaArquivo(Path arquivoFQP, String text) {
        if (arquivoFQP == null || text == null || text.isBlank()) {
            throw new RuntimeException(" Nao foi possivel salvar o arquivo: " + arquivoFQP);
        }

        File dir = arquivoFQP.toFile().getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = arquivoFQP.toFile();
        int attempts = 0;

        while (attempts < 3) {
            try {
                if (file.exists()) {
                    file.delete();
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    System.out.println(" Salvando arquivo em: " + file.getAbsolutePath());
                    writer.write(text);
                }

                break;
            } catch (IOException e) {
                attempts++;
                System.out.println(" Nao foi possivel salvar o consolidador (" + attempts + "ª tentativa): " + e.getMessage());
            }
        }
    }


}