package cn.textworld.java.textdb.cursor;

import cn.textworld.java.textdb.Table;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface Cursor {
    String tableName();

    boolean advance() throws NoSuchElementException;

    Object column(String columnName);

    Iterator<Object> columns();

    boolean isTraversing(Table t);

    Object update(String columnName, Object newValue);

    void delete();
}
