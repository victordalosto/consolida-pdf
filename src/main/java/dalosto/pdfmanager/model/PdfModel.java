package dalosto.pdfmanager.model;
import java.util.Arrays;
import java.util.List;
import lombok.Data;


@Data
public class PdfModel {

    private String nome;
    private String numeroNota;
    private String precoInicial;
    private String reajustamento;
    private String municipio;
    private String valorServico;
    private String aliquota;
    private String iss;


    public static String getHeader() {
        return "Nome;Nº;Precos Iniciais;Reajustamento;Municipio ISSQN;Valor dos Servicos;Aliquota;ISS";
    }

    @Override
    public String toString() {
        return nome.replaceAll(";", ",") + ";"
            + numeroNota.replaceAll(";", ",") + ";"
            + precoInicial.replaceAll(";", ",") + ";"
            + reajustamento.replaceAll(";", ",") + ";"
            + municipio.replaceAll(";", ",") + ";"
            + valorServico.replaceAll(";", ",") + ";"
            + aliquota.replaceAll(";", ",") + ";"
            + iss.replaceAll(";", ",");
    }



    public PdfModel(String fileName, String pdfTEXT) {
        List<String> resultList = Arrays.asList(pdfTEXT.split(System.lineSeparator()));
        this.nome = fileName;
        atribuiNumeroNota(resultList);
        this.precoInicial = buscaTextoNaList(resultList, "PREÇOS INICIAIS:");
        this.reajustamento = buscaTextoNaList(resultList, "REAJUSTAMENTO:");
        atribuiMunicpio(resultList);
        this.valorServico = buscaTextoNaList(resultList, "Valor dos serviços: R$ ");
        this.aliquota = buscaTextoNaList(resultList, "(x) Alíquota: ");
        this.iss = buscaTextoNaList(resultList, "(=)Valor do ISS: R$ ");
    }



    private void atribuiNumeroNota(List<String> list) {
        String text = list.get(1);
        if (text == null || text.isBlank() || !text.contains("Nº:")) {
            throw new RuntimeException("\nErro na hora de buscar o valor " + "Nº:" + " na linha " + text + " do arquivo " + this.nome);
        }
        this.numeroNota = text.substring(text.indexOf("Nº:") + 3).trim();
    }



    private void atribuiMunicpio(List<String> resultList) {
        int j=-1;
        for (int i=0; i<resultList.size(); i++) {
            String text = resultList.get(i);
            if (text != null && text.contains("Cod/Município da incidência do ISSQN:")) {
                j = i + 1;
                break;
            }
        }
        String text = resultList.get(j);
        if (j == -1 || !text.contains(" / ")) {
            throw new RuntimeException("\nErro na hora de buscar o valor " + "Cod/Município da incidência do ISSQN:" + " na linha " + resultList + " do arquivo " + this.nome);
        }
        this.municipio = text.substring(text.indexOf(" / ") + 3).trim();
    }



    private String buscaTextoNaList(List<String> listString, String contains) {
        String expected = "null";
        for (String s : listString) {
            if (s != null && s.contains(contains)) {
                if (!expected.equals("null") && !s.equals(expected)) { // Apenas para garantir que o valor buscado é o mesmo em todas as linhas
                    throw new RuntimeException("\nExistem valores duplicados e diferentes de " + contains + " no arquivo " + this.nome);
                }
                expected = s;
            }
        }
        if (expected.equals("null")) {
            throw new RuntimeException("\nErro na hora de buscar o valor " + contains + " na linha " + expected + " do arquivo " + this.nome);
        }
        return expected.substring(expected.indexOf(contains) + contains.length()).trim();
    }



}
