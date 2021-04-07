/*
 Navicat Premium Data Transfer

 Source Server         : localhost_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 100013
 Source Host           : localhost:5432
 Source Catalog        : sishi
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100013
 File Encoding         : 65001

 Date: 07/04/2021 19:04:32
*/


-- ----------------------------
-- Table structure for tb_user_answer
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_user_answer";
CREATE TABLE "public"."tb_user_answer" (
  "user_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_answer_status" int4 NOT NULL,
  "time" date
)
;

-- ----------------------------
-- Records of tb_user_answer
-- ----------------------------
INSERT INTO "public"."tb_user_answer" VALUES ('123', '321', 1, '2021-04-07');
INSERT INTO "public"."tb_user_answer" VALUES ('222', '111', 1, '2021-04-28');

-- ----------------------------
-- Foreign Keys structure for table tb_user_answer 【可不要】
-- ----------------------------
ALTER TABLE "public"."tb_user_answer" ADD CONSTRAINT "fk_tag" FOREIGN KEY ("tag_name") REFERENCES "public"."tb_tag" ("tag_name") ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE "public"."tb_user_answer" ADD CONSTRAINT "fk_user" FOREIGN KEY ("user_name") REFERENCES "public"."tb_user" ("user_name") ON DELETE SET NULL ON UPDATE CASCADE;
