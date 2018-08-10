package com.yijian.staff.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yijian.staff.bean.HuiFangTypeBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HUI_FANG_TYPE_BEAN".
*/
public class HuiFangTypeBeanDao extends AbstractDao<HuiFangTypeBean, Void> {

    public static final String TABLENAME = "HUI_FANG_TYPE_BEAN";

    /**
     * Properties of entity HuiFangTypeBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Menu = new Property(0, int.class, "menu", false, "MENU");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
    }


    public HuiFangTypeBeanDao(DaoConfig config) {
        super(config);
    }
    
    public HuiFangTypeBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HUI_FANG_TYPE_BEAN\" (" + //
                "\"MENU\" INTEGER NOT NULL ," + // 0: menu
                "\"NAME\" TEXT);"); // 1: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HUI_FANG_TYPE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HuiFangTypeBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getMenu());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HuiFangTypeBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getMenu());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public HuiFangTypeBean readEntity(Cursor cursor, int offset) {
        HuiFangTypeBean entity = new HuiFangTypeBean( //
            cursor.getInt(offset + 0), // menu
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HuiFangTypeBean entity, int offset) {
        entity.setMenu(cursor.getInt(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(HuiFangTypeBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(HuiFangTypeBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(HuiFangTypeBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
