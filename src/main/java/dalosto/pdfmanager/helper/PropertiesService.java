package dalosto.pdfmanager.helper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesService {

    private final String caminhoConfiguracoes;


    /**
     * Indica o caminho com os arquivos de configuracao
     * @param caminhoConfiguracoes
     */
    public PropertiesService(String caminhoConfiguracoes) {
        this.caminhoConfiguracoes = caminhoConfiguracoes;
    }



    public String getStringParam(String key) throws IOException {
        try (var fis = new FileInputStream(caminhoConfiguracoes)) {
            var properties = new Properties();
            properties.load(fis);
            String value = properties.getProperty(key);
            return value;
        }
    }



    public boolean getBooleanParam(String key) throws IOException {
        return Boolean.parseBoolean(getStringParam(key));
    }



    public Double getDoubleParam(String key) throws IOException {
        return Double.parseDouble(getStringParam(key));
    }



    public Integer getIntParam(String key) throws IOException {
        return Integer.parseInt(getStringParam(key));
    }

}