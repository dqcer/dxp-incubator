package com.dqcer.dxptools.sync.dbhelper;


import com.dqcer.dxptools.sync.dbhelper.impl.MySql;
import com.dqcer.dxptools.sync.dbhelper.impl.SqlServer;

public class Factory
{
  public static DbHelper create(String type)
  {
    if (type.toLowerCase().equals("sqlserver")) {
   //   return new SqlServer();
    }
    else if (type.toLowerCase().equals("mysql")){
      return new MySql();
    }
    return null;
  }
}
