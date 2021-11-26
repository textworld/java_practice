package cn.textworld.java.textdb.exporter;

import cn.textworld.java.textdb.Table;
import cn.textworld.java.textdb.TableFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;

public class JTableExporter implements Table.Exporter {
    private String[] columnHeads;
    private Object[][] contents;
    private int rowIndex = 0;

    @Override
    public void startTable() {
        rowIndex = 0;
    }

    @Override
    public void storeMetaData(String tableName, int width, int height, Iterator columnNames) throws IOException {
        contents = new Object[width][];
        columnHeads = new String[width];

        for(int i = 0; columnNames.hasNext();) {
            columnHeads[i++] = columnNames.next().toString();
        }
    }

    @Override
    public void storeRow(Iterator data) throws IOException {
        int columnIndex = 0;
        while (data.hasNext()) {
            contents[rowIndex][columnIndex++] = data.next();
        }

        ++rowIndex;
    }

    @Override
    public void endTable() {
        // do nothing
    }


    public JTable getJTable() {
        return new JTable(contents, columnHeads);
    }


    public static class Test {
        public static void main(String[] args) throws IOException {
            Table people = TableFactory.create("people",
                    new String[] { "Frist", "Last"});
            people.insert(new String[]{"Allen", "Holub"});

        }
    }
}
