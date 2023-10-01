package dalosto.pdfmanager.helper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ArquivoProxy {

    private HashMap<String, List<Path>> filesCache = new HashMap<>();


    public synchronized List<Path> getFilesInDirectory(String directory) {
        if (!filesCache.containsKey(directory)) {
            try {
                List<Path> arquivos = new ArrayList<Path>();
                if (Files.exists(Paths.get(directory))) {
                    Files.walk(Paths.get(directory))
                        .filter(Files::isRegularFile)
                        .forEach(arquivos::add);
                    if (!arquivos.isEmpty()) {
                        filesCache.put(directory, arquivos);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return filesCache.get(directory);
    }




    public void clearCache() {
        filesCache.clear();
    }



}