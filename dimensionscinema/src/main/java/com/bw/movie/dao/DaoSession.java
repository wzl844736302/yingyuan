package com.bw.movie.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bw.movie.bean.AllUser;

import com.bw.movie.dao.AllUserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig allUserDaoConfig;

    private final AllUserDao allUserDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        allUserDaoConfig = daoConfigMap.get(AllUserDao.class).clone();
        allUserDaoConfig.initIdentityScope(type);

        allUserDao = new AllUserDao(allUserDaoConfig, this);

        registerDao(AllUser.class, allUserDao);
    }
    
    public void clear() {
        allUserDaoConfig.clearIdentityScope();
    }

    public AllUserDao getAllUserDao() {
        return allUserDao;
    }

}