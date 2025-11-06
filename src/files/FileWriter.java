package files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {

    private FileWriter() {}

    public static void CriarArquivo(String nomeArquivo, String conteudo) throws IOException {
        Files.writeString(Path.of(nomeArquivo), conteudo, StandardCharsets.UTF_8);
    }
}
