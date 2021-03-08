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

 Date: 08/03/2021 20:07:17
*/


-- ----------------------------
-- Table structure for tb_mapinfo
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_mapinfo";
CREATE TABLE "public"."tb_mapinfo" (
  "map_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "map_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "map_json" text COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" int8
)
;

-- ----------------------------
-- Records of tb_mapinfo
-- ----------------------------
INSERT INTO "public"."tb_mapinfo" VALUES ('3dc0cc03-5ba0-4ef5-b40f-2750d7296932', 'test4', '{}', 1615123939558);
INSERT INTO "public"."tb_mapinfo" VALUES ('3dc4cc03-5b80-4ef5-b40f-275hd7296932', 'test', '{geometries:[{}]}', 1615116610957);

-- ----------------------------
-- Primary Key structure for table tb_mapinfo
-- ----------------------------
ALTER TABLE "public"."tb_mapinfo" ADD CONSTRAINT "tb_map_pkey" PRIMARY KEY ("map_id");
