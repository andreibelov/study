package info.rosalind;

import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * info.rosalind.Solver Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class SolverTest {
    /**
     * Method: getDnaComplement
     */
    @Test
    public void getDnaComplement() throws Exception {
        String in = "AAAACCCGGT";
        String out = "ACCGGGTTTT";

        StringBuilder sb = new StringBuilder();

        Solver.getDnaComplement(in.chars().mapToObj(i -> (char)i))
                .forEachOrdered(sb::append);

        assertThat(sb.reverse().toString(), is(out) );
    }

    /**
     * Method: countNucleotides
     */
    @Test
    public void countNucleotides() throws Exception {
        String in = "AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC";
        String out = "20 12 17 21";

        Map<Character, Long> map = Solver.countNucleotides(in.chars().mapToObj(i -> (char)i));
        String sb = map.get('A').toString() + " " +
                map.get('C').toString() + " " +
                map.get('G').toString() + " " +
                map.get('T').toString();
        assertThat(sb, is(out) );
    }

    /**
     * Method: doRnaTranscription
     */
    @Test
    public void doRnaTranscription() throws Exception {

        String in = "GATGGAACTTGACTACGTAAATT";
        String out = "GAUGGAACUUGACUACGUAAAUU";
        StringBuilder sb = new StringBuilder();
        Solver.doRnaTranscription(in.chars().mapToObj(i -> (char)i))
                .forEachOrdered(sb::append);
        assertThat(sb.toString(), is(out) );

    }

}