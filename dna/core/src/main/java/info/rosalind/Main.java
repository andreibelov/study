package info.rosalind;

import info.rosalind.util.FileUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static info.rosalind.Loader.load;

/**
 * Created by john on 10/31/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            load();
//            performAction1();
//            performAction2();
            performAction3();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }


    private static void performAction3() throws IOException {

        FileUtil.saveToFile(Solver
                .getDnaComplement(FileUtil
                        .readReverseFile("rosalind_revc_in.txt")),"rosalind_revc_out.txt");
    }

    private static void performAction1() throws IOException {
            Map<Character, Long> map = Solver.countNucleotides(FileUtil.readFile("rosalind_dna_in.txt"));
            FileUtil.saveToFile(map.get('A').toString()
                    .concat(" ").concat(map.get('C').toString())
                    .concat(" ").concat(map.get('G').toString())
                    .concat(" ").concat(map.get('T').toString()).chars().mapToObj(i -> (char)i),"rosalind_dna_out.txt");
    }

    private static void performAction2() throws IOException {
            FileUtil.saveToFile(Solver
                    .doRnaTranscription(FileUtil
                            .readFile("rosalind_rna_in.txt")),"rosalind_rna_out.txt");
    }

}
