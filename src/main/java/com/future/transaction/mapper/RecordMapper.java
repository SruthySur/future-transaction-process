package com.future.transaction.mapper;

import com.future.transaction.model.record.RecordBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RecordMapper {

    RecordBean recordBean;

    public RecordBean populateBean(RecordBean recordBean, String attributeName,
                                   ArrayList<RecordBean> recordData) {
        recordBean.setAttribute(attributeName, recordData);
        return recordBean;
    }

    public RecordBean populateBean(RecordBean recordBean, String attributeName, String recordData) {
        recordBean.setAttribute(attributeName, recordData);
        return recordBean;
    }
}
