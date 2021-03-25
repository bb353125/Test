package com.keeko.master.entity;

import lombok.Data;

import java.util.List;

@Data
public class CharacterGroupCondition {

    private Integer groupId;
    private String groupName;
    private List<CharacterCondition> characterList;
}
