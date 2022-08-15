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

    public interface Select {
    }

    public interface Insert {
    }

    public interface Update {
    }

    public interface Delete {
    }

    public interface Export {
    }

    public interface Other {
    }

}
