package com.pncomp.javaneural.data.generators;

import java.util.Date;

/**
 * Class to build names of the neural networks
 */
public class NamingFactory {

    /**
     * Build a name
     * @param prefix name prefix
     * @return string value of the prefix and current datetime
     */
    public static String createNameForNow(final String prefix){
        return prefix + new Date();
    }
}
