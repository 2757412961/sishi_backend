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

 Date: 10/03/2021 19:33:04
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
