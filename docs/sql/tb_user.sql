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

 Date: 05/03/2021 17:04:15
*/


-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_user";
CREATE TABLE "public"."tb_user" (
                                    "user_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
                                    "create_time" int8,
                                    "update_time" int8,
                                    "password" varchar(60) COLLATE "pg_catalog"."default",
                                    "mobile" varchar(11) COLLATE "pg_catalog"."default",
                                    "email" varchar(50) COLLATE "pg_catalog"."default",
                                    "user_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO "public"."tb_user" VALUES ('7263b235-2141-420e-a30b-c141f817cccf', 1614933506, 1614933506, 'f379eaf3c831b04de153469d1bec345e', '15601623391', NULL, 'user1');

-- ----------------------------
-- Primary Key structure for table tb_user
-- ----------------------------
ALTER TABLE "public"."tb_user" ADD CONSTRAINT "tb_user_pkey" PRIMARY KEY ("user_id");
