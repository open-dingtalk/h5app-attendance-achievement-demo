package com.dingtalk.attendance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AntdTableColumn {
    private String key;
    private String dataIndex;
    private String title;
    private String fixed;
    private Integer width;

    public AntdTableColumn(String key, String dataIndex, String title) {
        this.key = key;
        this.dataIndex = dataIndex;
        this.title = title;
    }
}
