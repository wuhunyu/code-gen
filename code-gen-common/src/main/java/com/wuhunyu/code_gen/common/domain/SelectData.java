package com.wuhunyu.code_gen.common.domain;

import lombok.Data;

import java.util.List;

/**
 * 选择器适配数据结构
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2022/8/16 19:34
 */

@Data
public class SelectData {

    private String label;

    private String value;

    private List<SelectData> children;

    public SelectData() {
    }

    public SelectData(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public SelectData(String label, String value, List<SelectData> children) {
        this.label = label;
        this.value = value;
        this.children = children;
    }
}
