/*
 Navicat Premium Data Transfer

 Source Server         : PostgreSQL
 Source Server Type    : PostgreSQL
 Source Server Version : 110005
 Source Host           : localhost:5432
 Source Catalog        : sishi
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110005
 File Encoding         : 65001

 Date: 10/03/2021 13:31:10
*/


-- ----------------------------
-- Table structure for tb_tag_resource_map
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_tag_resource_map";
CREATE TABLE "public"."tb_tag_resource_map" (
  "tag_id" varchar(36) COLLATE "pg_catalog"."default",
  "tag_name" varchar(50) COLLATE "pg_catalog"."default",
  "resource_id" varchar(36) COLLATE "pg_catalog"."default",
  "resource_type" varchar(20) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of tb_tag_resource_map
-- ----------------------------
INSERT INTO "public"."tb_tag_resource_map" VALUES ('11b722e2-a7d9-42b2-92a2-c7eb7920b842', '党史新学@中共一大', '3dc0cc03-5ba0-4ef5-b40f-2750d7296932', 'tb_mapinfo');
INSERT INTO "public"."tb_tag_resource_map" VALUES ('11b722e2-a7d9-42b2-92a2-c7eb7920b842', '党史新学@中共一大', 'test', 'tb_mapinfo');
