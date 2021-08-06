package com.dqcer.dxptools.core.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongqin
 * @description order注解 比较器测试
 * @date 2021/08/06
 */
public class AnnotationAwareOrderComparatorTest {

    private final static Log LOG = LogFactory.getLog(AnnotationAwareOrderComparatorTest.class);

    private static List<Object> listObject = new ArrayList<>(2);

    public static void main(String[] args) {
        listObject.add(new Role());
        listObject.add(new User());

        doPrintLogByIndex(0);
        doPrintLogByIndex(1);

        if (LOG.isInfoEnabled()) {
            LOG.info("开始执行[AnnotationAwareOrderComparator#sort(List list)]");
        }

        AnnotationAwareOrderComparator.sort(listObject);

        doPrintLogByIndex(0);
        doPrintLogByIndex(1);
    }

    /**
     * 通过索引打印日志
     *
     * @param index 指数
     */
    public static void doPrintLogByIndex(int index) {
        Object o = listObject.get(index);
        if (o instanceof User) {
            if (LOG.isInfoEnabled()) {
                LOG.info(String.format("第[%d]个执行的是类：[%s]", ++ index , User.class.getName()));
            }
            return;
        }
        if (LOG.isInfoEnabled()) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("第[");
            buffer.append(++ index);
            buffer.append("]个执行的是类：[");
            buffer.append(Role.class.getName());
            buffer.append("]");
            LOG.info(buffer.toString());
        }
    }
}

@Order(-200)
class User {

    @Override
    public String toString() {
        return "User{}";
    }
}

@Order(2)
class Role {
    @Override
    public String toString() {
        return "Role{}";
    }
}
