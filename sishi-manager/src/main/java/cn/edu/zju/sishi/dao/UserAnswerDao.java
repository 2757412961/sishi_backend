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


    @Select("select * from tb_user_answer where tag_name=#{tag_name} and user_name=#{user_name}")
    UserAnswer getUserAnswerStatus(@Param("tag_name") String tag_name, @Param("user_name") String user_name);


    @Select("select q.question_id, q.question_content, q.\"optionA\",q.\"optionB\",q.\"optionC\",q.\"optionD\",q.\"optionE\",q.answer from tb_question q, tb_tag_resource_map t where t.tag_name = #{tag_name} and t.resource_type = 'tb_question' and t.resource_id = q.question_id")
    List<Question> getQuesByTag (@Param(("tag_name")) String tag_name);


    @Update("update tb_user set score = score+10 where user_name = #{user_name}")
    int  updateUserScore(String user_name);


    @Insert("insert into tb_user_answer(user_name,tag_name,user_answer_status,time) values(#{user_name},#{tag_name},1,#{tag_name})")
    int insertUserAnswerStatus(@Param("tag_name") String tag_name,@Param("user_name") String user_name,@Param("time") Date time);


}
