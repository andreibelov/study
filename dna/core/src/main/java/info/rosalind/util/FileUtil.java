package info.rosalind.util;

import com.diffplug.common.base.Errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Function;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by john on 11/1/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String FOLDER_NAME = System.getProperty("java.io.tmpdir");
    private static String IN_FOLDER_NAME = FOLDER_NAME.concat("/in/");
    private static String OUT_FOLDER_NAME = FOLDER_NAME.concat("/out/");

    public static void saveToFile(Stream<Character> stream, String fileName) throws IOException {

        Path path = Paths.get(OUT_FOLDER_NAME.concat(fileName)); //        replaceFirst("^/(.:/)", "$1");
        PipedInputStream in = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(in);

        try (Writer out = new BufferedWriter(new OutputStreamWriter(pipedOutputStream),200)) {
            stream.forEachOrdered((c) -> {
                try {
                    out.write(c);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.copy(in,path, StandardCopyOption.REPLACE_EXISTING);
    }

    public static Stream<Character> readFile(String fileName) throws IOException {
        Path resourcePath = Paths.get(IN_FOLDER_NAME.concat(fileName));
        return Files.lines(resourcePath).collect(Collectors.joining("")).chars().mapToObj(i -> (char)i);
    }

    public static Stream<Character> readReverseFile(String fileName) throws IOException {
        Path resourcePath = Paths.get(IN_FOLDER_NAME.concat(fileName));
        StringBuffer sb = new StringBuffer();
        try (RandomAccessFile file = new RandomAccessFile(resourcePath.toFile(),"r");
        ) {
            Long size = file.length()-2;
            LongStream.range(0, size)
                    .map(i -> size - i-1)
                    .mapToObj(Long::valueOf)
                    .map(Errors.rethrow().wrapFunction((Long l) -> {
                        file.seek(l);
                        return file.read();
                    }))
                    .map(i -> (char) i.intValue())
            .forEachOrdered(sb::append);
        }
        return sb.chars().mapToObj(i -> (char) i);
    }

    public static void init(String folderName){
        FOLDER_NAME = folderName;
        IN_FOLDER_NAME = folderName.concat("/in/");
        OUT_FOLDER_NAME = folderName.concat("/out/");
    }
}
