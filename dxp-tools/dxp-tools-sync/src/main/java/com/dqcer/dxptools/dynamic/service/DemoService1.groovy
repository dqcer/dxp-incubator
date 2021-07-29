package com.dqcer.dxptools.dynamic.service

import com.dqcer.dxptools.dynamic.dao.BaseDAO
import org.springframework.beans.factory.annotation.Autowired

class DemoService1 implements IBaseService {

    @Autowired
    private BaseDAO roleDAO

    /**
     * 视图
     *
     * @return {@link }
     */
    @Override
    def Object view() {
        return null;
    }

    /**
     * 导出excel
     */
    @Override
    void exportExcel() {
    }
}
