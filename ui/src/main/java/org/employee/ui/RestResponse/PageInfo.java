package org.employee.ui.RestResponse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
    private int size;
    private int number;
    private long totalElements;
    private int totalPages;

    @JsonCreator
    public PageInfo(
            @JsonProperty("size") int size,
            @JsonProperty("number") int number,
            @JsonProperty("totalElements") long totalElements,
            @JsonProperty("totalPages") int totalPages) {
        this.size = size;
        this.number = number;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
