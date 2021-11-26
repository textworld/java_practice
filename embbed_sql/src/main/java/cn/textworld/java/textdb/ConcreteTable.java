package cn.textworld.java.textdb;

import org.apache.commons.collections4.iterators.ArrayIterator;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ConcreteTable implements Table{

    private LinkedList rowSet = new LinkedList();;
    private String[] columnNames;
    private String tableName;
    private transient boolean isDirty = false;
    private transient LinkedList transactionStack = new LinkedList();


    public ConcreteTable(String name, String[] columns) {
        this.tableName = name;
        this.columnNames = (String[]) columns.clone();
    }

    public ConcreteTable(Table.Importer importer) throws IOException {
        importer.startTable();
        this.tableName = importer.loadTableName();
        int width = importer.loadWidth();
        this.columnNames = new String[width];
        Iterator columnIterator = importer.loadColumnNames();
        for(int i = 0; columnIterator.hasNext(); ) {
            this.columnNames[i++] = (String) columnIterator.next();
        }

        while((columnIterator = importer.loadRow()) != null ) {
            Object[] row = new Object[width];
            for(int i = 0; columnIterator.hasNext();) {
                row[i++] = columnIterator.next();
            }
            this.insert(row);
        }
        importer.endTable();
    }

    private int width(){
        return columnNames.length;
    }


    public int insert(String[] intoTheseColumns, Object[] values) {
        assert(intoTheseColumns.length == values.length) : "There must be exactly one value for " + "each specified column";
        Object[] newRow = new Object[width()];

        for(int i = 0; i < intoTheseColumns.length; i++) {
            newRow[indexOf(intoTheseColumns[i])] = values[i];
        }

        doInsert(newRow);
        return 1;
    }

    public int insert(Collection intoTheseRows, Collection values) {
       assert(intoTheseRows.size() == values.size()) : "There must be exactly one value for " + "each specified column";
       Object[] newRow = new Object[width()];

    }

    private void doInsert(Object[] newRow){
        assert(newRow.length == width()) : "There must be exactly same length";

    }

    private int indexOf(String columnName) {
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals(columnName)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException("Column (" + columnName + ") was not found");
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void rename(String newName) {

    }

    @Override
    public void export(Exporter exporter) throws IOException {
        exporter.startTable();
        exporter.storeMetaData(this.tableName, this.columnNames.length, this.rowSet.size(), new ArrayIterator(this.columnNames));
        for (Iterator i = rowSet.iterator(); i.hasNext();) {
            exporter.storeRow(new ArrayIterator((Object[]) i.next() ));
        }
        exporter.endTable();

        isDirty = false;
    }
}
