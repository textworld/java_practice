package cn.textworld.java.textdb;

import org.apache.commons.collections4.iterators.ArrayIterator;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConcreteTable implements Table{

    private LinkedList rowSet = new LinkedList();;
    private String[] columnNames;
    private String tableName;
    private transient boolean isDirty = false;
    private transient LinkedList<List<Undo>> transactionStack = new LinkedList<>();


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

    @Override
    public void begin() {
        transactionStack.addLast(new LinkedList<>());
    }


    @Override
    public void commit(boolean all) throws IllegalStateException {
        if (transactionStack.isEmpty()) {
            throw  new IllegalStateException("No BEGIN for COMMIT");
        }
        do {
            LinkedList<Undo> currentLevel = (LinkedList<Undo>)transactionStack.removeLast();
            if (!transactionStack.isEmpty()) {
                ((LinkedList<Undo>)transactionStack.getLast()).addAll(currentLevel);
            }
        } while (all && !transactionStack.isEmpty());
    }

    @Override
    public void rollback(boolean all) throws IllegalStateException {
        if (transactionStack.isEmpty()) {
            throw new IllegalStateException("No BEGIN for ROLLBACK");
        }

        do {
            LinkedList<Undo> currentLevel = (LinkedList<Undo>) transactionStack.getLast();

            while (!currentLevel.isEmpty()) {
                currentLevel.removeLast().execute();
            }
        } while (all && !transactionStack.isEmpty());
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
        rowSet.add(newRow);
        registerInsert(newRow);
        isDirty = true;
    }



    private void register(Undo op) {
        ((LinkedList<Undo>)transactionStack.getLast()).addLast(op);
    }
    private void registerUpdate(Object[] row, int cell, Object oldContents) {
        if (!transactionStack.isEmpty()) {
            register(new UndoUpdate(row, cell, oldContents));
        }
    }
    private void registerDelete(Object[] oldRow) {
        if (!transactionStack.isEmpty()) {
            register(new UndoDelete(oldRow));
        }
    }
    private void registerInsert(Object[] newRow) {
        if (!transactionStack.isEmpty()) {
            register(new UndoInsert(newRow));
        }
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

    private interface Undo {
        void execute();
    }

    private class UndoInsert implements Undo {
        private final Object[] insertedRows;

        public UndoInsert(Object[] insertedRows) {
            this.insertedRows = insertedRows;
        }

        @Override
        public void execute() {
            rowSet.remove(insertedRows);
        }
    }

    private class UndoDelete implements Undo {
        private final Object[] deletedRows;

        public UndoDelete(Object[] deletedRows) {
            this.deletedRows = deletedRows;
        }

        @Override
        public void execute() {
            rowSet.add(deletedRows);
        }
    }


    private class UndoUpdate implements Undo {
        private Object[] row;
        private int cell;
        private Object oldContents;

        public UndoUpdate(Object[] row, int cell, Object oldContents) {
            this.row = row;
            this.cell = cell;
            this.oldContents = oldContents;
        }

        @Override
        public void execute() {
            row[cell] = oldContents;
        }
    }
}
