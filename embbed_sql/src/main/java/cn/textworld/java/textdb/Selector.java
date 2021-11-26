package cn.textworld.java.textdb;

import cn.textworld.java.textdb.cursor.Cursor;

public interface Selector {
    boolean approve(Cursor[] rows);
    void modify(Cursor current);

    public static class Adapter implements Selector {
        public boolean approve(Cursor[] tables) {
            return true;
        }
        public void modify(Cursor current) {
            throw new UnsupportedOperationException(
                    "Can't use a Selector.Adapter in an update"
            );
        }
    }

    public static final Selector ALL = new Adapter();
}
