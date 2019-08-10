package com.person.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 16:51
 */
public class BuildTree {

    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }

        List<Tree<T>> topNodes = new ArrayList<>();

        for (Tree<T> children : nodes) {
            String parentId = children.getParentId();
            if ("0".equals(parentId) || parentId == null) {
                topNodes.add(children);
                continue;
            }

            for (Tree<T> parent : nodes) {
                String id = parent.getId();
                if (parentId.equals(id)) {
                    parent.getChildren().add(children);
                }
            }
        }

        return topNodes;
    }

}
