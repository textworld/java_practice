package cn.textworld.java.textdb;

import cn.textworld.java.textdb.importer.CSVImporter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class TableFactory {
    public static Table create(String name, String [] columns){
        ConcreteTable concreteTable = new ConcreteTable(name, columns);
        return concreteTable;
    }

    public static Table create(Table.Importer importer) throws Exception{
        return new ConcreteTable(importer);
    }

    public static Table loadCSV(String name, File directory) throws IOException {
        Reader in = new FileReader(new File(directory, name));
        Table load = new ConcreteTable(new CSVImporter(in));
        in.close();
        return load;
    }
}
