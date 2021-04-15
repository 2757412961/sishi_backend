package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.entity.UserAnswer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author sky
 * @create 2021-03-10 10:18
 */

@Mapper
@Component
public interface UserAnswerDao {

    @Select("SELECT us.user_name, rm.tag_name, us.user_answer_status, us.time \n" +
            "FROM \n" +
            "\t(select * from tb_user_answer where user_name in (select user_name from tb_user where user_id=#{userId})) us \n" +
            "\t\tRIGHT JOIN \n" +
            "\t(select distinct tag_name from tb_tag_resource_map where resource_type = 'tb_question') rm \n" +
            "\t\tON us.tag_name = rm.tag_name \n")
    List<UserAnswer> getUserAnswerByUser(@Param("userId") String userId);

    @Select("select * from tb_user_answer where tag_name=#{tag_name} and user_name=#{user_name}")
    UserAnswer getUserAnswerStatus(@Param("tag_name") String tag_name, @Param("user_name") String user_name);


    @Select("select q.question_id, q.question_content, q.\"optionA\",q.\"optionB\",q.\"optionC\",q.\"optionD\",q.\"optionE\",q.answer, q.is_public from tb_question q, tb_tag_resource_map t where t.tag_name = #{tag_name} and t.resource_type = 'tb_question' and t.resource_id = q.question_id AND (is_public = true ${logicSymbol} 1=1)")
    List<Question> getQuesByTag(@Param(("tag_name")) String tag_name, @Param("logicSymbol") String logicSymbol);


    @Update("update tb_user set score = score+10 where user_name = #{user_name}")
    int updateUserScore(String user_name);


    @Insert("insert into tb_user_answer(user_name,tag_name,user_answer_status,time) values(#{user_name},#{tag_name},1,#{time})")
    int insertUserAnswerStatus(@Param("tag_name") String tag_name, @Param("user_name") String user_name, @Param("time") Date time);


}
