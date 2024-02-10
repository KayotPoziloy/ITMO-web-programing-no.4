package com.example.demo;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class DotDTO {
    @NotNull private String x;
    @NotNull private String y;
    @NotNull private String r;
    @NotNull private String owner;
}
