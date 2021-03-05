/*
 Navicat Premium Data Transfer

 Source Server         : PG 192.168.2.2
 Source Server Type    : PostgreSQL
 Source Server Version : 120004
 Source Host           : 192.168.2.2:5432
 Source Catalog        : sishi
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120004
 File Encoding         : 65001

 Date: 05/03/2021 17:01:18
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
-- Records of tb_token
-- ----------------------------
INSERT INTO "public"."tb_token" VALUES ('7263b235-2141-420e-a30b-c141f817cccf', 1614933506, 1614933506, 'f379eaf3c831b04de153469d1bec345e', 1000000000);

-- ----------------------------
-- Primary Key structure for table tb_token
-- ----------------------------
ALTER TABLE "public"."tb_token" ADD CONSTRAINT "tb_user_copy1_pkey" PRIMARY KEY ("user_id");
