package com.keeko.common.dictMap;

import com.keeko.common.dictMap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author Yelq
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId","字典ID");
        put("label","字典名称");
        put("value","字典内容");
    }

    @Override
    protected void initBeWrapped() {}
}
