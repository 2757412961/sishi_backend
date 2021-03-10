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

 Date: 10/03/2021 13:31:01
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
INSERT INTO "public"."tb_mapinfo" VALUES ('bd01478d-510f-4f98-9cc2-0ebaf30a1da1', 'controllerTest', 'djsapfjsdjifgsoigsdfgfsdgsdfgergoisjdgiolrdgsreg{}SDG{DF}SG{DF}G{SDG', 1615345554401);
INSERT INTO "public"."tb_mapinfo" VALUES ('54b02b67-b182-4eb9-9865-fc88b3ffb7f2', 'NamwecontrollerTest333', 'djsapfgsreg{}SDG{DF}SG{DF}G{SDG', 1615353032821);
INSERT INTO "public"."tb_mapinfo" VALUES ('d29c1865-2215-4853-8ecd-5e6e2e67e143', 'NamwecontrollerTest555', 'djsapfgsreg{}SDG{DF}SG{DF}G{SDG', 1615353222248);
INSERT INTO "public"."tb_mapinfo" VALUES ('16694acf-d702-4bd4-85e8-e10156c5f381', '777', 'djsapfgsreg{}SDG{DF}SG{DF}G{SDG', 1615353272742);
INSERT INTO "public"."tb_mapinfo" VALUES ('6ea2c98a-6cc9-4d02-855e-6e3fa5455500', 'NamwecontrollerTest', 'djsapfgsreg{}SDG{DF}SG{DF}G{SDG', 1615352977268);

-- ----------------------------
-- Primary Key structure for table tb_mapinfo
-- ----------------------------
ALTER TABLE "public"."tb_mapinfo" ADD CONSTRAINT "tb_map_pkey" PRIMARY KEY ("map_id");
