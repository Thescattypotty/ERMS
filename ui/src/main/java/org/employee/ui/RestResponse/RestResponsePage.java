package org.employee.ui.RestResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RestResponsePage<T> extends PageImpl<T> {

    private static final long serialVersionUID = 3248189030448292002L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestResponsePage(
        @JsonProperty("content") List<T> content,
        @JsonProperty("page") PageInfo page
    ) {
        super(content, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
    }

    public RestResponsePage(List<T> content){
        super(content);
    }
    public RestResponsePage(){
        super(new ArrayList<>());
    }
}
