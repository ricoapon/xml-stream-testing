package nl.ricoapon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

/**
 * Class that makes it easy to create {@link Supplier} instances.
 */
public class ReaderSupplierBuilder {
    public static Supplier<BufferedReader> fromClasspathResource(String resourcePath) {
        //noinspection ConstantConditions
        return () -> new BufferedReader(new InputStreamReader(ReaderSupplierBuilder.class.getResourceAsStream(resourcePath)));
    }

    public static Supplier<BufferedReader> fromFileOnDisk(String filePath) {
        return () -> {
            try {
                return new BufferedReader(new FileReader(filePath));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
