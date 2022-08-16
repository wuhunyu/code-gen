package com.wuhunyu.code_gen.system.operation_type;

import javax.validation.groups.Default;

/**
 * 操作类型
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 16:17
 */

public interface OperationTypeGroup extends Default {

    public interface Select extends OperationTypeGroup {
    }

    public interface Insert extends OperationTypeGroup {
    }

    public interface Update extends OperationTypeGroup {
    }

    public interface Delete extends OperationTypeGroup {
    }

    public interface Export extends OperationTypeGroup {
    }

    public interface Other extends OperationTypeGroup {
    }

}
