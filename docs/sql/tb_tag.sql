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

 Date: 10/03/2021 19:32:54
*/


-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_tag";
CREATE TABLE "public"."tb_tag" (
  "tag_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Uniques structure for table tb_tag
-- ----------------------------
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "tb_tag_tag_id_key" UNIQUE ("tag_id");
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "tb_tag_tag_name_key" UNIQUE ("tag_name");

-- ----------------------------
-- Primary Key structure for table tb_tag
-- ----------------------------
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "id" PRIMARY KEY ("tag_id");
