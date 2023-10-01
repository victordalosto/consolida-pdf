package dalosto.pdfmanager.model;

public class FileBuffer {

    private StringBuffer buffer;

    public FileBuffer(String header) {
        buffer = new StringBuffer();
        add(header);
    }

    public void append(Object o) {
       add(o.toString());
    }


    public void add(String text) {
        buffer.append(text+"\n");
    }


    public String getText() {
        return buffer.toString();
    }

}
