package com.varnabuslines.controller;

import org.modelmapper.ModelMapper;

public class Mapper
{
    private static final ModelMapper modelMapper = new ModelMapper();

    private Mapper()
    {
    }


    public static <T> T map(final Object source, final Class<T> destinationType)
    {
        var response = modelMapper.map(source, destinationType);
        if (response == null)
            throw new MapperException("Failed to map " + source + " to " + destinationType);
        return response;
    }

    public static class MapperException extends RuntimeException
    {
        public MapperException(final String message)
        {
            super(message);
        }
    }
}
