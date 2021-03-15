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

 Date: 10/03/2021 19:32:45
*/


-- ----------------------------
-- Table structure for tb_picture
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_picture";
CREATE TABLE "public"."tb_picture" (
  "picture_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "picture_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "picture_url" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" int8
)
;

-- ----------------------------
-- Primary Key structure for table tb_picture
-- ----------------------------
ALTER TABLE "public"."tb_picture" ADD CONSTRAINT "tb_picture_pkey" PRIMARY KEY ("picture_id");
