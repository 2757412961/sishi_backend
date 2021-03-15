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

 Date: 10/03/2021 19:33:12
*/


-- ----------------------------
-- Table structure for tb_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_token";
CREATE TABLE "public"."tb_token" (
  "user_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" int8,
  "update_time" int8,
  "token" varchar(50) COLLATE "pg_catalog"."default",
  "expire" int8
)
;

-- ----------------------------
-- Primary Key structure for table tb_token
-- ----------------------------
ALTER TABLE "public"."tb_token" ADD CONSTRAINT "tb_user_copy1_pkey" PRIMARY KEY ("user_id");
