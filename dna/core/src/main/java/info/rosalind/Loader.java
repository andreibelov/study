package info.rosalind;

import info.rosalind.util.FileUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by john on 10/31/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class Loader {

    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "fs.properties";


    private static String getFolder(){
        return (String) properties.getProperty("folder");
    }

    static void load() throws IOException {

        ClassLoader classloader = Loader.class.getClassLoader();
        properties.load(classloader.getResourceAsStream(PROPERTIES_FILE));
        FileUtil.init(getFolder());

    }
}
