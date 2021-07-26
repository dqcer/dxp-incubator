package com.dqcer.dxptools.sync.strategy;


/**
 * @author dongqin
 * @description db上下文
 * @date 2021/07/26
 */
public class DbContext {

    public static final String MYSQL = "mysql";

    public static DbStrategy create(String type) {
      if (MYSQL.equals(type.toLowerCase())){
        return new MySqlStrategy();
      }
      return null;
    }
}
