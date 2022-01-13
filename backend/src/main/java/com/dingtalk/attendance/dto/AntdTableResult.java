package com.dingtalk.attendance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AntdTableResult {

    private List<AntdTableColumn> columns;

    private List<Map<String, Object>> dataSources;

}
