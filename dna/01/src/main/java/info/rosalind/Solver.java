package info.rosalind;

import info.rosalind.enums.RnaCodon;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by john on 10/31/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class Solver {

    public enum Nucleotide {
        A, C, G, T;
    }

    private static final Map<Character,Character> dnaComplemets = new ConcurrentHashMap<>();
    private static final Map<char[],Character> rnaCodonTable = new ConcurrentHashMap<>();

    static {
        rnaCodonTable.putAll(
                Arrays.stream(RnaCodon.values()).collect(Collectors.toMap(e->e.name().toCharArray(), RnaCodon::getAminoAcid))
        );



        dnaComplemets.put('A','T');
        dnaComplemets.put('C','G');
        dnaComplemets.put('G','C');
        dnaComplemets.put('T','A');
    }

    static Map<Character, Long> countNucleotides(Stream<Character> chars){
        return chars.parallel()
                .collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
    }

    static Stream<Character> doRnaTranscription(Stream<Character> chars){
        return chars.parallel().map(ch -> ch.equals('T')?'U':ch);
    }

    static Stream<Character> getDnaComplement(Stream<Character> chars){
        return chars.parallel().map(dnaComplemets::get);
    }

    static Stream<Character> getPeptideFromRna(Stream<char[]> chars){

        chars.flatMap(ch -> rnaCodonTable.containsKey(ch)?
                ((rnaCodonTable.get(ch)!=null)?rnaCodonTable.get(ch):
                        "Stop".chars().mapToObj(i -> (char)i).map(Character::valueOf)):null)
                .filter(Objects::nonNull)

        return chars.parallel()
                .reduce(0,(c2,c3)-> RnaCodon.valueOf(""+c1+c2+c3)
                        .getAminoAcid());
    }

}
