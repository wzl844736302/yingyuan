package com.bw.movie.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bw.movie.bean.AllUser;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALL_USER".
*/
public class AllUserDao extends AbstractDao<AllUser, Long> {

    public static final String TABLENAME = "ALL_USER";

    /**
     * Properties of entity AllUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property SessionId = new Property(1, String.class, "sessionId", false, "SESSION_ID");
        public final static Property UserId = new Property(2, int.class, "userId", false, "USER_ID");
        public final static Property NickName = new Property(3, String.class, "nickName", false, "NICK_NAME");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Birthday = new Property(5, long.class, "birthday", false, "BIRTHDAY");
        public final static Property Sex = new Property(6, int.class, "sex", false, "SEX");
        public final static Property LastLoginTime = new Property(7, long.class, "lastLoginTime", false, "LAST_LOGIN_TIME");
        public final static Property HeadPic = new Property(8, String.class, "headPic", false, "HEAD_PIC");
    }


    public AllUserDao(DaoConfig config) {
        super(config);
    }
    
    public AllUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"SESSION_ID\" TEXT," + // 1: sessionId
                "\"USER_ID\" INTEGER NOT NULL ," + // 2: userId
                "\"NICK_NAME\" TEXT," + // 3: nickName
                "\"PHONE\" TEXT," + // 4: phone
                "\"BIRTHDAY\" INTEGER NOT NULL ," + // 5: birthday
                "\"SEX\" INTEGER NOT NULL ," + // 6: sex
                "\"LAST_LOGIN_TIME\" INTEGER NOT NULL ," + // 7: lastLoginTime
                "\"HEAD_PIC\" TEXT);"); // 8: headPic
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AllUser entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(2, sessionId);
        }
        stmt.bindLong(3, entity.getUserId());
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
        stmt.bindLong(6, entity.getBirthday());
        stmt.bindLong(7, entity.getSex());
        stmt.bindLong(8, entity.getLastLoginTime());
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(9, headPic);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AllUser entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String sessionId = entity.getSessionId();
        if (sessionId != null) {
            stmt.bindString(2, sessionId);
        }
        stmt.bindLong(3, entity.getUserId());
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(4, nickName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
        stmt.bindLong(6, entity.getBirthday());
        stmt.bindLong(7, entity.getSex());
        stmt.bindLong(8, entity.getLastLoginTime());
 
        String headPic = entity.getHeadPic();
        if (headPic != null) {
            stmt.bindString(9, headPic);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public AllUser readEntity(Cursor cursor, int offset) {
        AllUser entity = new AllUser( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sessionId
            cursor.getInt(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // nickName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.getLong(offset + 5), // birthday
            cursor.getInt(offset + 6), // sex
            cursor.getLong(offset + 7), // lastLoginTime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // headPic
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AllUser entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setSessionId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.getInt(offset + 2));
        entity.setNickName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBirthday(cursor.getLong(offset + 5));
        entity.setSex(cursor.getInt(offset + 6));
        entity.setLastLoginTime(cursor.getLong(offset + 7));
        entity.setHeadPic(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AllUser entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AllUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AllUser entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
