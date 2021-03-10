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

 Date: 10/03/2021 13:30:40
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
-- Records of tb_tag
-- ----------------------------
INSERT INTO "public"."tb_tag" VALUES ('11b722e2-a7d9-42b2-92a2-c7eb7920b842', '党史新学@中共一大');
INSERT INTO "public"."tb_tag" VALUES ('e4c5d880-02c5-44ae-a8ed-c58bb6d9721f', '党史新学@中共一大@北京');
INSERT INTO "public"."tb_tag" VALUES ('e12167e3-cdbb-4b74-83a4-3ebd3afab0ac', '党史新学@中共一大@杭州@西溪');
INSERT INTO "public"."tb_tag" VALUES ('8e77f22e-b104-4a36-8b7a-7533e75fc160', '党史新学@中共一大@上海');
INSERT INTO "public"."tb_tag" VALUES ('18473032-d275-4f6a-ad76-e5156acb478f', '改革开放@改革复兴@上海');
INSERT INTO "public"."tb_tag" VALUES ('33fdc470-5d21-435b-8931-e0cfe90d3414', '改革开放@改革复兴@杭州');
INSERT INTO "public"."tb_tag" VALUES ('f5afe8c1-7829-40d5-b9bb-291640309939', '改革开放@改革复兴@北京');

-- ----------------------------
-- Uniques structure for table tb_tag
-- ----------------------------
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "tb_tag_tag_id_key" UNIQUE ("tag_id");
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "tb_tag_tag_name_key" UNIQUE ("tag_name");

-- ----------------------------
-- Primary Key structure for table tb_tag
-- ----------------------------
ALTER TABLE "public"."tb_tag" ADD CONSTRAINT "id" PRIMARY KEY ("tag_id");
