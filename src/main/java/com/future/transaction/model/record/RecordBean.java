package com.future.transaction.model.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordBean {

    protected Map<String, Object> beanMap = new HashMap<String, Object>();

    public List getRecordBean() {
        return new ArrayList();
    }

    public void setAttribute(String key, Object value) {
        beanMap.put(key, value);
    }

    public String getAttribute(String key) {

        return  (String)beanMap.get(key);
    }

}
