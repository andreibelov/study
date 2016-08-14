package ru.belov.study.proj5.core;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.regex.Matcher;

/**
 * Created by john on 7/7/2016.
 */
public class MatchItr extends Spliterators.AbstractSpliterator<String[]>
{
    private final Matcher m;

    public MatchItr(Matcher m) {
        super(Long.MAX_VALUE, ORDERED | NONNULL | IMMUTABLE);
        this.m = m;
    }

    @Override public boolean tryAdvance(Consumer<? super String[]> action) {
        if (!m.find()) return false;
        final String[] groups = new String[m.groupCount()+1];
        for (int i = 0; i <= m.groupCount(); i++) groups[i] = m.group(i);
        action.accept(groups);
        return true;
    }
}
