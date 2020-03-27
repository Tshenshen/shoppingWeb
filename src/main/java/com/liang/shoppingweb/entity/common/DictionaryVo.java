package com.liang.shoppingweb.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictionaryVo extends Dictionary {

    private List<Dictionary> dictionaryList;
}
