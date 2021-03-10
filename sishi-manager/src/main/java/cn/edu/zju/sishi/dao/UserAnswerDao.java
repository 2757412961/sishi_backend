package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sky
 * @create 2021-03-10 10:18
 */

@Mapper
@Component
public interface UserAnswerDao {


    @Select("select user_answer_status from tb_user_answer where tag_name=#{tag_name} and user_name=#{user_name}")
    int getUserAnswerStatus(String tag_name, String user_name);


    @Select("select q.question_id, q.question_content, q.options, q.answer from tb_question q, tb_tag_resource_map t where t.tag_name = #{tag_name} and t.resource_type = 'tb_question' and t.resource_id = q.question_id")
    List<Question> getQuesByTag (String tag_name);


    @Update("update tb_question set score = score+10 where user_name = #{user_name}")
    int  updateUserScore(String user_name);


    @Insert("insert into tb_user_answer(user_name,tag_name,user_answer_status) values(#{user_name},#{tag_name},1)")
    int insertUserAnswerStatus(String tag_name, String user_name);


}
