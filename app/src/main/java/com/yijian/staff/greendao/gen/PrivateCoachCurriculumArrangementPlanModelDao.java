package com.yijian.staff.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yijian.staff.db.bean.PrivateCoachCurriculumArrangementPlanModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PRIVATE_COACH_CURRICULUM_ARRANGEMENT_PLAN_MODEL".
*/
public class PrivateCoachCurriculumArrangementPlanModelDao extends AbstractDao<PrivateCoachCurriculumArrangementPlanModel, Long> {

    public static final String TABLENAME = "PRIVATE_COACH_CURRICULUM_ARRANGEMENT_PLAN_MODEL";

    /**
     * Properties of entity PrivateCoachCurriculumArrangementPlanModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Idx = new Property(0, Long.class, "idx", true, "_id");
        public final static Property CoachId = new Property(1, String.class, "coachId", false, "COACH_ID");
        public final static Property Colour = new Property(2, String.class, "colour", false, "COLOUR");
        public final static Property DataType = new Property(3, Integer.class, "dataType", false, "DATA_TYPE");
        public final static Property Duration = new Property(4, Integer.class, "duration", false, "DURATION");
        public final static Property ETime = new Property(5, String.class, "eTime", false, "E_TIME");
        public final static Property Id = new Property(6, String.class, "id", false, "ID");
        public final static Property STime = new Property(7, String.class, "sTime", false, "S_TIME");
        public final static Property Week = new Property(8, Integer.class, "week", false, "WEEK");
    }


    public PrivateCoachCurriculumArrangementPlanModelDao(DaoConfig config) {
        super(config);
    }
    
    public PrivateCoachCurriculumArrangementPlanModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRIVATE_COACH_CURRICULUM_ARRANGEMENT_PLAN_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idx
                "\"COACH_ID\" TEXT," + // 1: coachId
                "\"COLOUR\" TEXT," + // 2: colour
                "\"DATA_TYPE\" INTEGER," + // 3: dataType
                "\"DURATION\" INTEGER," + // 4: duration
                "\"E_TIME\" TEXT," + // 5: eTime
                "\"ID\" TEXT," + // 6: id
                "\"S_TIME\" TEXT," + // 7: sTime
                "\"WEEK\" INTEGER);"); // 8: week
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRIVATE_COACH_CURRICULUM_ARRANGEMENT_PLAN_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PrivateCoachCurriculumArrangementPlanModel entity) {
        stmt.clearBindings();
 
        Long idx = entity.getIdx();
        if (idx != null) {
            stmt.bindLong(1, idx);
        }
 
        String coachId = entity.getCoachId();
        if (coachId != null) {
            stmt.bindString(2, coachId);
        }
 
        String colour = entity.getColour();
        if (colour != null) {
            stmt.bindString(3, colour);
        }
 
        Integer dataType = entity.getDataType();
        if (dataType != null) {
            stmt.bindLong(4, dataType);
        }
 
        Integer duration = entity.getDuration();
        if (duration != null) {
            stmt.bindLong(5, duration);
        }
 
        String eTime = entity.getETime();
        if (eTime != null) {
            stmt.bindString(6, eTime);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(7, id);
        }
 
        String sTime = entity.getSTime();
        if (sTime != null) {
            stmt.bindString(8, sTime);
        }
 
        Integer week = entity.getWeek();
        if (week != null) {
            stmt.bindLong(9, week);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PrivateCoachCurriculumArrangementPlanModel entity) {
        stmt.clearBindings();
 
        Long idx = entity.getIdx();
        if (idx != null) {
            stmt.bindLong(1, idx);
        }
 
        String coachId = entity.getCoachId();
        if (coachId != null) {
            stmt.bindString(2, coachId);
        }
 
        String colour = entity.getColour();
        if (colour != null) {
            stmt.bindString(3, colour);
        }
 
        Integer dataType = entity.getDataType();
        if (dataType != null) {
            stmt.bindLong(4, dataType);
        }
 
        Integer duration = entity.getDuration();
        if (duration != null) {
            stmt.bindLong(5, duration);
        }
 
        String eTime = entity.getETime();
        if (eTime != null) {
            stmt.bindString(6, eTime);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(7, id);
        }
 
        String sTime = entity.getSTime();
        if (sTime != null) {
            stmt.bindString(8, sTime);
        }
 
        Integer week = entity.getWeek();
        if (week != null) {
            stmt.bindLong(9, week);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PrivateCoachCurriculumArrangementPlanModel readEntity(Cursor cursor, int offset) {
        PrivateCoachCurriculumArrangementPlanModel entity = new PrivateCoachCurriculumArrangementPlanModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // idx
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // coachId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // colour
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // dataType
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // duration
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // eTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // id
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // sTime
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8) // week
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PrivateCoachCurriculumArrangementPlanModel entity, int offset) {
        entity.setIdx(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCoachId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setColour(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDataType(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDuration(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setETime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setWeek(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PrivateCoachCurriculumArrangementPlanModel entity, long rowId) {
        entity.setIdx(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PrivateCoachCurriculumArrangementPlanModel entity) {
        if(entity != null) {
            return entity.getIdx();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PrivateCoachCurriculumArrangementPlanModel entity) {
        return entity.getIdx() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
