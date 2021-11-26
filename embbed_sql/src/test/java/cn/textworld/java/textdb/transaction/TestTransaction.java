package cn.textworld.java.textdb.transaction;

import cn.textworld.java.textdb.ConcreteTable;
import cn.textworld.java.textdb.Table;
import org.junit.Test;

public class TestTransaction {
    @Test
    public void embeddedTransaction() throws {
        Table t = new ConcreteTable("x", "data");
        t.begin();
            t.insert("data", "A");
            t.insert("data", "B");
            t.begin();
                t.insert("data", "C");
                t.insert("data", "D");
            t.commit(false);
        t.commit(false);

    }
}
