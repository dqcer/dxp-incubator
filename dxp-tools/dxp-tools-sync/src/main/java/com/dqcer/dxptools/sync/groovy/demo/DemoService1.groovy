package com.dqcer.dxptools.sync.groovy.demo

import com.dqcer.dxptools.sync.groovy.dao.RoleDAO
import org.springframework.beans.factory.annotation.Autowired

class DemoService1 implements IBaseService {

    @Autowired
    private RoleDAO roleDAO

    /**
     * 视图
     *
     * @return {@link }
     */
    @Override
    def Object view() {
        return roleDAO.get()
    }

    /**
     * 导出excel
     */
    @Override
    void exportExcel() {
    }
}
