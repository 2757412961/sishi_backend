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

 Date: 10/03/2021 19:32:28
*/


-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS "public"."tb_article";
CREATE TABLE "public"."tb_article" (
  "article_id" varchar(36) COLLATE "pg_catalog"."default" NOT NULL,
  "article_title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "article_author" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "article_content" text COLLATE "pg_catalog"."default" NOT NULL,
  "publish_time" int8,
  "create_time" int8
)
;
