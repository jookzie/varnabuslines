package com.varnabuslines.business.utils.impl;

import com.varnabuslines.business.utils.IWtfJavaAbstractRequestUseCase;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class WtfJavaAbstractRequestUseCaseImpl implements IWtfJavaAbstractRequestUseCase
{
    public static <T> Set<T> toHashSet(final Iterable<T> list)
    {
        return StreamSupport.stream(list.spliterator(), false)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public static int getCount(final Iterable<?> list)
    {
        return (int) StreamSupport.stream(list.spliterator(), false).count();
    }

    private WtfJavaAbstractRequestUseCaseImpl(){}
}

