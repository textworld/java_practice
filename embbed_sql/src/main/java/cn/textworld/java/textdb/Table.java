package cn.textworld.java.textdb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public interface Table extends Cloneable, Serializable {
    Object clone() throws CloneNotSupportedException;


    String name();

    void rename(String newName);

    boolean isDirty();

    int insert(String[] columnNames, Object[] values);
    int insert(Collection columnNames, Collection values);
    int insert(Object[] values);
    int insert(Collection values);
    // int update(Selector where);
    // int delete(Selector where);
    void begin();
    void commit(boolean all) throws IllegalStateException;
    void rollback(boolean all) throws IllegalStateException;

    void insert(Object[] row);
    void export(Exporter exporter) throws IOException;
    interface Exporter{
        void startTable();
        void storeMetaData(
                String tableName,
                int width,
                int height,
                Iterator columnNames
        ) throws IOException;
        void storeRow(Iterator data) throws IOException;
        void endTable();
    }

    interface Importer {
        void startTable() throws IOException;
        String loadTableName() throws IOException;
        int loadWidth() throws IOException;
        Iterator loadColumnNames() throws IOException;
        Iterator loadRow() throws IOException;
        void endTable();
    }

}
