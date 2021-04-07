package cn.edu.zju.sishi.enums;

import org.springframework.util.StringUtils;

public enum ResourceTypeEnum {

    ARTICLE("tb_article"),
    PICTURE("tb_picture"),
    AUDIO("tb_audio"),
    VIDEO("tb_video"),
    MAPINFO("tb_mapinfo"),
    QUESTION("tb_question");

    private String resourceType;

    ResourceTypeEnum(String resourceType) {
        this.resourceType = resourceType;
    }

    public static boolean hasResource(String resourceType) {
        ResourceTypeEnum[] payTypes = values();
        for (ResourceTypeEnum payType : payTypes) {
            if (payType.getResourceType().equals(resourceType)) {
                return true;
            }
        }
        return false;
    }

    public static ResourceTypeEnum getTypeByValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (ResourceTypeEnum enums : ResourceTypeEnum.values()) {
            if (enums.getResourceType().equals(value)) {
                return enums;
            }
        }
        return null;
    }


    public String getResourceType() {
        return resourceType;
    }

}
