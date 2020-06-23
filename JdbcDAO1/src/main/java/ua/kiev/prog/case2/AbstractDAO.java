package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.Column;
import ua.kiev.prog.shared.Id;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<K, T> {
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Column.class)) {
                    names.append(f.getAnnotation(Column.class).realName()).append(',');
                } else {
                    names.append(f.getName()).append(',');
                }


                values.append('"').append(f.get(t)).append("\",");
            }
            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null)
                throw new RuntimeException("No Id field");

            StringBuilder sb = new StringBuilder();

            for (Field f : fields) {
                if (f != id) {
                    f.setAccessible(true);

                    sb.append(f.getName())
                            .append(" = ")
                            .append('"')
                            .append(f.get(t))
                            .append('"')
                            .append(',');
                }
            }

            sb.deleteCharAt(sb.length() - 1);

            String sql = "UPDATE " + table + " SET " + sb.toString() + " WHERE " +
                    id.getName() + " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null)
                throw new RuntimeException("No Id field");

            String sql = "DELETE FROM " + table + " WHERE " + id.getName() +
                    " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getAll(Class<T> cls, String... columns) {
        List<T> res = new ArrayList<>();
        StringBuilder columnsNames = new StringBuilder();
        if (columns.length != 0) {
            for (String column : columns) {
                column = checkAnotationColumns(column);
                columnsNames.append(column + ",");
                System.out.println("column = " + column);
            }
            columnsNames.deleteCharAt(columnsNames.length() - 1);
        } else {
            columnsNames.append("*");
        }
        System.out.println("columnsNames = " + columnsNames.toString());


        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT " + columnsNames + " FROM " + table)) {
                    System.out.println("SELECT " + columnsNames + " FROM " + table);
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        T t = cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);
                            Field[] fields = t.getClass().getDeclaredFields();
                            for (Field f : fields) {
                                if (f.isAnnotationPresent(Column.class)) {
                                    columnName = f.getName();
                                }
                            }

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);
                            columnName = checkAnotationColumns(columnName);
                            field.set(t, rs.getObject(columnName));
                        }

                        res.add(t);
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String checkAnotationColumns(String column) {
        Client c = new Client();
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                column = field.getAnnotation(Column.class).realName();
                return column;
            }
        }
        return column;
    }

    public void init() {
        // TODO:
    }
}
