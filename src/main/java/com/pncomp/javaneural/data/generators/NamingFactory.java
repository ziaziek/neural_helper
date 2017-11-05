package com.pncomp.javaneural.data.generators;

import java.util.Date;

public class NamingFactory {

    public static String createNameForNow(final String prefix){
        return prefix + new Date();
    }
}
