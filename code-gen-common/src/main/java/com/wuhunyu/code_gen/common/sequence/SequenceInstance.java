package com.wuhunyu.code_gen.common.sequence;

/**
 * 全局唯一id实例
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/15 13:58
 */

public enum SequenceInstance {

    /**
     * 全局唯一id实例
     */
    INSTANCE;

    private final Sequence sequence;

    SequenceInstance() {
        sequence = new Sequence();
    }

    /**
     * 获取下一个id
     *
     * @return 全局唯一id
     */
    public long nextId() {
        return sequence.nextId();
    }

}
