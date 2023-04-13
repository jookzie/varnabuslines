package com.varnabuslines.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Serves as a DTO for easier access
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListWrapper<T>
{
    private T[] array;
}
