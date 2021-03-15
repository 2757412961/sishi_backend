package cn.edu.zju.sishi.enums;

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

    public String getResourceType() {
        return resourceType;
    }

}
