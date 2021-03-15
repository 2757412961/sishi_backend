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

 Date: 10/03/2021 19:32:37
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
-- Primary Key structure for table tb_mapinfo
-- ----------------------------
ALTER TABLE "public"."tb_mapinfo" ADD CONSTRAINT "tb_map_pkey" PRIMARY KEY ("map_id");
