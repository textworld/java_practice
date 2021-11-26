package cn.textworld.java.textdb.importer;

import cn.textworld.java.textdb.Table;
import org.apache.commons.collections4.iterators.ArrayIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class CSVImporter implements Table.Importer{
    private BufferedReader in;
    private String tableName;
    private String[] columnNames;
    public  CSVImporter(Reader reader) {
        this.in = reader instanceof BufferedReader ?
                (BufferedReader) reader : new BufferedReader(in);

    }
    @Override
    public void startTable() throws IOException {
        this.tableName = in.readLine().trim();
        this.columnNames = in.readLine().split("\\s*,\\s*");
    }

    @Override
    public String loadTableName() throws IOException {
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException {
        return columnNames.length;
    }

    @Override
    public Iterator loadColumnNames() throws IOException {
        return new ArrayIterator(this.columnNames);
    }

    @Override
    public Iterator loadRow() throws IOException {
        Iterator row = null;
        if (this.in != null) {
            String line = this.in.readLine();
            if (line == null) {
                this.in = null;
            }else{
                row = new ArrayIterator(line.split("\\s*,\\s*"));
            }
        }
        return row;
    }

    @Override
    public void endTable() {

    }
}
